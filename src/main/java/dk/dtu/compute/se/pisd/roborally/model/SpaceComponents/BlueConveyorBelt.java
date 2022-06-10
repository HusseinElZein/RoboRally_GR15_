package dk.dtu.compute.se.pisd.roborally.model.SpaceComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * This class moves the player standing on the conveyor belt to the neighbor that the player is phasing.
 */
public class BlueConveyorBelt extends FieldAction {
    private Heading heading;

    /**
     * Moves the player standing on the conveyor belt to the neighbor that the player is phasing.
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return false
     */
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

    /**
     * Method that returns the heading.
     * @return heading
     */
    public Heading getHeading() {
        return heading;
    }

    /**
     * Method that sets the heading.
     * @param heading direction
     */
    public void setHeading(Heading heading) {
        this.heading = heading;
    }

}
