package dk.dtu.compute.se.pisd.roborally.view.ViewComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.Wall;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;



public class ViewConveyorBelt {

    public static void insertConveyorBeltView(SpaceView spaceView, FieldAction fieldAction) {
        ConveyorBelt conveyorBelt = (ConveyorBelt) fieldAction;
        spaceView.getChildren().clear();

        Rectangle rectangle = new Rectangle(50, 50);
        Polygon arrow = new Polygon(0.0, 0.0,
                15.0, 30.0,
                30.0, 0.0);
        double v = 0;

        switch (conveyorBelt.getHeading()) {
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
        spaceView.getChildren().add(rectangle);
        spaceView.getChildren().add(arrow);
    }
}