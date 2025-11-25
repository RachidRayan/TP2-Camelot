package ca.qc.bdeb.sim.tp2.gameEngine;

import ca.qc.bdeb.sim.tp2.entites.Camelot;
import ca.qc.bdeb.sim.tp2.entites.Entite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Partie {
    private final Camelot camelot = new Camelot();
    private ArrayList<Object> listePlanArriere;
    private ArrayList<Entite> listeEntite = new ArrayList<>();

    public Partie() {
//        Camelot camelot = new Camelot();
        this.listeEntite.add(camelot);
    }

    public void update (double deltaTemps) {
            for (Entite e : listeEntite) {
                e.update(deltaTemps);
            }
            if (Input.isKeyPressed(KeyCode.Q)) {
                camelot.
            }
    }

    public void draw (GraphicsContext context) {
       for (Entite e : listeEntite) {
           e.draw(context);
       }
    }
}
