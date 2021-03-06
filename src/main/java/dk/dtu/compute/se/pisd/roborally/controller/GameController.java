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
import dk.dtu.compute.se.pisd.roborally.Exception.cantMoveThroughWallExeption;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.*;
import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * This is the GameController class. This class is used to control the flow of the game, and to invoke different
 * methods that relate to any logic in the game2
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class GameController {

    final public Board board;

    /**
     * Constructor for the GameController class.
     * @param board board object.
     */
    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        // TODO Assignment V1: method should be implemented by the students:
        //   - the current player should be moved to the given space
        //     (if it is free()
        //   - and the current player should be set to the player
        //     following the current player
        //   - the counter of moves in the game should be increased by one
        //     if the player is moved

        if (space != null && space.board == board) {
            Player currentPlayer = board.getCurrentPlayer();
            if (currentPlayer != null && space.getPlayer() == null) {
                currentPlayer.setSpace(space);
                int playerNumber = (board.getPlayerNumber(currentPlayer) + 1) % board.getPlayersNumber();
                board.setCurrentPlayer(board.getPlayer(playerNumber));
            }
        }
    }

    /**
     * This method starts the programming phase. Here the phase is sat, and then for each player programming fields
     * and card fields are generated and made visible.
     */
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    /**
     * This method generetes a random card by returning an command object that is randomised from an array of
     * commands.
     */
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    /**
     * This method finish the programming phase. This is done by setting the phase to the activation phase.
     */
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    /**
     * This method makes the program fields visible. If 0 is parsed in the parameter, they won't be made visible.
     */
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    /**
     * This method makes the programming fields invisible by setting the setVisible method to false.
     */
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    /**
     * This method executes the programs which the player has placed.
     */
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    /**
     * Method that executes step of the program for the program cards.
     */
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    /**
     * Method that continues program by executing the next step so long that the phase is in activation and the
     * board is in step mode.
     */
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    /**
     * Method to execute the next step in the program.
     */
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;

                    if(command.isInteractive()){
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }else {
                        executeCommand(currentPlayer, command);
                    }
                }
                int nextPlayer = 1 + board.getPlayerNumber(currentPlayer);
                if (nextPlayer < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayer));
                } else {
                    for(int i = 0; i < board.getPlayersNumber(); i++) {
                        Player player = board.getPlayer(i);
                        if (player.getSpace().getActions() != null && player.getSpace() != null) {
                            for (FieldAction field : player.getSpace().getActions()) {
                                field.doAction(this, player.getSpace());
                            }
                        }
                    }
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    private Client client = new Client();

    /**
     * Method that executes the different commands that the player might place on their programming fields.
     * Here we use the client object to update the game for every command that is being executed.
     * We also update the state of the game, but this is not so important.
     * @param player player to be moved
     * @param command command for player
     */
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {
                case FORWARD -> this.moveForward(player);
                case RIGHT -> this.turnRight(player);
                case LEFT -> this.turnLeft(player);
                case FAST_FORWARD -> this.fastForward(player);
                case TRIPPLE_FORWARD -> this.tripleForward(player);
                case U_TURN -> this.uTurn(player);
                case BACK_UP -> this.backUp(player);
                default -> {
                }
                // DO NOTHING (for now)
            }

            if (client.getIsStarted()) {
                try {
                    client.updateWholeGame();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }

        String stateOfGame = "Current player: " + board.getCurrentPlayer().getName() + "\nGame ended: " + "NO"
                + "\nPhase " + board.getPhase() + "\nAmount of checkpoints in game: " + board.getCheckpointCounter();

        if (client.getIsStarted()) {
            try {
                client.setStateOfGame(stateOfGame);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    boolean canMoveThroughWall = true;

    /**
     * This method checks if a player is able to move through the wall.
     * @param player player to check for
     * @throws cantMoveThroughWallExeption used to check for when a player tries to move through a wall.
     */
    public void canMoveOntoWalls(Player player) throws cantMoveThroughWallExeption {
        Heading heading = player.getHeading();
        Space space = player.getSpace();

        if (!space.getWalls().isEmpty()) {
            for (Heading wallHeading : space.getWalls()) {

                if (wallHeading == player.getHeading()) {
                    canMoveThroughWall = false;
                    throw new cantMoveThroughWallExeption(player);
                }
            }
        }

        Space target = board.getNeighbour(space, heading);

        //Checking if player can move into the other field
        if (!target.getWalls().isEmpty()) {
            for (Heading wallHeading : target.getWalls()) {

                if ((wallHeading == Heading.SOUTH && player.getHeading() == Heading.NORTH)
                || (wallHeading == Heading.NORTH && player.getHeading() == Heading.SOUTH)) {
                    canMoveThroughWall = false;
                    throw new cantMoveThroughWallExeption(player);
                } else if((wallHeading == Heading.EAST && player.getHeading() == Heading.WEST)
                        || (wallHeading == Heading.WEST && player.getHeading() == Heading.EAST)){
                    canMoveThroughWall = false;
                    throw new cantMoveThroughWallExeption(player);
                }
            }
        }
    }


    // TODO: V2
    /**
     * This method moves the player forward, while checking for any field actions. It checks both for the
     * current player and also the player which is targeted.
     * The method also takes care of checking if the player is able to move through a wall.
     * @param player
     */
    public void moveForward(@NotNull Player player) {
        Space space = player.getSpace();

        if (player != null && player.board == board && space != null) {
            Heading heading = player.getHeading();
            Space target = board.getNeighbour(space, heading);

            if (target != null) {
                // XXX note that this removes another player from the space, when there
                //     is another player on the target. Eventually, this needs to be
                //     implemented in a way so that other players are pushed away!
                try {
                    canMoveOntoWalls(player);
                } catch (cantMoveThroughWallExeption e) {
                    // Empty catch statement.
                }

                Player targetedPlayer = null;
                Space otherSpace = board.getNeighbour(space, heading);

                if (otherSpace.getPlayer() != null) {
                    otherSpace = board.getNeighbour(otherSpace.getPlayer().getSpace(), heading);
                }

                if (target.getPlayer() != null && otherSpace.getPlayer() == null && canMoveThroughWall) {
                    targetedPlayer = target.getPlayer();
                    boolean again = true;
                    while(targetedPlayer.getSpace().getActions().size() > 0 && again) {
                        for (FieldAction fieldAction : player.getSpace().getActions()) {
                            if (fieldAction instanceof ConveyorBelt) {
                                fieldAction.doAction(this, targetedPlayer.getSpace());
                            } else if (fieldAction instanceof BlueConveyorBelt) {
                                fieldAction.doAction(this, targetedPlayer.getSpace());
                                fieldAction.doAction(this, targetedPlayer.getSpace());
                            } else if (fieldAction instanceof Gear) {
                                fieldAction.doAction(this, targetedPlayer.getSpace());
                                again = false;
                                break;
                            } else if (fieldAction instanceof Checkpoint) {
                                fieldAction.doAction(this, targetedPlayer.getSpace());
                                again = false;
                                break;
                            } else if (fieldAction instanceof PushPanel){
                                fieldAction.doAction(this, targetedPlayer.getSpace());
                                again = false;
                                break;
                            }
                        }
                    }

                    Player cp = targetedPlayer;
                    space = cp.getSpace();

                    // Space targetNew = board.getNeighbour(space, heading);
                    // targetNew.setPlayer(targetedPlayer);
                }
            }

            if (target.getPlayer() == null && canMoveThroughWall) {
                target.setPlayer(player);
            }

            boolean again = true;

            while(player.getSpace().getActions().size() > 0 && again){
                for (FieldAction fieldAction : player.getSpace().getActions()) {

                    if (fieldAction instanceof ConveyorBelt) {
                        fieldAction.doAction(this, player.getSpace());
                    } else if (fieldAction instanceof BlueConveyorBelt) {
                        fieldAction.doAction(this, player.getSpace());
                        fieldAction.doAction(this, player.getSpace());
                    } else if(fieldAction instanceof Gear){
                        fieldAction.doAction(this, player.getSpace());
                        again = false;
                        break;
                    } else if(fieldAction instanceof Checkpoint){
                        fieldAction.doAction(this, player.getSpace());
                        again = false;
                        break;
                    } else if (fieldAction instanceof PushPanel){
                        fieldAction.doAction(this, player.getSpace());
                        again = false;
                        break;
                    }
                }
            }
        }
        if(!canMoveThroughWall){
            canMoveThroughWall = true;
        }
    }

    // TODO: V2
    /**
     * This method uses the previous moveForward method two times.
     * @param player player to be moved.
     */
    public void fastForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }

    // TODO: V2
    /**
     * This method turns the heading of a player by using the setHeading method in the player class.
     * @param player player to be turned
     */
    public void turnRight(@NotNull Player player) {
        if (player.board == board) {
            player.setHeading(player.getHeading().next());
        }
    }

    // TODO: V2
    /**
     * This method turns the heading of a player by using the setHeading method in the player class.
     * @param player player to be turned
     */
    public void turnLeft(@NotNull Player player) {
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().prev());
        }
    }

    /**
     * This method moves a player forward three times by using the moveForward method.
     * @param player player to be moved
     */
    public void tripleForward (@NotNull Player player){
        moveForward(player);
        moveForward(player);
        moveForward(player);
    }

    /**
     * This method makes the player do a u-turn dependent on which directing they are already heading.
     * @param player player to be turned
     */
    public void uTurn (@NotNull Player player){
        switch (player.getHeading()) {
            case NORTH -> player.setHeading(Heading.SOUTH);
            case EAST -> player.setHeading(Heading.WEST);
            case WEST -> player.setHeading(Heading.EAST);
            case SOUTH -> player.setHeading(Heading.NORTH);
        }
    }

    /**
     * This method makes the player move backwards while still keeping their heading intact.
     * @param player player to be backed up
     */
    public void backUp(@NotNull Player player){
        uTurn(player);
        moveForward(player);
        uTurn(player);
    }

    /**
     * This method is used for checking if the player has already moved a command card.
     * @param source command card to be moved
     * @param target place where command card is placed
     * @return true or false
     */
    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is called when no corresponding controller operation is implemented yet.
     * @param chosenCommand command card to be executed.
     */
    public void executeCommandOptionAndContinue(Command chosenCommand) {
        Player currentPlayer = board.getCurrentPlayer();

        if(currentPlayer != null && board.getPhase() == Phase.PLAYER_INTERACTION && chosenCommand != null) {
            board.setPhase(Phase.ACTIVATION);
            executeCommand(currentPlayer, chosenCommand);

            int nextPlayer = 1 + board.getPlayerNumber(currentPlayer);

            if (nextPlayer < board.getPlayersNumber()) {
                board.setCurrentPlayer(board.getPlayer(nextPlayer));
            } else {
                int step = board.getStep() + 1;

                if (step < Player.NO_REGISTERS) {
                    makeProgramFieldsVisible(step);
                    board.setStep(step);
                    board.setCurrentPlayer(board.getPlayer(0));
                } else {
                    startProgrammingPhase();
                }
            }
        }
    }

    /**
     * This method is used for finding the winner. And when found, an alert message saying
     * @param player player to win
     */
    public void findWinner(Player player) {
        Alert winMessage = new Alert(Alert.AlertType.INFORMATION, player.getName() + " won. Congratulations!");
        winMessage.showAndWait();
        System.exit(0);
    }
}