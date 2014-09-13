package com.brunobasto.ebnf.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.io.FileUtil;

import org.antlr.v4.Tool;

import org.json.JSONArray;
@WebServlet(
	name = "ValidatorServlet", urlPatterns = {"/validate-grammar"}
)
public class ValidatorServlet extends HttpServlet {

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String grammar = request.getParameter("grammar");

		ServletOutputStream out = response.getOutputStream();

		if (grammar != null) {
			JSONArray errorsJSONArray = processGrammar(grammar);

			out.write(errorsJSONArray.toString().getBytes());
		}

		out.flush();
		out.close();
	}

	private JSONArray processGrammar(String grammar) {
		ByteArrayOutputStream errorsByteArray = new ByteArrayOutputStream();

		PrintStream origError = System.err;
		PrintStream antlrError = new PrintStream(errorsByteArray);

		String tempDirSufix = String.valueOf((new Date()).getTime());

		String result = "";

		try {
			File tempDir = FileUtil.createTempDirectory(
				"grammar", tempDirSufix);

			File grammarFile = new File(
				tempDir.getAbsolutePath() + "/grammar.g4");

			FileUtil.writeString(grammarFile, grammar);

			Tool antlr = new Tool(new String[] {
				grammarFile.getAbsolutePath()
			});

			System.setErr(antlrError);

			antlr.processGrammarsOnCommandLine();

			result = errorsByteArray.toString();

			result = result.replaceAll(grammarFile.getAbsolutePath(), "");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.setErr(origError);
		}

		JSONArray jsonArray = new JSONArray();

		for (String line : result.split("\n")) {
			jsonArray.put(line);
		}

		return jsonArray;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}