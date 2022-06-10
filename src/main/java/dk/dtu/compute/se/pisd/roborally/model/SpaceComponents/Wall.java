package dk.dtu.compute.se.pisd.roborally.model.SpaceComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * This class implements the Walls
 */

public class Wall extends FieldAction {

    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
