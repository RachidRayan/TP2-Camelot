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

    protected static final int[] numerosAdresse = setAdresse();
    public static int[] getNumerosAdresse() {
        return numerosAdresse;
    }

    //Numero d'adresse des maison pour livrer les journaux
    public static int[] setAdresse(){
        int[] numeros = new int[8];
        Random r = new Random();
        numeros[0] = r.nextInt(80);
        for (int i = 1; i < numeros.length; i++) {
            numeros[i] = numeros[0] +i*2 ;
            System.out.println(numeros[i]);
        }
        return numeros;
    }


    public void update(double deltaTemps){
        position = position.add(velocite.multiply(deltaTemps));
        camera.setPositionCamera(position);
    }

    //Porte avec adresse
    public void draw(GraphicsContext context) {
        Point2D positionEcran = camera.coordoEcran(position.multiply(-1));


        for (int i = 0; i < getNumerosAdresse().length*1.5; i++) {
            //Draw porte
            context.setFill(Color.BLACK);
            context.fillRect(positionEcran.getX() + differencePositionnement *i, JavaFX.h- hauteurPorte, largeurPorte, hauteurPorte);

            //Draw numero sur la porte, incluant des mauvais adresses
            context.setFill(Color.WHITE);
            context.setFont(new Font(80));
            context.fillText(String.valueOf(getNumerosAdresse()[0]+i),positionEcran.getX() + differencePositionnement *i,JavaFX.h- hauteurPorte +100);
        }
    }
}
