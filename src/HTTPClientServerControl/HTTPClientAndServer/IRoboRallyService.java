package HTTPClientAndServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface IRoboRallyService {
    String listOfSavedGames();
    void updateGame(String id, String gameState);
    String getGameState(String gameId);

    void updateGame(String gameState);

    String getGameState();

    String hostServer(String serverId) throws ExecutionException, InterruptedException, TimeoutException;
    String joinGame(String serverToJoin);
}
