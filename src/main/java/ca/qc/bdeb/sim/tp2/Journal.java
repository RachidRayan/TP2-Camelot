package ca.qc.bdeb.sim.tp2;

import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Journal {
    private Point2D position;
    private Image journalImage = new Image("journal.png");

    private Point2D taille = new Point2D(journalImage.getWidth(), journalImage.getHeight());

    private Point2D velocite;
    private Point2D accelerationGravité = new Point2D(0, 1500);

    private Camera camera;

    private boolean detruitStatus = false;

    public boolean isDetruitStatus() {
        return detruitStatus;
    }

    public void setDetruitStatus(boolean detruitStatus) {
        this.detruitStatus = detruitStatus;
    }

    public Point2D getPosition() {
        return position;
    }

    public Journal(Point2D startPosition, Point2D velociteInitiale, Camera camera) {
        this.position = startPosition;
        this.velocite = velociteInitiale;
        this.camera = camera;
    }

    public void update(double deltaTemps) {
        // Physique du journal
        velocite = velocite.add(accelerationGravité.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));
    }


    public void draw(GraphicsContext context) {
        Point2D screenPos = camera.coordoEcran(position);
        context.drawImage(journalImage, screenPos.getX(), screenPos.getY(), taille.getX(), taille.getY());
    }


    public Rectangle2D getHitBox() {
        return new Rectangle2D(position.getX(), position.getY(),journalImage.getWidth(),journalImage.getHeight());
    }

    //coordonnée
    public double getX() {
        return camera.coordoEcran(position).getX();
    }

    public double getY() {
        return camera.coordoEcran(position).getY();
    }

    public double getWidth() {
        return taille.getX();
    }

    public double getHeight() {
        return taille.getY();
    }
}
