package ca.qc.bdeb.sim.tp2.gameEngine;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.generation.*;
import ca.qc.bdeb.sim.tp2.Camelot;
//import ca.qc.bdeb.sim.tp2.entites.Entite;
import ca.qc.bdeb.sim.tp2.Journal;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;


import java.util.ArrayList;

public class Partie {
    private UI ui;
    private final Camera camera = new Camera();
    private Camelot camelot;
    private GenerationBriques generationBriques;
    private GenerationMaisons generationMaisons;
    private GenerationBoitesAuxLettres generationBoitesAuxLettres;
    private GenerationFenetres generationFenetres;
    private ArrayList<GenerationPlanArriere> generationPlanArrieres = new ArrayList<>();
    private ArrayList<Journal> journaux = new ArrayList<>();

    private final double xApparitionPosition = 100;
    private final double xPositionCamera = xApparitionPosition - (JavaFX.w / 5.0);



    private int nombreJournaux = 12;
    private double forceX = 0;
    private double forceY = 0;
    private final double forceMaximaleX = 600;
    private final double forceMaximaleY = -1000;
    private final double tauxDeCharge = 1100;
    private double lancerTemps = 0;
    private final double rechargeLaner = 0.6;

    private boolean statusZAvant = false;
    private boolean statusXAvant = false;
    private boolean statusQAvant = false;
    private boolean statusDAvant = false;
    private boolean modeDebug = false;

    private int argent = 0;

    private int niveau;
    private boolean niveauFini = false;
    private boolean partieFinie = false;

    public int getArgent() {
        return argent;
    }

    public int getNiveau() {
        return niveau;
    }

    public boolean isNiveauFini() {
        return niveauFini;
    }

    public boolean isPartieFinie() {
        return partieFinie;
    }

    public Partie() {

        camera.setPositionCamera(new Point2D(xPositionCamera, 0));

        this.camelot = new Camelot(camera,xApparitionPosition);
        this.generationBriques = new GenerationBriques(camera);
        this.generationMaisons = new GenerationMaisons(camera);
        this.generationFenetres = new GenerationFenetres(camera, generationMaisons.getXPositionAdresses(), generationMaisons.getAbonnementsListe());
        this.generationBoitesAuxLettres = new GenerationBoitesAuxLettres(camera, generationMaisons.getXPositionAdresses(), generationMaisons.getAbonnementsListe());
        this.ui = new UI(generationMaisons.getAdressesAbonnees());

        this.generationPlanArrieres.add(generationBriques);
        this.generationPlanArrieres.add(generationMaisons);
        this.generationPlanArrieres.add(generationFenetres);
        this.generationPlanArrieres.add(generationBoitesAuxLettres);

        this.niveau = 1;

    }

    public int getNombreJournaux() {
        return nombreJournaux;
    }

    public void update(double deltaTemps) {
        boolean accelerationEnPesant = Input.isKeyPressed(KeyCode.RIGHT);
        boolean decelerationEnPesant = Input.isKeyPressed(KeyCode.LEFT);

        // Logique de la vitesse du monde
        double vitesseMouvement = 1200;
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
        }

        // Logique pour l'action de lancer un journal vers le haut (Z)
        boolean statusZMaintenant = Input.isKeyPressed(KeyCode.Z);
        boolean zPeser = statusZMaintenant && !statusZAvant;
        statusZAvant = statusZMaintenant;

        // Logique lorsqu'on clique sur Z
        if (zPeser && !statusShift && forceX == 0 && forceY == 0 && lancerTemps <= 0 && nombreJournaux > 0) {
            lancerJournal(50,-1000);
            nombreJournaux--;
            lancerTemps = rechargeLaner;
        }

        // Logique pour l'action de lancer un journal vers l'avant (X)
        boolean statusXMaintenant = Input.isKeyPressed(KeyCode.X);
        boolean xPeser = statusXMaintenant && !statusXAvant;
        statusXAvant = statusXMaintenant;

        // Logique lorsqu'on clique sur X
        if (xPeser && !statusShift && forceX == 0 && forceY == 0 && lancerTemps <= 0 && nombreJournaux > 0) {
            lancerJournal(500,-600);
            nombreJournaux--;
            lancerTemps = rechargeLaner;
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
        for (GenerationPlanArriere planArriere : generationPlanArrieres) {
            planArriere.update(vitesseMouvement * deltaTemps);
        }

        camelot.update(deltaTemps, vitesseMouvement);

        // Update des journaux
        for (Journal journal : journaux) {
            journal.update(deltaTemps);
        }

        // Logique de contact entre un journal et une boite aux lettres
        argent += generationBoitesAuxLettres.collisionAvecJournal(journaux);

        journaux.removeIf(journal -> {
            if (journal.isDetruitStatus()) return true;
            Point2D positionEcran = camera.coordoEcran(journal.getPosition());
            return positionEcran.getX() + journal.getWidth() < 0 || positionEcran.getY() > JavaFX.h;
        });

        // Logique de contact entre un journal et une fenêtre
        argent += generationFenetres.collisionAvecJournal(journaux);

        journaux.removeIf(journal -> {
            if (journal.isDetruitStatus()) return true;
            Point2D positionEcran = camera.coordoEcran(journal.getPosition());
            return positionEcran.getX() + journal.getWidth() < 0 || positionEcran.getY() > JavaFX.h;
        });

        // Update de l'UI
        ui.update(getNombreJournaux(),argent);


        boolean adresseFinaleDepassee = generationMaisons.getAdresseFinale() < camera.getPositionCamera().getX();

        if (adresseFinaleDepassee && nombreJournaux > 0) {
            niveauFini = true;
            partieFinie = false;
        }

        else if (nombreJournaux == 0 && !adresseFinaleDepassee) {
            niveauFini = false;
            partieFinie = true;
        }

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

//        boolean statusDMaintenant = Input.isKeyPressed(KeyCode.D);
//        boolean dPeser = statusDMaintenant && !statusDAvant;
//        statusDAvant = statusDMaintenant;
//
//        if (dPeser) {
//            modeDebug = true;
//        }
//        if (!dPeser) {
//            modeDebug = false;
//        }


        // Draw du plan d'arrière
        for (GenerationPlanArriere planArriere : generationPlanArrieres) {
            planArriere.draw(context);
        }

        camelot.draw(context);

        // Draw des journaux
        for (Journal journal : journaux) {
            journal.draw(context);
        }
        // Draw de l'UI
        ui.draw(context);
    }

    public void debutNouveauNiveau() {
        niveauFini = false;
        partieFinie = false;
        niveau++;

        camelot = new Camelot(camera,xApparitionPosition);

        generationMaisons = new GenerationMaisons(camera);
        generationBoitesAuxLettres = new GenerationBoitesAuxLettres(camera,generationMaisons.getXPositionAdresses(), generationMaisons.getAbonnementsListe());
        generationPlanArrieres.clear();
        generationPlanArrieres.add(generationBriques);
        generationPlanArrieres.add(generationMaisons);
        generationPlanArrieres.add(generationFenetres);
        generationPlanArrieres.add(generationBoitesAuxLettres);

        ui = new UI(generationMaisons.getAdressesAbonnees());

        nombreJournaux += 12;

        forceX = 0;
        forceY = 0;
        lancerTemps = 0;
        journaux.clear();
    }

}
