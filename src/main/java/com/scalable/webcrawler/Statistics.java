package com.scalable.webcrawler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Statistics {

	public static void countLibraries(List<String> listOfLibraries) {
		Map<String, Long> counted = listOfLibraries.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		counted.entrySet().stream().sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue())).limit(5)
				.forEach(System.out::println);
	}
}
