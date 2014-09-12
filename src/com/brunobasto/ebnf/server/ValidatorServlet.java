package com.brunobasto.ebnf.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(
	name = "ValidatorServlet", urlPatterns = {"/hello"}
)
public class ValidatorServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		ServletOutputStream out = resp.getOutputStream();
		out.write("hello heroku".getBytes());
		out.flush();
		out.close();
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}