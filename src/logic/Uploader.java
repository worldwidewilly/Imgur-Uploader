package logic;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Uploader {

	// Client id
	private static final String CLIENT_ID = "Insert client id here";

	public static String upload(String imagePath) throws IOException, ParseException {

		final URL UPLOAD_URL = new URL("https://api.imgur.com/3/image");
		HttpURLConnection conn = (HttpURLConnection) UPLOAD_URL.openConnection();

		// create base64 image
		BufferedImage image = null;
		File file = new File(imagePath);
		// read image
		image = ImageIO.read(file);
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ImageIO.write(image, "png", byteArray);
		byte[] byteImage = byteArray.toByteArray();
		String dataImage = Base64.getEncoder().encodeToString(byteImage);
		String data =
				URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(dataImage, "UTF-8");

		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Client-ID " + CLIENT_ID);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		conn.connect();
		StringBuilder stb = new StringBuilder();
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			stb.append(line).append("\n");
		}
		wr.close();
		rd.close();

		JSONParser responseParser = new JSONParser();
		Map response = (Map) responseParser.parse(stb.toString());
		Iterator iter = response.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (entry.getKey().toString().equals("data")) {
				JSONParser parser = new JSONParser();
				Map json = (Map) parser.parse(entry.getValue().toString());
				Iterator jsonIter = json.entrySet().iterator();
				while (jsonIter.hasNext()) {
					Map.Entry jsonEntry = (Map.Entry) jsonIter.next();
					if (jsonEntry.getKey().equals("link")) {
						System.out.println("link: " + jsonEntry.getValue().toString());
						return jsonEntry.getValue().toString();
					}
				}
			}
		}
		return "Failed to get link.";
	}
}
