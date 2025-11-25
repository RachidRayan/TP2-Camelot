package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.entites.Camelot;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brique extends Bg {

    Image imageBrique = new Image("brique.png");
    Camera camera = new Camera();

//    Point2D position = Point2D.ZERO;
//    Point2D velocite = new Point2D(100,0);

    @Override
    public void update(double deltaTemps){
        position = position.add(velocite.multiply(deltaTemps));
        camera.setPositionCamera(position);
    }
    @Override
    public void draw(GraphicsContext context){
        Point2D positionEcran = camera.coordoEcran(position.multiply(-1));
        double width =0;

        while (width < positionEcran.getX()*-1+JavaFX.w) {
            for (double height = 0; height < JavaFX.h; height += imageBrique.getHeight()) {
                context.drawImage(imageBrique, positionEcran.getX() + width, positionEcran.getY() + height);
            }
            width += imageBrique.getWidth();
        }
    }

}
