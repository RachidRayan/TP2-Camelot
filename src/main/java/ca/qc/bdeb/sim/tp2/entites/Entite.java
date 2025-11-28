package ca.qc.bdeb.sim.tp2.entites;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

abstract public class Entite {
        protected Point2D velocite;
        protected Point2D position;

        public abstract void update(double deltaTemps, double vitesseMouvement);

        public abstract void draw(GraphicsContext context);

        public abstract void hitBox(GraphicsContext context);

}
