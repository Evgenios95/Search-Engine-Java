package searchengine;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The WebServer class is the main class. Its purpose is to create the content of the server and receive the query of the user.
 */
public class WebServer {
    static final int PORT = 8080;
    static final int BACKLOG = 0;
    static final Charset CHARSET = StandardCharsets.UTF_8;

    private final Pages pages;
    final HttpServer server;

    WebServer(int port, String filename) throws IOException {
        pages = new Pages(filename);
        server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
        server.start();
        configure();
        welcome(port);
    }

    /**
     * this method uses the webfiles (files in the webfolder) to create the content of the server webpage
     */
    private void configure() {
        server.createContext("/", io -> respond(io, 200, "text/html", getFile("web/index.html")));
        server.createContext("/search", io -> search(io));
        server.createContext(
                "/favicon.ico", io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
        server.createContext(
                "/code.js", io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
        server.createContext(
                "/style.css", io -> respond(io, 200, "text/css", getFile("web/style.css")));
        server.createContext(
                "/itulogo (2).png", io -> respond(io, 200, "image/jpg", getFile("web/itulogo (2).png")));
        server.createContext(
                "/searchicon.png", io -> respond(io, 200, "image/jpg", getFile("web/searchicon.png")));
        server.createContext(
                "/bananas.png", io -> respond(io, 200, "image/jpg", getFile("web/bananas.png")));
    }

    /**
     * This method outputs the welcome statement in the terminal of the IDE
     * @param port - the port of the webserver.
     */
    private void welcome(int port) {
        String msg = " WebServer running on http://localhost:" + port + " ";
        System.out.println("╭" + "─".repeat(msg.length()) + "╮");
        System.out.println("│" + msg + "│");
        System.out.println("╰" + "─".repeat(msg.length()) + "╯");
    }

    /**
     * This method is responsible for receiving the query from the user and sending back the response.
     * The method receives the query and turns it into a single string that can be used as input for other methods
     * It also sends back the appropriate pages after they have been found using other methods for ranking and
     * scoring the pages
     * @param io -input/output of the webserver
     */
    void search(HttpExchange io) {
        var searchTerm = io.getRequestURI().getRawQuery().split("=")[1];
        try {
            searchTerm = URLDecoder.decode(searchTerm, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.err.println(e);
        }
        var response = new ArrayList<String>();
        for (var page : pages.search(searchTerm)) {
            response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
                    page.getUrl(), page.getTitle()));
        }
        var bytes = response.toString().getBytes(CHARSET);
        respond(io, 200, "application/json", bytes);
    }

    /**
     *
     * @param filename -  the file from from which the info for setting up the frontend of the webserver webpage is read from.
     *  This includes the css file, the javascrpt file, the html file and all the image files.
     * @return a bytearray containing
     */
    private byte[] getFile(String filename) {
        try {
            return Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


    private void respond(HttpExchange io, int code, String mime, byte[] response) {
        try {
            io.getResponseHeaders()
                    .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
            io.sendResponseHeaders(200, response.length);
            io.getResponseBody().write(response);
        } catch (Exception e) {
        } finally {
            io.close();
        }
    }

    /**
     * This is the main method of the project. It reads the file and creates a new Webserver object.
     *
     * @throws IOException
     */
    public static void main(final String... args) throws IOException {
        var filename = Files.readString(Paths.get("config.txt")).strip();
        new WebServer(PORT, filename);
    }
}