package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

// Classe BoiteAuxLettres (Boite aux lettres)
public class BoiteAuxLettres extends GenerationPlanArriere {

    private final Image boiteALettre = new Image("boite-aux-lettres.png");
    private final Image boiteALettreVert = new Image("boite-aux-lettres-vert.png");
    private final Image boiteALettreRouge = new Image("boite-aux-lettres-rouge.png");
    private Image image = boiteALettre;

    private Point2D positionMonde;

    private boolean estFrapper = false;
    private boolean statusAbonnement;

    private boolean debugModeDraw;

    public void setDebugModeDraw(boolean debugModeDraw) {
        this.debugModeDraw = debugModeDraw;
    }
    // Constructeur
    public BoiteAuxLettres(Camera camera, double xPositionMaison, boolean statusAbonnement, double yPosition) {
        super(camera);
        this.positionMonde = new Point2D(xPositionMaison + 200, yPosition);
        this.statusAbonnement = statusAbonnement;
        this.debugModeDraw = false;
    }
    // Méthode de renouvellement de BoiteAuxLettres (Boite aux lettres)
    @Override
    public void update(double mouvementVersGauche) {
        positionMonde = new Point2D(positionMonde.getX() - mouvementVersGauche, positionMonde.getY());
    }
    // Méthode de dessin de BoiteAuxLettres (Boite aux lettres)
    public void draw(GraphicsContext context) {
        Point2D coordoEcran = camera.coordoEcran(positionMonde);
        context.drawImage(image, coordoEcran.getX(), coordoEcran.getY());
        if (debugModeDraw) {

            context.setStroke(Color.YELLOW);
            context.setLineWidth(2.0);
            context.strokeRect(coordoEcran.getX(), coordoEcran.getY(), image.getWidth(), image.getHeight());
        }
    }

    // Méthode qui contient la logique de collision d'un journal avec une boite aux lettres
    public int contactAvecJournal(Journal journal) {
        if (getHitBox().intersects(journal.getHitBox())) {
            journal.setDetruitStatus(true);
            if (!estFrapper) {
                if (statusAbonnement) {
                    image = boiteALettreVert;
                    estFrapper = true;
                    return 1;
                } else {
                    image = boiteALettreRouge;
                    estFrapper = true;
                    return 0;
                }
            } else {
                return 0;
            }
        }
        return 0;
    }

    // Méthode pour avoir le hitbox de BoiteAuxLettres (Boite aux lettres) (Debug)
    public Rectangle2D getHitBox() {
        return new Rectangle2D(positionMonde.getX(), positionMonde.getY(), image.getWidth(), image.getHeight());
    }
}
