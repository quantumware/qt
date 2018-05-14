package labs.java.qt.resources;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

/**
 * 
 * @author 
 * @since 2018-05-14
 */
public class UrlExtractorTest {
	
	@Test
	public void testNotExisted() {
		JSONObject obj = new UrlExtractor(1).parse("http://notexisted/");
		System.out.println(obj);
		assertNull(obj.get("title"));
	}

	@Test
	public void testGoogle0() {
		JSONObject obj = new UrlExtractor(0).parse("http://www.google.com/");
		JSONArray nodes = (JSONArray) obj.get("nodes");
		assertEquals(0, nodes.size());
	}

	@Test
	public void testGoogle1() {
		JSONObject obj = new UrlExtractor(1).parse("http://www.google.com/");
		assertEquals("Google", obj.get("title"));
		
		JSONArray nodes = (JSONArray) obj.get("nodes");
		assertTrue(nodes.size() > 1);
		
		boolean containsYouTube = false;
		JSONObject node = null;
		Iterator<JSONObject> itr = nodes.iterator();
		while (itr.hasNext()) {
			node = itr.next();
			if ("YouTube".equalsIgnoreCase(node.get("title").toString())) {
				containsYouTube = true;
				break;
			}
			assertEquals(0, ((JSONArray) node.get("nodes")).size());
		}
		
		assertTrue(containsYouTube);
	}

}
