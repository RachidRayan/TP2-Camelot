package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class BoiteAuxLettres extends GenerationPlanArriere {

    private static final Image boiteALettre = new Image("boite-aux-lettres.png");
    private static final Image boiteALettreVert = new Image("boite-aux-lettres-vert.png");
    private static final Image boiteALettreRouge = new Image("boite-aux-lettres-rouge.png");


    private Point2D positionMonde;
    private boolean statusAbonnement;
    private boolean dejaFrappee = false;
    private Image image = boiteALettre;

    public Image getImage() {
        return image;
    }

    public Point2D getPositionMonde() {
        return positionMonde;
    }

    public BoiteAuxLettres(Camera camera, double xPositionMaison, boolean statusAbonnement, double yPosition) {
        super(camera);
        this.positionMonde = new Point2D(xPositionMaison + 200, yPosition);
        this.statusAbonnement = statusAbonnement;
    }

    @Override
    public void update(double mouvementVersGauche) {
        positionMonde = new Point2D(positionMonde.getX() - mouvementVersGauche, positionMonde.getY());
    }


    public void draw(GraphicsContext context) {
        Point2D coordoEcran = camera.coordoEcran(positionMonde);
        context.drawImage(image, coordoEcran.getX(), coordoEcran.getY());
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D(positionMonde.getX(),positionMonde.getY(),image.getWidth(), image.getHeight());
    }

    public int contactAvecJournal (Journal journal) {
        if (dejaFrappee || journal.isDetruitStatus()) {
            return 0;
        }
        if (getHitBox().intersects(journal.getHitBox())) {
            journal.setDetruitStatus(true);
            dejaFrappee = true;
            if (statusAbonnement) {
                image = boiteALettreVert;
                return 2;
            }
            else {
                image = boiteALettreRouge;
                return 0;
            }
        }
        return 0;
    }
}
