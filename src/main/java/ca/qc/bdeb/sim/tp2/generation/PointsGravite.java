package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.UtilitairesDessins;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.swing.text.Position;
import java.util.Random;

public class PointsGravite extends GenerationPlanArriere {

    private final int rayon = 10;

    Random randomCoulour = new Random();
    private double teinte = randomCoulour.nextInt(0, 360);// random entre 0 et 360
    private int charge; //q
    private int constaneCoulomb; //k

    private boolean montrerChampsElectrique;

    public void setMontrerChampsElectrique(boolean montrerChampsElectrique) {
        this.montrerChampsElectrique = montrerChampsElectrique;
    }

    private Point2D positionMonde;
    public void setPositionMonde(Point2D positionMonde) {
        this.positionMonde = positionMonde;
    }

    public PointsGravite(Camera camera, Point2D positionMonde) {
        super(camera);
        this.positionMonde = positionMonde;
        this.charge = 900;
        this.constaneCoulomb = 90;
        this.montrerChampsElectrique = false;
    }

    @Override
    public void update(double mouvementVersGauche) {
        positionMonde = new Point2D(positionMonde.getX() - mouvementVersGauche, positionMonde.getY());
    }

    public void draw(GraphicsContext context) {
        Point2D coordoEcran = camera.coordoEcran(positionMonde);
        context.setFill(Color.hsb(teinte, 1, 1));
        context.fillOval(coordoEcran.getX(), coordoEcran.getY(), rayon, rayon);
        if (montrerChampsElectrique) {
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
