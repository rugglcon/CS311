import java.util.*;
import java.net.*;
import java.io.*;

/**
 * @author Connor Ruggles
 */
public class WikiCrawler {
	private int maxPages;
	private String writeTo;
	private String start;
	public static final String BASE_URL = "https://en.wikipedia.org";
	public static final int MAX_REQUESTS = 100;

	public WikiCrawler(String seedUrl, int max, String fileName) {
		start = seedUrl;
		maxPages = max;
		writeTo = fileName;
	}

	public ArrayList<String> extractLinks(String doc)
			throws MalformedURLException, IOException {
		ArrayList<String> list = new ArrayList<String>();
		String docTwo = doc.substring(doc.indexOf("<p>"));
		String[] links = docTwo.split("href=\"");
		for(int i = 0; i < links.length; i++) {
			if(links[i].startsWith("/wiki/")) {
				String line = links[i].substring(0, links[i].indexOf("\""));
				if(!line.contains(":") &&
					(!line.contains("#")) &&
					(!list.contains(line))) {
					list.add(line);
				}
			}
		}

		return list;
	}

	public void crawl() throws IOException, InterruptedException {
		FileWriter bfs = new FileWriter(new File(writeTo));
		HashSet<String> v = new HashSet<String>();
		LinkedList<String> q = new LinkedList<String>();
		String write = "";
		write += maxPages + "\n";
		v.add(start);
		q.add(start);
		int numRequests = 0;
		int pages = 1;
		while(!q.isEmpty()) {
			String currentPage = q.removeFirst();
			URL url = new URL(BASE_URL + currentPage);
			InputStream is = url.openStream();
			numRequests++;
			if(numRequests == MAX_REQUESTS + 1) {
				numRequests = 0;
				System.out.println("waiting...");
				Thread.sleep(3000);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			String page = "";
			while(line != null) {
				page += line;
				line = br.readLine();
			}
			ArrayList<String> links = extractLinks(page);

			for(String u : links) {
				if(pages < maxPages) {
					if(!v.contains(u)) {
						pages++;
						if(!u.equals(start)) {
							q.add(u);
						}
						v.add(u);
						write += currentPage + " " + u + "\n";
					}
				} else {
					if(v.contains(u) && !currentPage.equals(u)) {
						write += currentPage + " " + u + "\n";
					}
				}
			}
			br.close();
		}

		bfs.write(write);
		bfs.close();
	}
}