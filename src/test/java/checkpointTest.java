import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.Checkpoint;

/**
 * Testing out the method for reaching checkpoints on board
 */

class checkpointTest {

    public static void main(String[] args) {
        /**
         * creating board, player and checkpoint
         */
        Board board = new Board(10, 10);
        Checkpoint checkpoint = new Checkpoint(1);

        Player player = new Player(board, null, "testPlayer");
        board.addPlayer(player);

        System.out.println(player.getSpace());
        /**
         * adding and placing checkpoint and thereafter placing the player on the same field
         */

        board.getSpace(3, 3).getActions().add(checkpoint);
        board.getSpace(3, 3).setPlayer(player);
        /**
         * if method is not equal to null which makes the player land on checkpoint
         * in case of method being equal to null the checkpoints will not be shown which is an error
         */

        if (player.getSpace().getActions() != null) {
            System.out.println("Player has landed on checkpoint");
        } else {
            //This should not happen
            System.out.println("There are no checkpoints on here");
        }
    }
}