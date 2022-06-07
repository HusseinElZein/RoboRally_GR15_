package dk.dtu.compute.se.pisd.roborally.view.ViewComponents;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.SpaceComponents.PushPanel;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class ViewPushpanel {

    public static void insertPushPanelView (SpaceView spaceview, FieldAction fieldAction) {
            PushPanel pushPanel = (PushPanel) fieldAction;
            Heading heading = pushPanel.getHeading();
     spaceview.getChildren().clear();
            try {
                javafx.scene.image.Image PointRight = new javafx.scene.image.Image("Images/pushPanelRight.png", 60, 60, true, true);
                javafx.scene.image.Image PointLeft = new javafx.scene.image.Image("Images/pushPanelLeft.png", 60, 60, true, true);
                javafx.scene.image.Image PointUp = new javafx.scene.image.Image("Images/pushPanelUp.png", 60, 60, true, true);
                javafx.scene.image.Image PointDown = new Image("Images/pushPanelDown.png", 60, 60, true, true);

                javafx.scene.canvas.Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
                GraphicsContext GraphicsContext = canvas.getGraphicsContext2D();

                switch (heading) {
                    case EAST -> GraphicsContext.drawImage(PointRight, 0, 0);
                    case WEST -> GraphicsContext.drawImage(PointLeft, 0, 0);
                    case NORTH -> GraphicsContext.drawImage(PointUp, 0, 1);
                    case SOUTH -> GraphicsContext.drawImage(PointDown, 0, 0);
                }

                spaceview.getChildren().add(canvas);
            }
            catch(Exception e){
                System.out.print("Images for pushing panels are not in files.");
            }
        }



    }


