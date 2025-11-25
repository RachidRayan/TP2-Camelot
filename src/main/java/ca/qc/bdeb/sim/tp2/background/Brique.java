package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brique {


    Image imageBrique = new Image("brique.png");
    Camera camera = new Camera();


    Point2D position = Point2D.ZERO;
    Point2D velocite = new Point2D(-30,0);

    public void update(double deltaTemps){
        position = position.add(velocite.multiply(deltaTemps));
        camera.setPositionCamera(position);
    }

    public void draw(GraphicsContext context){
        Point2D positionEcran = camera.coordoEcran(position);
        double width =0;

        while (width < JavaFX.w) {
            for (double height = 0; height < JavaFX.h; height += imageBrique.getHeight()) {
                context.drawImage(imageBrique, positionEcran.getX() + width, positionEcran.getY() + height);
            }
            width += imageBrique.getWidth();
        }
    }

}
