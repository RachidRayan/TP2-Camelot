package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerationPointsGravite extends GenerationPlanArriere{



    private ArrayList<PointsGravite> particules = new ArrayList<>();
    private boolean montrerChampsElectrique;
    private boolean placementTest;
    Random r = new Random();

    public ArrayList<PointsGravite> getParticules() {
        return particules;
    }



    public void setMontrerChampsElectrique(boolean montrerChampsElectrique) { this.montrerChampsElectrique = montrerChampsElectrique; }

    public void setPlacementTest(boolean placementTest){
        this.placementTest = placementTest;
    }

    public GenerationPointsGravite(Camera camera) {
        super(camera);

        for (int i = 0; i < 200; i++) {
            this.particules.add(new PointsGravite(camera , new Point2D(r.nextInt(0,JavaFX.w*20),r.nextInt(0,JavaFX.h))));
        }
        this.montrerChampsElectrique = false;
    }

    public void genererParticulesDebug(Camera camera) {
        particules.clear();

        double yHaut = 10;
        double yBas = JavaFX.h - 10;

        double largeur = JavaFX.w * 20;

        for (double x = 0; x < largeur; x += 50) {

            PointsGravite p1 = new PointsGravite(camera, new Point2D(r.nextInt(0,JavaFX.w*20),r.nextInt(0,JavaFX.h)));
            p1.setPositionMonde(new Point2D(x, yHaut));
            particules.add(p1);

            PointsGravite p2 = new PointsGravite(camera, new Point2D(r.nextInt(0,JavaFX.w*20),r.nextInt(0,JavaFX.h)));
            p2.setPositionMonde(new Point2D(x, yBas));
            particules.add(p2);
        }
    }

    public void regenererPointsGravite(){
        particules.clear();
        while(particules.size() < 200){
            particules.add(new PointsGravite(camera , new Point2D(r.nextInt(0,JavaFX.w*20),r.nextInt(0,JavaFX.h))));
        }
    }


    @Override
    public void update(double mouvementVersGauche) {
        super.update(mouvementVersGauche);
        for (PointsGravite pointsGravite : particules) {
            pointsGravite.update(mouvementVersGauche);
        }
    }

    public void draw(GraphicsContext context) {
        for ( PointsGravite pointsGravite : particules) {
            pointsGravite.setMontrerChampsElectrique(montrerChampsElectrique);
            pointsGravite.draw(context);
        }
    }

    public Point2D champsElectrique(List<PointsGravite> particules, Journal j) {
        Point2D total = Point2D.ZERO;

        for (PointsGravite pointsGravite : this.particules) {
            total = total.add(pointsGravite.champsElectrique(j.getPosition()));
        }

        return total;
    }


}
