package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class GenerationBoitesAuxLettres extends GenerationPlanArriere {

    private ArrayList<BoiteALettre> boites = new ArrayList<>();

    public GenerationBoitesAuxLettres(Camera camera, ArrayList<Double> xPositionAdresses, ArrayList<Boolean> abonnements) {
        super(camera);
        for (int i = 0; i < xPositionAdresses.size(); i++) {
            double xPositionAdresse = xPositionAdresses.get(i);
            boolean statusAbonnement = abonnements.get(i);
            double yPosition = (0.2 + Math.random() * 0.5) * JavaFX.h;
            boites.add(new BoiteALettre(camera, xPositionAdresse, statusAbonnement, yPosition));
        }
    }

    @Override
    public void update(double mouvementVersGauche) {
        super.update(mouvementVersGauche);
        for (BoiteALettre boiteALettre : boites) {
            boiteALettre.update(mouvementVersGauche);
        }
    }

    public void draw(GraphicsContext context) {
        for (BoiteALettre b : boites) {
            b.draw(context);
        }
    }

}
