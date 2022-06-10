package dk.dtu.compute.se.pisd.roborally.model.SpaceComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

/**
 * This class allows for the player to only collect a checkpoint if and only if the amount of checkpoints
 * that the player has is one less than the checkpoint that they want to collect.
 * The class is also used to check if a player on that space has the same amount of checkpoints as the game contain
 * which has been set in the setCheckpointCounter method.
 */
public class Checkpoint extends FieldAction {
    private int checkpoint;

    /**
     * Constructor for checkpoint class.
     * @param checkpoint checkpoint variable to be sat in constructor.
     */
    public Checkpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }

    /**
     * Method that return the checkpoint object.
     * @return checkpoint
     */
    public int getCheckpoints() {
        return checkpoint;
    }

    /**
     * This method allows for the player to only collect a checkpoint if and only if the amount of checkpoints
     * that the player has is one less than the checkpoint that they want to collect.
     * Every time, we also check if a player on that space has the same amount of checkpoints as the game contain
     * which has been set in the setCheckpointCounter method.
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return true or false.
     */
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        Player playerOnSpace = space.getPlayer();

        if (space.getPlayer() != null) {
            if (playerOnSpace.getCheckpoints() == this.checkpoint - 1) {
                space.getPlayer().setCheckpoints(space.getPlayer().getCheckpoints() + 1);
            }

            if (playerOnSpace.getCheckpoints() == gameController.board.getCheckpointCounter()) {
                gameController.findWinner(playerOnSpace);
            }
            return true;
        }
        return false;
    }
}
