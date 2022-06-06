package dk.dtu.compute.se.pisd.roborally.model.SpaceComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class BlueConveyorBelt extends FieldAction {

    private Heading heading;

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {

        if(space.getPlayer() != null) {
            Player playerOnSpace = space.getPlayer();

            Space target = gameController.board.getNeighbour(space, heading);

            if(target.getPlayer() != null){
                Space targetNew = gameController.board.getNeighbour(target, heading);
                targetNew.setPlayer(target.getPlayer());
            }

            target.setPlayer(playerOnSpace);
        }

        return false;
    }
}
