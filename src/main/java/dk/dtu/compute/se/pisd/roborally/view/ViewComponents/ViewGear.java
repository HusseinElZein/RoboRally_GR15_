package dk.dtu.compute.se.pisd.roborally.view.ViewComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.Gear;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ViewGear {
    public static void insertGearView(SpaceView spaceView, FieldAction fieldAction) {
        Gear gear = (Gear) fieldAction;
        Heading header = gear.getHeading();
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Image gearRight = new Image("Images/leftGear.png", 50, 50, true, true);
        Image gearLeft = new Image("Images/rightGear.png", 50, 50, true, true);
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
