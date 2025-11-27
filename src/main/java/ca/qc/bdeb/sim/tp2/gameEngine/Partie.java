package ca.qc.bdeb.sim.tp2.gameEngine;

import ca.qc.bdeb.sim.tp2.background.Bg;
import ca.qc.bdeb.sim.tp2.background.Brique;
import ca.qc.bdeb.sim.tp2.background.Maison;
import ca.qc.bdeb.sim.tp2.entites.Camelot;
import ca.qc.bdeb.sim.tp2.entites.Entite;
import ca.qc.bdeb.sim.tp2.entites.Journal;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;


import java.util.ArrayList;

public class Partie {
    private final UI ui = new UI();
    private final Camera camera = new Camera();
    private final Camelot camelot = new Camelot(camera);
    private final Brique brique = new Brique();
    private final Maison maison = new Maison();
    private ArrayList<Bg> listePlanArriere = new ArrayList<>();
    private ArrayList<Entite> listeEntite = new ArrayList<>();

    private int nombreJournaux = 12;
    private double lancerTemps = 0;
    private final double rechargeLaner = 0.6;
    private boolean statusQAvant = false;

    public Partie() {
        this.listeEntite.add(camelot);
        this.listePlanArriere.add(brique);
        this.listePlanArriere.add(maison);
    }

    public int getNombreJournaux() {
        return nombreJournaux;
    }

    public void update(double deltaTemps) {

        for (Bg bg : listePlanArriere) {
            bg.update(deltaTemps);
        }

        boolean statusQMaintenant = Input.isKeyPressed(KeyCode.Q);
        boolean qPeser = statusQMaintenant && !statusQAvant;
        statusQAvant = qPeser;

        lancerTemps -= deltaTemps;

        if (qPeser && lancerTemps <= 0 && nombreJournaux > 0) {
            Point2D positionCamelot = camelot.getPosition();
            Point2D tailleCamelot = camelot.getTaille();
            Point2D velociteCamelot = camelot.getVelocite();

            Point2D positionLancer = new Point2D(positionCamelot.getX() + tailleCamelot.getX() / 2.0,
                    positionCamelot.getY() + tailleCamelot.getY() / 2.0);

            Point2D velociteInitialJournal = new Point2D(velociteCamelot.getX() + 200, -500);

            listeEntite.add(new Journal(positionLancer, velociteInitialJournal, camera));
            nombreJournaux--;
            lancerTemps = rechargeLaner;
        }

        for (Entite e : listeEntite) {
            e.update(deltaTemps);
        }

        ui.update(getNombreJournaux(),0);

    }

    public void draw(GraphicsContext context) {
        for (Bg bg : listePlanArriere) {
            bg.draw(context);
        }
//
        for (Entite e : listeEntite) {
            e.draw(context);
        }

        ui.draw(context);
    }

}
