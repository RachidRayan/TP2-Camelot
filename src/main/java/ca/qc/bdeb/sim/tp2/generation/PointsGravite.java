package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class PointsGravite extends GenerationPlanArriere{

    private final int rayon = 10;

    Random randomCoulour = new Random();
    private double teinte = randomCoulour.nextInt(0,360) ;// random entre 0 et 360
    private Color couleur = Color.hsb(teinte, 1, 1);
    private int charge; //q
    private int constaneCoulomb ; //k

    private boolean debugModeDraw;
    public void setDebugModeDraw(boolean debugModeDraw) {
        this.debugModeDraw = debugModeDraw;
    }

    private Point2D positionMonde;
    Random positionInitiale = new Random();

    public PointsGravite(Camera camera) {
        super(camera);
        this.positionMonde = new Point2D(positionInitiale.nextInt(JavaFX.w, JavaFX.w *20 ) , positionInitiale.nextInt(0,JavaFX.h));
        this.charge = 900;
        this.constaneCoulomb = 90;
        this.debugModeDraw = false;
    }

    @Override
    public void update(double mouvementVersGauche) {
        positionMonde = new Point2D(positionMonde.getX() - mouvementVersGauche, positionMonde.getY());
    }

    public void draw(GraphicsContext context){
        Point2D coordoEcran = camera.coordoEcran(positionMonde);
        context.setFill(couleur);
        context.fillOval(coordoEcran.getX(), coordoEcran.getY(),rayon,rayon);
    }

    public Point2D champsElectrique(Journal journal){

        //calcule r
        Point2D r = journal.getPosition().subtract(positionMonde);


        double distance = r.magnitude();
        if (distance < 1) distance = 1;

        double Ei = constaneCoulomb * Math.abs(charge) / (distance * distance);

        Point2D direction = r.normalize();

        return direction.multiply(Ei);

    }


}
