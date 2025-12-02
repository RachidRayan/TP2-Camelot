package ca.qc.bdeb.sim.tp2.gameEngine;

import ca.qc.bdeb.sim.tp2.JavaFX;
import ca.qc.bdeb.sim.tp2.generation.*;
import ca.qc.bdeb.sim.tp2.Camelot;
import ca.qc.bdeb.sim.tp2.Journal;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;


import java.util.ArrayList;
import java.util.Random;
// Classe Partie
public class Partie {
    private UI ui;
    private final Camera camera = new Camera();
    private Camelot camelot;
    private GenerationBriques generationBriques;
    private GenerationMaisons generationMaisons;
    private GenerationBoitesAuxLettres generationBoitesAuxLettres;
    private GenerationFenetres generationFenetres;
    private GenerationPointsGravite generationPointsGravite;
    private ArrayList<GenerationPlanArriere> generationPlanArriere = new ArrayList<>();
    private ArrayList<Journal> journauxVivants = new ArrayList<>();

    private final double xApparitionPosition = 100;
    private final double xPositionCamera = xApparitionPosition - (JavaFX.largeur / 5.0);

    private double vitesseMouvement;
    private final double vitesseDeBase = 400;
    private final double vitesseMouvementMaximale = 600;
    private final double vitesseMouvementMinimale = 200;
    private final double acceleration = 300;

    private int nombreJournaux = 12;
    private float masseJournaux;
    private double lancerTemps = 0;
    private final double rechargeLaner = 0.6;

    private boolean statusZAvant = false;
    private boolean statusXAvant = false;
    private boolean statusQAvant = false;
    private boolean statusKAvant = false;
    private boolean statusLAvant = false;
    private boolean statusDAvant = false;
    private boolean statusFAvant = false;
    private boolean statusIAvant = false;
    private boolean debugMode = false;
    private boolean montrerChampsElectique = false;
    private boolean testChampsElectrique = false;

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

    // Constructeur
    public Partie() {

        camera.setPositionCamera(new Point2D(xPositionCamera, 0));

        this.camelot = new Camelot(camera, xApparitionPosition);
        this.generationBriques = new GenerationBriques(camera);
        this.generationMaisons = new GenerationMaisons(camera);
        this.generationFenetres = new GenerationFenetres(camera, generationMaisons.getXPositionAdresses(), generationMaisons.getAbonnementsListe());
        this.generationBoitesAuxLettres = new GenerationBoitesAuxLettres(camera, generationMaisons.getXPositionAdresses(), generationMaisons.getAbonnementsListe());
        this.generationPointsGravite = new GenerationPointsGravite(camera);
        this.ui = new UI(generationMaisons.getAdressesAbonnees());

        this.generationPlanArriere.add(generationBriques);
        this.generationPlanArriere.add(generationMaisons);
        this.generationPlanArriere.add(generationFenetres);
        this.generationPlanArriere.add(generationBoitesAuxLettres);


        this.niveau = 1;

        Random random = new Random();
        masseJournaux = random.nextFloat(1,3);

        this.vitesseMouvement = vitesseDeBase;

    }

    public int getNombreJournaux() {
        return nombreJournaux;
    }
    // Méthode de renouvellement de Partie (Contient tous les renouvellements)
    public void update(double deltaTemps) {
        boolean accelerationEnPesant = Input.isKeyPressed(KeyCode.RIGHT);
        boolean decelerationEnPesant = Input.isKeyPressed(KeyCode.LEFT);

        // Logique de la vitesse du monde
        if (accelerationEnPesant && vitesseMouvement < vitesseMouvementMaximale) {
            vitesseMouvement = vitesseMouvement + acceleration * deltaTemps;
        }
        if (decelerationEnPesant && vitesseMouvement > vitesseMouvementMinimale) {
            vitesseMouvement = vitesseMouvement - acceleration * deltaTemps;
        }
        if (!accelerationEnPesant && !decelerationEnPesant && vitesseMouvement != vitesseDeBase) {
            if (vitesseMouvement > vitesseDeBase) {
                vitesseMouvement = vitesseMouvement - acceleration * deltaTemps;
            } else if (vitesseMouvement < vitesseDeBase) {
                vitesseMouvement = vitesseMouvement + acceleration * deltaTemps;
            }
        }

        // Logique pour l'accumulation de force pour le lancer du journal (Shift)
        boolean statusShift = Input.isKeyPressed(KeyCode.SHIFT);

        // Logique pour l'action de lancer un journal vers le haut (Z)
        boolean statusZMaintenant = Input.isKeyPressed(KeyCode.Z);
        boolean zPeser = statusZMaintenant && !statusZAvant;
        statusZAvant = statusZMaintenant;

        if (zPeser && lancerTemps <= 0 && nombreJournaux > 0) {
            if (statusShift) {
                lancerJournal(150 * 1.5, -1100 * 1.5);
            }
            else {
                lancerJournal(150, -1100);
            }
            nombreJournaux--;
            lancerTemps = rechargeLaner;
        }

        // Logique pour l'action de lancer un journal vers l'avant (X)
        boolean statusXMaintenant = Input.isKeyPressed(KeyCode.X);
        boolean xPeser = statusXMaintenant && !statusXAvant;
        statusXAvant = statusXMaintenant;

        // Logique lorsqu'on clique sur X
        if (xPeser && lancerTemps <= 0 && nombreJournaux > 0) {
            if (statusShift) {
                lancerJournal(900 * 1.5, -900 * 1.5);
            }
            else {
                lancerJournal(900, -900);
            }
            nombreJournaux--;
            lancerTemps = rechargeLaner;
        }

        lancerTemps -= deltaTemps;

        // Logique pour l'action de restocker les journaux +10 (Debug) (Q)
        boolean statusQMaintenant = Input.isKeyPressed(KeyCode.Q);
        boolean qPeser = statusQMaintenant && !statusQAvant;
        statusQAvant = statusQMaintenant;

        if (qPeser) {
            nombreJournaux += 10;
        }

        // Logique pour l'action de épuiser le stock (Debug) (K)
        boolean statusKMaintenant = Input.isKeyPressed(KeyCode.K);
        boolean kPeser = statusKMaintenant && !statusKAvant;
        statusKAvant = statusKMaintenant;

        if (kPeser) {
            nombreJournaux = 0;
        }

        // Logique pour l'action de changer de niveau (Debug) (L)
        boolean statusLMaintenant = Input.isKeyPressed(KeyCode.L);
        boolean lPeser = statusLMaintenant && !statusLAvant;
        statusLAvant = statusLMaintenant;

        if (lPeser) {
            niveauFini = true;
            partieFinie = false;
        }

        // Logique pour l'action d'activer le debug mode (Debug) (D)
        boolean statusDMaintenant = Input.isKeyPressed(KeyCode.D);
        boolean dPeser = statusDMaintenant && !statusDAvant;
        statusDAvant = statusDMaintenant;

        if (dPeser) {
            debugMode = !debugMode;
            activationDebugMode(debugMode);
        }

        // Logique pour l'action d'activer le champs electrique visuel (F)
        boolean statusFMaintenant = Input.isKeyPressed(KeyCode.F);
        boolean fPeser = statusFMaintenant && !statusFAvant;
        statusFAvant = statusFMaintenant;

        if(fPeser){
            montrerChampsElectique = !montrerChampsElectique;
            activationChampsElectrique(montrerChampsElectique);
        }

        // Logique pour l'action d'activer le champs electrique de test (I)
        boolean statusIMaintenant = Input.isKeyPressed(KeyCode.I);
        boolean iPeser = statusIMaintenant && !statusIAvant;
        statusIAvant = statusIMaintenant;

        if(iPeser){
            testChampsElectrique = !testChampsElectrique;
            activationChampsElectriqueTest(testChampsElectrique);
        }

        //   Update du plan d'arrière
        for (GenerationPlanArriere planArriere : generationPlanArriere) {
            planArriere.update(vitesseMouvement * deltaTemps);
        }

        camelot.update(deltaTemps);

        // Update des journaux
        for (Journal journal : journauxVivants) {
            journal.update(deltaTemps);
        }

        // Logique de contact entre un journal et une boite aux lettres
        argent += generationBoitesAuxLettres.collisionAvecJournal(journauxVivants);

        journauxVivants.removeIf(journal -> {
            if (journal.isDetruitStatus()) return true;
            Point2D positionEcran = camera.coordoEcran(journal.getPosition());
            return positionEcran.getX() + journal.getLargeur() < 0 || positionEcran.getY() > JavaFX.hauteur;
        });

        // Logique de contact entre un journal et une fenêtre
        argent += generationFenetres.collisionAvecJournal(journauxVivants);

        journauxVivants.removeIf(journal -> {
            if (journal.isDetruitStatus()) return true;
            Point2D positionEcran = camera.coordoEcran(journal.getPosition());
            return positionEcran.getX() + journal.getLargeur() < 0 || positionEcran.getY() > JavaFX.hauteur;
        });

        //Logique de la force des champs magnétiques
        if(niveau > 1) {
            for (PointsGravite points : generationPointsGravite.getParticules()) {
                for (Journal journal : journauxVivants) {
                    journal.setVelocite(points.champsElectrique(journal.getPosition()));
                }
            }
        }

        // Update de l'UI
        ui.update(getNombreJournaux(), argent);

        // Logique de verification de fin du niveau
        boolean adresseFinaleDepassee = generationMaisons.getAdresseFinale() < camera.getPositionCamera().getX();

        if (adresseFinaleDepassee && nombreJournaux > 0) {
            niveauFini = true;
            partieFinie = false;
        } else if (nombreJournaux == 0 && !adresseFinaleDepassee) {
            niveauFini = false;
            partieFinie = true;
        }

    }
    // Méthode de dessin de Partie (Contient tous les dessins)
    public void draw(GraphicsContext context) {
        // Draw du plan d'arrière
        for (GenerationPlanArriere planArriere : generationPlanArriere) {
            planArriere.draw(context);
        }

        camelot.draw(context);

        // Draw des journaux
        for (Journal journal : journauxVivants) {
            journal.draw(context);
        }
        // Draw de l'UI
        ui.draw(context);
    }

    // Méthode d'activation du debug mode
    public void activationDebugMode(boolean debugMode) {
        generationBoitesAuxLettres.setDebugMode(debugMode);
        generationFenetres.setDebugMode(debugMode);

        for (Journal journal : journauxVivants) {
            journal.setDebugModeDraw(debugMode);
        }
        camelot.setDebugModeDraw(debugMode);
    }

    public void activationChampsElectrique(boolean montrerChampsElectique){
        generationPointsGravite.setMontrerChampsElectrique(montrerChampsElectique);
    }

    public void activationChampsElectriqueTest(boolean testChampsElectrique){
        if (testChampsElectrique) {
            generationPointsGravite.genererParticulesDebug(camera);
        }
        else{
            generationPointsGravite.regenererPointsGravite();
        }
    }

    // Méthode de lancer un journal (et en créer un)
    public void lancerJournal(double quantiteMouvementX, double quantiteMouvementY) {
        Point2D positionCamelot = camelot.getPosition();
        Point2D tailleCamelot = camelot.getTaille();

        Point2D positionLancer = new Point2D(positionCamelot.getX() + tailleCamelot.getX() / 2.0,
                positionCamelot.getY() + tailleCamelot.getY() / 2.0);

        Point2D quantiteDeMouvement = new Point2D(quantiteMouvementX, quantiteMouvementY);
        Journal journal = new Journal(positionLancer, quantiteDeMouvement, camelot.getVelocite(), masseJournaux, camera);
        journal.setDebugModeDraw(debugMode);
        journauxVivants.add(journal);
    }

    // Méthode de début de niveau
    public void debutNouveauNiveau() {
        niveauFini = false;
        partieFinie = false;
        niveau++;

        // Repositionnement du camelot (on crée un nouveau)
        camelot = new Camelot(camera, xApparitionPosition);
        // Régénération
        generationMaisons = new GenerationMaisons(camera);
        generationFenetres = new GenerationFenetres(camera, generationMaisons.getXPositionAdresses(), generationMaisons.getAbonnementsListe());
        generationBoitesAuxLettres = new GenerationBoitesAuxLettres(camera, generationMaisons.getXPositionAdresses(), generationMaisons.getAbonnementsListe());
        generationFenetres = new GenerationFenetres(camera, generationMaisons.getXPositionAdresses(),generationMaisons.getAbonnementsListe());
        // Vidage de la liste des objets qui font partie du plan arrière
        generationPlanArriere.clear();
        // Ajout des nouveaux objets dans la liste de génération du plan arrière
        generationPlanArriere.add(generationBriques);
        generationPlanArriere.add(generationMaisons);
        generationPlanArriere.add(generationFenetres);
        generationPlanArriere.add(generationBoitesAuxLettres);
        if(niveau > 1) { //Génère les particules que si on a fini niveau 1
            generationPlanArriere.add(generationPointsGravite);
            if(testChampsElectrique){
                generationPointsGravite.genererParticulesDebug(camera);
            }else {
                generationPointsGravite.regenererPointsGravite();
            }

        }
        // Régénération du l'UI
        ui = new UI(generationMaisons.getAdressesAbonnees());

        nombreJournaux += 12;
        // Changement de masse des journaux
        Random random = new Random();
        masseJournaux = random.nextInt(1,3);

        lancerTemps = 0;
        journauxVivants.clear();
    }

}
