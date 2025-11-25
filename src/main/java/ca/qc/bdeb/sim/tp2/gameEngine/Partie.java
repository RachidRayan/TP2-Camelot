package ca.qc.bdeb.sim.tp2.gameEngine;

import ca.qc.bdeb.sim.tp2.background.Brique;
import ca.qc.bdeb.sim.tp2.background.Maison;
import ca.qc.bdeb.sim.tp2.entites.Camelot;
import ca.qc.bdeb.sim.tp2.entites.Entite;
import ca.qc.bdeb.sim.tp2.entites.Journal;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class Partie {
    private final Camelot camelot = new Camelot();
    private final Brique brique = new Brique();
    private final Maison maison = new Maison();
    private ArrayList<Object> listePlanArriere;
    private ArrayList<Entite> listeEntite = new ArrayList<>();

    public Partie() {
//        Camelot camelot = new Camelot();

        this.listeEntite.add(camelot);
    }

    public void update (double deltaTemps) {
        brique.update(deltaTemps);
        maison.update(deltaTemps);
            for (Entite e : listeEntite) {
                e.update(deltaTemps);
            }
//            ArrayList<Journal> newJournals = camelot.getJournalsToCreateThisFrame();
//            listeEntite.addAll(newJournals);
    }

    public void draw (GraphicsContext context) {
        brique.draw(context);
        maison.draw(context);
       for (Entite e : listeEntite) {
           e.draw(context);
       }
    }
}
