import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;

/**
 * Testing out the method for moving a player on the board
 */
    public class moveAPlayerTest {


        public static void main(String[] args) {
            /**
             * Creating a new player and  board.
             */

            Board board = new Board(10, 10);
            Player player = new Player(board, null, "testPlayer");
            board.addPlayer(player);
            /**
             * placing the player on a starting position and getting an end position in return
             */

            board.getSpace(3, 3).setPlayer(player);
            System.out.println("Player starting position:"+ player.getSpace().getX() + "," +player.getSpace().getY() );


            board.getSpace(5, 5).setPlayer(player);
            System.out.println("Player ending position:"+ player.getSpace().getX() + "," +player.getSpace().getY() );
            /**
             * if method is not equal to null which makes the player move if not there is an error in the method.
             */

            if (player.getSpace().getActions() != null) {
                System.out.println("Player has moved");
            } else {
                //This should not happen
                System.out.println("There are no movement");
            }

        }
    }




