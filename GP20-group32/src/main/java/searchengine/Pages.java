package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Pages class  is responsible for taking the webpages and loading them on the searchengine. A Pages object has the state information needed to operate on all the pages
 * in the file. This includes:
 * pageList- a list of all webpage objects in the given file.
 * index- a hashmap containing all the keywords in a webpage (in lowercase) mapped to pages that contain that keyword
 * @see QueryHandler
 */
public class Pages {
    private final List<WebPage> pageList;
    private final Map<String, List<WebPage>> index;

    /**
     * This method is a constructor for the pages object.
     * @param filename - the file from which the info about Webpages is read from.
     */
    public Pages(String filename) {
        FileReader reader = new FileReader(filename);
        pageList = reader.readFile();
        this.index = populateIndex(pageList);
    }

    /**
     *
     * @param searchTerm - the String entered by the user.
     * @return the method returns a list of all the webpages that match the string entered by the user.
     */
    public List<WebPage> search(String searchTerm) {
        QueryHandler query = new QueryHandler(searchTerm, index, pageList);
        List<WebPage> finalQuery = query.getMatchingWebpages(searchTerm);
        return finalQuery;
    }

    /**
     * This method is responsible for creating the reverted index
     * @param pageList - a list of all webpage objects in the given file.
     * @return -  a hashmap containing all the keywords in a webpage (in lowercase) mapped to pages that contain that keyword
     */
    public static Map<String, List<WebPage>> populateIndex(List<WebPage> pageList) {
        Map<String, List<WebPage>> result = new HashMap<>();
        for (var page : pageList) {
            for (String keyword : page.getKeywords()) {
                String word = keyword.toLowerCase();
                List<WebPage> pages = result.get(word);
                if (pages == null) {
                    pages = new ArrayList<>();
                    result.put(word, pages);
                }
                if (!pages.contains(page)) {
                    pages.add(page);
                }
            }
        }
        return Map.copyOf(result);
    }

}
