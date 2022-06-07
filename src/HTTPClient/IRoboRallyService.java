
import dk.dtu.compute.se.pisd.roborally.model.Player;

public interface IRoboRallyService
{
    String getGameById(int gameId);
    String listOfSavedGames();
    void updateGame(String stateOfGame, String gameId);
    boolean connectToServer(String serverId);
    void disconnectFromServer();

}
