package HTTPClientAndServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * This interface contains all the possible methods that we could come up with that the client could
 * send and request to the server.
 * Here some methods have no usage, but are still implemented and ready for usage in the Client class.
 *
 * Since all methods in this class are implemented by the Client class, there is no need for describing
 * theese methods.
 */
public interface IRoboRallyService {
    String hostServer(String serverId) throws ExecutionException, InterruptedException, TimeoutException;
    String getServer();
    void setServer(String server);
    String getStateOfGame();
    void setStateOfGame(String stringForStateOfGame) throws ExecutionException, InterruptedException, TimeoutException;
    void updateWholeGame() throws ExecutionException, InterruptedException, TimeoutException;
    String getUpdateWholeGame() throws ExecutionException, InterruptedException, TimeoutException;
}
