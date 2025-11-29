package ca.qc.bdeb.sim.tp2.gameEngine;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.generation.*;
import ca.qc.bdeb.sim.tp2.Camelot;
import ca.qc.bdeb.sim.tp2.entites.Entite;
import ca.qc.bdeb.sim.tp2.entites.Journal;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;


import java.util.ArrayList;

public class Partie {
    private UI ui;
    private final Camera camera = new Camera();
    private Camelot camelot;
    private GenerationBriques briques;
    private GenerationMaisons maisons;
    private GenerationBoitesAuxLettres boiteAuxLettres;
    private GenerationFenetres fenetres;
    private ArrayList<GenerationPlanArriere> listePlanArriere = new ArrayList<>();
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

    private boolean statusZAvant = false;
    private boolean statusXAvant = false;
    private boolean statusQAvant = false;

    private int argent = 0;

    public Partie() {

        camera.setPositionCamera(new Point2D(xPositionCamera, 0));

        this.camelot = new Camelot(camera,xApparitionPosition);
        this.briques = new GenerationBriques(camera);
        this.maisons = new GenerationMaisons(camera);
        this.fenetres = new GenerationFenetres(camera);
        this.boiteAuxLettres = new GenerationBoitesAuxLettres(camera,maisons);
        this.ui = new UI(maisons.getAdressesAbonnees());

        this.listePlanArriere.add(briques);
        this.listePlanArriere.add(maisons);
        this.listePlanArriere.add(fenetres);
        this.listePlanArriere.add(boiteAuxLettres);
    }

    public int getNombreJournaux() {
        return nombreJournaux;
    }

    public void update(double deltaTemps) {
        boolean accelerationEnPesant = Input.isKeyPressed(KeyCode.RIGHT) || Input.isKeyPressed(KeyCode.D);
        boolean decelerationEnPesant = Input.isKeyPressed(KeyCode.LEFT) || Input.isKeyPressed(KeyCode.A);
        // Logique de la vitesse du monde
        double vitesseMouvement = 350;
        if (accelerationEnPesant) vitesseMouvement = 500;
        if (decelerationEnPesant) vitesseMouvement = 200;

        // Logique pour l'accumulation de force pour le lancer du journal (Shift)
        boolean statusShift = Input.isKeyPressed(KeyCode.SHIFT);

        if (statusShift && nombreJournaux > 0) {
            forceX = Math.min(forceMaximaleX, forceX + tauxDeCharge * deltaTemps);
            forceY = Math.max(forceMaximaleY, forceY - tauxDeCharge * deltaTemps);
        }

        boolean doitLancerAvecForce = false;

        if ((!statusShift) && (forceX != 0 || forceY != 0) &&
                lancerTemps <= 0 &&
                nombreJournaux > 0) {
            doitLancerAvecForce = true;
        }

        // Logique lorsqu'on relache Shift
        if (doitLancerAvecForce) {
            lancerJournal(forceX,forceY);
            nombreJournaux--;
            lancerTemps = rechargeLaner;
            forceX = 0;
            forceY = 0;
            argent++;
        }

        // Logique pour l'action de lancer un journal vers le haut (Z)
        boolean statusZMaintenant = Input.isKeyPressed(KeyCode.Z);
        boolean zPeser = statusZMaintenant && !statusZAvant;
        statusZAvant = statusZMaintenant;

        // Logique lorsqu'on clique sur Z
        if (zPeser && !statusShift && forceX == 0 && forceY == 0 && lancerTemps <= 0 && nombreJournaux > 0) {
            lancerJournal(50,-650);
            nombreJournaux--;
            lancerTemps = rechargeLaner;
            argent++;
        }

        // Logique pour l'action de lancer un journal vers l'avant (X)
        boolean statusXMaintenant = Input.isKeyPressed(KeyCode.X);
        boolean xPeser = statusXMaintenant && !statusXAvant;
        statusXAvant = statusXMaintenant;

        // Logique lorsqu'on clique sur X
        if (xPeser && !statusShift && forceX == 0 && forceY == 0 && lancerTemps <= 0 && nombreJournaux > 0) {
            lancerJournal(400,-300);
            nombreJournaux--;
            lancerTemps = rechargeLaner;
            argent++;
        }

        lancerTemps -= deltaTemps;

        // Logique pour l'action de restockerles journaux +10 (Debug) (Q)
        boolean statusQMaintenant = Input.isKeyPressed(KeyCode.Q);
        boolean qPeser = statusQMaintenant && !statusQAvant;
        statusQAvant = statusQMaintenant;

        if (qPeser) {
            nombreJournaux += 10;
        }

        //   Update du plan d'arrière
        for (GenerationPlanArriere planArriere : listePlanArriere) {
            planArriere.update(vitesseMouvement * deltaTemps);
        }

        camelot.update(deltaTemps, vitesseMouvement);

        // Update des journaux
        for (Journal journal : journaux) {
            journal.update(deltaTemps);
        }

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

    public boolean casserVitres(Journal journal , GenerationFenetres fenetre, double x, double y){
        if(journal.getX() + journal.getWidth() > fenetre.getX() + fenetre.getWidth() && journal.getY() + journal.getHeight() > fenetre.getY() + fenetre.getHeight()){
            return true;
        }
        return false;
    }



    public void draw(GraphicsContext context) {
        // Draw du plan d'arrière
        for (GenerationPlanArriere bg : listePlanArriere) {
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
