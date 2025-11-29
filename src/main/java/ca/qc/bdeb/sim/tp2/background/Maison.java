package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

public class Maison extends PlanArriere {

    private final double hauteurPorte = 250;
    private final double largeurPorte = 150;
    private final double differencePositionnement = 2000; //difference entre les portes

    private  int[] adresses = setAdresse();

    public Maison(Camera camera) {
        super(camera);
    }

    public int[] getAdresses() {
        return adresses;
    }
    public void setAdresses(int[] adresses) {
        this.adresses = adresses;
    }

    public int[] setAdresse(){
        Random random = new Random();
        int troisiemeChiffre = random.nextInt(8);

        int[] numbers = new int[8];
        for (int i = 0; i < numbers.length; i++) {
            Random r = new Random();
            numbers[i] = r.nextInt(100);
            numbers[i] += troisiemeChiffre*100;
        }

        return numbers;
    }


//    public void update(double deltaTemps){
//        position = position.add(velocite.multiply(deltaTemps));
//    }

    //Porte avec adresse
    public void draw(GraphicsContext context) {
        Point2D positionEcran = camera.coordoEcran(position);


        for (int i = 0; i < adresses.length; i++) {
            int number = adresses[i];

            context.setFill(Color.BROWN);
            context.fillRect(positionEcran.getX() + differencePositionnement *i, JavaFX.h- hauteurPorte, largeurPorte, hauteurPorte);

            context.setFill(Color.YELLOW);
            context.setFont(new Font(80));
            context.fillText(String.valueOf(adresses[i]),positionEcran.getX() + differencePositionnement *i,JavaFX.h- hauteurPorte);
        }
    }
}
