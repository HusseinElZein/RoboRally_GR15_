package dk.dtu.compute.se.pisd.roborally.model.SpaceComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Implemented the Gear feature
 */
public class Gear extends FieldAction {

    private final Heading heading;

    public Gear(Heading heading) {
        this.heading = heading;
    }

    public Heading getHeading(){
        return heading;
    }


    /**
     * The method for changing the direction of the gear
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return
     */
    @Override
    public boolean doAction(GameController gameController, Space space) {

        Heading newHeading = null;
        switch (space.getPlayer().getHeading()){
            case SOUTH -> newHeading = Heading.EAST;
            case EAST -> newHeading = Heading.NORTH;
            case NORTH -> newHeading = Heading.WEST;
            case WEST -> newHeading = Heading.SOUTH;
        }

        space.getPlayer().setHeading(newHeading);
        return true;
    }
}
