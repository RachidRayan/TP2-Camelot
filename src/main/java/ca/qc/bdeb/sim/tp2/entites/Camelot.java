package ca.qc.bdeb.sim.tp2.entites;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Camelot extends Entite {
    private int Nombrejournaux;
    private Image camelotImage1 = new Image("camelot1.png");
    private Image camelotImage2 = new Image("camelot2.png");

    public int getNombrejournaux() {
        return Nombrejournaux;
    }

    public void verificationNombreJournaux (int NbrJournauxRestants) {
        if (NbrJournauxRestants == 0) {
            Nombrejournaux = 12;
        }
        else {
            Nombrejournaux += NbrJournauxRestants;
        }
    }

}
