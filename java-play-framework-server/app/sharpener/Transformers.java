package sharpener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import apimodels.GeneList;
import apimodels.Property;
import apimodels.Attribute;
import apimodels.GeneInfo;
import apimodels.TransformerInfo;
import apimodels.TransformerQuery;
import play.Logger;

public class Transformers {

	private static Map<String, TransformerInfo> transformers;

	private static Map<String, String> urls = new HashMap<>();

	private static ObjectMapper mapper = new ObjectMapper();


	/**
	 * Implement /transformers API endpoint
	 * 
	 * @return
	 */
	public static ArrayList<TransformerInfo> getTransformers() {
		ArrayList<TransformerInfo> transformers = new ArrayList<TransformerInfo>();
		Map<String, TransformerInfo> transformerMap = new HashMap<String, TransformerInfo>();
		Map<String, String> urlMap = new HashMap<String, String>();
		try {
			BufferedReader transformerFile = new BufferedReader(new FileReader("transformers.txt"));
			for (String line = transformerFile.readLine(); line != null; line = transformerFile.readLine()) {
				try {
					URL url = new URL(line + "/transformer_info");
					TransformerInfo info = mapper.readValue(HTTP.get(url), TransformerInfo.class);
					transformers.add(info);
					urlMap.put(info.getName(), line);
					transformerMap.put(info.getName(), info);
				} catch (Exception e) {
					e.printStackTrace();
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


	private synchronized static String getFunction(String transformerName) {
		TransformerInfo transformer = transformers.get(transformerName);
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
			return GeneLists.error("unknown transformer: '" + transformerName + "'");
		}
		if (GeneLists.getGeneList(query.getGeneListId()) == null && !"producer".equals(getFunction(transformerName))) {
			return GeneLists.error("gene list " + query.getGeneListId() + " not found");
		}
		GeneInfo[] genes = new GeneInfo[0];
		try {
			URL url = new URL(baseURL + "/transform");
			String json = mapper.writeValueAsString(new Query(query));
			String res = HTTP.post(url, json);
			genes = mapper.readValue(res, GeneInfo[].class);
		} catch (IOException e) {
			return GeneLists.error(transformerName + "(" + baseURL + "/transform) failed: " + e.getMessage());
		}

		for (GeneInfo gene : genes) {
			try {
				addSource(gene, transformerName);
				MyGene.Info.addInfo(gene);
			} catch (Exception e) {
				Logger.warn("failed to obtaing myGene.info attributes for " + gene.getGeneId());
			}
		}
		return GeneLists.createList(genes);
	}


	private static void addSource(GeneInfo gene, String source) {
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
			GeneList geneList = GeneLists.getGeneList(query.getGeneListId());
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
