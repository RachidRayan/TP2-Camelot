package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Fenetre extends GenerationPlanArriere {

    private static final Image fenetre = new Image("fenetre.png");
    private static final Image fenetreBriseeVert = new Image("fenetre-brisee-vert.png");
    private static final Image fenetreBriseeRouge = new Image("fenetre-brisee-rouge.png");

    private boolean debugModeDraw;

    private Point2D positionMonde;
    private boolean estFrapper = false;
    private boolean statusAbonnement;
    private Image image = fenetre;

    public void setDebugModeDraw(boolean debugModeDraw) {
        this.debugModeDraw = debugModeDraw;
    }

    public Fenetre(Camera camera, double xPositionMaison, boolean statusAbonnement, double yPosition) {
        super(camera);
        this.positionMonde = new Point2D(xPositionMaison + 300, yPosition);
        this.statusAbonnement = statusAbonnement;
        this.debugModeDraw = false;
    }

    @Override
    public void update(double mouvementVersGauche) {
        positionMonde = new Point2D(positionMonde.getX() - mouvementVersGauche, positionMonde.getY());
    }

    public void draw(GraphicsContext context) {
        Point2D coordoEcran = camera.coordoEcran(positionMonde);
        context.drawImage(image, coordoEcran.getX(), coordoEcran.getY());
        if (debugModeDraw) {
            context.setStroke(Color.YELLOW);
            context.setLineWidth(2.0);
            context.strokeRect(coordoEcran.getX(), coordoEcran.getY(), image.getWidth(), image.getHeight());
        }
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D(positionMonde.getX(),positionMonde.getY(),image.getWidth(), image.getHeight());
    }

    public int contactAvecJournal (Journal journal) {

        if (getHitBox().intersects(journal.getHitBox())) {
            journal.setDetruitStatus(true);
            if (!estFrapper) {
                if (statusAbonnement) {
                    image = fenetreBriseeRouge;
                    estFrapper = true;
                    return -2;
                }
                else {
                    image = fenetreBriseeVert;
                    estFrapper = true;
                    return 2;
                }
            }
            else {
                return 0;
            }

        }
        return 0;
    }
}
