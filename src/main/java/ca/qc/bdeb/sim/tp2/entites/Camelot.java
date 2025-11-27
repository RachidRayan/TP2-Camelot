package ca.qc.bdeb.sim.tp2.entites;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import ca.qc.bdeb.sim.tp2.gameEngine.Input;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Camelot extends Entite {

    protected Image camelotImage1 = new Image("camelot1.png");
    protected Image camelotImage2 = new Image("camelot2.png");

    // position = coin en haut à gauche du flocon
    protected Point2D position = Point2D.ZERO;
    protected Point2D taille;

    protected Camera camera;
    // Physique
    protected Point2D velocite = Point2D.ZERO;
    protected Point2D acceleration = new Point2D(0, 600);

    protected boolean contactSol;

    private double tempsAnimation = 0;
    private double intervalleAnimation = 0.4;
    private boolean changementImage12 = true;

    public Camelot(Camera camera) {
        taille = new Point2D(camelotImage1.getWidth(), camelotImage1.getHeight());
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


    @Override
    public void update(double deltaTemps) {
        boolean accelerationEnPesant = Input.isKeyPressed(KeyCode.RIGHT) || Input.isKeyPressed(KeyCode.D);
        boolean decelerationEnPesant = Input.isKeyPressed(KeyCode.LEFT) || Input.isKeyPressed(KeyCode.A);

        // Mouvement horizontal
        if (decelerationEnPesant) {
            velocite = new Point2D(velocite.getX() * 0.9, velocite.getY());
            // Animation plus lente
            intervalleAnimation = 0.6;
        }
        else if (accelerationEnPesant) {
            velocite = new Point2D(velocite.getX() * 1.2, velocite.getY());
            // Animation plus rapide
            intervalleAnimation = 0.1;
        }
        else {
            velocite = new Point2D(velocite.getX() * 0.95, velocite.getY());
            // Animation normale
            intervalleAnimation = 0.4;
        }
        // Sauter avec Espace ou Flèche vers le haut
        boolean saut = Input.isKeyPressed(KeyCode.SPACE)
                || Input.isKeyPressed(KeyCode.UP);

        // Sauter
        if (contactSol && saut) {
            velocite = new Point2D(velocite.getX(), -400);
            contactSol = false;
        }

        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));

        // Position et velocité quand camelot revient sur le sol
        if (position.getY() + taille.getY() >= JavaFX.h) {
            contactSol = true;
            velocite = new Point2D(velocite.getX(), 0);
            position = new Point2D(position.getX(), JavaFX.h - taille.getY());
        }

        // Positionnement
        position = new Point2D(position.getX(), Math.clamp(position.getY(), 0, JavaFX.h - taille.getY()));

        // Positionnement de la caméra
        Point2D positionCamera = new Point2D(position.getX() - (JavaFX.w / 4.0), 0);
        camera.setPositionCamera(positionCamera);

        // Animation du camelot de base
        tempsAnimation += deltaTemps;
        if (tempsAnimation >= intervalleAnimation) {
            changementImage12 = !changementImage12;
            tempsAnimation = 0;
        }
    }

    @Override
    public void draw(GraphicsContext context) {

        Point2D positionEcran = camera.coordoEcran(position);

        if (!changementImage12) {
            context.drawImage(camelotImage1,positionEcran.getX(), positionEcran.getY(),taille.getX(), taille.getY());
        }
        else {
            context.drawImage(camelotImage2,positionEcran.getX(), positionEcran.getY(),taille.getX(), taille.getY());
        }

//        context.fillText(String.valueOf(velocite.getX()), 0,0);
    }

    @Override
    public void hitBox(GraphicsContext context) {
        boolean activationHitBox = Input.isKeyPressed(KeyCode.H);

//        if (activationHitBox) {
//            context.set
//        }
    }
}
