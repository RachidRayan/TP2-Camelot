package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import org.w3c.dom.Text;

import java.util.Random;

public class Maison extends Bg {

    double HeightDoor = 300;
    double WidthDoor = 100;

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

    @Override
    public void update(double deltaTemps){
        position = position.add(velocite.multiply(deltaTemps));
        camera.setPositionCamera(position);
    }

    //Porte avec adresse
    @Override
    public void draw(GraphicsContext context) {
        Point2D positionEcran = camera.coordoEcran(position.multiply(-1));


        for (int i = 0; i < adressNumbers.length; i++) {
            int number = adressNumbers[i];
            javafx.scene.text.Text


            context.fillRect(JavaFX.w +positionEcran.getX(), positionEcran.getY()-HeightDoor,WidthDoor,HeightDoor);
            context.fillText(new Text(String.valueOf(adressNumbers[i])),JavaFX.w +positionEcran.getX(),positionEcran.getY()-HeightDoor);
        }
    }
}
