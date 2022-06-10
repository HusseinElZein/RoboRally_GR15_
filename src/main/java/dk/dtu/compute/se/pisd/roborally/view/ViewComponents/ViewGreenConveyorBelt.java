package dk.dtu.compute.se.pisd.roborally.view.ViewComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The visual representation of the conveyerbelt is implemented below
 */

public class ViewGreenConveyorBelt {

    public static void insertConveyorBeltView(SpaceView spaceView, FieldAction fieldAction) {
        ConveyorBelt conveyorBelt = (ConveyorBelt) fieldAction;
        Heading heading = conveyorBelt.getHeading();
        spaceView.getChildren().clear();
        try {
            Image conveyorPointRight = new Image("Images/conveyorRight.png", 64, 64, true, true);
            Image conveyorPointLeft = new Image("Images/conveyorLeft.png", 64, 64, true, true);
            Image conveyorPointUp = new Image("Images/conveyorUp.png", 64.5, 60, true, true);
            Image conveyorPointDown = new Image("Images/conveyorDown.png", 64.5, 60, true, true);

            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
            GraphicsContext GraphicsContext = canvas.getGraphicsContext2D();

            switch (heading) {
                case EAST -> GraphicsContext.drawImage(conveyorPointRight, 0, 0);
                case WEST -> GraphicsContext.drawImage(conveyorPointLeft, 1, 0);
                case NORTH -> GraphicsContext.drawImage(conveyorPointUp, -3, 0);
                case SOUTH -> GraphicsContext.drawImage(conveyorPointDown, -3, 0);
            }

            spaceView.getChildren().add(canvas);
        }
        catch(Exception e){
            System.out.print("Images for Conveyor belts are not in files.");
        }
    }
}