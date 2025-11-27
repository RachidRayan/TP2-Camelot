package ca.qc.bdeb.sim.tp2.gameEngine;

import ca.qc.bdeb.sim.tp2.JavaFX;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UI {
    private final Image iconeDollar = new Image("icone-dollar.png");
//    private int argent = 0;

    private final Image iconeJournal = new Image("icone-journal.png");
    private int nombreJournauxAffichage;

    private final Image iconeMaison = new Image("icone-maison.png");
    private final String adressesCibles = "Aucune pour l'instant!";

    private final double grandeurIcone = 32;
    private final double espaceEntre = 10;
    private final double yEntreLeHaut = 8;

    public void update (int journaux, int argent) {
        this.nombreJournauxAffichage = journaux;
//        this.argent = argent;
    }

    public void draw (GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRect(0,0,JavaFX.w,50);

        double xTravailler = espaceEntre;


        context.drawImage(iconeJournal, xTravailler, yEntreLeHaut);
        drawText(context, String.valueOf(nombreJournauxAffichage),xTravailler + grandeurIcone + 8, espaceEntre);
        xTravailler += grandeurIcone + 100;

        context.drawImage(iconeDollar, xTravailler, yEntreLeHaut + 5);


    }

    private void drawText(GraphicsContext context, String text, double x, double y) {
        context.setTextBaseline(VPos.TOP);
        context.setFont(Font.font("Arial", 25));
        context.setFill(Color.WHITE);
        context.fillText(text, x, y);
    }
}
