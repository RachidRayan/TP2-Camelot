package ca.qc.bdeb.sim.tp2;

import ca.qc.bdeb.sim.tp2.gameEngine.UI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFX extends Application {
    public static int w = 600,  h=400;
    @Override
    public void start(Stage stage) throws IOException {
        HBox root = new HBox();
        Scene scene = new Scene(root, w, h);

        UI ui = new UI(w,h);
        ui.uiInitialization(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}