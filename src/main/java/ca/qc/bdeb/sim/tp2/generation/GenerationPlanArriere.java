package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
// Classe GenerationPlanArriere (Génération des objets du plans arrière)
abstract public class GenerationPlanArriere {

    protected final Camera camera;
    protected Point2D position = Point2D.ZERO;

    // Constructeur
    public GenerationPlanArriere(Camera camera) {
        this.camera = camera;
    }
    // Méthode de renouvellement de base
    public void update(double mouvementVersGauche)  {
        position = new Point2D(position.getX() - mouvementVersGauche, position.getY());
    };
    // Méthode abstraite de dessin
    public abstract void draw(GraphicsContext context);
}
