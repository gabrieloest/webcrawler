package com.scalable.webcrawler;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the search term.");
		String searchTerm = scanner.nextLine();
		scanner.close();

		GoogleSearch googleSearch = new GoogleSearch();
		googleSearch.search(searchTerm);

		JSExtractor extractJS = new JSExtractor();
		List<String> listOfLibraries = extractJS.extractListOfLibraries().stream().sorted()
				.collect(Collectors.toList());

		listOfLibraries.forEach(System.out::println);

		Statistics statistics = new Statistics();
		statistics.countLibraries(listOfLibraries);
	}

}
