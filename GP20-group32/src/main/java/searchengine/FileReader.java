package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Filereader is responsible for reading the data file and feeding it  into the Webpage class.
 */
public class FileReader {

    private final String filename;

    public FileReader(String filename) {
        this.filename = filename;
    }

    /**
     * This method reads the given file, sorts it into elements of lines that start with PAGE.
     * It compares the line after the url to the end of the url to identify the titles. In addition it checks that the page has at least one
     * keyword. If both conditions are satisfied the info has buildwebpage method called on it   and  Webpage objects are created.
     * @return List of Webpages that satisfy the conditions of a valid webpage.
     */
    public List<WebPage> readFile() {
        final List<List<String>> pages = new ArrayList<>();

        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get(filename));
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        var lastIndex = lines.size();
        for (var i = lines.size() - 1; i >= 0; --i) {
            if (lines.get(i).startsWith("*PAGE")) {
                pages.add(lines.subList(i, lastIndex));
                lastIndex = i;
            }
        }
        Collections.reverse(pages);
        List<WebPage> webPages = new ArrayList<>();
        for (List<String> page : pages) {
            WebPage webPage = buildWebpage(page);
            if (webPage != null) {
                webPages.add(webPage);
            }
        }
        return webPages;
    }

    /**
     *  This method is responsible of creating Webpage objects with an url, title and keywords.
     * @param  page - elements of lines that start with PAGE
     * @return new Webpage objects.
     */
    private WebPage buildWebpage(List<String> page) {
        if (page.size() < 3) {
            return null;
        }

        String url = page.get(0);
        if(!url.startsWith("*PAGE:")) {
            return null;
        }

        url = url.replace("*PAGE:", "");

        String title = page.get(1);
        List<String> keywords = new ArrayList<>();

        for (int i=2; i<page.size(); i++) {
            keywords.add(page.get(i));
        }

        return new WebPage(url, title, keywords);
    }
}
