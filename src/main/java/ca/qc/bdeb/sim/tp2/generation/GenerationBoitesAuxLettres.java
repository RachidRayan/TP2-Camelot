package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

// Classe GenerationBoitesAuxLettres (Génération des boites aux lettres)
public class GenerationBoitesAuxLettres extends GenerationPlanArriere {

    private ArrayList<BoiteAuxLettres> boites = new ArrayList<>();

    private boolean debugMode;

    // Constructeur
    public GenerationBoitesAuxLettres(Camera camera, ArrayList<Double> xPositionAdresses, ArrayList<Boolean> abonnements) {
        super(camera);
        for (int i = 0; i < xPositionAdresses.size(); i++) {
            double xPositionAdresse = xPositionAdresses.get(i);
            boolean statusAbonnement = abonnements.get(i);
            double yPosition = (0.2 + Math.random() * 0.5) * JavaFX.hauteur + 40;
            boites.add(new BoiteAuxLettres(camera, xPositionAdresse, statusAbonnement, yPosition));

        }
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    // Méthode de renouvellement de GenerationBoitesAuxLettres (Génération des boites aux lettres)
    @Override
    public void update(double mouvementVersGauche) {
        super.update(mouvementVersGauche);
        for (BoiteAuxLettres boiteALettre : boites) {
            boiteALettre.update(mouvementVersGauche);
        }
    }
    // Méthode de dessin de GenerationBoitesAuxLettres (Génération des boites aux lettres)
    public void draw(GraphicsContext context) {
        for (BoiteAuxLettres boiteAuxLettres : boites) {
            boiteAuxLettres.setDebugModeDraw(debugMode);
            boiteAuxLettres.draw(context);
        }
    }

    // Méthode qui retourne l'argent gagné après la collision du journal avec les boites à lettres
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
