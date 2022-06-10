import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;

    public class moveAPlayerTest {


        public static void main(String[] args) {

            Board board = new Board(10, 10);
            Player player = new Player(board, null, "testPlayer");
            board.addPlayer(player);


            board.getSpace(3, 3).setPlayer(player);
            System.out.println("Player starting position:"+ player.getSpace().getX() + "," +player.getSpace().getY() );


            board.getSpace(5, 5).setPlayer(player);
            System.out.println("Player ending position:"+ player.getSpace().getX() + "," +player.getSpace().getY() );

            if (player.getSpace().getActions() != null) {
                System.out.println("Player has moved");
            } else {
                //This should not happen
                System.out.println("There are no movement");
            }

        }
    }




