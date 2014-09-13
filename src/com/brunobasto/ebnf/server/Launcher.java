package com.brunobasto.ebnf.server;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;
public class Launcher {

	public static void main(String[] args) {
		String webappDirLocation = "docroot/";

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(1234);
		tomcat.setBaseDir(new File("tomcat").getAbsolutePath());

		String path = new File(webappDirLocation).getAbsolutePath();

		try {
			StandardContext ctx = (StandardContext)tomcat.addWebapp("/", path);

			VirtualDirContext resources = new VirtualDirContext();

			resources.setExtraResourcePaths(
				"/WEB-INF/classes=" + new File("classes")
			);

			ctx.setResources(resources);

//			tomcat.start();
		}
		catch (ServletException se) {
			se.printStackTrace();
		}
//		catch (LifecycleException le) {
//			le.printStackTrace();
//		}

		tomcat.getServer().await();
	}

}