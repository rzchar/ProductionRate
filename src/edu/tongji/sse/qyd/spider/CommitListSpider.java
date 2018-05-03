package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.ConnectionAssistant;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class CommitListSpider {

	private int pagesCount = 0;

	private static String testString =
		"<https://api.github.com/repositories/32935745/commits?page=6>; rel=\"next\","
			+ " <https://api.github.com/repositories/32935745/commits?page=204>; rel=\"last\","
			+ " <https://api.github.com/repositories/32935745/commits?page=1>; rel=\"first\","
			+ " <https://api.github.com/repositories/32935745/commits?page=4>; rel=\"prev\"";

	Pattern nextLinkPattern = Pattern.compile("\\s<(.*)>;\\sref=\"next\"");

	Set<String> urls = new HashSet<>();

	private String getNextLink(String[] links) {
		String nextLink = "";

		return nextLink;

	}

	private String getCommitListFromRequest(String urlString) {
		String responseContent = "";

		try {
			URL url = new URL(urlString);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			ConnectionAssistant.addAuthority(connection);
			connection.connect();
			//connection.getInputStream();

			System.out.println("response code: " + connection.getResponseCode());
			System.out.println("x-ratelimit-remaining: " + connection.getHeaderField("x-ratelimit-remaining"));

			//get next page
			String[] links = connection.getHeaderField("Link").split(",");
			String next = "";
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));


			String line = null;
			while ((line = br.readLine()) != null) {
				responseContent += line + "\n";
			}
			//System.out.println(responseContent);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseContent;
	}

	public static void main(String[] args) {

	}
}
