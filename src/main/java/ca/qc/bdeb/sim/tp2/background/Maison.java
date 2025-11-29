package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;

public class Maison extends PlanArriere {

    private final double hauteurPorte = 250;
    private final double largeurPorte = 150;
    private final double differencePositionnement = 1000; //difference entre les portes

    private ArrayList<Integer> adresses = new ArrayList<>() ;
    private ArrayList<Integer> adressesAbonnees = new ArrayList<>();
    private ArrayList<Integer> adressesNonAbonnees = new ArrayList<>();

    public Maison(Camera camera) {
        super(camera);
        setAdresses();
        System.out.println(adresses);

        System.out.println(adressesAbonnees);

        System.out.println(adressesNonAbonnees);
    }

    public ArrayList<Integer> getAdressesAbonnees() {
        return adressesAbonnees;
    }

    public ArrayList<Integer> getAdressesNonAbonnees() {
        return adressesNonAbonnees;
    }


    public void setAdresses(){
        Random random = new Random();

        int premiereAdresse = random.nextInt(100,950);
        adresses.add(premiereAdresse);
        verifivcationAbonnement(premiereAdresse);

        int nombreAdressesRestant = 11;

        for (int i = 0; i < nombreAdressesRestant; i++) {
            int adresseProchaine = premiereAdresse + 2;
            premiereAdresse = adresseProchaine;
            adresses.add(adresseProchaine);
            verifivcationAbonnement(adresseProchaine);
        }

    }

    public void verifivcationAbonnement(int adresse) {
        Random chanceRandom = new Random();
        int chance = chanceRandom.nextInt(0,2);
        if (chance == 1) {
            adressesAbonnees.add(adresse);
        }
        else {
            adressesNonAbonnees.add(adresse);
        }
    }


//    public void update(double deltaTemps){
//        position = position.add(velocite.multiply(deltaTemps));
//    }

    //Porte avec adresse
    public void draw(GraphicsContext context) {
        Point2D positionEcran = camera.coordoEcran(position);

        for (int adresse : adresses) {
            context.setFill(Color.BROWN);
            context.fillRect(positionEcran.getX() + differencePositionnement , JavaFX.h- hauteurPorte, largeurPorte, hauteurPorte);

            context.setFill(Color.YELLOW);
            context.setFont(new Font(80));
            context.fillText(String.valueOf(adresse),positionEcran.getX() + differencePositionnement,JavaFX.h- hauteurPorte);
        }
//
//        for (int i = 0; i < nombreAdressesTotal; i++) {
////            int number = adresses[i];
//
//            context.setFill(Color.BROWN);
//            context.fillRect(positionEcran.getX() + differencePositionnement *i, JavaFX.h- hauteurPorte, largeurPorte, hauteurPorte);
//
//            context.setFill(Color.YELLOW);
//            context.setFont(new Font(80));
//            context.fillText(String.valueOf(adresses.indexOf(i)),positionEcran.getX() + differencePositionnement *i,JavaFX.h- hauteurPorte);
//        }
    }
}
