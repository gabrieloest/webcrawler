package com.scalable.webcrawler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleSearch {

	private static final String PAGE_NAME = "page";

	private static final String DOWNLOAD_FOLDER = "download";

	private static final String RESULT_LINK = "h3.r > a:not([class])";

	private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

	private static int counter = 1;

	public void search(String searchTerm) throws IOException {
		cleanDownloadFolder();

		String searchURL = GOOGLE_SEARCH_URL + "?q=" + searchTerm;
		Document doc = Jsoup.connect(searchURL).timeout(5000).userAgent(USER_AGENT).get();

		Elements results = doc.select(RESULT_LINK);

		Stream<Element> stream = StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(results.iterator(), Spliterator.ORDERED), false);

		System.out.println("---------- Search Result ----------");
		stream.forEach(it -> {
			downloadPage(it);
		});
	}

	private void downloadPage(Element element) {
		String linkHref = element.attr("href");
		try {
			Response response = Jsoup.connect(linkHref).userAgent(USER_AGENT).execute();
			final Document doc = response.parse();
			final File f = new File(getFileName());
			Files.write(Paths.get(f.getPath() + ".html"), doc.outerHtml().getBytes());
			System.out.println(linkHref);
		} catch (IOException e) {
			System.err.println("Access denied: " + linkHref);
		}
	}

	private String getFileName() {
		return DOWNLOAD_FOLDER.concat("/").concat(PAGE_NAME).concat(String.valueOf(counter++));
	}

	private void cleanDownloadFolder() {
		try {
			List<Path> paths = Files.walk(Paths.get(DOWNLOAD_FOLDER)).filter(Files::isRegularFile)
					.collect(Collectors.toList());
			for (Path path : paths) {
				Files.delete(path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
