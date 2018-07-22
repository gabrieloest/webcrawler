package com.scalable.webcrawler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleSearchJava
{

    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";


    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the search term.");
        String searchTerm = scanner.nextLine();
        scanner.close();

        GoogleSearchJava.search(searchTerm);

        List<Path> readFilesFromFolder = GoogleSearchJava.readFilesFromFolder();
        Stream<List<String>> map = readFilesFromFolder.stream().map(it -> GoogleSearchJava.readLines(it));
        List<String> listOfLibraries =
            map.flatMap(it -> {
                if (it == null)
                {
                    return null;
                }
                return it.stream();
            }).collect(Collectors.toList());

        ConcurrentHashMap<String, LongAdder> libraryCounts = new ConcurrentHashMap<>();

        listOfLibraries.forEach(library -> {
            if (!libraryCounts.containsKey(library))
            {
                libraryCounts.put(library, new LongAdder());
            }
            libraryCounts.get(library).increment();
        });

        libraryCounts
            .keySet()
            .stream()
            .map(key -> String.format("%-10d %s", libraryCounts.get(key).intValue(), key))
            .sorted((prev, next) -> Integer.compare(Integer.parseInt(next.split("\\s+")[0]), Integer.parseInt(prev.split("\\s+")[0])))
            .limit(5)
            .forEach(t -> System.out.println("\t" + t));
    }


    private static void search(String searchTerm) throws IOException
    {
        String searchURL = GoogleSearchJava.GOOGLE_SEARCH_URL + "?q=" + searchTerm;
        Document doc = Jsoup.connect(searchURL).timeout(5000).userAgent(GoogleSearchJava.USER_AGENT).get();

        Elements results = doc.select("h3.r > a:not([class])");

        Stream<Element> stream =
            StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(results.iterator(), Spliterator.ORDERED),
                false);

        stream.forEach(it -> {
            GoogleSearchJava.downloadPage(it);
        });
    }


    public static void downloadPage(Element element)
    {
        String linkHref = element.attr("href");
        String linkText = element.text();
        Response response;
        try
        {
            response = Jsoup.connect(linkHref).userAgent("Mozilla").execute();
            final Document doc = response.parse();
            final File f = new File("download/" + linkText);
            Files.write(Paths.get(f.getPath() + ".html"), doc.outerHtml().getBytes());
            System.out.println(linkHref);
        }
        catch (IOException e)
        {
            System.err.println("Access denied: " + linkHref);
        }

    }


    private static List<Path> readFilesFromFolder()
    {
        try
        {
            return Files
                .walk(Paths.get("download"))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    private static List<String> readLines(Path path)
    {
        try (Stream<String> stream = Files.lines(path))
        {
            return stream
                .filter(it -> GoogleSearchJava.isJavascriptLibrary(it))
                .map(it -> GoogleSearchJava.extractJsLibrary(it))
                .collect(Collectors.toList());
        }
        catch (Exception e)
        {}
        return null;
    }


    private static String extractJsLibrary(String it)
    {
        int indexOfSrc = it.indexOf("src=\"");
        int indexOfJs = it.lastIndexOf(".js\"");
        String substringSrc = it.substring(indexOfSrc, indexOfJs);
        int lastIndexOfSlash = substringSrc.lastIndexOf("/");
        System.out.println(substringSrc.substring(lastIndexOfSlash + 1));
        return substringSrc.substring(lastIndexOfSlash + 1);
    }


    private static boolean isJavascriptLibrary(String it)
    {
        return it.contains("<script") && it.contains("src=\"") && it.contains(".js");
    }

}
