package com.brunobasto.ebnf.server;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;
public class Launcher {

	public static void main(String[] args) {
		int port = 80;

		if (args.length > 0) {
			port = Integer.valueOf(args[0]);
		}

		String webappDirLocation = "docroot/";

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(port);
		tomcat.setBaseDir(new File("tomcat").getAbsolutePath());

		Connector connector = this.current.getConnector();
		connector.setProperty(
			"compressableMimeType", "text/html,text/xml, text/css," +
			"application/json, application/javascript");
		connector.setProperty("compression", "on");
		connector.setProperty("compressionMinSize", "1024");
		connector.setProperty("noCompressionUserAgents", "gozilla, traviata");

		String path = new File(webappDirLocation).getAbsolutePath();

		try {
			StandardContext ctx = (StandardContext)tomcat.addWebapp("/", path);

			VirtualDirContext resources = new VirtualDirContext();

			resources.setExtraResourcePaths(
				"/WEB-INF/classes=" + new File("classes")
			);

			ctx.setResources(resources);

			tomcat.start();
		}
		catch (ServletException se) {
			se.printStackTrace();
		}
		catch (LifecycleException le) {
			le.printStackTrace();
		}

		tomcat.getServer().await();
	}

}