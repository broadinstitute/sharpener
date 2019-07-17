package sharpener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP {

	static String get(URL url) throws IOException {
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

	static String post(URL url, String json) throws IOException {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(json.getBytes());
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

}
