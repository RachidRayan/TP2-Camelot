package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;


public class GenerationFenetres extends GenerationPlanArriere {

    private ArrayList<Fenetre> fenetres = new ArrayList<>();

    private boolean debugMode;

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public GenerationFenetres(Camera camera, ArrayList<Double> xPositionAdresses, ArrayList<Boolean> abonnements) {
        super(camera);
        Random random = new Random();
        for (int i = 0; i < xPositionAdresses.size(); i++) {
            double xPositionAdresse = xPositionAdresses.get(i);
            boolean statusAbonnement = abonnements.get(i);
            int nombreFenetres = random.nextInt(0,3);
            if (nombreFenetres == 1) {
                fenetres.add(new Fenetre(camera, xPositionAdresse, statusAbonnement, 60));
            }
            else if (nombreFenetres == 2) {
                fenetres.add(new Fenetre(camera, xPositionAdresse, statusAbonnement, 60));
                fenetres.add(new Fenetre(camera, xPositionAdresse + 300, statusAbonnement, 60));
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
        for (Fenetre fenetre : fenetres) {
            fenetre.setDebugModeDraw(debugMode);
            fenetre.draw(context);
        }
    }

    public int collisionAvecJournal(ArrayList<Journal> journaux) {
        int argent = 0;
        for (Fenetre fenetre : fenetres) {
            for (Journal journal : journaux) {
                argent += fenetre.contactAvecJournal(journal);
            }
        }
        return argent;
    }
}
