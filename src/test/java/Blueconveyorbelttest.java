import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.BlueConveyorBelt;

class Blueconveyorbelttest {

    public static void main(String[] args) {

        Board board = new Board(10,10);
        GameController gameController = new GameController(board);
        BlueConveyorBelt blueConveyorBelt = new BlueConveyorBelt();
        blueConveyorBelt.setHeading(Heading.SOUTH);

        Player player = new Player(board,null,"testPlayer");
        board.addPlayer(player);

        System.out.println(player.getSpace());

        board.getSpace(1,1).getActions().add(blueConveyorBelt);
        board.getSpace(1,1).setPlayer(player);

        if(player.getSpace().getActions() != null){
            System.out.println("Player has landed on blue conveyor belt");
        }else{
            //This should not happen
            System.out.println("There are no blue conveyor belt on here");
        }

    }

}