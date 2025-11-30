package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class GenerationPointsGravite extends GenerationPlanArriere{



    private ArrayList<PointsGravite> particules = new ArrayList<>();

    public ArrayList<PointsGravite> getParticules() {
        return particules;
    }

    private boolean debugMode;

    public void setDebugMode(boolean debugMode) { this.debugMode = debugMode; }


    public GenerationPointsGravite(Camera camera, boolean debugMode) {
        super(camera);
        //temporaire
        for (int i = 0; i < 20; i++) {
            this.particules.add(new PointsGravite(camera));
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
            pointsGravite.setDebugModeDraw(debugMode);
            pointsGravite.draw(context);
        }
    }

    public Point2D champsElectrique(List<PointsGravite> particules, Journal j) {
        Point2D total = Point2D.ZERO;

        for (PointsGravite pointsGravite : this.particules) {
            total = total.add(pointsGravite.champsElectrique(j));
        }

        return total;
    }


}
