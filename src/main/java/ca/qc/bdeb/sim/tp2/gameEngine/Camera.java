package ca.qc.bdeb.sim.tp2.gameEngine;

import javafx.geometry.Point2D;
// Classe Camera
public class Camera {
    private Point2D positionCamera = Point2D.ZERO;
    public void setPositionCamera(Point2D positionCamera) {
        this.positionCamera = positionCamera;
    }

    public Point2D getPositionCamera() {
        return positionCamera;
    }

    // Méthode pour avoir les coordonnées de l'écran
    public Point2D coordoEcran(Point2D positionMonde){
        return new Point2D (positionMonde.getX() - positionCamera.getX(),
                positionMonde.getY() - positionCamera.getY());
    }


}