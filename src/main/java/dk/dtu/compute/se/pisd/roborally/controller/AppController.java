/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import HTTPClientAndServer.Client;
import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import dk.dtu.compute.se.pisd.roborally.RoboRally;

import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class AppController implements Observer {

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    final private RoboRally roboRally;

    private GameController gameController;
    static Board board;

    public static Board getBoard(){
        return board;
    }

    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    public void newGame() {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);

        String path = "src/main/resources/boards";
        File file = new File(path);
        String absPath = file.getAbsolutePath();
        absPath = absPath.replaceAll("\\\\", "$0$0");
        File filePath = new File(absPath);
        File[] folder = filePath.listFiles();
        String[] filenames = new String[folder.length];

        for (int i = 0; i < filenames.length; i++) {
            filenames[i] = folder[i].getName().substring(0,folder[i].getName().length()-5);
        }

        ChoiceDialog<String> boardDialog = new ChoiceDialog<>(filenames[0],filenames);

        boardDialog.setTitle("Board");
        boardDialog.setHeaderText("Select a board");
        Optional<String> boardResult = boardDialog.showAndWait();

        board = LoadBoard.loadBoard(boardResult.get());

        boardDialog.setTitle("Player number");
        boardDialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (gameController != null) {
                // The UI should not allow this, but in case this happens anyway.
                // give the user the option to save the game or abort this operation!
                if (!stopGame()) {
                    return;
                }
            }

            gameController = new GameController(board);
            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));
            }

            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    Client client = new Client();

    public void connectToServer() {
        //This is not implemented! Sorry!
    }

    public void hostGame(String... errorMessage) throws ExecutionException, InterruptedException, TimeoutException {
        TextInputDialog createServer = new TextInputDialog();
        createServer.setHeaderText("Enter Server name:");
        createServer.setTitle("Starting new server");

        Optional<String> result = createServer.showAndWait();

        String response = client.hostServer(result.get());
        if (!Objects.equals(response, "success"))
            hostGame(response);
        else {
            newGame();
        }
    }

    public void saveGame() {
        // XXX needs to be implemented eventually
        TextInputDialog input = new TextInputDialog();
        input.setHeaderText("Enter filename");
        Optional<String> boardName = input.showAndWait();
        input.setTitle("Save state of game");

        boardName.ifPresent(end ->{LoadBoard.saveBoard(board, end);});
    }

    public void loadGame() {
        // XXX needs to be implemented eventually
        // for now, we just create a new game
        if (gameController == null) {
            String path = "src/main/resources/boards";
            File file = new File(path);
            String absPath = file.getAbsolutePath();
            absPath = absPath.replaceAll("\\\\", "$0$0");
            File filePath = new File(absPath);
            File[] folder = filePath.listFiles();
            String[] filenames = new String[folder.length];
            for (int i = 0; i < filenames.length; i++) {
                filenames[i] = folder[i].getName().substring(0,folder[i].getName().length()-5);
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(filenames[0],filenames);
            dialog.setTitle("Load Game");
            dialog.setHeaderText("Select a game to load");
            Optional<String> result = dialog.showAndWait();


            result.ifPresent(choice->{
                board = LoadBoard.loadBoard(choice);
                gameController = new GameController(board);
                gameController.startProgrammingPhase();
                roboRally.createBoardView(gameController);
            });
        }
    }

    /**
     * Stop playing the current game, giving the user the option to save
     * the game or to cancel stopping the game. The method returns true
     * if the game was successfully stopped (with or without saving the
     * game); returns false, if the current game was not stopped. In case
     * there is no current game, false is returned.
     *
     * @return true if the current game was stopped, false otherwise
     ***/
    public boolean stopGame() {
        if (gameController != null) {

            // here we save the game (without asking the user).
            saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    public void exit() {
        if (gameController != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
        }

        // If the user did not cancel, the RoboRally application will exit
        // after the option to save the game
        if (gameController == null || stopGame()) {
            Platform.exit();
        }
    }

    public boolean isGameRunning() {
        return gameController != null;
    }


    @Override
    public void update(Subject subject) {
        // XXX do nothing for now
    }

}
