package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.entites.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class Fenetre extends PlanArriere {

    private  Image fenetreBlanche = new Image("fenetre.png");
    private final Image fenetreVerte = new Image("fenetre-brisee-vert.png");
    private final Image fenetreRouge = new Image("fenetre-brisee-rouge.png");


    protected Point2D taille = new Point2D(fenetreBlanche.getWidth(),fenetreBlanche.getHeight());

    public Point2D getTaille() {
        return taille;
    }

    public Fenetre(Camera camera) {
        super(camera);
    }

    public void setFenetre(Image image, boolean bonneAdresse){
        if(bonneAdresse){
            fenetreBlanche = fenetreVerte;
        }
        else {
            fenetreBlanche = fenetreRouge;
        }
    }

    //Verifie si le numero est une bonne adresse
    public boolean bonneAdresse(int i) {
        boolean estBonneAdresse = false;

//        for (int j = 0; j < getAdresses().length; j++) {
//            if (i == getAdresses()[j]) {
//                estBonneAdresse = true;
//                break;
//            }
//        }
        return estBonneAdresse;
    }



    @Override
    public void draw(GraphicsContext context) {
//        Point2D positionEcran = camera.coordoEcran(position);
//
//        for (int i = 0; i < getAdresses().length; i++) {
//            int numero=getAdresses()[i];
//
//            Point2D position = new Point2D(positionEcran.getX() + differencePositionnement * i + 200,200);
//
//            if (bonneAdresse(numero)) {
//
//                context.setFill(Color.BLACK);
//                context.drawImage(fenetreVerte, positionEcran.getX() + differencePositionnement * i + 200, 200);
////                context.fillText(String.valueOf(getAdresses()[i]), position.getX(), position.getY());
//
//            } else {
//                context.setFill(Color.BLACK);
//                context.drawImage(fenetreRouge, positionEcran.getX() + differencePositionnement * i + 200, 200);
////                context.fillText(String.valueOf(getAdresses()[i]), position.getX(), position.getY());
//            }
//        }
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
