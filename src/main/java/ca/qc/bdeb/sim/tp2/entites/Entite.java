package ca.qc.bdeb.sim.tp2.entites;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

abstract public class Entite {
        protected Point2D velocite;
        protected Point2D position;

        public abstract void update(double deltaTemps);

        public abstract void draw(GraphicsContext context);

        public abstract void hitBox();

}
