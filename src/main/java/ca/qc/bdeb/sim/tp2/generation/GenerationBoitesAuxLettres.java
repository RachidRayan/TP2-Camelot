package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class GenerationBoitesAuxLettres extends GenerationPlanArriere {

    private ArrayList<BoiteALettre> boites = new ArrayList<>();

    public GenerationBoitesAuxLettres(Camera camera, ArrayList<Double> xPositionAdresses, ArrayList<Boolean> abonnements) {
        super(camera);
        for (int i = 0; i < xPositionAdresses.size(); i++) {
            double xPositionAdresse = xPositionAdresses.get(i);
            boolean abonnee = abonnements.get(i);
            double yPosition = (0.2 + Math.random() * 0.5) * JavaFX.h;
            boites.add(new BoiteALettre(camera, xPositionAdresse, abonnee, yPosition));
        }
        System.out.println(boites);
    }

    public void draw(GraphicsContext context) {
        for (BoiteALettre b : boites) {
            b.draw(context);
        }
    }

//    public void drawHitBoxes(javafx.scene.canvas.GraphicsContext context) {
//        for (BoiteALettre b : boites) {
//            b.hitBox(context);
//        }
//    }

//    // Returns total money earned from all collisions this frame
//    public int collisionAvecJournal(ArrayList<Journal> journaux) {
//        int argent = 0;
//        for (BoiteALettre b : boites) {
//            for (Journal j : journaux) {
//                argent += b.contactAvecJournal(j);
//            }
//        }
//        return argent;
//    }
}
