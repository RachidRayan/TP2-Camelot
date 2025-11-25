package ca.qc.bdeb.sim.tp2.entites;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Journal extends Entite {
    protected Image journalImage = new Image("journal.png");

    protected Point2D position;
    protected Point2D taille = new Point2D(journalImage.getWidth(), journalImage.getHeight());

    protected Point2D velocite;
    protected Point2D accelerationGravité = new Point2D(0, 600);  // Gravité

    protected Camera camera;

    public Journal(Point2D startPosition, Point2D velociteInitiale, Camera camera) {
        this.position = startPosition;
        this.velocite = velociteInitiale;
        this.camera = camera;
    }
    @Override
    public void update(double deltaTemps) {
        // Physique
        velocite = velocite.add(accelerationGravité.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));

        // Enlevement du journal
        if (position.getY() > JavaFX.h || position.getX() < 0 || position.getX() > JavaFX.w * 2) {

        }
    }
    @Override
    public void draw(GraphicsContext context) {
        Point2D screenPos = camera.coordoEcran(position);
        context.drawImage(journalImage, screenPos.getX(), screenPos.getY(), taille.getX(), taille.getY());
    }
    @Override
    public void hitBox(GraphicsContext context) {
        // Implement for collisions if needed
    }
}
