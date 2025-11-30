package ca.qc.bdeb.sim.tp2;

import ca.qc.bdeb.sim.tp2.gameEngine.Input;
import ca.qc.bdeb.sim.tp2.gameEngine.Partie;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFX extends Application {
    public static int w = 1000, h = 600;
    private final Partie partie = new Partie();

    private enum statusJeu {
        DEBUT_NIVEAU,
        NIVEAU,
        FIN_DE_PARTIE
    }

    private statusJeu statusMaintenant = statusJeu.DEBUT_NIVEAU;
    private double tempsDeDebutNiveau = 0;

    @Override
    public void start(Stage stage) throws IOException {
        var root = new Pane();
        var scene = new Scene(root, w, h);
        var canvas = new Canvas(w, h);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();


        var timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();

            @Override
            public void handle(long now) {

                double deltaTemps = (now - dernierTemps) * 1e-9;

                switch (statusMaintenant) {
                    case DEBUT_NIVEAU :
                        tempsDeDebutNiveau += deltaTemps;
                        if (tempsDeDebutNiveau >= 3.0) {
                            statusMaintenant = statusJeu.NIVEAU;
                            tempsDeDebutNiveau = 0;
                        }
                        break;
                    case NIVEAU:
                        partie.update(deltaTemps);
                        if (partie.isNiveauFini()) {
                            partie.debutNouveauNiveau();
                            statusMaintenant = statusJeu.DEBUT_NIVEAU;
                        } else if (partie.isPartieFinie()) {
                            statusMaintenant = statusJeu.FIN_DE_PARTIE;
                        }
                        break;
                    case FIN_DE_PARTIE:
                        break;
                }
                context.setFill(Color.gray(0.0));
                context.fillRect(0, 0, w, h);

                if (statusMaintenant == statusJeu.DEBUT_NIVEAU) {
                    context.setFill(Color.BLACK);
                    context.fillRect(0, 0, w, h);
                    context.setFill(Color.GREEN);
                    context.setFont(new Font(50));
                    context.fillText("Niveau " + partie.getNiveau(), w / 2 - 100, h / 2);
                }

                else if (statusMaintenant == statusJeu.FIN_DE_PARTIE) {
                    context.setFill(Color.BLACK);
                    context.fillRect(0, 0, w, h);
                    context.setFill(Color.RED);
                    context.setFont(new Font(50));
                    context.fillText("Rupture stocks! ", w / 2 - 150, h / 2 - 50);
                    context.setFill(Color.RED);
                    context.fillText("Argent collecté " + partie.getArgent() + "$", w / 2 -100, h/ 2 +50);
                }

                else if (statusMaintenant == statusJeu.NIVEAU) {
                    partie.draw(context);
                }

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
