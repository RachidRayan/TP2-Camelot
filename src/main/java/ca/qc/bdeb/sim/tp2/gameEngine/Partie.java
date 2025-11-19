package ca.qc.bdeb.sim.tp2.gameEngine;

import ca.qc.bdeb.sim.tp2.entites.Camelot;
import javafx.scene.canvas.GraphicsContext;

public class Partie {
    private final Camelot camelot = new Camelot();


    public void update (double deltaTemps) {
            camelot.update(deltaTemps);
    }

    public void draw (GraphicsContext context) {
        camelot.draw(context);
    }
}
