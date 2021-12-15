package tsp.pro11.reseau;

import java.io.Serializable;

/**
 *
 * Classe permettant de demander la supression d'un événements
 *
 */
public class SupressionEvent implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * le nom de l'événement à suprimer
     */
    private String nomEvent;

    /**
     * Constructeur
     * @param nomEvent
     */
    public SupressionEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    @Override
    public String toString() {
        return "SupressionEvent [nomEvent=" + nomEvent + "]";
    }

}
