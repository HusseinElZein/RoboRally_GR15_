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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.Gear;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.Wall;
import dk.dtu.compute.se.pisd.roborally.view.ViewComponents.ViewConveyorBelt;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 60; // 75;
    final public static int SPACE_WIDTH = 60; // 75;

    public final Space space;


    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }

        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    private void updatePlayer() {

        Player player = space.getPlayer();
        if (player != null) {
            this.getChildren().clear();
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0 );
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90*player.getHeading().ordinal())%360);
            this.getChildren().add(arrow);
        }
    }

    private void updateWall(){

        Wall wall = space.getWall();
        if (wall != null) {
            Rectangle squareWall = new Rectangle(30, 30);
            try {
                squareWall.setFill(Color.DARKGRAY);
            } catch (Exception e) {
                squareWall.setFill(Color.DARKGRAY);
            }
            this.getChildren().add(squareWall);
        }
    }

    private void updateTransportField(){

        Gear gear = space.getTransportField();

        if (gear != null) {

            Circle circle = new Circle(10);

            try {
                circle.setFill(Color.GOLD);
            } catch (Exception e) {
                circle.setFill(Color.GOLD);
            }
            this.getChildren().add(circle);
        }
    }

    private void updateConveyorField(){
        ConveyorBelt conveyorBelt = space.conveyorBelt;

        if(conveyorBelt != null){
            Rectangle rectangle = new Rectangle(50, 50);
            Polygon arrow = new Polygon(0.0, 0.0,
                    15.0, 30.0,
                    30.0, 0.0);
            double v = 0;



            switch (conveyorBelt.getHeading()){
                case SOUTH -> v = 0;
                case WEST -> v = 90;
                case NORTH -> v = 180;
                case EAST -> v = 270;
            }

            arrow.setRotate(v);

            try {
                arrow.setFill(Color.BLACK);
                rectangle.setFill(Color.GREEN);
            } catch (Exception e) {
                arrow.setFill(Color.BLACK);
                rectangle.setFill(Color.GREEN);
            }
            this.getChildren().add(rectangle);
            this.getChildren().add(arrow);
        }
    }

    int x, y;


   /* @Override
    public void updateView(Subject subject) {
        x = space.board.width;
        y = space.board.width;

        if (subject == this.space) {
            updatePlayer();
            updateWall();
            updateTransportField();
            updateConveyorField();
        }
    }

    */

    @Override
    public void updateView(Subject subject) {
        //this.getChildren().clear();
        if (subject == this.space) {


            /*if(!this.space.getWalls().isEmpty()){
                WallView.drawWall(this, this.space);
            }

             */
            for(FieldAction fieldAction : space.getActions()) {

               /* else if (fieldAction instanceof Gear) {
                    GearView.drawGear(this, fieldAction);
                }
                else if (fa instanceof Checkpoint) {
                    CheckpointView.drawCheckpoint(this, fieldAction);
                }

                */

                if (fieldAction instanceof ConveyorBelt) {
                    ViewConveyorBelt.insertConveyorBeltView(this, fieldAction);
                }
            }
            updatePlayer();
        }
    }
}