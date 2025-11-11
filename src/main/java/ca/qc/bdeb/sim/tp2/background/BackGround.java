package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JeuCamelot;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class BackGround {

    Image imageBrique = new Image("brique.png");
    ImageView brique = new ImageView(imageBrique);

    VBox cologneBrique = bg(brique);


    public static VBox bg(ImageView brique){
        VBox cologneBrique = new VBox();
        int nbBriqueY =0;

        while(nbBriqueY * brique.getY() < JeuCamelot.h ) {
            cologneBrique.getChildren().add(brique);
        }
        return cologneBrique;
    }

}
