package searchengine;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * The WebPage class - is responsible for the creation of single Webpage objects.
 */

public class WebPage implements Comparable<WebPage> {
    private final String url;
    private final String title;
    private final List<String> keywords;
    private double relevance;

    /**
     * This is a constructor for a single Webpage object
     *
     * @param url      - the url of the website
     * @param title    - the title of the website
     * @param keywords - the words in the website(excluding the title and the url).
     */
    public WebPage(String url, String title, List<String> keywords) {
        this.url = url;
        this.title = title;
        this.keywords = keywords;
        this.relevance = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public double getScore() {
        return relevance;
    }

    public void setScore(double score) {
        relevance = score;
    }

    public void setfinalScore(double score) {
        relevance = score;
    }

    public double getfinalScore() {
        return relevance;
    }

    public boolean isEmpty() {
        return keywords.isEmpty();
    }

    /**
     * This method checks if the URL of two Webpages are equal
     *
     * @param o - A class Object o
     * @return url.equals(page.url) - returns true if both url are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebPage page = (WebPage) o;
        return url.equals(page.url);
    }

    /**
     * This method will return a hashcode of a webpages URL
     *
     * @return Object.hash(urL) - returns the URL hash code field of an object
     */
    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    /**
     * This method compares the getScore of two webpages, reverses then to make them descending, and then compares their URL.
     * It will override the method rankingAlgorithm in the QueryHandler when it creates a new comparator.
     * @param o - A WebPage object that is to be compared
     * @return Comparator.comparing - It will return true if two webpages have the same Score and URL.
     */
    @Override
    public int compareTo(WebPage o) {
        return Comparator.comparing(WebPage::getScore).reversed().thenComparing(WebPage::getUrl).compare(this, o);
    }
}
