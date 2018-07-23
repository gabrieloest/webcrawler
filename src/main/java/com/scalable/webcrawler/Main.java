package com.scalable.webcrawler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	private static final String DOWNLOAD_FOLDER = "download";

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the search term.");
		String searchTerm = scanner.nextLine();
		scanner.close();

		GoogleSearch googleSearch = new GoogleSearch();
		googleSearch.search(searchTerm);

		JSExtractor extractJS = new JSExtractor();
		List<String> listOfLibraries = extractJS.extractListOfLibraries(DOWNLOAD_FOLDER).stream().sorted()
				.collect(Collectors.toList());

		System.out.println("---------- All Libraries ----------");
		listOfLibraries.forEach(System.out::println);

		Statistics statistics = new Statistics();
		Map<String, Long> librariesStatistics = statistics.countLibraries(listOfLibraries);
		System.out.println("---------- Top 5 Libraries ----------");
		librariesStatistics.entrySet().stream().sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue()))
				.forEach(System.out::println);
	}

}
