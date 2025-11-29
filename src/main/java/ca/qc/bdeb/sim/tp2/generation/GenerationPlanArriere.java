package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

abstract public class GenerationPlanArriere {

    protected final Camera camera;
    protected Point2D position = Point2D.ZERO;
    protected final double differencePositionnement = 2000;

    public GenerationPlanArriere(Camera camera) {
        this.camera = camera;
    }

    public Point2D getPosition() {
        return position;
    }

    public void update(double mouvementVersGauche)  {
        position = new Point2D(position.getX() - mouvementVersGauche, position.getY());
    };

    public abstract void draw(GraphicsContext context);
}
