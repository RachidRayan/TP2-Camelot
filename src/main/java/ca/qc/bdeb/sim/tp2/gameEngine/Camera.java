package ca.qc.bdeb.sim.tp2.gameEngine;

import javafx.geometry.Point2D;

public class Camera {
    private Point2D positionCamera = Point2D.ZERO;

    public void setPositionCamera(Point2D positionCamera) {
        this.positionCamera = positionCamera;
    }

    public Point2D getPositionCamera() {
        return positionCamera;
    }

    public Point2D coordoEcran(Point2D positionMonde){
        return positionMonde.subtract(positionMonde.getX() - positionCamera.getX(),
                positionMonde.getY() - positionCamera.getY());
    }


}