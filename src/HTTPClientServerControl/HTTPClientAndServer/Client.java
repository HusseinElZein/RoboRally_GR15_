package HTTPClientAndServer;

import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import static java.util.concurrent.TimeUnit.*;

/**
 * This will create a http server using the roborally server, remember to have the server started before starting this
 * client.
 */
public class Client implements IRoboRallyService {
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10)).build();
    private String urlUri = "http://localhost:8080";
    private String serverId; // Not used but could be useful for later purpases.
    public static boolean isStarted = false;

    /**
     * This method hosts a new game on a server and prepares the program for future updates.
     * @param title object to be inserted for POST request to server
     * @return returns succes string
     * @throws ExecutionException standard Java library exception class
     * @throws InterruptedException standard Java library exception class
     * @throws TimeoutException standard Java library exception class
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
        isStarted = true;
        return "success";
    }

    /**
     * This method sets the overall address of the server that has just been started.
     * @param server object to be inserted as parameter in serverID
     */
    public void setServer(String server) {
        this.serverId = "http://" + server + ":8080";
    }

    /**
     * Method that returns the urlUri object. For now the method is not used, but could be useful for later
     * purpases.
     */
    public String getServer() {
        return urlUri;
    }

    /**
     * This method return the boolean of isStarted to the game controller. Here isStarted becomes true whenever
     * a player hosts a game.
     */
    public boolean getIsStarted(){
        return isStarted;
    }


    /**
     * This method sets the updates the whole game by sending the string of the JSON file to the server via
     * a POST request. The method is used in the executeCommand method, such that a new version of the JSON file
     * gets send every time a new command has been used.
     */
    @Override
    public void updateWholeGame() throws ExecutionException, InterruptedException, TimeoutException {
        String jsonBoard = LoadBoard.saveBoardToString(AppController.getBoard(), "temporary");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonBoard))
                .uri(URI.create(urlUri + "/board"))
                .setHeader("User-Agent", "RoboRally Client")
                .setHeader("Content-Type", "application/json")
                .build();

        //This is the completable future, it sends an async, and can in the future get a response
        CompletableFuture<HttpResponse<String>> response =
                HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        //In case i would want the result, i have it here, it can throw a message! Thats
        //Why I have it in here
        String result = response.thenApply(HttpResponse::body).get(5, HOURS);
    }

    /**
     * This is the getter method that gets all the updates. This method could be useful in the future.
     **/
    @Override
    public String getUpdateWholeGame() throws ExecutionException, InterruptedException, TimeoutException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlUri + "/board"))
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
     * This method sets the state of the game to the server by using POST via HTTP.
     * The method is never used, but is included to show how a hypothetical scenario in which a client could
     * request from the server the state of the game.
     */
    @Override
    public void setStateOfGame(String stringForStateOfGame) throws ExecutionException, InterruptedException, TimeoutException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(stringForStateOfGame))
                .uri(URI.create(urlUri + "/stateOfGame"))
                .setHeader("User-Agent", "RoboRally Client")
                .setHeader("Content-Type", "application/json")
                .build();

        //This is the completable future, it sends an async, and can in the future get a response
        CompletableFuture<HttpResponse<String>> response =
                HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        //In case i would want the result, i have it here, it can throw a message! Thats
        //Why I have it in here
        String result = response.thenApply(HttpResponse::body).get(5, HOURS);
    }

    /**
     * This method gets the state of the game by requesting via HTTP.
     * The method is never used, but is included to show how a hypothetical scenario in which a client could
     * request from the server the state of the game.
     */
    @Override
    public String getStateOfGame() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlUri + "/stateOfGame"))
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
}