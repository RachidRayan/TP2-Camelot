package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

abstract public class PlanArriere {

    private final Camera camera;
    protected Point2D position = Point2D.ZERO;
    protected final double differencePositionnement = 2000;

    public PlanArriere(Camera camera) {
        this.camera = camera;
    }


    //    protected final int[] numerosAdresse = setAdresse();
//    public int[] getNumerosAdresse() {
//        return numerosAdresse;
//    }
//
//    //Numero d'adresse des maison pour livrer les journaux
//    public int[] setAdresse(){
//        int[] numeros = new int[8];
//        Random r = new Random();
//        numeros[0] = r.nextInt(80);
//        for (int i = 1; i < numeros.length; i++) {
//            numeros[i] = numeros[0] +i*2 ;
//            System.out.println(numeros[i]);
//        }
//        return numeros;
//    }


    public Point2D getPosition() {
        return position;
    }

    public void update(double mouvementVersGauche)  {
        position = new Point2D(position.getX() - mouvementVersGauche, position.getY());
    };

    public abstract void draw(GraphicsContext context);
}
