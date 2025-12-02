package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class GenerationPointsGravite extends GenerationPlanArriere{



    private ArrayList<PointsGravite> particules = new ArrayList<>();
    private boolean montrerChampsElectrique;
    Random r = new Random();
    int largeur = JavaFX.largeur * 20;
    int nbParticules = 25;

    public ArrayList<PointsGravite> getParticules() {
        return particules;
    }

    public void setMontrerChampsElectrique(boolean montrerChampsElectrique) { this.montrerChampsElectrique = montrerChampsElectrique; }

    public GenerationPointsGravite(Camera camera) {
        super(camera);
        //Placement des paticule dans le niveau
        for (int i = 0; i < nbParticules; i++) {
            this.particules.add(new PointsGravite(camera , new Point2D(r.nextInt(0,largeur),r.nextInt(0,JavaFX.hauteur))));
        }
        this.montrerChampsElectrique = false;
    }

    //Place les particules en haut et en bas du niveau
    public void genererParticulesDebug(Camera camera) {
        particules.clear();

        double yHaut = 10;
        double yBas = JavaFX.hauteur - 10;

        for (double x = 0; x < largeur; x += 50) {

            PointsGravite p1 = new PointsGravite(camera, new Point2D(r.nextInt(0,largeur),r.nextInt(0,JavaFX.hauteur)));
            p1.setPositionMonde(new Point2D(x, yHaut));
            particules.add(p1);

            PointsGravite p2 = new PointsGravite(camera, new Point2D(r.nextInt(0,largeur),r.nextInt(0,JavaFX.hauteur)));
            p2.setPositionMonde(new Point2D(x, yBas));
            particules.add(p2);
        }
    }

    //Donne des nouvelles particules (Pour quand un niveau commence)
    public void regenererPointsGravite(){
        particules.clear();
        while(particules.size() < nbParticules){
            particules.add(new PointsGravite(camera , new Point2D(r.nextInt(0,JavaFX.largeur *20),r.nextInt(0,JavaFX.hauteur))));
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
        }
    }
}
