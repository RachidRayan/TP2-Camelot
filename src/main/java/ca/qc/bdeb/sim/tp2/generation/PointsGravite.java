package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.UtilitairesDessins;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class PointsGravite extends GenerationPlanArriere {

    private final int rayon = 10;

    Random randomCoulour = new Random();
    private double teinte = randomCoulour.nextInt(0, 360);// random entre 0 et 360
    private Color couleur = Color.hsb(teinte, 1, 1);
    private int charge; //q
    private int constaneCoulomb; //k

    private boolean debugModeDraw;

    public void setDebugModeDraw(boolean debugModeDraw) {
        this.debugModeDraw = debugModeDraw;
    }

    private Point2D positionMonde;
    Random positionInitiale = new Random();

    public PointsGravite(Camera camera) {
        super(camera);
        this.positionMonde = new Point2D(positionInitiale.nextInt(JavaFX.w, JavaFX.w * 20), positionInitiale.nextInt(0, JavaFX.h));
        this.charge = 900;
        this.constaneCoulomb = 90;
        this.debugModeDraw = false;
    }

    @Override
    public void update(double mouvementVersGauche) {
        positionMonde = new Point2D(positionMonde.getX() - mouvementVersGauche, positionMonde.getY());
    }

    public void draw(GraphicsContext context) {
        Point2D coordoEcran = camera.coordoEcran(positionMonde);
        context.setFill(Color.hsb(teinte, 1, 1));
        context.fillOval(coordoEcran.getX(), coordoEcran.getY(), rayon, rayon);
        if (debugModeDraw) {
            for (double x = 0; x < JavaFX.w; x += 50) {
                for (double y = 0; y < JavaFX.h; y += 50) {
                    Point2D positionMonde = new Point2D(x, y);

                    Point2D positionEcran = camera.coordoEcran(positionMonde);

                    Point2D force = champsElectrique( positionMonde);

                    UtilitairesDessins.dessinerVecteurForce(positionEcran, force, context);
                }
            }
        }
    }

    public Point2D champsElectrique(Point2D point2D) {

        Point2D r = point2D.subtract(positionMonde);

        double distance = r.magnitude();
        if (distance < 1) { distance = 1; }

        double Ei = constaneCoulomb * Math.abs(charge) / (distance * distance);

        Point2D direction = r.normalize();

        return direction.multiply(Ei);

    }


}
