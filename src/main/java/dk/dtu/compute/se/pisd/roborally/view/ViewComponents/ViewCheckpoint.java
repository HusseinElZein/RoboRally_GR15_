package dk.dtu.compute.se.pisd.roborally.view.ViewComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class ViewCheckpoint {
    public static void drawCheckpoint(SpaceView spaceView, FieldAction fieldAction){
        Checkpoint checkpoints = new Checkpoint();
        spaceView.getChildren().clear();

        Circle circle = new Circle(30);
        circle.setRadius(25);
        circle.setFill(Color.YELLOW);

        javafx.scene.shape.Polygon flag = new Polygon(0.0, 0.0,
                10.0, 25.0,
                25.0, 5.0);

        spaceView.getChildren().add(circle);
        spaceView.getChildren().add(flag);


        /*javafx.scene.shape.Rectangle rectangle = new Rectangle(50, 50);
        rectangle.setFill(Color.YELLOW);
        spaceView.getChildren().add(rectangle);
         */
    }
}
