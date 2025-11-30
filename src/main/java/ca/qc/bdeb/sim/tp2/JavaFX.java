package ca.qc.bdeb.sim.tp2;

import ca.qc.bdeb.sim.tp2.gameEngine.Input;
import ca.qc.bdeb.sim.tp2.gameEngine.Partie;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFX extends Application {
    public static int w = 900, h = 580;
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
                    ecranDebutNiveau(context);
                }

                else if (statusMaintenant == statusJeu.FIN_DE_PARTIE) {
                    ecranDeFin(context);
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

        stage.setTitle("Camelot à vélo");
        stage.setResizable(false);
        stage.getIcons().add(new Image("journal.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void ecranDebutNiveau(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, w, h);
        context.setFill(Color.GREEN);
        context.setFont(new Font(60));

        String text = "Niveau " + partie.getNiveau();
        Text tempText = new Text(text);
        tempText.setFont(new Font(60));
        double textWidth = tempText.getLayoutBounds().getWidth();
        double textHeight = tempText.getLayoutBounds().getHeight();

        double x = w / 2 - textWidth / 2;
        double y = h / 2 + textHeight / 4;
        context.fillText(text, x, y);
    }

    public void ecranDeFin(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, w, h);

        context.setFill(Color.RED);
        context.setFont(new Font(60));
        String text1 = "Rupture stocks!";
        Text tempText1 = new Text(text1);
        tempText1.setFont(new Font(60));
        double textWidth1 = tempText1.getLayoutBounds().getWidth();
        double textHeight1 = tempText1.getLayoutBounds().getHeight();
        context.fillText(text1, w / 2 - textWidth1 / 2, h / 2 - 40 + textHeight1 / 4);

        context.setFill(Color.GREEN);
        String text2 = "Argent collecté: " + partie.getArgent() + "$";
        Text tempText2 = new Text(text2);
        tempText2.setFont(new Font(60));
        double textWidth2 = tempText2.getLayoutBounds().getWidth();
        double textHeight2 = tempText2.getLayoutBounds().getHeight();
        context.fillText(text2, w / 2 - textWidth2 / 2, h / 2 + 20 + textHeight2 / 4);
    }
}
