package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public class Brique {

    Image imageBrique = new Image("brique.png");
    Camera camera = new Camera();


    Point2D position = Point2D.ZERO;
    Point2D velocite = new Point2D(-30,0);

    public void update(double deltaTemps){
        position = position.add(velocite.multiply(deltaTemps));
    }

    public void draw(GraphicsContext context){
//        Point2D positionEcran = camera.coordoEcran(position);

        for (double width = 0; width < JavaFX.w ; width += imageBrique.getWidth() ) {
            for (double height = 0; height < JavaFX.h; height += imageBrique.getHeight()) {
                context.drawImage(imageBrique, width, height);
            }
        }
    }




//    HBox wall = RangeeBrique();
//    public HBox getWall() {
//        return wall;
//    }
//
//    public VBox CologneBrique(){
//        VBox column = new VBox();
//        double height=0;
//
//        while (height< JavaFX.h){
//            column.getChildren().add(new ImageView(imageBrique));
//            height += imageBrique.getHeight();
//        }
//
//        return column;
//    }
//
//    public HBox RangeeBrique(){
//        HBox row = new HBox();
//        double width =0;
//
//        while(width<JavaFX.w){
//            row.getChildren().add(CologneBrique());
//            width += imageBrique.getWidth();
//        }
//
//        return row;
//    }




}
