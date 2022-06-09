import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.Checkpoint;

class checkpointTest {

    public static void main(String[] args) {

        Board board = new Board(10, 10);
        Checkpoint checkpoint = new Checkpoint(1);

        Player player = new Player(board, null, "testPlayer");
        board.addPlayer(player);

        System.out.println(player.getSpace());

        board.getSpace(3, 3).getActions().add(checkpoint);
        board.getSpace(3, 3).setPlayer(player);

        if (player.getSpace().getActions() != null) {
            System.out.println("Player has landed on checkpoint");
        } else {
            //This should not happen
            System.out.println("There are no checkpoints on here");
        }
    }
}