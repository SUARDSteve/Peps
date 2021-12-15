package tsp.pro11.reseau;

import java.io.Serializable;
import java.util.ArrayList;

public class ListeEvent implements  Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * liste de événement pris au hasard
     */
    private ArrayList<Event> listeEvent;

    public ArrayList<Event> getListeEvent() {
        return listeEvent;
    }

    ListeEvent(ArrayList<Event> listeEvent) {
        this.listeEvent = listeEvent;
    }

    public void addEvent(Event event) {
        this.listeEvent.add(event);
    }

    public Event getEvent(int indice) {
        return this.listeEvent.get(indice);
    }

    public String toString() {
        String resultat = "";
        for (int i=0;i<this.listeEvent.size();i++) {
            resultat = resultat + "\n" + listeEvent.get(i).toString();
        }
        resultat = resultat + "\n";
        return resultat;
    }

    public int size() {
        return listeEvent.size();
    }
}
