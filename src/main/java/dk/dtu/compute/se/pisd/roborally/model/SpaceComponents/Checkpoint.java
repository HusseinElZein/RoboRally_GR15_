package dk.dtu.compute.se.pisd.roborally.model.SpaceComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class Checkpoint extends FieldAction {
    private int checkpoint;

    public int getCheckpoints() {
        return checkpoint;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        Player playerOnSpace = space.getPlayer();

        if (space.getPlayer() != null) {

            if (playerOnSpace.getCheckpoints() == this.checkpoint - 1) {
                space.getPlayer().setCheckpoints(space.getPlayer().getCheckpoints() + 1);
            }

            /*
            if(playerOnSpace.getCheckpoints() == gameController.board.getCheckpointCounter()) {
                gameController.findWinner(playerOnSpace);
            }
             */
            return true;
        }

        return false;
    }
}
