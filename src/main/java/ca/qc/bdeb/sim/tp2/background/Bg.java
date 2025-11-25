package ca.qc.bdeb.sim.tp2.background;

import ca.qc.bdeb.sim.tp2.entites.Camelot;
import ca.qc.bdeb.sim.tp2.gameEngine.Camera;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

abstract public class Bg {
    //Camelot camelot = new Camelot();


    protected Point2D velocite = new Point2D(200, 0);
    protected Point2D position = Point2D.ZERO;
    Camera camera = new Camera();

    public abstract void update(double deltaTemps);

    public abstract void draw(GraphicsContext context);
}
