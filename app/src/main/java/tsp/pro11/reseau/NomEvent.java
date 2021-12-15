package tsp.pro11.reseau;

import java.io.Serializable;

public class NomEvent implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String nomEvent;

    public String getNomEvent() {
        return nomEvent;
    }

    public NomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

}
