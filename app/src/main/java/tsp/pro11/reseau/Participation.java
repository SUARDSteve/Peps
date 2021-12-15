package tsp.pro11.reseau;

import java.io.Serializable;

public class Participation implements  Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String utilisateur;

    private String nomEvent;

    public Participation(String utilisateur,String nomEvent) {
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
        return "Participation [utilisateur=" + utilisateur + ", nomEvent=" + nomEvent + "]";
    }
}
