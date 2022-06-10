package dk.dtu.compute.se.pisd.roborally.model.SpaceComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Implementing the push panel feature
 */
public class PushPanel extends FieldAction {
    private Heading heading;


    public Heading getHeading() {
        return heading;
    }

    /**
     * The method for pushing the player
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return false boolean
     */
    @Override
    public boolean doAction(GameController gameController, Space space) {

        if (gameController.board.getStep() == 1 || gameController.board.getStep() == 3) {

            Space neighbourSpace = gameController.board.getNeighbour(space, heading);

            if (neighbourSpace.getPlayer() != null) {
                Space otherSpace = gameController.board.getNeighbour(neighbourSpace, heading);
                otherSpace.setPlayer(neighbourSpace.getPlayer());
            }

            neighbourSpace.setPlayer(space.getPlayer());
        }
        return false;
    }
}