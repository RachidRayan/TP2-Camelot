package ca.qc.bdeb.sim.tp2.gameEngine;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.background.Maison;
import ca.qc.bdeb.sim.tp2.background.PlanArriere;
import ca.qc.bdeb.sim.tp2.background.Brique;
import ca.qc.bdeb.sim.tp2.Camelot;
import ca.qc.bdeb.sim.tp2.entites.Entite;
import ca.qc.bdeb.sim.tp2.entites.Journal;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;


import java.util.ArrayList;

public class Partie {
    private final UI ui = new UI();
    private final Camera camera = new Camera();
    private Camelot camelot;
    private Brique brique;
    private Maison maison;
//    private Fenetre fenetre;
    private ArrayList<PlanArriere> listePlanArriere = new ArrayList<>();
    private ArrayList<Journal> journaux = new ArrayList<>();

    private final double xApparitionPosition = 100;
    private final double xPositionCamera = xApparitionPosition - (JavaFX.w / 4.0);



    private int nombreJournaux = 12;
    private double forceX = 0;
    private double forceY = 0;
    private final double forceMaximaleX = 200;
    private final double forceMaximaleY = -600;
    private final double tauxDeCharge = 800;
    private double lancerTemps = 0;
    private final double rechargeLaner = 0.6;
    private boolean statusQAvant = false;

    private int argent = 0;

    public Partie() {
        camera.setPositionCamera(new Point2D(xPositionCamera, 0));

        this.camelot = new Camelot(camera,xApparitionPosition);
        this.brique = new Brique(camera);
        this.maison= new Maison(camera);
//        this.fenetre = new Fenetre(camera);

        this.listePlanArriere.add(brique);
        this.listePlanArriere.add(maison);
//        this.listePlanArriere.add(fenetre);
    }

    public int getNombreJournaux() {
        return nombreJournaux;
    }

    public void update(double deltaTemps) {
        boolean accelerationEnPesant = Input.isKeyPressed(KeyCode.RIGHT) || Input.isKeyPressed(KeyCode.D);
        boolean decelerationEnPesant = Input.isKeyPressed(KeyCode.LEFT) || Input.isKeyPressed(KeyCode.A);
        // Logique de la vitesse du monde
        double vitesseMouvement = 200;
        if (accelerationEnPesant) vitesseMouvement = 400;
        if (decelerationEnPesant) vitesseMouvement = 100;

        // Logique pour l'accumulation de force pour le lancer du journal (Shift)
        boolean statusShift = Input.isKeyPressed(KeyCode.SHIFT);

        if (statusShift && nombreJournaux > 0) {
            forceX = Math.min(forceMaximaleX, forceX + tauxDeCharge * deltaTemps);
            forceY = Math.max(forceMaximaleY, forceY - tauxDeCharge * deltaTemps);
        }

        boolean doitLancerAvecForce = false;

        if ((!statusShift) && (forceX != 0 || forceY != 0) && // Ajout de || qPeser si on veut lancer avec force en cliquant sur Q et en tenant Shift
                lancerTemps <= 0 &&
                nombreJournaux > 0) {
            doitLancerAvecForce = true;
        }

        // Logique pour l'action de lancer un journal (Q)
        boolean statusQMaintenant = Input.isKeyPressed(KeyCode.Q);
        boolean qPeser = statusQMaintenant && !statusQAvant;
        statusQAvant = statusQMaintenant;

        lancerTemps -= deltaTemps;

        // Logique lorsqu'on relache Shift
        if (doitLancerAvecForce) {
            lancerJournal(forceX,forceY);
            nombreJournaux--;
            lancerTemps = rechargeLaner;
            forceX = 0;
            forceY = 0;
            argent++;
        }
        // Logique lorsqu'on relache Q
        if (qPeser && !statusShift && forceX == 0 && forceY == 0 && lancerTemps <= 0 && nombreJournaux > 0) {
            lancerJournal(200,-500);
            nombreJournaux--;
            lancerTemps = rechargeLaner;
            argent++;
        }

        //   Update du plan d'arrière
        for (PlanArriere planArriere : listePlanArriere) {
            planArriere.update(vitesseMouvement * deltaTemps);
        }

        camelot.update(deltaTemps, vitesseMouvement);

        // Update des journaux
        for (Journal journal : journaux) {
            journal.update(deltaTemps);
        }

        // Test argent (à enlever)
//        argent++;

        // Update de l'UI
        ui.update(getNombreJournaux(),argent);

    }

    public void lancerJournal (double forceX, double forceY) {
        Point2D positionCamelot = camelot.getPosition();
        Point2D tailleCamelot = camelot.getTaille();

        Point2D positionLancer = new Point2D(positionCamelot.getX() + tailleCamelot.getX() / 2.0,
                positionCamelot.getY() + tailleCamelot.getY() / 2.0);

        Point2D velociteInitialJournal = new Point2D(50 + forceX, forceY);

        journaux.add(new Journal(positionLancer, velociteInitialJournal, camera));
    }

    public void draw(GraphicsContext context) {
        // Draw du plan d'arrière
        for (PlanArriere bg : listePlanArriere) {
            bg.draw(context);
        }

        camelot.draw(context);

        // Draw des entités
        for (Entite e : journaux) {
            e.draw(context);
        }
        // Draw de l'UI
        ui.draw(context);
    }

}
