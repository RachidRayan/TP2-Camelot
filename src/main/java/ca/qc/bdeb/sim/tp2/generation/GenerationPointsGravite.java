package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.UtilitairesDessins;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ca.qc.bdeb.sim.tp2.generation.PointsGravite.chargeParticule;
import static ca.qc.bdeb.sim.tp2.generation.PointsGravite.constaneCoulomb;

public class GenerationPointsGravite extends GenerationPlanArriere{



    private ArrayList<PointsGravite> particules = new ArrayList<>();
    private boolean montrerChampsElectrique;
    Random r = new Random();
    int largeur = JavaFX.w * 20;
    int nbParticules = 25;

    public ArrayList<PointsGravite> getParticules() {
        return particules;
    }

    public void setMontrerChampsElectrique(boolean montrerChampsElectrique) { this.montrerChampsElectrique = montrerChampsElectrique; }

    public GenerationPointsGravite(Camera camera) {
        super(camera);
        //Placement des paticule dans le niveau
        for (int i = 0; i < nbParticules; i++) {
            this.particules.add(new PointsGravite(camera , new Point2D(r.nextInt(0,largeur),r.nextInt(0,JavaFX.h))));
        }
        this.montrerChampsElectrique = false;
    }

    //Place les particules en haut et en bas du niveau
    public void genererParticulesDebug(Camera camera) {
        particules.clear();

        double yHaut = 10;
        double yBas = JavaFX.h - 10;

        for (double x = 0; x < largeur; x += 50) {

            PointsGravite p1 = new PointsGravite(camera, new Point2D(r.nextInt(0,largeur),r.nextInt(0,JavaFX.h)));
            p1.setPositionMonde(new Point2D(x, yHaut));
            particules.add(p1);

            PointsGravite p2 = new PointsGravite(camera, new Point2D(r.nextInt(0,largeur),r.nextInt(0,JavaFX.h)));
            p2.setPositionMonde(new Point2D(x, yBas));
            particules.add(p2);
        }
    }

    //Donne des nouvelles particules (Pour quand un niveau commence)
    public void regenererPointsGravite(){
        particules.clear();
        while(particules.size() < nbParticules){
            particules.add(new PointsGravite(camera , new Point2D(r.nextInt(0,JavaFX.w*20),r.nextInt(0,JavaFX.h))));
        }
    }

    //Update les particules dans le arraylist
    @Override
    public void update(double mouvementVersGauche) {
        super.update(mouvementVersGauche);
        for (PointsGravite pointsGravite : particules) {
            pointsGravite.update(mouvementVersGauche);
        }
    }

    //Dessiner les particules du arraylist
    public void draw(GraphicsContext context) {
        for ( PointsGravite pointsGravite : particules) {
            pointsGravite.setMontrerChampsElectrique(montrerChampsElectrique);
            pointsGravite.draw(context);
            if (montrerChampsElectrique) {
                for (double x = 0; x < JavaFX.w; x += 50) {
                    for (double y = 0; y < JavaFX.h; y += 50) {

                        Point2D coordoEcran = camera.coordoEcran(pointsGravite.getPositionMonde());

                        Point2D positionMonde = new Point2D(coordoEcran.getX()-x, y);
                        Point2D positionEcran = camera.coordoEcran(positionMonde);

                        Point2D force = champsElectrique(positionMonde , coordoEcran);

                        UtilitairesDessins.dessinerVecteurForce(positionEcran, force, context);
                    }
                }
            }
        }

    }

    //Calcule de Ei
    public Point2D champsElectrique(Point2D point2D , Point2D coordoEcran) {

        Point2D distance2D = coordoEcran.subtract(point2D); //Distance en x,y

        double r = distance2D.magnitude(); //Pythagore
        if (r < 1) { r = 1; }

        double Ei = constaneCoulomb * Math.abs(chargeParticule) / (r * r); //Module du champs éléctrique

        Point2D orientation = distance2D.normalize();//Vecteur orientation

        return orientation.multiply(Ei); //retourne le vecteur du champs electrique

    }


}
