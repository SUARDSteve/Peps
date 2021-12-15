package tsp.pro11.reseau;

import java.io.Serializable;

public class Desinscription implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String utilisateur;

    private String nomEvent;

    public Desinscription(String utilisateur, String nomEvent) {
        this.utilisateur = utilisateur;
        this.nomEvent = nomEvent;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    @Override
    public String toString() {
        return "Désinscription [utilisateur=" + utilisateur + ", nomEvent=" + nomEvent + "]";
    }
}
