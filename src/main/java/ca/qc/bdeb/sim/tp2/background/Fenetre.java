package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.entites.Journal;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static ca.qc.bdeb.sim.tp2.background.Maison.getNumerosAdresse;

public class Fenetre extends PlanArriere {

    private  Camera camera;
    private final Image fenetreBlanche = new Image("fenetre.png");
    private final Image fenetreVerte = new Image("fenetre-brisee-vert.png");
    private final Image fenetreRouge = new Image("fenetre-brisee-rouge.png");

    public Fenetre(Camera camera) {
        super(camera);
    }

    public boolean casserVitre(Journal journal) {
        if (){

        }

        return false;
    }

//    @Override
//    public void update(double deltaTemps) {
//        position = position.add(velocite.multiply(deltaTemps));
//        camera.setPositionCamera(position);
//    }

    @Override
    public void draw(GraphicsContext context) {
        Point2D positionEcran = camera.coordoEcran(position.multiply(-1));

        for (int i = 0; i < getNumerosAdresse().length * 1.5; i++) {
            //Changer les constantes
            int numero = getNumerosAdresse()[0]+i;
            boolean bonneAdress = false;
            //Verifie si le numero est une bonne adresse
            for (int j = 0; j < getNumerosAdresse().length; j++) {
                if (numero == getNumerosAdresse()[j]) {
                    bonneAdress = true;
                    break;
                }
            }


            if(bonneAdress){
                context.setFill(Color.BLACK);
                context.drawImage(fenetreVerte,positionEcran.getX() + differencePositionnement *i+200, 200);
                context.fillText(String.valueOf(getNumerosAdresse()[0]+i) ,positionEcran.getX() + differencePositionnement *i+200, 200);

            }
            else {
                context.setFill(Color.BLACK);
                context.drawImage(fenetreRouge,positionEcran.getX() + differencePositionnement *i+200, 200);
                context.fillText(String.valueOf(getNumerosAdresse()[0]+i) ,positionEcran.getX() + differencePositionnement *i+200, 200);
            }
        }
    }

}
