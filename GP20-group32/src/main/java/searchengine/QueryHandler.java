package searchengine;

import java.util.*;

/**
 * The QueryHandler classes purpose is to handle all searches made on the search engine.
 */

public class QueryHandler {

    String searchTerm;
    Map<String, List<WebPage>> index;
    List<WebPage> pageList;

    public QueryHandler(String searchTerm, Map<String, List<WebPage>> index, List<WebPage> pageList) {
        this.searchTerm = searchTerm;
        this.index = index;
        this.pageList = pageList;
    }

    /**
     * This method breaks up the the String entered by the user in several phases. Firstly, around OR to searches for multiple topics.
     * Secondly, the topic are broken into  individual words, Webpages are sorted so that only those that have both words would be shown .
     * After that the tfidf method is called to sort the pages according to their ranking.
     *
     * @param query -  the String entered by the user
     * @return scoredPages - returns list of webpages  that match the query sorted from most relevant to the least relevant
     * ( based on the the TFIDF score they have been assigned)
     */
    public List<WebPage> getMatchingWebpages(String query) {
        String[] words2 = query.split(" OR ");
        Set<WebPage> result = new LinkedHashSet<>();
        List<String[]> orWords = new ArrayList<>();

        for (String word : words2) {
            orWords.add(word.trim().toLowerCase().split(" "));
        }

        for (String[] andWords : orWords) {
            List<WebPage> andResult = null;
            for (String singleWord : andWords) {
                List<WebPage> result1 = index.get(singleWord);
                if (result1 != null) {
                    if (andResult == null) {
                        andResult = new ArrayList<>(result1);
                    } else {
                        andResult.retainAll(result1);
                    }
                }
            }
            if (andResult != null) {
                result.addAll(andResult);
            }
        }

        List<WebPage> result2 = new ArrayList<>(result);
        List<WebPage> scoredPages = tfIdf(result2, orWords);
        return scoredPages;
    }

    /**
     *
     * @param result2 - the list of webpages that have the searched term in them
     * @param orWords -  Arraylist of word(pairs) that have firstly been separated by the OR and then further separated
     * to an Arrays containing each word as a separate String
     * @return  scoredPages - list of webpages that have been assigned and tf  score and sorted by the tf score and title
     */
    public List<WebPage> rankingAlgorithm(List<WebPage> result2, List<String[]> orWords) {
        List<WebPage> scoredPages = new ArrayList<>();
        for (var page : result2) {
            List<String> kwords = page.getKeywords();
            int keywordsSize = kwords.size();
            double score = 0;
            double weightedScore = 0;
            for (var orWord : orWords) {
                for (var wordz : orWord) {
                    for (var word : kwords) {
                        if (wordz.equals(word)) {
                            score += 1;
                        }
                    }
                }
            }
            weightedScore = score / keywordsSize;
            page.setScore(weightedScore);
            scoredPages.add(page);
        }
        Comparator<WebPage> c = Comparator.comparing(WebPage::getScore).thenComparing(WebPage::getTitle);
        Collections.sort(scoredPages, c);
        Collections.reverse(scoredPages);
        return scoredPages;
    }

    /**
     *
     * @param pageList - a list of all webpage objects in the given file.
     * @param result2 - the list of webpages that have the searched term in them
     * @return idfscore - Inverse Document Frequency score, which measures how important a term is by  dividing total number of pages with Number of pages with searchterm  in them
     */
    public double idfRankingAlgorithm(List<WebPage> pageList, List<WebPage> result2) {
        var idfscore = pageList.size() / result2.size();
        return idfscore ;
    }

    /**
     * @param result2 -the list of webpages that have the searched term in them
     *  @param orWords - Arraylist of word(pairs) that have firstly been separated by the OR and then further separated  to an Arrays containing each word as a separate String.
     *  @return Webpages  with the tf-idf weight calculated by multiplying the term frequency score of an individual document by the  Inverse Document Frequency
     *

     */
    public List<WebPage> tfIdf(List<WebPage> result2, List<String[]> orWords) {
        List<WebPage> finalScoredPages = new ArrayList<>();

        for (WebPage page : rankingAlgorithm(result2, orWords)) {
            double totalscore = page.getScore() * idfRankingAlgorithm(pageList, result2);
            page.setfinalScore(totalscore);
            finalScoredPages.add(page);
        }
        return finalScoredPages;
    }
}
    
    
    
    
    
    
    
    
    
    
  