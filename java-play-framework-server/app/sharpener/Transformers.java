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
import apimodels.GeneInfo;
import apimodels.TransformerInfo;
import apimodels.TransformerQuery;

public class Transformers {

	private static Map<String, TransformerInfo> transformers;

	private static Map<String, String> urls;

	private static ObjectMapper mapper = new ObjectMapper();

	public static ArrayList<TransformerInfo> getTransformers() throws IOException {
		ArrayList<TransformerInfo> transformers = new ArrayList<TransformerInfo>();
		Map<String, TransformerInfo> transformerMap = new HashMap<String, TransformerInfo>();
		Map<String, String> urlMap = new HashMap<String, String>();
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
		updateMaps(transformerMap, urlMap);
		return transformers;
	}

	synchronized static void updateMaps(Map<String, TransformerInfo> transformerMap, Map<String, String> urlMap) {
		Transformers.transformers = transformerMap;
		Transformers.urls = urlMap;
	}

	public static GeneList transform(TransformerQuery query) throws IOException {
		String transformerName = query.getName();
		URL url = new URL(urls.get(transformerName) + "/transform");
		String json = mapper.writeValueAsString(new Query(query));
		String res = HTTP.post(url, json);
		GeneInfo[] genes = mapper.readValue(res, GeneInfo[].class);
		for (GeneInfo gene : genes) {
			MyGene.Info.addInfo(gene);
		}
		return GeneLists.createList(genes);
	}

	static class Query {
		private List<GeneInfo> genes;
		private List<Property> controls;

		Query(TransformerQuery query) {
			genes = GeneLists.getList(query.getGeneListId()).getGenes();
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
