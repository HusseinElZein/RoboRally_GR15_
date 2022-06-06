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
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.*;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.ViewComponents.ViewBlueConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.view.ViewComponents.ViewCheckpoint;
import dk.dtu.compute.se.pisd.roborally.view.ViewComponents.ViewConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.view.ViewComponents.ViewGear;
import dk.dtu.compute.se.pisd.roborally.view.ViewComponents.ViewWall;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    private void updatePlayer() {

        if (this.getShape() instanceof Polygon) {
            this.getChildren().clear();
        }

        Player player = space.getPlayer();
        if (player != null) {


            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0);
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90 * player.getHeading().ordinal()) % 360);
            this.getChildren().add(arrow);
        }
    }

    @Override
    public void updateView(Subject subject) {
        this.getChildren().clear();
        if (subject == this.space) {

            updateSpaceView();
            for (FieldAction fieldAction : space.getActions()) {

                if (fieldAction instanceof ConveyorBelt) {
                    ViewConveyorBelt.insertConveyorBeltView(this, fieldAction);
                }

                if (fieldAction instanceof BlueConveyorBelt) {
                    ViewBlueConveyorBelt.insertBlueConveyorBelt(this, fieldAction);
                }

                if (fieldAction instanceof Checkpoint) {
                    ViewCheckpoint.insertCheckpointView(this, fieldAction);
                }

                if (fieldAction instanceof Gear) {
                    ViewGear.insertGearView(this, fieldAction);
                }

                if (this.space.getWalls().size() > 0) {
                    ViewWall.drawWall(this, this.space);
                }

            }
            updatePlayer();
        }
    }

    public void updateSpaceView() {
        Image image = new Image("Images/Space.png", 60, 60, true, true);
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0);
        this.getChildren().add(canvas);
    }
}