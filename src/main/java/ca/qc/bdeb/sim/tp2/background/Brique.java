package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.JavaFX;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Brique {

    Image imageBrique = new Image("brique.png");

    HBox wall = RangeeBrique();
    public HBox getWall() {
        return wall;
    }

    public VBox CologneBrique(){
        VBox column = new VBox();
        double height=0;

        while (height< JavaFX.h){
            column.getChildren().add(new ImageView(imageBrique));
            height += imageBrique.getHeight();
        }

        return column;
    }

    public HBox RangeeBrique(){
        HBox row = new HBox();
        double width =0;

        while(width<JavaFX.w){
            row.getChildren().add(CologneBrique());
            width += imageBrique.getWidth();
        }

        return row;
    }


}
