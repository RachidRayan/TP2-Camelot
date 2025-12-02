package ca.qc.bdeb.sim.tp2;

import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import ca.qc.bdeb.sim.tp2.gameEngine.Input;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Camelot {

    private final Image camelotImage1 = new Image("camelot1.png");
    private final Image camelotImage2 = new Image("camelot2.png");
    private Point2D taille;

    private Point2D position;
    private Point2D velocite = Point2D.ZERO;
    private boolean contactSol;

    private Camera camera;

    private boolean debugModeDraw;

    private double tempsAnimation = 0;
    private double intervalleAnimation = 0.25;
    private boolean changementImage = true;
    // Constructeur Camelot
    public Camelot(Camera camera, double positionX) {
        this.position = new Point2D(positionX,JavaFX.h - camelotImage1.getHeight());
        this.taille = new Point2D(camelotImage1.getWidth(), camelotImage1.getHeight());
        this.camera = camera;
        this.debugModeDraw = false;
    }

    public void setDebugModeDraw(boolean debugModeDraw) {
        this.debugModeDraw = debugModeDraw;
    }

    public Point2D getPosition() {
        return position;
    }

    public Point2D getTaille() {
        return taille;
    }

    public Point2D getVelocite() {
        return velocite;
    }
    // Méthode de rénouvellement du Camelot
    public void update(double deltaTemps) {

        // Action de sauter avec Espace ou Flèche vers le haut
        boolean saut = Input.isKeyPressed(KeyCode.SPACE)
                || Input.isKeyPressed(KeyCode.UP);

        // Logique pour le saut
        if (contactSol && saut) {
            velocite = new Point2D(0, -700);
            contactSol = false;
        }

        if (!contactSol) {
            velocite = new Point2D(0, velocite.getY() + 1500 * deltaTemps);

            position = new Point2D(position.getX(), position.getY() + velocite.getY() * deltaTemps);

            // Position et velocité quand camelot revient sur le sol
            if (position.getY() + taille.getY() >= JavaFX.h) {
                position = new Point2D(position.getX(), JavaFX.h - taille.getY());
                velocite = new Point2D(0, 0);
                contactSol = true;
            }
        }

        // Positionnement
        position = new Point2D(position.getX(), Math.clamp(position.getY(), 0, JavaFX.h - taille.getY()));

        // Animation du camelot de base
        tempsAnimation += deltaTemps;
        if (tempsAnimation >= intervalleAnimation) {
            changementImage = !changementImage;
            tempsAnimation = 0;
        }
    }

    // Méthode de dessin du Camelot (avec ou sans debug)
    public void draw(GraphicsContext context) {
        Point2D positionEcran = camera.coordoEcran(position);

        if (!changementImage) {
            context.drawImage(camelotImage1,positionEcran.getX(), positionEcran.getY(),taille.getX(), taille.getY());
        }

        else {
            context.drawImage(camelotImage2,positionEcran.getX(), positionEcran.getY(),taille.getX(), taille.getY());
        }

        if (debugModeDraw) {
            context.setFill(Color.YELLOW);
            context.fillRect(positionEcran.getX(),0,2, JavaFX.h);
        }
    }
}
