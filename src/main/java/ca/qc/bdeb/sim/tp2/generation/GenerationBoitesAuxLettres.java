package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class GenerationBoitesAuxLettres extends GenerationPlanArriere {

    private ArrayList<BoiteAuxLettres> boites = new ArrayList<>();

    public GenerationBoitesAuxLettres(Camera camera, ArrayList<Double> xPositionAdresses, ArrayList<Boolean> abonnements) {
        super(camera);
        for (int i = 0; i < xPositionAdresses.size(); i++) {
            double xPositionAdresse = xPositionAdresses.get(i);
            boolean statusAbonnement = abonnements.get(i);
            double yPosition = (0.2 + Math.random() * 0.5) * JavaFX.h + 40;
            boites.add(new BoiteAuxLettres(camera, xPositionAdresse, statusAbonnement, yPosition));
        }
    }

    @Override
    public void update(double mouvementVersGauche) {
        super.update(mouvementVersGauche);
        for (BoiteAuxLettres boiteALettre : boites) {
            boiteALettre.update(mouvementVersGauche);
        }
    }

    public void draw(GraphicsContext context) {
        for (BoiteAuxLettres b : boites) {
            b.draw(context);
        }
    }

    public int collisionAvecJournal(ArrayList<Journal> journaux) {
        int argent = 0;
        for (BoiteAuxLettres boiteALettre : boites) {
            for (Journal journal : journaux) {
                argent += boiteALettre.contactAvecJournal(journal);
            }
        }
        return argent;
    }

}
