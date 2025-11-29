package ca.qc.bdeb.sim.tp2.generation;

import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fenetre extends GenerationPlanArriere {

    private static final Image fenetre = new Image("fenetre.png");
    private static final Image fenetreBriseeVert = new Image("fenetre-brisee-vert.png");
    private static final Image fenetreBriseeRouge = new Image("fenetre-brisee-rouge.png");


    private Point2D positionMonde;
    private boolean statusAbonnement;
    private boolean dejaFrappee = false;
    private Image image = fenetre;

    public Image getImage() {
        return image;
    }

    public Point2D getPositionMonde() {
        return positionMonde;
    }

    public Fenetre(Camera camera, double xPositionMaison, boolean statusAbonnement, double yPosition) {
        super(camera);
        this.positionMonde = new Point2D(xPositionMaison + 500, yPosition);
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

    public void hitBox(GraphicsContext context) {
    }
}
