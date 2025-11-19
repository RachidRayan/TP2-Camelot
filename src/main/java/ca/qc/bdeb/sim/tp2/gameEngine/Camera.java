package ca.qc.bdeb.sim.tp2.gameEngine;

import javafx.geometry.Point2D;

public class Camera {
    private Point2D positionCamera;

    public Point2D coordoEcran(Point2D positionMonde){
        return positionMonde.subtract(positionCamera);
    }

    public void setPositionCamera(Point2D positionCamera) {
        this.positionCamera = positionCamera;
    }
}