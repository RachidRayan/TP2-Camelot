package ca.qc.bdeb.sim.tp2;

import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Journal {
    private Point2D position;
    private Image journalImage = new Image("journal.png");

    private Point2D taille = new Point2D(journalImage.getWidth(), journalImage.getHeight());
    private float masse;
    private Point2D quantiteMouvementInitial;
    private Point2D velociteCamelot;
    private Point2D velocite;
    private Point2D accelerationGravité = new Point2D(0, 1500);
    private Camera camera;

    private boolean detruitStatus = false;
    private boolean debugModeDraw;


    public boolean isDetruitStatus() {
        return detruitStatus;
    }

    public void setDetruitStatus(boolean detruitStatus) {
        this.detruitStatus = detruitStatus;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setDebugModeDraw(boolean debugModeDraw) {
        this.debugModeDraw = debugModeDraw;
    }
    // Constructeur
    public Journal(Point2D startPosition, Point2D quantiteMouvementInitial, Point2D velociteCamelot ,float masse, Camera camera) {
        this.position = startPosition;
        this.quantiteMouvementInitial = quantiteMouvementInitial;
        this.velociteCamelot = velociteCamelot;
        this.masse = masse;
        this.camera = camera;
        this.debugModeDraw = false;
        double velociteInitialeX = velociteCamelot.getX() + quantiteMouvementInitial.getX() / masse;
        double velociteInitialeY = velociteCamelot.getY() + quantiteMouvementInitial.getY() / masse;
        this.velocite = new Point2D(velociteInitialeX,velociteInitialeY);

    }
    // Méthode de renouvellement du journal
    public void update(double deltaTemps) {
        velocite = velocite.add(accelerationGravité.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));
    }

    // Méthode de dessin du journal
    public void draw(GraphicsContext context) {
        Point2D coordoEcran = camera.coordoEcran(position);
        context.drawImage(journalImage, coordoEcran.getX(), coordoEcran.getY(), taille.getX(), taille.getY());

        if (debugModeDraw) {
            context.setStroke(Color.YELLOW);
            context.setLineWidth(2.0);
            context.strokeRect(coordoEcran.getX(), coordoEcran.getY(), taille.getX(), taille.getY());
        }
    }

    // Getter dut hitbox pour le debug mode
    public Rectangle2D getHitBox() {
        return new Rectangle2D(position.getX(), position.getY(),journalImage.getWidth(),journalImage.getHeight());
    }
    // Getter de la largeur du journal
    public double getLargeur() {
        return taille.getX();
    }

    // Setter de la vélocité par rapport à la force électrique
    public void setVelocite(Point2D forceElec) {
        this.velocite = velocite.add(forceElec);
    }
}
