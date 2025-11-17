package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Brique {

    Image imageBrique = new Image("brique.png");
    ImageView brique = new ImageView(imageBrique);

    HBox wall = rangeeBrique(cologneBrique());

    public Brique(HBox wall) {
        this.wall = wall;
    }
    public HBox getWall() {
        return wall;
    }

    public VBox cologneBrique(){
        VBox column = new VBox();
        double height = 0;

        while(height<JavaFX.h){
            column.getChildren().add(brique);
            height += brique.getFitHeight();
        }

        return column;
    }

    public HBox rangeeBrique(VBox cologneBrique){
        HBox row = new HBox();
        double width = 0;

        while(width<JavaFX.w){
            row.getChildren().add(cologneBrique);
            width += brique.getFitWidth();
        }

        return row;
    }

}
