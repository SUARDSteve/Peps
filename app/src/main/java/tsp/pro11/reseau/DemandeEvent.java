package tsp.pro11.reseau;

import java.io.Serializable;

public class DemandeEvent implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int nbrEvent;

    private String utilisateur;


    public DemandeEvent(int nbrEvent, String utilisateur) {
        this.nbrEvent = nbrEvent;
        this.utilisateur = utilisateur;
    }


    public int getNbrEvent() {
        return nbrEvent;
    }


    public String getUtilisateur() {
        return utilisateur;
    }


    @Override
    public String toString() {
        return "DemandeEvent [nbrEvent=" + nbrEvent + ", utilisateur=" + utilisateur + "]";
    }

}
