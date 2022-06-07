package HTTPClientAndServer;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class RoboRallyService implements IRoboRallyService {
    ArrayList<Server> servers = new ArrayList<>();
    private int id = 0;

    @Override
    public void updateGame(String id, String gameState) {
        Server server = findServer(id);
        server.setGameState(gameState);
        if (server.getMaxAmountOfPlayers() != 0)
            return;
        server.setMaxAmountOfPlayers(StringUtils.countOccurrencesOf(gameState, "Player "));
    }

    @Override
    public String getGameState(String serverId) {
        return (findServer(serverId)).getGameState();
    }

    @Override
    public void updateGame(String gameState) {
    }

    @Override
    public String getGameState() {
        return null;
    }

    @Override
    public String hostServer(String title) {
        servers.add(new Server(title, id));
        String newServerId = String.valueOf(id);
        id++;
        return newServerId;
    }

    @Override
    public String listOfSavedGames() {
        Gson gson = new Gson();

        ArrayList<Server> server = new ArrayList<>();
        servers.forEach(e -> {
            if (e.getAmountOfPlayers() != e.getMaxAmountOfPlayers()) {
                server.add(e);
            }
        });
        return gson.toJson(server);
    }

    @Override
    public String joinGame(String serverID) {
        Server server = findServer(serverID);
        if (server == null)
            return "Error, something is wring with the ";
        server.addPlayer();
        return String.valueOf(server.getARobot());
    }

    private Server findServer(String serverId){
        for (Server e : servers) {
            if (Objects.equals(e.getId(), serverId)) {
                return e;
            }
        }
        return null;
    }
}