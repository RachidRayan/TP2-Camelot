package ca.qc.bdeb.sim.tp2.entites;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import ca.qc.bdeb.sim.tp2.gameEngine.Input;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Journal extends Entite {
    protected Image journalImage = new Image("journal.png");

    protected Point2D taille = new Point2D(journalImage.getWidth(), journalImage.getHeight());

    protected Point2D velocite;
    protected Point2D accelerationGravité = new Point2D(0, 700);

    protected Camera camera;

    public Journal(Point2D startPosition, Point2D velociteInitiale, Camera camera) {
        this.position = startPosition;
        this.velocite = velociteInitiale;
        this.camera = camera;
    }

    public void update(double deltaTemps) {
        // Physique du journal
        velocite = velocite.add(accelerationGravité.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));

        // Enlevement du journal (à faire)
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
//    package ca.qc.bdeb.sim.tp2.entites;
//
//import ca.qc.bdeb.sim.tp2.JavaFX;
//import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
//import ca.qc.bdeb.sim.tp2.gameEngine.Input;
//import javafx.geometry.Point2D;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//import javafx.scene.input.KeyCode;
//
//    public class Journal extends Entite {
//
//        private static final Image IMAGE = new Image("journal.png");
//        private static final double LARGEUR = 52;
//        private static final double HAUTEUR = 31;
//        private static final Point2D GRAVITE = new Point2D(0, 1500); // PDF p.4
//        private static final double MAX_VITESSE = 1500;             // PDF p.5
//
//        private Point2D position; // world position (center of journal)
//        private Point2D velocite;
//        private Camera camera;
//        private boolean destroyed = false;
//
//        public Journal(Point2D positionInitiale, Point2D velociteInitiale, Camera camera) {
//            this.position = positionInitiale;
//            this.velocite = velociteInitiale;
//            this.camera = camera;
//        }
//
//        @Override
//        public void update(double deltaTemps) {
//            if (destroyed) return;
//
//            // Apply gravity
//            velocite = velocite.add(GRAVITE.multiply(deltaTemps));
//
//            // Enforce max speed (PDF p.5)
//            if (velocite.magnitude() > MAX_VITESSE) {
//                velocite = velocite.multiply(MAX_VITESSE / velocite.magnitude());
//            }
//
//            // Update position
//            position = position.add(velocite.multiply(deltaTemps));
//
//            // Remove if off-screen and not coming back (PDF p.5)
//            if (position.getY() > JavaFX.h + 200 ||           // far below screen
//                    position.getX() < -200 ||                     // far left
//                    position.getX() > JavaFX.w * 3) {             // far right
//                destroyed = true;
//            }
//        }
//
//        @Override
//        public void draw(GraphicsContext context) {
//            if (destroyed) return;
//            // Draw centered: convert center → top-left
//            Point2D screen = camera.coordoEcran(new Point2D(
//                    position.getX() - IMAGE.getWidth() / 2,
//                    position.getY() - IMAGE.getHeight() / 2
//            ));
//            context.drawImage(IMAGE, screen.getX(), screen.getY());
//        }
//
//        @Override
//        public void hitBox(GraphicsContext context) {
//            if (Input.isKeyPressed(KeyCode.D) && !destroyed) {
//                Point2D screen = camera.coordoEcran(new Point2D(
//                        position.getX() - LARGEUR / 2,
//                        position.getY() - HAUTEUR / 2
//                ));
//                context.setStroke(javafx.scene.paint.Color.YELLOW);
//                context.strokeRect(screen.getX(), screen.getY(), LARGEUR, HAUTEUR);
//            }
//        }
//
//        // === COLLISION LOGIC ===
//        public Rectangle2D getHitBox() {
//            return new Rectangle2D(
//                    position.getX() - LARGEUR / 2,
//                    position.getY() - HAUTEUR / 2,
//                    LARGEUR,
//                    HAUTEUR
//            );
//        }
//
//        public boolean isDestroyed() {
//            return destroyed;
//        }
//
//        public void setDestroyed(boolean destroyed) {
//            this.destroyed = destroyed;
//        }
//
//        // === GETTERS (for debug/UI) ===
//        public double getX() { return camera.coordoEcran(position).getX(); }
//        public double getY() { return camera.coordoEcran(position).getY(); }
//        public double getWidth() { return LARGEUR; }
//        public double getHeight() { return HAUTEUR; }
//    }

