package com.brunobasto.ebnf.server;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
public class Launcher {

	public static void main(String[] args) {
		String webappDirLocation = "docroot/";

		Tomcat tomcat = new Tomcat();

		String webPort = "8080";

		tomcat.setPort(Integer.valueOf(webPort));

		String path = new File("./" + webappDirLocation).getAbsolutePath();

		try {
			tomcat.addWebapp("/", path);

			tomcat.start();
		}
		catch (ServletException se) {
			se.printStackTrace();
		}
		catch (LifecycleException le) {
			le.printStackTrace();
		}

		System.out.println("configuring app with basedir: " + path);

		tomcat.getServer().await();
	}

}