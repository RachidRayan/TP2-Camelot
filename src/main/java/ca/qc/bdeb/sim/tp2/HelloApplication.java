package cs.qc.bdeb.sim.tp2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static int w = 600,  h=400;
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new HBox(), w, h);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}