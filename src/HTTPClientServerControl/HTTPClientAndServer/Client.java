package HTTPClientAndServer;
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
 * client!
 */
public class Client implements IRoboRallyService {
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10)).build();

    private String urlUri = "http://localhost:8080";
    private String serverId;

    /**
     * This method hosts a new game on a server and prepares
     * the program for future updates and stuff
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

        return "success";
    }

    /**
     * This method will update the game state on the game server with a JSON string.
     * This method throws exceptions from the request and response. They get catched in
     * RoborallyMenyBar in case I would want it in there
     */

    @Override
    public void updateGame(String gameState) throws ExecutionException, InterruptedException, TimeoutException {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(gameState))
                .uri(URI.create(urlUri + "/gameState/" + urlUri))
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
     * This method sets the overall address of the server that has just been started
     */
    public void setServer(String server) {
        this.serverId = "http://" + server + ":8080";
    }

    public String getServer() {
        return urlUri;
    }


}