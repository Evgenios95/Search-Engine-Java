package searchengine;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebPageTest {

    @Test
    void isEmpty_twoKeywords_false() {
        WebPage page = new WebPage("www.google.com","Google", List.of("Abc","Def"));
        assertFalse(page.isEmpty());
    }

    @Test
    void testingtheFinalScoreGetter(){
        WebPage page = new WebPage("www.google.com","Google", List.of("Abc","Def"));
        assertEquals(0.0, page.getfinalScore());
    }

    @Test
    void justTestingTheSetter(){
        WebPage page = new WebPage("www.google.com","Google", List.of("Abc","Def"));
        page.setfinalScore(3.25);
        assertEquals(3.25, page.getfinalScore());
    }

    @Test
    void justTestingAGetter_theTitleOne() {
        WebPage page = new WebPage("www.google.com","Google", List.of("Abc","Def"));
        List <String> listToCompare = new ArrayList<>();
        listToCompare.add("Abc");
        listToCompare.add("Def");
        assertEquals(page.getKeywords(), listToCompare);
    }


    @Test
    void justTestingAGetter() {
        WebPage page = new WebPage("www.google.com","Google", List.of("Abc","Def"));
        assertEquals(page.getTitle(), "Google");
    }

    @Test
    void isEmpty_noKeywords_true() {
        WebPage page = new WebPage("www.google.com","Google", List.of());
        assertTrue(page.isEmpty());
    }

    @Test
    void testEquals_caseSensitive_no() {
        WebPage page1 = new WebPage("www.google.com","Google", List.of());
        WebPage page2 = new WebPage("WWW.google.com","Google", List.of("Abc","Def"));
        assertFalse(page1.equals(page2));
    }

    @Test
    @Disabled("for case insensitive")
    void testEquals_caseSensitive_yes() {
        WebPage page1 = new WebPage("www.google.com","Google", List.of());
        WebPage page2 = new WebPage("WWW.google.com","Google", List.of("Abc","Def"));
        assertTrue(page1.equals(page2));
    }

    @Test
    void testHashCode() {
        WebPage page1 = new WebPage("WWW.google.com","Google", List.of("Abc","Def"));
        WebPage page2 = new WebPage("WWW.google.com","Google", List.of("Abc","Def"));
        assertEquals(page1.hashCode(),page2.hashCode());
    }

    @Test
    void testEquals_differentDataTypes() {
        WebPage page2 = new WebPage("WWW.google.com","Google", List.of("Abc","Def"));
        assertFalse(page2.equals(new Object()));
    }

    @Test
    void testEquals_sameReference() {
        WebPage page2 = new WebPage("WWW.google.com","Google", List.of("Abc","Def"));
        assertTrue(page2.equals(page2));
    }
/*
            return Comparator.comparing(WebPage::getScore).thenComparing(WebPage::getUrl).compare(this,o);
*/

    @Test
    void compareTo_sameScoreDifferentUrl() {
        WebPage page1 = new WebPage("www.facebook.com","Facebook", List.of());
        WebPage page2 = new WebPage("www.google.com","Google", List.of("Abc","Def"));
        page1.setScore(2);
        page2.setScore(2);
        int result = page1.compareTo(page2);
        assertEquals(-1,result);
    }

    @Test
    void compareTo_differentScore() {
        WebPage page1 = new WebPage("www.facebook.com","Facebook", List.of());
        WebPage page2 = new WebPage("www.google.com","Google", List.of("Abc","Def"));
        page1.setScore(2);
        page2.setScore(1);
        int result = page1.compareTo(page2);
        assertEquals(-1,result);
    }
}