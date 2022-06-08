package HTTPClientAndServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface IRoboRallyService {

    void updateGame(String gameState) throws ExecutionException, InterruptedException, TimeoutException;

    String hostServer(String serverId) throws ExecutionException, InterruptedException, TimeoutException;

    String getServer();
    void setServer(String server);

    String getGameState();

    void updateWholeGame() throws ExecutionException, InterruptedException, TimeoutException;
}
