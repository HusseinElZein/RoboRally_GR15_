package HTTPClientAndServer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.concurrent.TimeUnit.*;

/**
 * Create a simple http client that can interact with the RoboRally game server
 *
 * @author
 */
public class Client implements IRoboRallyService {
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10)).build();

    private boolean connectedToServer = false;
    private String urlUri = "http://localhost:8080";
    private String serverId;

    public boolean isConnectedToServer(){
        return connectedToServer;
    }

    /**
     * Updates the game state on the game server with a JSON string
     *
     * @param gameState JSON string to update state with
     */

    @Override
    public void updateGame(String gameState) {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(gameState))
                .uri(URI.create(urlUri + "/gameState/" + urlUri))
                .setHeader("User-Agent", "RoboRally Client")
                .setHeader("Content-Type", "application/json")
                .build();
        CompletableFuture<HttpResponse<String>> response =
                HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try {
            String result = response.thenApply(HttpResponse::body).get(5, HOURS);
            // Result ignorer for now
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the current game state as a JSON string
     *
     * @return JSON string with game state
     */
    @Override
    public String getGameState() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlUri + "/gameState/" + urlUri))
                .setHeader("User-Agent", "RoboRally Client")
                .header("Content-Type", "application/json")
                .build();
        CompletableFuture<HttpResponse<String>> response =
                HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String result;
        try {
            result = response.thenApply(HttpResponse::body).get(5, SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Hosts a new game on the server and sets the server id to future communication
     *
     * @param title the title of the new server
     * @return serverId string
     */
    @Override
    public String hostServer(String title) throws ExecutionException, InterruptedException, TimeoutException {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(title))
                .uri(URI.create(urlUri + "/game"))
                .setHeader("User-Agent", "RoboRally Client")
                .header("Content-Type", "text/plain")
                .build();
        CompletableFuture<HttpResponse<String>> response =
                HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());


            serverId = response.thenApply(HttpResponse::body).get(5, SECONDS);
            if (response.get().statusCode() == 500) {
                return response.get().body();
            }
            connectedToServer = true;


        return "success";
    }

    /**
     * Lists all games available on the server
     *
     * @return list of available games
     */
    @Override
    public String listOfSavedGames() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlUri + "/game"))
                .setHeader("User-Agent", "RoboRally Client")
                .header("Content-Type", "application/json")
                .build();
        CompletableFuture<HttpResponse<String>> response =
                HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String result;
        try {
            result = response.thenApply(HttpResponse::body).get(5, SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return "server timeout";
        }
        return result;

    }

    @Override
    public void updateGame(String id, String gameState) {
    }

    @Override
    public String getGameState(String gameId) {
        return null;
    }

    /**
     * Joins a game and get the current game state
     *
     * @param serverToJoin the id of the server to join
     * @return gamestate and empty string if game is not up yet
     */
    @Override
    public String joinGame(String serverToJoin) {

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create(urlUri + "/game/" + serverToJoin))
                .header("User-Agent", "RoboRally Client")
                .header("Content-Type", "text/plain")
                .build();

        CompletableFuture<HttpResponse<String>> response =
                HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> message = response.get(5, SECONDS); //gets the message back from the server
            if (message.statusCode() == 200)
                return message.body();
            if (message.statusCode() == 404)
                return message.body();
            urlUri = serverToJoin;

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return "service timeout";
        }
        return "ok";
    }

    public String getServer() {
        return urlUri;
    }

    /**
     * This method sets the ip address of the server
     */
    public void setServer(String server) {
        Pattern pattern = Pattern.compile("^(?:\\d{1,3}\\.){3}\\d{1,3}$");

        this.serverId = "http://" + server + ":8080";

    }

}