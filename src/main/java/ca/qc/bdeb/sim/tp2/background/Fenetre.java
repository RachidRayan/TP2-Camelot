package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

public class Fenetre extends Bg {

    Image fenetre = new Image("fenetre.png");
    Image fenetreVerte = new Image("fenetre-brisee-vert.png");
    Image fenetreRouge = new Image("fenetre-brisee-rouge.png");


    @Override
    public void update(double deltaTemps) {
        position = position.add(velocite.multiply(deltaTemps));
        camera.setPositionCamera(position);
    }

    @Override
    public void draw(GraphicsContext context) {
        Point2D positionEcran = camera.coordoEcran(position.multiply(-1));

        for (int i = 0; i < numerosAdresse.length * 1.5; i++) {
            //Changer les constantes
            context.drawImage(fenetre,positionEcran.getX() + differencePositionnement *i+200, 200);

        }
    }

}
