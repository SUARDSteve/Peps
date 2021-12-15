package tsp.pro11.reseau;

import java.io.Serializable;

public class DemandeNombreParticipation implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String nomParticipant;

    public DemandeNombreParticipation(String nomParticipant) {
        this.nomParticipant = nomParticipant;
    }

    public String getNomParticipant() {
        return nomParticipant;
    }

    @Override
    public String toString() {
        return "DemandeNombreParticipation [nomParticipant=" + nomParticipant + "]";
    }

}
