package labs.java.qt.resources;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 
 * @since 2018-05-14
 */
public class UrlExtractor {
	private static final Logger LOG = LoggerFactory.getLogger(UrlExtractor.class);
	
	private int maxDepth;
	public UrlExtractor(int maxDepth) {
		this.maxDepth = (maxDepth == -1) ? Integer.MAX_VALUE : maxDepth;
	}
	public UrlExtractor() {
		this.maxDepth = 2;
	}

    public JSONObject parse(String url) {
    	return parse(url, 0);
    }
    private JSONObject parse(String url, int depth) {
    	LOG.debug("url: " + url + ", depth: " + depth);
    	JSONObject obj = new JSONObject();
    	obj.put("url", url);
    	
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			LOG.error("", e);
		}
		
		if (doc == null) {
			return obj;
		}
		
		obj.put("title", doc.title());
		
		JSONArray nodes = new JSONArray();
		if (depth < maxDepth) {
			String href = "";
			Elements elems = doc.select("a");
			for (Element elem : elems) {
				href = elem.absUrl("href");
				if (!href.isEmpty()) {
					nodes.add(parse(href, depth + 1));
				}
			}
		}
		obj.put("nodes", nodes);
		
		return obj;
    }
}
