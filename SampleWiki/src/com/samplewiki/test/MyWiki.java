package com.samplewiki.test;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class is a test application class for Wiki communication
 * 
 * @author Rais Nazim
 * 
 */
public class MyWiki {

	/**
	 * Scanner instance for accepting user input from command line
	 */
	private static Scanner scanner;
	/**
	 * The Wiki URL
	 */
	private static final String WIKI_URL = "https://en.wikipedia.org/wiki/";

	/**
	 * This is the main method which accepts the query data from the end user
	 * and invokes the Wiki URL with the search Query and displays the results
	 * in the command window.
	 * 
	 * 
	 * @param hh
	 * @throws IOException
	 */
	public static void main(String[] hh) throws IOException {
		System.out.println("Welcome to MyWiki Application");
		System.out.println("*****************************");
		String query = "";
		if (hh != null && hh.length > 0) {
			for (String h : hh) {
				query = query + h + "_";
			}
			if (query.endsWith("_")) {
				query = query.substring(0, query.length() - 1);
			}
		} else {
			scanner = new Scanner(System.in);
			do {
				query = acceptQueryAndCreateWikiSearchParam();
			} while (StringUtils.isBlank(query));

		}
		System.out.println("Query entered is " + query);
		String result = queryWikiAndExtractResponse(query);
		System.out.println("Result for your search.");
		System.out.println("***********************");
		System.out.println(result);
	}

	/**
	 * This operation invokes the Wiki URL with the query parameter and returns
	 * the first paragraph as String
	 * 
	 * @param query
	 * @return firstParagraph
	 * @throws IOException
	 */
	private static String queryWikiAndExtractResponse(String query) throws IOException {
		// JSoup invokes the Wiki URL with the query string and gets the
		// response
		Document doc = Jsoup.connect(WIKI_URL + query).get();
		Elements paragraphs = doc.select("p");
		Element firstParagraph = paragraphs.first();
		String contentFirstParagraph = firstParagraph.text();
		contentFirstParagraph = contentFirstParagraph.replaceAll("\\. ", "\\. \n");
		return contentFirstParagraph;
	}

	/**
	 * This operation accepts the user query from command line It also converts
	 * the spaces to under scores for the Wiki search.
	 * 
	 * @return
	 */
	private static String acceptQueryAndCreateWikiSearchParam() {
		String query;
		System.out.print("Enter the query string : ");
		query = scanner.nextLine();
		query = query.trim().replace(" ", "_");
		return query;
	}
}
