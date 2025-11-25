package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.w3c.dom.Text;

import java.util.Random;

public class Maison extends Bg {

    double HeightDoor = 250;
    double WidthDoor = 150;
    double Difference = 2000; //difference entre les portes

    int[] adressNumbers = setAdresse();
    public int[] getAdressNumbers() {
        return adressNumbers;
    }
    public void setAdressNumbers(int[] adressNumbers) {
        this.adressNumbers = adressNumbers;
    }

    public int[] setAdresse(){
        Random style = new Random();
        int thirdDigit = style.nextInt(8);

        int[] numbers = new int[8];
        for (int i = 0; i < numbers.length; i++) {
            Random r = new Random();
            numbers[i] = r.nextInt(100);
            numbers[i] += thirdDigit*100;
        }

        return numbers;
    }


    public void update(double deltaTemps){
        position = position.add(velocite.multiply(deltaTemps));
        camera.setPositionCamera(position);
    }

    //Porte avec adresse
    public void draw(GraphicsContext context) {
        Point2D positionEcran = camera.coordoEcran(position.multiply(-1));


        for (int i = 0; i < adressNumbers.length; i++) {
            int number = adressNumbers[i];

            context.setFill(Color.BLACK);
            context.fillRect(positionEcran.getX() + Difference *i, JavaFX.h-HeightDoor,WidthDoor,HeightDoor);

            context.setFill(Color.WHITE);
            context.setFont(new Font(80));
            context.fillText(String.valueOf(adressNumbers[i]),positionEcran.getX() + Difference*i,JavaFX.h-HeightDoor+100);
        }
    }
}
