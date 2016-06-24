package com.samplewiki.test;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class is a test application class for Wiki communication using JSoup
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
	 * The constant to point to <p> tag
	 */
	private static final String P = "p";
	/**
	 * The constant variable for SPACE character
	 */
	private static final String SPACE = " ";
	/**
	 * The constant variable to Under Score character
	 */
	private static final String UNDER_SCORE = "_";
	/**
	 * The constant variable for No results found.
	 */
	private static final String NO_RESULTS_FOUND = "No results found.";
	/**
	 * The Wiki base URL
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
		try {
			// JSoup invokes the Wiki URL with the query string and gets the response
			Document doc = Jsoup.connect(WIKI_URL + query).get();
			// JSoup Document API method returns the list of all HTML <p> tags
			Elements paragraphs = doc.select(P);
			// JSoup Elements API method which returns the first element
			Element firstParagraph = paragraphs.first();
			// JSoup Element API method which returns the text content of the element
			String contentFirstParagraph = firstParagraph.text();
			// Inserting line space after every period.
			contentFirstParagraph = contentFirstParagraph.replaceAll("\\. ", "\\. \n");
			return contentFirstParagraph;
		} catch (HttpStatusException he) {
			// If no records found, JSoup Connect method throws exception HttpStatusException
			return NO_RESULTS_FOUND;
		}
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
		// Accepts the query from the command line
		query = scanner.nextLine();
		// Code replaces the <SPACE> with under score
		query = query.trim().replace(SPACE, UNDER_SCORE);
		return query;
	}
}
