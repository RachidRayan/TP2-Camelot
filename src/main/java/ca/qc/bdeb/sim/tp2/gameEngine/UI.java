package ca.qc.bdeb.sim.tp2.gameEngine;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class UI {
    private final Image iconeDollar = new Image("icone-dollar.png");
    private final Image iconeJournal = new Image("icone-journal.png");
    private final Image iconeMaison = new Image("icone-maison.png");

    public void uiInitialization (GraphicsContext context) {

        Point2D positionIconeDollar = new Point2D(iconeDollar.getWidth(), iconeDollar.getWidth());
        context.drawImage(iconeDollar, positionIconeDollar.getX(),  positionIconeDollar.getY());

        Point2D positionIconeJournal = new Point2D(positionIconeDollar.getX() + iconeJournal.getWidth(), positionIconeDollar.getY() + iconeJournal.getHeight());

        context.drawImage(iconeJournal, positionIconeJournal.getX(), positionIconeJournal.getY());

        Point2D positionIconeMaison = new Point2D(positionIconeJournal.getX() + iconeMaison.getWidth() , positionIconeJournal.getY() + iconeMaison.getHeight());
        context.drawImage(iconeJournal,positionIconeMaison.getX(), positionIconeMaison.getY());

    }
}
