package labs.java.qt;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import labs.java.qt.resources.QtResource;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import io.dropwizard.setup.Environment;


/**
 * 
 * @author
 * @since 2018-05-14
 */
public class DwApp extends Application<DwConf> {
	private static final Logger LOG = LoggerFactory.getLogger(DwApp.class);

    public static void main(String[] args) throws Exception {
    	new DwApp().run(args);
    }

	@Override
	public void run(DwConf conf, Environment env) throws Exception {
		LOG.info(conf.getAppName());
		env.lifecycle().addServerLifecycleListener(new ServerLifecycleListener() {
		    @Override
		    public void serverStarted(Server server) {
		      for (Connector connector : server.getConnectors()) {
		        if (connector instanceof ServerConnector) {
		          ServerConnector serverConnector = (ServerConnector) connector;
		          System.out.println(serverConnector.getName() + " " + serverConnector.getPort());
		        }
		      }
		    }
		  });
		// Enable CORS headers
	    final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

	    // Configure CORS parameters
	    cors.setInitParameter("allowedOrigins", "*");
	    cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
	    cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

	    // Add URL mapping
	    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	    
		env.healthChecks().register("first check", new FirstCheck());
		env.jersey().register(new QtResource());
	}

}
