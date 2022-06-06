package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.scene.control.Alert;

public class cantMoveTroughWallExeption extends Exception {
    private Player player;

    public cantMoveTroughWallExeption(Player player) {
        this.player = player;

        Alert hitWall = new Alert(Alert.AlertType.WARNING,
                player.getName() + " you can't move trough a wall.");
        hitWall.showAndWait();
    }
}
