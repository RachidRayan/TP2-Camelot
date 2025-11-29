package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;


public class GenerationFenetres extends GenerationPlanArriere {

    private ArrayList<Fenetre> fenetres = new ArrayList<>();

    public GenerationFenetres(Camera camera, ArrayList<Double> xPositionAdresses, ArrayList<Boolean> abonnements) {
        super(camera);
        Random random = new Random();
        for (int i = 0; i < xPositionAdresses.size(); i++) {
            double xPositionAdresse = xPositionAdresses.get(i);
            boolean statusAbonnement = abonnements.get(i);
            int nombreFenetres = random.nextInt(1,3);
            if (nombreFenetres == 1) {
                double yPosition = (0.2 + Math.random() * 0.5) * JavaFX.h;
                fenetres.add(new Fenetre(camera, xPositionAdresse, statusAbonnement, yPosition));
            }
            else if (nombreFenetres == 2) {
                double yPosition = (0.2 + Math.random() * 0.5) * JavaFX.h;
                fenetres.add(new Fenetre(camera, xPositionAdresse, statusAbonnement, yPosition));
                yPosition = (0.2 + Math.random() * 0.5) * JavaFX.h;
                fenetres.add(new Fenetre(camera, xPositionAdresse + 500, statusAbonnement, yPosition));
            }

        }
    }

    @Override
    public void update(double mouvementVersGauche) {
        super.update(mouvementVersGauche);
        for (Fenetre fenetre : fenetres) {
            fenetre.update(mouvementVersGauche);
        }
    }

    public void draw(GraphicsContext context) {
        for (Fenetre fenetres : fenetres) {
            fenetres.draw(context);
        }
    }
    //coordonnée
    public double getX() {
        return camera.coordoEcran(position).getX();
    }
    public double getY() {
        return camera.coordoEcran(position).getY();
    }
}
