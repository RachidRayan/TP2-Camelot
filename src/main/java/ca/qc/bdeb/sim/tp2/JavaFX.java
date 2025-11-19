package ca.qc.bdeb.sim.tp2;

import ca.qc.bdeb.sim.tp2.background.Brique;
import ca.qc.bdeb.sim.tp2.gameEngine.Input;
import ca.qc.bdeb.sim.tp2.gameEngine.Partie;
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
    public static int w = 1000,  h=600;
    private final Partie partie = new Partie();
    @Override
    public void start(Stage stage) throws IOException {
        var root = new Pane();
        var scene = new Scene(root, w, h);
        var canvas = new Canvas(w, h);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

        //wall testing
        Brique brique = new Brique();
        root.getChildren().add(brique.getWall());

        var timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();

            @Override
            public void handle(long now) {

                double deltaTemps = (now - dernierTemps) * 1e-9;
                // Renouvlement
                partie.update(deltaTemps);

                // Arrière-plan
                context.setFill(Color.gray(0.2));
                context.fillRect(0, 0, w, h);

                // Dessin
                partie.draw(context);

                dernierTemps = now;
            }
        };
        timer.start();


        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
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