package dk.dtu.compute.se.pisd.roborally.model.SpaceComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PushPanel extends FieldAction {
    private Heading heading;


    public Heading getHeading(){
        return heading;
    }

    @Override
    public boolean doAction (GameController gameController, Space space){

        Space neighbourSpace = gameController.board.getNeighbour(space, heading);

        neighbourSpace.setPlayer(space.getPlayer());

        return false;
    }

}
