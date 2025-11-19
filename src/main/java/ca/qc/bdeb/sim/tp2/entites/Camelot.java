package ca.qc.bdeb.sim.tp2.entites;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Input;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Camelot extends Entite {

    protected int Nombrejournaux;
    protected Image camelotImage1 = new Image("camelot1.png");
    protected Image camelotImage2 = new Image("camelot2.png");

    // position = coin en haut à gauche du flocon
    protected Point2D position = Point2D.ZERO;
    protected Point2D taille = new Point2D(120, 60);

    // Physique
    protected Point2D velocite = Point2D.ZERO;
    protected Point2D acceleration = new Point2D(0, 600);

    protected boolean toucheLeSol;

    public Camelot() {
        position = new Point2D(
                JavaFX.w / 2.0 - taille.getX() / 2.0,
                JavaFX.h - taille.getY());
        toucheLeSol = true;
    }

    public int getNombrejournaux() {
        return Nombrejournaux;
    }

    public void verificationNombreJournaux (int NbrJournauxRestants) {
        if (NbrJournauxRestants == 0) {
            Nombrejournaux = 12;
        }
        else {
            Nombrejournaux += NbrJournauxRestants;
        }
    }







    @Override
    public void update(double deltaTemps) {
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);

        // Mouvement horizontal
        if (gauche)
            velocite = new Point2D(-300, velocite.getY());
        else if (droite)
            velocite = new Point2D(+300, velocite.getY());
        else // Pas de flèche appuyée
            velocite = new Point2D(0, velocite.getY());

        // Sauter avec Espace ou Flèche vers le haut
        boolean jump = Input.isKeyPressed(KeyCode.SPACE)
                || Input.isKeyPressed(KeyCode.UP);

        // Sauter = donner une vitesse vers le haut
        if (toucheLeSol && jump) {
            velocite = new Point2D(velocite.getX(), -300);
            toucheLeSol = false;
        }

        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));

        if (position.getY() + taille.getY() >= JavaFX.h) {
            toucheLeSol = true;
            velocite = new Point2D(velocite.getX(), 0);
        }

        position = new Point2D(
                Math.clamp(position.getX(), 0, JavaFX.w - taille.getX()),
                Math.clamp(position.getY(), 0, JavaFX.h - taille.getY())
        );
    }

    @Override
    public void draw(GraphicsContext context) {

    }

    @Override
    public void hitBox() {

    }
}
