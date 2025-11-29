package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.entites.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class GenerationBoitesAuxLettres extends GenerationPlanArriere {
    private final Image boiteAuxLetrresImage = new Image("boite-aux-lettres.png");
    private final Image boiteAuxLetrresRougeImage = new Image("boite-aux-lettres-rouge.png");
    private final Image boiteAuxLetrresVertImage = new Image("boite-aux-lettres-vert.png");

    private ArrayList<Point2D> positionBoites;


    public GenerationBoitesAuxLettres(Camera camera, GenerationMaisons maisons) {
        super(camera);

        this.positionBoites = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < maisons.getXPositionAdresses().size(); i++) {
            double xPositionAdresse = maisons.getXPositionAdresses().get(i);
            double xPositionBoite = xPositionAdresse + 200;

            double yPositionBoite = (random.nextDouble() * 0.5 + 0.2) * JavaFX.h;

            positionBoites.add(new Point2D(xPositionBoite, yPositionBoite));
        }

        System.out.println(positionBoites);
    }

    @Override
    public void draw(GraphicsContext context) {

    }

    public void collisionAvecJournal(ArrayList<Journal> journaux) {

    }
}
