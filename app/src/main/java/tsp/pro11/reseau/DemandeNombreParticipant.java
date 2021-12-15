package tsp.pro11.reseau;

import java.io.Serializable;

public class DemandeNombreParticipant implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String nomEvent;

    public DemandeNombreParticipant(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    @Override
    public String toString() {
        return "DemandeNombreParticipant [ nomEvent=" + nomEvent + "]";
    }
}
