package ca.qc.bdeb.sim.tp2.gameEngine;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class UI {
    private final Image iconeDollar = new Image("https://github.com/RachidRayan/TP2-Camelot/blob/cef12d1674fc107bc1d7c54c02be444595cfaab3/Assets/icone-dollar.png");
    private final Image iconeJournal = new Image("https://github.com/RachidRayan/TP2-Camelot/blob/cef12d1674fc107bc1d7c54c02be444595cfaab3/Assets/icone-journal.png");
    private final Image iconeMaison = new Image("https://github.com/RachidRayan/TP2-Camelot/blob/cef12d1674fc107bc1d7c54c02be444595cfaab3/Assets/icone-maison.png");
    private final int w;
    private final int h;

    public UI(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void uiInitialization (HBox hBox) {
        ImageView iconeDollarImageView = new ImageView(iconeDollar);
        ImageView iconeJournalImageView = new ImageView(iconeJournal);
        ImageView iconeMaisonImageView = new ImageView(iconeMaison);

        hBox.getChildren().add(iconeDollarImageView);
        hBox.getChildren().add(iconeJournalImageView);
        hBox.getChildren().add(iconeMaisonImageView);
    }
}
