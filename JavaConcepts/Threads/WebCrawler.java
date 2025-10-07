package Threads;

import java.util.*;
import java.util.concurrent.*;

// Dummy HtmlParser interface
interface HtmlParser {
    List<String> getUrls(String url);
}

public class WebCrawler {
    private final Set<String> visited = ConcurrentHashMap.newKeySet();
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        String hostname = getHostname(startUrl);
        visited.add(startUrl);
        
        crawlHelper(startUrl, htmlParser, hostname);
        
        executor.shutdown();
        return new ArrayList<>(visited);
    }
    
    private void crawlHelper(String url, HtmlParser htmlParser, String hostname) {
        List<String> urls = htmlParser.getUrls(url);
        
        for (String nextUrl : urls) {
            if (visited.contains(nextUrl) || !getHostname(nextUrl).equals(hostname)) {
                continue;
            }
            
            visited.add(nextUrl);
            executor.execute(() -> crawlHelper(nextUrl, htmlParser, hostname));
        }
    }
    
    private String getHostname(String url) {
        return url.split("/")[2];
    }
    
    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        HtmlParser parser = new HtmlParser() {
            private final Map<String, List<String>> webGraph = Map.of(
                "http://example.com", List.of("http://example.com/a", "http://example.com/b", "http://example.com/c"),
                "http://example.com/a", List.of("http://example.com"),
                "http://example.com/b", List.of("http://example.com/f"),
                "http://example.com/c", List.of("http://example.com/e")
            );
            
            @Override
            public List<String> getUrls(String url) {
                return webGraph.getOrDefault(url, Collections.emptyList());
            }
        };
        
        List<String> result = crawler.crawl("http://example.com", parser);
        System.out.println("Crawled URLs: " + result);
    }
}

