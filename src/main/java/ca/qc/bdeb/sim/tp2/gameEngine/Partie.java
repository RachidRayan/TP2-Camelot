package ca.qc.bdeb.sim.tp2.gameEngine;

import ca.qc.bdeb.sim.tp2.background.Bg;
import ca.qc.bdeb.sim.tp2.background.Brique;
import ca.qc.bdeb.sim.tp2.background.Maison;
import ca.qc.bdeb.sim.tp2.entites.Camelot;
import ca.qc.bdeb.sim.tp2.entites.Entite;
import ca.qc.bdeb.sim.tp2.entites.Journal;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;


import java.util.ArrayList;

public class Partie {
    private Camera camera = new Camera();
    private final Camelot camelot = new Camelot(camera);
    private final Brique brique = new Brique();
    private final Maison maison = new Maison();
    private ArrayList<Bg> listePlanArriere = new ArrayList<>();
    private ArrayList<Entite> listeEntite = new ArrayList<>();

    public Partie() {
//        Camelot camelot = new Camelot();

        this.listeEntite.add(camelot);
        this.listePlanArriere.add(brique);
        this.listePlanArriere.add(maison);
    }

    public void update(double deltaTemps) {

        for (Bg bg : listePlanArriere) {
            bg.update(deltaTemps);
        }
//        brique.update(deltaTemps);
//        maison.update(deltaTemps);
        for (Entite e : listeEntite) {
            e.update(deltaTemps);
        }
//            ArrayList<Journal> newJournals = camelot.getJournalsToCreateThisFrame();
//            listeEntite.addAll(newJournals);
    }

    public void draw(GraphicsContext context) {
        for (Bg bg : listePlanArriere) {
            bg.draw(context);
        }
//        brique.draw(context);
//        maison.draw(context);
        for (Entite e : listeEntite) {
            e.draw(context);
        }
    }

    public Journal lancementJournal() {
        Point2D positionInitialeLancer = camelot.getPosition().add(new Point2D(camelot.getTaille().getX() / 2.0, camelot.getTaille().getY() / 2.0));
        Point2D velociteInitiale = new Point2D(camelot.getVelocite().getX() + 200, -450);
        return new Journal(positionInitialeLancer, velociteInitiale, camera);
    }
}
