package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.entites.Entite;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class BoiteALettre {

    private static final Image boiteALettre = new Image("boite-aux-lettres.png");
    private static final Image boiteALettreVert = new Image("boite-aux-lettres-vert.png");
    private static final Image boiteALettreRouge = new Image("boite-aux-lettres-rouge.png");

    private Point2D positionMonde;
    private boolean abonnee;
    private boolean dejaFrappee = false;
    private Image image = boiteALettre;
    private Camera camera;

    public BoiteALettre(Camera camera, double xPositionMaison, boolean abonnee, double yPosition) {
        this.camera = camera;
        this.positionMonde = new Point2D(xPositionMaison + 200, yPosition);
        this.abonnee = abonnee;
    }


//    public Rectangle2D getHitBox() {
//        return new Rectangle2D(positionMonde.getX(), positionMonde.getY(), 81, 76);
//    }


    public void draw(GraphicsContext context) {
        Point2D coordoEcran = camera.coordoEcran(positionMonde);
        context.drawImage(image, coordoEcran.getX(), coordoEcran.getY());
    }

    public void hitBox(GraphicsContext context) {
//        if (isDebugMode()) {
//            Point2D screen = camera.coordoEcran(positionMonde);
//            context.setStroke(javafx.scene.paint.Color.YELLOW);
//            context.strokeRect(screen.getX(), screen.getY(), 81, 76);
//        }
    }
//
//    public int contactAvecJournal(Journal journal) {
//        if (dejaFrappee || journal.isDestroyed()) {
//            return 0;
//        }
//        if (getHitBox().intersects(journal.getHitBox())) {
//            journal.setDestroyed(true);
//            dejaFrappee = true;
//            image = abonnee ? boiteALettreVert : boiteALettreRouge;
//            return abonnee ? 1 : 0;
//        }
//        return 0;
//    }


}
