package searchengine;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileReaderTest {

    @Test
    void readFile_EqualWebsite1() {
        List<WebPage> webPages = new FileReader("data/test-file.txt").readFile();
        assertEquals(2, webPages.size());

        var page =webPages.get(0);

        assertEquals("http://page1.com",page.getUrl());
        assertEquals("title1",page.getTitle());
        assertEquals(List.of("word1", "word2"),page.getKeywords());

        var page1 =webPages.get(1);
        assertEquals("http://page2.com",page1.getUrl());
        assertEquals("title2",page1.getTitle());
        assertEquals(List.of("word1", "word3"),page1.getKeywords());
    }

    @Test
    void readFile_fileError1() {
        List<WebPage> webPages = new FileReader("data/test-file-errors.txt").readFile();
        assertEquals(2, webPages.size());


        var page =webPages.get(0);

        assertEquals("http://page1.com",page.getUrl());
        assertEquals("title1",page.getTitle());
        assertEquals(List.of("word1", "word2"),page.getKeywords());

        var page1 =webPages.get(1);
        assertEquals("http://page2.com",page1.getUrl());
        assertEquals("title2",page1.getTitle());
        assertEquals(List.of("word1", "word3"),page1.getKeywords());
    }
}