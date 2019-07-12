package sharpener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import apimodels.TransformerInfo;

public class Transformers {

	private static Map<String, TransformerInfo> transformers;

	private static Map<String, String> urls;
	
	private static ObjectMapper mapper = new ObjectMapper();

	private static String get(URL url) throws IOException {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		int responsecode = con.getResponseCode();
		if (responsecode != 200)
			throw new IOException("WARNING: Failed connection (" + url + "): " + responsecode);
		BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder response = new StringBuilder();
		for (String line = input.readLine(); line != null; line = input.readLine()) {
			response.append(line).append('\n');
		}
		return response.toString();
	}

	public static ArrayList<TransformerInfo> getTransformers() throws IOException {
		ArrayList<TransformerInfo> transformers = new ArrayList<TransformerInfo>();
		Map<String, TransformerInfo> transformerMap = new HashMap<String, TransformerInfo>();
		Map<String, String> urlMap = new HashMap<String, String>();
		BufferedReader transformerFile = new BufferedReader(new FileReader("transformers.txt"));
		for (String line = transformerFile.readLine(); line != null; line = transformerFile.readLine()) {
			try {
				URL url = new URL(line + "/transformer_info");	
				TransformerInfo info = mapper.readValue(get(url), TransformerInfo.class);
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

}
