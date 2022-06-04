package dk.dtu.compute.se.pisd.roborally.view.ViewComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ViewCheckpoint {

    public static void drawCheckpoint(SpaceView spaceView, FieldAction fieldAction){
        Checkpoint checkpoint = (Checkpoint) fieldAction;
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        try {
            Image flagImage = new Image("Images/flag.png", 50, 50, true, true);
            graphicsContext.drawImage(flagImage, 0, 0);
        }
        catch (Exception e){
            System.out.print("flag.png not found.");
        }
        graphicsContext.setLineWidth(1);
        graphicsContext.strokeText(String.valueOf(checkpoint.getCheckpoints()),
                SpaceView.SPACE_WIDTH/2, SpaceView.SPACE_WIDTH * 0.8);
        spaceView.getChildren().add(canvas);
    }
}
