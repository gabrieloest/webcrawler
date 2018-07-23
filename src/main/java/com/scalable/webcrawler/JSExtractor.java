package com.scalable.webcrawler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class JSExtractor {

	private static final String DOWNLOAD_FOLDER = "download";

	public List<String> extractListOfLibraries() {
		return readFilesFromFolder().stream().map(it -> readLines(it)).flatMap(it -> {
			if (it == null) {
				return null;
			}
			return it.stream();
		}).collect(Collectors.toList());
	}

	private List<Path> readFilesFromFolder() {
		try {
			return Files.walk(Paths.get(DOWNLOAD_FOLDER)).filter(Files::isRegularFile).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<String> readLines(Path path) {
		try {
			return Files.lines(path).filter(it -> containsJavascriptLibrary(it)).map(it -> extractJsLibrary(it))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String extractJsLibrary(String it) {
		String src = extractSource(it);
		return extractLibrary(src);
	}

	private String extractLibrary(String src) {
		int lastIndexOfSlash = src.lastIndexOf("/");
		String substring = src.substring(lastIndexOfSlash + 1);
		return substring + ".js";
	}

	private String extractSource(String it) {
		int indexOfSrc = it.indexOf("src=\"");
		int indexOfJs = it.lastIndexOf(".js");
		return it.substring(indexOfSrc, indexOfJs);
	}

	private boolean containsJavascriptLibrary(String it) {
		return it.contains("<script") && it.contains("src=\"") && it.contains(".js");
	}

}
