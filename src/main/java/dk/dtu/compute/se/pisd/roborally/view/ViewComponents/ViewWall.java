package dk.dtu.compute.se.pisd.roborally.view.ViewComponents;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.List;
/**
 * The visual representation of the drawaWall is implemented below
 */
public class ViewWall {

    public static void drawWall(SpaceView spaceView, Space space) {
        /**
         * creating the wall and  its direction on the board
         */

        List<Heading> walls = space.getWalls();
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image verticalWall = new Image("Images/VerticalWall.png", 60, 60, true, true);
        Image horizontalWall = new Image("Images/HorizontalWall.png", 60, 60, true, true);
        /**
         * the direction for where the wall is placed on the fields
         */

        for (Heading heading : walls) {
            switch (heading) {
                case EAST -> gc.drawImage(horizontalWall, 44, 0);
                case WEST -> gc.drawImage(horizontalWall, 0, 0);
                case SOUTH -> gc.drawImage(verticalWall, 0, 44);
                case NORTH -> gc.drawImage(verticalWall, 0, 0);
            }
        }
        spaceView.getChildren().add(canvas);
    }
}