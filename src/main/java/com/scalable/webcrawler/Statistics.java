package com.scalable.webcrawler;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Statistics {

	public Map<String, Long> countLibraries(List<String> listOfLibraries) {
		Map<String, Long> counted = GroupingAndCountLibraries(listOfLibraries);

		return printTop5UsedLibraries(counted);
	}

	private Map<String, Long> printTop5UsedLibraries(Map<String, Long> counted) {
		return counted.entrySet().stream().sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue())).limit(5)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	private Map<String, Long> GroupingAndCountLibraries(List<String> listOfLibraries) {
		return listOfLibraries.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
}
