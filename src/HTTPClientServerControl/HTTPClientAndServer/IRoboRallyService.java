package HTTPClientAndServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface IRoboRallyService {

    String hostServer(String serverId) throws ExecutionException, InterruptedException, TimeoutException;

    String getServer();

    void updateGame(String gameState) throws ExecutionException, InterruptedException, TimeoutException;

    void setServer(String server);

    String getStateOfGame();

    void setStateOfGame(String stringForStateOfGame) throws ExecutionException, InterruptedException, TimeoutException;

    void updateWholeGame() throws ExecutionException, InterruptedException, TimeoutException;

    String getUpdateWholeGame() throws ExecutionException, InterruptedException, TimeoutException;
}
