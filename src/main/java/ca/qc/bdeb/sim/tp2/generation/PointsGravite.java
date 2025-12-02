package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.UtilitairesDessins;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class PointsGravite extends GenerationPlanArriere {

    protected final static int rayon = 10;

    Random randomCoulour = new Random();
    private double teinte = randomCoulour.nextInt(0, 360);
    protected static int chargeParticule; //q d'une particule
    protected static int constaneCoulomb; //k


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
        this.chargeParticule = 900;
        this.constaneCoulomb = 90;
        this.montrerChampsElectrique = false;
    }

    //Update
    @Override
    public void update(double mouvementVersGauche) {
        positionMonde = new Point2D(positionMonde.getX() - mouvementVersGauche, positionMonde.getY());
    }

    //Dessiner les particules
    public void draw(GraphicsContext context) {
        Point2D coordoEcran = camera.coordoEcran(positionMonde);
        context.setFill(Color.hsb(teinte, 1, 1));
        context.fillOval(coordoEcran.getX(), coordoEcran.getY(), rayon, rayon);
    }

    //Calcule de Ei
    public Point2D champsElectrique(Point2D point2D) {

        Point2D distance2D = point2D.subtract(positionMonde); //Distance en x,y

        double r = distance2D.magnitude(); //Pythagore
        if (r < 1) { r = 1; }

        double Ei = constaneCoulomb * Math.abs(chargeParticule) / (r * r); //Module du champs éléctrique

        Point2D orientation = distance2D.normalize();//Vecteur orientation

        return orientation.multiply(Ei); //retourne le vecteur du champs electrique

    }


}
