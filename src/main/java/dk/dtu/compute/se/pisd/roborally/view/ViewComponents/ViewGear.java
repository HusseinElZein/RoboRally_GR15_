package dk.dtu.compute.se.pisd.roborally.view.ViewComponents;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.Gear;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
/**
 * The visual representation of the GearView is implemented below
 */

public class ViewGear {
    public static void insertGearView(SpaceView spaceView, FieldAction fieldAction) {
        /**
         * creating gear and the direction it rotates the robots in
         */
        Gear gear = (Gear) fieldAction;
        Heading header = gear.getHeading();
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        /**
         * Red gears rotate left, and green gears rotate right.
         */
        Image gearRight = new Image("Images/leftGear.png", 60, 60, true, true);
        Image gearLeft = new Image("Images/rightGear.png", 60, 60, true, true);
        switch (header) {
            case WEST -> {
                            graphicsContext.setStroke(Color.GREEN);
                            graphicsContext.drawImage(gearLeft, 0,0);
                         }
            case EAST -> {
                            graphicsContext.setStroke(Color.RED);
                            graphicsContext.drawImage(gearRight,0,0);
                         }
        }
        spaceView.getChildren().add(canvas);
    }
}
