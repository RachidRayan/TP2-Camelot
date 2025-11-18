package ca.qc.bdeb.sim.tp2.background;

import java.util.Random;

public class Maison {

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
}
