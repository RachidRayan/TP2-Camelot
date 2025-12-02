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
    public static int largeur = 900, hauteur = 580;
    private final Partie partie = new Partie();

    // Enum pour les différents status du jeu
    private enum statusJeu {
        DEBUT_DE_NIVEAU,
        NIVEAU,
        FIN_DE_PARTIE
    }

    // Status du jeu en ce moment
    private statusJeu statusMaintenant = statusJeu.DEBUT_DE_NIVEAU;

    private double tempsEcouleDesDebutNiveau = 0;

    private Font font = new Font(60);

    // Méthode start de JavaFX
    @Override
    public void start(Stage stage) throws IOException {
        var root = new Pane();
        var scene = new Scene(root, largeur, hauteur);
        var canvas = new Canvas(largeur, hauteur);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

        // Logique d'animation
        var timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();

            @Override
            public void handle(long now) {

                double deltaTemps = (now - dernierTemps) * 1e-9;

                // Vérification du status du jeu (DEBUT_NIVEAU, NIVEAU, FIN_DE_PARTIE)
                switch (statusMaintenant) {
                    case DEBUT_DE_NIVEAU:
                        tempsEcouleDesDebutNiveau += deltaTemps;
                        if (tempsEcouleDesDebutNiveau >= 3.0) {
                            statusMaintenant = statusJeu.NIVEAU;
                            tempsEcouleDesDebutNiveau = 0;
                        }
                        break;
                    case NIVEAU:
                        partie.update(deltaTemps);
                        if (partie.isNiveauFini()) {
                            partie.debutNouveauNiveau();
                            statusMaintenant = statusJeu.DEBUT_DE_NIVEAU;
                        } else if (partie.isPartieFinie()) {
                            statusMaintenant = statusJeu.FIN_DE_PARTIE;
                        }
                        break;
                    case FIN_DE_PARTIE:
                        break;
                }
                context.setFill(Color.gray(0.0));
                context.fillRect(0, 0, largeur, hauteur);

                if (statusMaintenant == statusJeu.DEBUT_DE_NIVEAU) {
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
    // Méthode main
    public static void main(String[] args) {
        launch();
    }
    // Méthode pour faire apparaitre l'écran de début d'un niveau
    public void ecranDebutNiveau(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, largeur, hauteur);
        context.setFill(Color.GREEN);
        context.setFont(font);

        String niveauString = "Niveau " + partie.getNiveau();
        Text texteNiveauTemporaire = new Text(niveauString);
        texteNiveauTemporaire.setFont(font);
        double texteLargeur = texteNiveauTemporaire.getLayoutBounds().getWidth();
        double texteHauteur = texteNiveauTemporaire.getLayoutBounds().getHeight();

        double positionX = largeur / 2.0 - texteLargeur / 2;
        double positionY = hauteur / 2.0 + texteHauteur / 4;
        context.fillText(niveauString, positionX, positionY);
    }

    // Méthode pour faire apparaitre l'écran de fin
    public void ecranDeFin(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, largeur, hauteur);

        context.setFill(Color.RED);
        context.setFont(font);
        String ruptureString = "Rupture stocks!";
        Text texteRuptureTemporaire = new Text(ruptureString);
        texteRuptureTemporaire.setFont(font);
        double texteRuptureLargeur = texteRuptureTemporaire.getLayoutBounds().getWidth();
        double texteRuptureHauteur = texteRuptureTemporaire.getLayoutBounds().getHeight();
        context.fillText(ruptureString, largeur / 2.0 - texteRuptureLargeur / 2, hauteur / 2.0 - 40 + texteRuptureHauteur / 4);

        context.setFill(Color.GREEN);
        context.setFont(font);
        String argentString = "Argent collecté: " + partie.getArgent() + "$";
        Text texteArgentTemporaire = new Text(argentString);
        texteArgentTemporaire.setFont(font);
        double texteArgentLargeur = texteArgentTemporaire.getLayoutBounds().getWidth();
        double texteArgentHauteur = texteArgentTemporaire.getLayoutBounds().getHeight();
        context.fillText(argentString, largeur / 2.0 - texteArgentLargeur / 2, hauteur / 2.0 + 20 + texteArgentHauteur / 4);
    }
}
