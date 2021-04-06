package searchengine;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryHandlerTest {

    @Test
    void idfRankingAlgorithm_score() {
        List<WebPage> pageList = new ArrayList<>();
        List<WebPage> result2 = new ArrayList<>();

        WebPage page = new WebPage("www.google.com", "Google", List.of("Ab25", "Def9"));
        WebPage page1 = new WebPage("www.google1.com", "Google2", List.of("Ab25", "De6f"));
        WebPage page2 = new WebPage("www.google2.com", "Google3", List.of("Ab2c", "D1ef"));
        WebPage page3 = new WebPage("www.google3.com", "Google4", List.of("Ab3c", "5Def"));
        pageList.add(page);
        pageList.add(page1);
        pageList.add(page2);
        pageList.add(page3);

        WebPage page4 = new WebPage("www.google2.com", "Google3", List.of("Ab25", "D1ef"));
        WebPage page5 = new WebPage("www.google3.com", "Google4", List.of("Ab3c", "5Def"));
        result2.add(page4);
        result2.add(page5);

        QueryHandler quer = new QueryHandler(null, null, pageList);

        var division = pageList.size() / result2.size();
        assertEquals(division, quer.idfRankingAlgorithm(pageList, result2));
    }

    @Test
    void rankingAlgorithm_order() {
        List<WebPage> result2 = new ArrayList<>();
        WebPage page1 = new WebPage("www.google2.com", "Google3", List.of("Horse", "Cat"));
        WebPage page2 = new WebPage("www.google3.com", "Google4", List.of("Fish", "Cat", "Dog"));
        page1.setScore(100);
        page2.setScore(50);

        result2.add(page1);
        result2.add(page2);

        List<String[]> orWords = new ArrayList<>();
        String[] word1 = {"Cat" };
        orWords.add(word1);

        QueryHandler quer = new QueryHandler(null, null, result2);

        List<WebPage> actualRanking = quer.rankingAlgorithm(result2, orWords);

        assertEquals(result2.get(0), actualRanking.get(0));
    }

    @Test
    void tfIdf_order() {
        List<WebPage> result2 = new ArrayList<>();
        WebPage page1 = new WebPage("www.google2.com", "Google3", List.of("Horse", "Cat"));
        WebPage page2 = new WebPage("www.google3.com", "Google4", List.of("Fish", "Cat", "Dog"));
        page1.setScore(100);
        page2.setScore(50);

        result2.add(page1);
        result2.add(page2);

        List<String[]> orWords = new ArrayList<>();
        String[] word1 = {"Cat" };
        orWords.add(word1);

        QueryHandler quer = new QueryHandler(null, null, result2);

        List<WebPage> actualRanking = quer.tfIdf(result2, orWords);

        assertEquals(result2.get(0), actualRanking.get(0));
    }

    @Test
    void getMatchingWebpages_singleWord() {
        String query = "Fish";
        List<WebPage> result2 = new ArrayList<>();
        WebPage page1 = new WebPage("www.google2.com", "Google3", List.of("Horse", "Cat"));
        WebPage page2 = new WebPage("www.google3.com", "Google4", List.of("Fish", "Cat", "Dog"));
        result2.add(page1);
        result2.add(page2);

        Map<String, List<WebPage>> indexActual;
        indexActual = Pages.populateIndex(result2);

        QueryHandler quer = new QueryHandler(query, indexActual, result2);

        List<WebPage> actualRanking = quer.getMatchingWebpages(query);

        assertEquals(1,actualRanking.size());

    }

    @Test
    void getMatchingWebpages_andWord() {
        String query = "Cat Horse";
        List<WebPage> result2 = new ArrayList<>();
        WebPage page1 = new WebPage("www.google2.com", "Google3", List.of("Horse", "Cat"));
        WebPage page2 = new WebPage("www.google3.com", "Google4", List.of("Fish", "Cat", "Dog"));
        result2.add(page1);
        result2.add(page2);

        Map<String, List<WebPage>> indexActual;
        indexActual = Pages.populateIndex(result2);

        QueryHandler quer = new QueryHandler(query, indexActual, result2);

        List<WebPage> actualRanking = quer.getMatchingWebpages(query);

        assertEquals(1,actualRanking.size());

    }

    @Test
    void getMatchingWebpages_orWord() {
        String query = "Horse OR Fish";
        List<WebPage> result2 = new ArrayList<>();
        WebPage page1 = new WebPage("www.google2.com", "Google3", List.of("Horse", "Cat"));
        WebPage page2 = new WebPage("www.google3.com", "Google4", List.of("Fish", "Cat", "Dog"));
        result2.add(page1);
        result2.add(page2);

        Map<String, List<WebPage>> indexActual;
        indexActual = Pages.populateIndex(result2);

        QueryHandler quer = new QueryHandler(query, indexActual, result2);

        List<WebPage> actualRanking = quer.getMatchingWebpages(query);

        assertEquals(2,actualRanking.size());

    }
}

