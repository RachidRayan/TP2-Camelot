package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;


public class Maison extends Bg {



    protected static final double hauteurPorte = 250;
    protected static final double largeurPorte = 150;


    public void update(double deltaTemps){
        position = position.add(velocite.multiply(deltaTemps));
        camera.setPositionCamera(position);
    }

    //Porte avec adresse
    public void draw(GraphicsContext context) {
        Point2D positionEcran = camera.coordoEcran(position.multiply(-1));


        for (int i = 0; i < numerosAdresse.length*1.5; i++) {
            //Draw porte
            context.setFill(Color.BLACK);
            context.fillRect(positionEcran.getX() + differencePositionnement *i, JavaFX.h- hauteurPorte, largeurPorte, hauteurPorte);

            //Draw numero sur la porte, incluant des mauvais adresses
            context.setFill(Color.WHITE);
            context.setFont(new Font(80));
            context.fillText(String.valueOf(numerosAdresse[0]+i),positionEcran.getX() + differencePositionnement *i,JavaFX.h- hauteurPorte +100);
        }
    }
}
