package ca.qc.bdeb.sim.tp2;

//import ca.qc.bdeb.sim.tp2.background.Brique;
import ca.qc.bdeb.sim.tp2.entites.Camelot;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import ca.qc.bdeb.sim.tp2.gameEngine.Input;
import ca.qc.bdeb.sim.tp2.gameEngine.UI;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFX extends Application {
    public static int w = 600,  h=400;
    @Override
    public void start(Stage stage) throws IOException {
        var root = new Pane();
        var scene = new Scene(root, w, h);
        var canvas = new Canvas(w, h);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

//        UI ui = new UI(w,h);
//        ui.uiInitialization(root);

//        Brique brique = new Brique();

        Camelot camelot = new Camelot();

        var timer = new AnimationTimer() {
            long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {

                double deltaTemps = (now - lastTime) * 1e-9;

                // -- Update --
                camelot.update(deltaTemps);

                // Arrière-plan
                context.setFill(Color.gray(0.2));
                context.fillRect(0, 0, w, h);

                camelot.draw(context);

                lastTime = now;
            }
        };
        timer.start();


        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                // Ferme JavaFX
                Platform.exit();
            } else {
                Input.setKeyPressed(e.getCode(), true);
            }
        });
        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}