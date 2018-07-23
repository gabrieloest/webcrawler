package com.scalable.webcrawler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Statistics {

	public void countLibraries(List<String> listOfLibraries) {
		Map<String, Long> counted = GroupingAndCountLibraries(listOfLibraries);

		printTop5UsedLibraries(counted);
	}

	private void printTop5UsedLibraries(Map<String, Long> counted) {
		counted.entrySet().stream().sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue())).limit(5)
				.forEach(System.out::println);
	}

	private Map<String, Long> GroupingAndCountLibraries(List<String> listOfLibraries) {
		return listOfLibraries.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
}
