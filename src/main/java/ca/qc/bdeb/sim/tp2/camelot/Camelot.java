package ca.qc.bdeb.sim.tp2.camelot;

public class Camelot {
    private int Nombrejournaux;

    public int getNombrejournaux() {
        return Nombrejournaux;
    }

    public void verificationNombreJournaux (int NbrJournauxRestants) {
        if (NbrJournauxRestants == 0) {
            Nombrejournaux = 12;
        }
        else {
            Nombrejournaux += NbrJournauxRestants;
        }
    }

}
