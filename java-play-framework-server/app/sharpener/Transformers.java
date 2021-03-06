package sharpener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import apimodels.GeneList;
import apimodels.Property;
import apimodels.Attribute;
import apimodels.GeneInfo;
import apimodels.TransformerInfo;
import apimodels.TransformerInfoProperties;
import apimodels.TransformerQuery;
import play.Logger;

public class Transformers {

	private static final TransformerInfo.FunctionEnum PRODUCER = TransformerInfo.FunctionEnum.PRODUCER;
	private static final TransformerInfo.StatusEnum ONLINE = TransformerInfo.StatusEnum.ONLINE;
	private static final TransformerInfo.StatusEnum OFFLINE = TransformerInfo.StatusEnum.OFFLINE;

	/**
	 * Maps transformer's URL to TransformerInfo object
	 */
	private static Map<String, TransformerInfo> transformers;

	/**
	 * Maps transformer's name to transformer's URL 
	 */
	private static Map<String, String> urls = new HashMap<>();

	private static ObjectMapper mapper = new ObjectMapper();
	
	static {
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}


	private static Map<String,TransformerInfo> internalTransformers = loadInternalTransformers();
	
	private static Map<String,TransformerInfo> loadInternalTransformers() {
		HashMap<String,TransformerInfo> map = new LinkedHashMap<String,TransformerInfo>();
		try {
			String json = new String(Files.readAllBytes(Paths.get("transformer_info.json")));
			TransformerInfo[] transformers = mapper.readValue(json, TransformerInfo[].class);
			for (TransformerInfo transformer : transformers) {
				map.put(transformer.getName(), transformer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * Implement /transformers API endpoint
	 * 
	 * @return
	 */
	public static ArrayList<TransformerInfo> getTransformers() {
		ArrayList<TransformerInfo> transformers = new ArrayList<TransformerInfo>();
		for (Map.Entry<String,TransformerInfo> entry : internalTransformers.entrySet()) {
			transformers.add(entry.getValue());
		}
		Map<String,TransformerInfo> transformerMap = new HashMap<String,TransformerInfo>();
		Map<String,String> urlMap = new HashMap<String,String>();
		try {
			BufferedReader transformerFile = new BufferedReader(new FileReader("transformers.txt"));
			for (String baseURL = transformerFile.readLine(); baseURL != null; baseURL = transformerFile.readLine()) {
				TransformerInfo info = null;
				try {
					URL url = new URL(baseURL + "/transformer_info");
					info = mapper.readValue(HTTP.get(url), TransformerInfo.class);
					info.setStatus(ONLINE);
					if (info.getLabel() == null || info.getLabel().length() == 0) {
						info.setLabel(info.getName().split(" ")[0]);
					}
					if (info.getProperties() == null) {
						info.setProperties(new TransformerInfoProperties()
							.listPredicate("related_to").memberPredicate("related_to"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (info == null) {
					info = Transformers.transformers.get(baseURL);
					if (info != null) {
						info.setStatus(OFFLINE);
					}
				}
				if (info != null) {
					transformers.add(info);
					info.setUrl(baseURL);
					urlMap.put(info.getName(), baseURL);
					transformerMap.put(baseURL, info);
				}
			}
			transformerFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateMaps(transformerMap, urlMap);
		return transformers;
	}


	private synchronized static void updateMaps(Map<String, TransformerInfo> transformerMap, Map<String, String> urlMap) {
		Transformers.transformers = transformerMap;
		Transformers.urls = urlMap;
	}


	private synchronized static String getURL(String transformerName) {
		return urls.get(transformerName);
	}


	private synchronized static TransformerInfo.FunctionEnum getFunction(String baseURL) {
		TransformerInfo transformer = transformers.get(baseURL);
		if (transformer == null) {
			return null;
		}
		return transformer.getFunction();
	}


	/**
	 * Implement /transform API endpoint
	 * 
	 * @param query
	 * @return
	 */
	public static GeneList transform(TransformerQuery query) {
		String transformerName = query.getName();
		String baseURL = getURL(transformerName);
		if (baseURL == null) {
			return GeneLists.error("unknown transformer: '" + transformerName + "'", transformerName);
		}
		if (GeneLists.findGeneList(query.getGeneListId()) == null && !PRODUCER.equals(getFunction(baseURL))) {
			return GeneLists.error("gene list " + query.getGeneListId() + " not found", transformerName);
		}
		GeneInfo[] genes = new GeneInfo[0];
		try {
			URL url = new URL(baseURL + "/transform");
			String json = mapper.writeValueAsString(new Query(query));
			String res = HTTP.post(url, json);
			genes = mapper.readValue(res, GeneInfo[].class);
		} catch (IOException e) {
			return GeneLists.error(transformerName + "(" + baseURL + "/transform) failed: " + e.getMessage(), transformerName);
		}

		for (GeneInfo gene : genes) {
			try {
				addSource(gene, transformerName);
				MyGene.Info.addInfo(gene);
			} catch (Exception e) {
				Logger.warn("failed to obtaing myGene.info attributes for " + gene.getGeneId());
			}
		}
		GeneList geneList = GeneLists.createList(genes);
		geneList.setSource(transformerName);
		for (Property property : query.getControls()) {
			geneList.addAttributesItem(new Attribute().name(property.getName()).source(transformerName).value(property.getValue()));
		}
		if (geneList.getAttributes() == null) {
			geneList.setAttributes(GeneLists.NO_ATTRIBUTES);
		}
		return geneList;
	}


	private static void addSource(GeneInfo gene, String source) {
		if (gene.getSource() == null) {
			gene.setSource(source);			
		}
		for (Attribute attribute : gene.getAttributes()) {
			if (attribute.getSource() == null) {
				attribute.setSource(source);
			}
		}
	}


	static class Query {
		private List<GeneInfo> genes = new ArrayList<GeneInfo>();
		private List<Property> controls;


		Query(TransformerQuery query) {
			GeneList geneList = GeneLists.findGeneList(query.getGeneListId());
			if (geneList != null) {
				genes = geneList.getGenes();
			}
			controls = query.getControls();
		}

		public List<GeneInfo> getGenes() {
			return genes;
		}

		public List<Property> getControls() {
			return controls;
		}
	}
}
