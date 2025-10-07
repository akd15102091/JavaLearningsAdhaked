package Revision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

interface HtmlParser {
    List<String> getUrls(String url);
}

@SuppressWarnings("unchecked")
public class Webcrawler {

    private Set<String> visited = ConcurrentHashMap.newKeySet();
    private ExecutorService executorService = Executors.newFixedThreadPool(3) ;
    public Webcrawler(){
    }

    public List<String> crawl(String url, HtmlParser parser){
        String hostname = getHostname(url) ;
        visited.add(url);

        crawlHelper(url, parser, hostname);

        return new ArrayList<>(visited);
        
    }

    public void crawlHelper(String url, HtmlParser parser, String hostName){
        List<String> parsedUrls = parser.getUrls(url) ;
        for(String nextUrl : parsedUrls){
            if(visited.contains(nextUrl) || !getHostname(nextUrl).equals(hostName)){
                continue;
            }
            visited.add(nextUrl) ;
            executorService.submit(() -> crawlHelper(nextUrl, parser, hostName));
        }
    }

    private String getHostname(String url){
        return url.split("/")[2] ;
    }

    public static void main(String[] args) {
        
        HtmlParser htmlParser = new HtmlParser() {
            Map<String, List<String>> urls = Map.of(
                "http://example.com", List.of("http://example.com/a", "http://example.com/b", "http://example.com/c"),
                "http://example.com/a", List.of("http://example.com"),
                "http://example.com/b", List.of("http://example.com/f"),
                "http://example.com/x", List.of("http://example.com/e")
            ) ;

            @Override
            public List<String> getUrls(String url) {
                return urls.getOrDefault(url, Collections.EMPTY_LIST) ;
            }
        };

        Webcrawler crawler = new Webcrawler();
        List<String> result = crawler.crawl("http://example.com", htmlParser);
        System.out.println("Crawled URLs: " + result);

    }
}
