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

import static ca.qc.bdeb.sim.tp2.generation.PointsGravite.*;

public class GenerationPointsGravite extends GenerationPlanArriere {


    private ArrayList<PointsGravite> particules = new ArrayList<>();
    private boolean montrerChampsElectrique;
    Random r = new Random();
    int largeur = JavaFX.largeur * 20;
    int nbParticules = 25;

    public ArrayList<PointsGravite> getParticules() {
        return particules;
    }

    public void setMontrerChampsElectrique(boolean montrerChampsElectrique) {
        this.montrerChampsElectrique = montrerChampsElectrique;
    }

    public GenerationPointsGravite(Camera camera) {
        super(camera);
        //Placement des paticule dans le niveau au hazard
        for (int i = 0; i < nbParticules; i++) {
            this.particules.add(new PointsGravite(camera, new Point2D(r.nextInt(0, largeur), r.nextInt(0, JavaFX.hauteur))));
        }
        this.montrerChampsElectrique = false;
    }

    //Place les particules en haut et en bas du niveau
    public void genererParticulesDebug(Camera camera) {
        particules.clear(); //enlève toutes les particules

        for (double x = 0; x < largeur; x += 50) {
            //Placement des particules du haut
            PointsGravite p1 = new PointsGravite(camera, new Point2D(x, rayon));
            particules.add(p1);

            //Placement des particules du bas
            PointsGravite p2 = new PointsGravite(camera, new Point2D(x, JavaFX.hauteur - rayon));
            particules.add(p2);
        }
    }

    //Donne des nouvelles particules (Pour quand un niveau commence)
    public void regenererPointsGravite() {
        particules.clear();
        //Placement des paticule dans le niveau au hazard
        while (particules.size() < nbParticules) {
            particules.add(new PointsGravite(camera, new Point2D(r.nextInt(0, JavaFX.largeur * 20), r.nextInt(0, JavaFX.hauteur))));
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
        for (PointsGravite pointsGravite : particules) {
            pointsGravite.setMontrerChampsElectrique(montrerChampsElectrique);
            pointsGravite.draw(context);
        }
        if (montrerChampsElectrique) {

            double cameraX = camera.getPositionCamera().getX(); // position X de la caméra dans le monde
            //Montre la force éléctrique à chaque 50px
            for (double x = cameraX; x < JavaFX.largeur; x += 50) {
                for (double y = 0; y < JavaFX.hauteur; y += 50) {

                    //Position monde en position écran
                    Point2D positionEcran = new Point2D(x-cameraX,y);

                    // Calcule la force totale dans un point
                    Point2D force = champsElectrique(particules, new Point2D(x, y));

                    // Dessine la flèche à l'écran à toutes les 50px
                    UtilitairesDessins.dessinerVecteurForce(positionEcran, force, context);
                }
            }
        }
    }

    //Mesure le vecteur de force electrique total en tenant compte de toutes les particules
    public Point2D champsElectrique(List<PointsGravite> particules, Point2D positionEcran) {
        Point2D total = Point2D.ZERO;

        for (PointsGravite g : particules) {
            total = total.add(g.champsElectrique(positionEcran));
        }

        return total;
    }
}
