package labs.java.qt.resources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import labs.java.qt.resources.UrlExtractor;

/**
 * 
 * @author
 * @since 2018-05-14
 */
@Path("/qt")
public class QtResource {
	private static final Logger LOG = LoggerFactory.getLogger(QtResource.class);
	
    private static final ExecutorService ES = 
    		Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);
    
	@Path("/crawl")
	@Produces(MediaType.APPLICATION_JSON)
    @GET
	public void getJSON(@Suspended final AsyncResponse ar, 
			@QueryParam("url") String url, @QueryParam("maxDepth") Integer maxDepth) {
		ES.submit(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				JSONObject obj = maxDepth == null ? new UrlExtractor().parse(url)
						: new UrlExtractor(maxDepth).parse(url);
				ar.resume(obj);
				LOG.debug("Crawling " + url + " at maxDepth " + maxDepth + " took " 
						+ (System.currentTimeMillis() - startTime) + "ms");
			}
		});
	}

}
