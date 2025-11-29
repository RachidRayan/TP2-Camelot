package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brique extends PlanArriere {

    private Image imageBrique = new Image("brique.png");

    public Brique(Camera camera) {
       super(camera);
    }

    // Logique pour dessin des briques
    @Override
    public void draw(GraphicsContext context){
        Point2D positionEcran = camera.coordoEcran(position);

        double xDecalage = positionEcran.getX() % imageBrique.getWidth();
        double yDecalage = positionEcran.getY() % imageBrique.getHeight();

        for (double y = yDecalage - imageBrique.getHeight(); y < JavaFX.h; y += imageBrique.getHeight()) {
            for (double x = xDecalage - imageBrique.getWidth(); x < JavaFX.w; x += imageBrique.getWidth()) {
                context.drawImage(imageBrique, x, y);
            }
        }

    }

}
