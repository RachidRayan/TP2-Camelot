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

    @Override
    public void update(double deltaTemps){
        position = position.add(velocite.multiply(deltaTemps));
        camera.setPositionCamera(position);
    }
    @Override
    public void draw(GraphicsContext context){
        Point2D positionEcran = camera.coordoEcran(position.multiply(-1));
        
        //Verifie où doivent etre placer les briques selon l'horizontale
        for (double largeur = 0; largeur < positionEcran.getX()*-1+JavaFX.w; largeur+= imageBrique.getWidth()) {
            //Placer des brique verticalement
            for (double hauteur = 0; hauteur < JavaFX.h; hauteur += imageBrique.getHeight()) {
                context.drawImage(imageBrique, positionEcran.getX() + largeur, positionEcran.getY() + hauteur);
            }
        }
    }

}
