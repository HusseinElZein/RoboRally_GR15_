package HTTPClientAndServer;

public class Server {
    private String id;
    private final String title;
    private transient String gameState;
    private int amountOfPlayers;
    private int maxAmountOfPlayers;
    private transient boolean[] playerSpotFilled;

    public Server(String title, int id){
        this.id = String.valueOf(id);
        this.title = title;
        this.amountOfPlayers = 1;
    }

    public void addPlayer(){
        amountOfPlayers++;
    }

    public int getAmountOfPlayers() {
        return amountOfPlayers;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public String getId(){
        return id;
    }

    public int getMaxAmountOfPlayers() {
        return maxAmountOfPlayers;
    }

    public void setMaxAmountOfPlayers(int amountOfPlayers) {
        this.maxAmountOfPlayers = amountOfPlayers;
        this.playerSpotFilled = new boolean[amountOfPlayers];
        playerSpotFilled[0] = true;
    }

    public int getARobot(){
        for (int i = 0; i < maxAmountOfPlayers; i++)
            if (!playerSpotFilled[i])
                return i;
        return 0;
    }

    public void setPlayerSpotFilled(int i, boolean flag){
        playerSpotFilled[i] = flag;
    }
}