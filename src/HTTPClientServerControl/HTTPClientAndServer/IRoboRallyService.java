package HTTPClientAndServer;

public interface IRoboRallyService {
    String listOfSavedGames();
    void updateGame(String id, String gameState);
    String getGameState(String gameId);

    void updateGame(String gameState);

    String getGameState();

    String hostServer(String serverId);
    String joinGame(String serverToJoin);
}
