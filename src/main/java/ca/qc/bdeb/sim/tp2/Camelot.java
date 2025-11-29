package ca.qc.bdeb.sim.tp2;

import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import ca.qc.bdeb.sim.tp2.gameEngine.Input;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Camelot {

    private final Image camelotImage1 = new Image("camelot1.png");
    private final Image camelotImage2 = new Image("camelot2.png");

    private Point2D position;
    private Point2D taille;

    private Camera camera;

    private Point2D velocite = Point2D.ZERO;

    private boolean contactSol;

    private double tempsAnimation = 0;
    private double intervalleAnimation = 0.4;
    private boolean changementImage12 = true;

    public Camelot(Camera camera, double positionX) {
        this.position = new Point2D(positionX,JavaFX.h - camelotImage1.getHeight());
        this.taille = new Point2D(camelotImage1.getWidth(), camelotImage1.getHeight());
        this.camera = camera;
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



    public void update(double deltaTemps, double vitesseMouvement) {

        // Sauter avec Espace ou Flèche vers le haut
        boolean saut = Input.isKeyPressed(KeyCode.SPACE)
                || Input.isKeyPressed(KeyCode.UP);

        // Sauter
        if (contactSol && saut) {
            velocite = new Point2D(0, -700);
            contactSol = false;
        }

        if (!contactSol) {
            velocite = new Point2D(0, velocite.getY() + 2000 * deltaTemps);

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
            changementImage12 = !changementImage12;
            tempsAnimation = 0;
        }
    }


    public void draw(GraphicsContext context) {

        Point2D positionEcran = camera.coordoEcran(position);

        if (!changementImage12) {
            context.drawImage(camelotImage1,positionEcran.getX(), positionEcran.getY(),taille.getX(), taille.getY());
        }
        else {
            context.drawImage(camelotImage2,positionEcran.getX(), positionEcran.getY(),taille.getX(), taille.getY());
        }
    }


    public void hitBox(GraphicsContext context) {
        boolean activationHitBox = Input.isKeyPressed(KeyCode.H);

//        if (activationHitBox) {
//            context.set
//        }
    }
}
