package dk.dtu.compute.se.pisd.roborally.Exception;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.scene.control.Alert;

/**
 * This exception class is used to check for when a player tries to move through a wall. The exception will be
 * thrown whenever this happens.
 */
public class cantMoveThroughWallExeption extends Exception {
    private Player player;

    /**
     * This method gives an alert message whenever the cantMoveThroughWallException class gets thrown whenever
     * a player attempts to move through a wall. This is checked for in the GameController.
     */
    public cantMoveThroughWallExeption(Player player) {
        this.player = player;

        Alert hitWall = new Alert(Alert.AlertType.WARNING,
                player.getName() + " you can't move trough a wall.");
        hitWall.showAndWait();
    }
}

