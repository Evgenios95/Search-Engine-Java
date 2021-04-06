package searchengine;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PagesTest {

    @Test
    void populateIndex_testfileSize () {
        FileReader reader = new FileReader("data/test-file.txt");
        List<WebPage> pageList1 = reader.readFile();
        Map<String, List<WebPage>> indexActual;
        indexActual = Pages.populateIndex(pageList1);
        assertEquals(3,indexActual.size());
    }

    @Test
    void populateIndex_testfileErrorSize () {
        FileReader reader = new FileReader("data/test-file-errors.txt");
        List<WebPage> pageList1 = reader.readFile();
        Map<String, List<WebPage>> indexActual;
        indexActual = Pages.populateIndex(pageList1);
        assertEquals(3,indexActual.size());
    }


    @Test
    void search_singleWord() {
        String searchTerm = "word2";
        Pages page = new Pages("data/test-file.txt");

        List<WebPage> finalQuery = page.search(searchTerm);

        assertEquals(1,finalQuery.size());
    }

    @Test
    void search() {
        Pages pages = new Pages("data/test-file.txt"); //es we want to check the method from
        List<WebPage> outputWebPages = pages.search("word1"); //this is what we want to check the validitz of

        //construct expected results
        List<WebPage> expectedWebPages = new ArrayList<>();
        List<String> keywords1 = new ArrayList<>();
        List<String> keywords2 = new ArrayList<>();
        keywords1.add("word1");
        keywords1.add("word2");
        keywords2.add("word1");
        keywords2.add("word3");
        WebPage webpage1 = new WebPage("*PAGE:http://page2.com", "title2", keywords2);
        WebPage webpage2 = new WebPage("*PAGE:http://page1.com", "title1", keywords1);
        expectedWebPages.add(webpage1);
        expectedWebPages.add(webpage2);

        assertEquals(expectedWebPages.get(0).getTitle(), outputWebPages.get(0).getTitle());
        assertEquals(expectedWebPages.get(1).getTitle(), outputWebPages.get(1).getTitle());
    }
}