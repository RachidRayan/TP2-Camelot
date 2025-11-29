package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;

public class GenerationMaisons extends GenerationPlanArriere {

    private final double hauteurPorte = 250;
    private final double largeurPorte = 150;
    private final double differencePositionnement = 1300; //difference entre les portes

    private ArrayList<Integer> adresses = new ArrayList<>();
    private ArrayList<Double> xPositionAdresses = new ArrayList<>();

    private ArrayList<Integer> adressesAbonnees = new ArrayList<>();
    private ArrayList<Boolean> abonnementsListe = new ArrayList<>();

    public GenerationMaisons(Camera camera) {
        super(camera);
        generationAdresses();
    }

    public ArrayList<Integer> getAdressesAbonnees() {
        return adressesAbonnees;
    }

    public ArrayList<Double> getXPositionAdresses() {
        return xPositionAdresses;
    }

    public ArrayList<Boolean> getAbonnementsListe() {
        return abonnementsListe;
    }

    // Méthode de génération des adresses
    public void generationAdresses() {
        Random random = new Random();
        int premiereAdresse = random.nextInt(100,950);

        int nombreAdressesRestant = 12;

        for (int i = 0; i < nombreAdressesRestant; i++) {
            int adresse = premiereAdresse + 2 * i;
            adresses.add(adresse);

            double xPositon = differencePositionnement + i * differencePositionnement;
            xPositionAdresses.add(xPositon);

            verificationAbonnement(adresse, xPositon);
        }
        System.out.println(adresses);
        System.out.println(abonnementsListe);

    }
    // Méthode de génération (vérification) si l'adresse est abonnée ou non
    public void verificationAbonnement(int adresse, double xPosition) {
        Random chanceRandom = new Random();
        int chance = chanceRandom.nextInt(0,2);
        if (chance == 1) {
            adressesAbonnees.add(adresse);
            abonnementsListe.add(true); // Status abonnée
        }
        else {
            abonnementsListe.add(false); // Status non abonnée
        }
    }

    // Méthode de dessin des portes avec adresse
    @Override
    public void draw(GraphicsContext context) {
        Point2D positionEcran = camera.coordoEcran(position);
        int sautAdresse = 1;

        for (int adresse : adresses) {
            context.setFill(Color.BROWN);
            context.fillRect(positionEcran.getX() + differencePositionnement * sautAdresse,JavaFX.h- hauteurPorte, largeurPorte, hauteurPorte);

            context.setFill(Color.YELLOW);
            context.setFont(new Font(60));
            context.fillText(String.valueOf(adresse),positionEcran.getX() + differencePositionnement * sautAdresse,JavaFX.h- hauteurPorte);
            sautAdresse++;
        }


    }
}
