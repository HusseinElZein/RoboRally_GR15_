package dk.dtu.compute.se.pisd.roborally.view.ViewComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.BlueConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ViewBlueConveyorBelt {

    public static void insertBlueConveyorBelt(SpaceView spaceView, FieldAction fieldAction) {
        BlueConveyorBelt blueConveyorBelt = (BlueConveyorBelt) fieldAction;
        Heading heading = blueConveyorBelt.getHeading();
        spaceView.getChildren().clear();
        try {
            Image conveyorPointRight = new Image("Images/blueConveyorRight.png", 60, 64.5, true, true);
            Image conveyorPointLeft = new Image("Images/blueConveyorLeft.png", 60, 64.5, true, true);
            Image conveyorPointUp = new Image("Images/blueConveyorUp.png", 60, 60, true, true);
            Image conveyorPointDown = new Image("Images/blueConveyorDown.png", 60, 60, true, true);

            Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
            GraphicsContext GraphicsContext = canvas.getGraphicsContext2D();

            switch (heading) {
                case EAST -> GraphicsContext.drawImage(conveyorPointRight, 0, 7);
                case WEST -> GraphicsContext.drawImage(conveyorPointLeft, 0, 7);
                case NORTH -> GraphicsContext.drawImage(conveyorPointUp, 0, 0);
                case SOUTH -> GraphicsContext.drawImage(conveyorPointDown, 0, 0);
            }

            spaceView.getChildren().add(canvas);
        }
        catch(Exception e){
            System.out.print("Images for Conveyor belts are not in files.");
        }
    }


}
