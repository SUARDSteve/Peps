package tsp.pro11.reseau;

import java.io.Serializable;
import java.util.ArrayList;

public class ListeEvent implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * liste d'événements pris au hasard
	 */
	private ArrayList<Event> listeEvent;
	
	/**
	 * Constructeur
	 * @param listeEvent
	 */
	public ListeEvent(ArrayList<Event> listeEvent) {
		this.listeEvent = listeEvent;
	}
	
	public ArrayList<Event> getListeEvent() {
		return listeEvent;
	}
	
	/**
	 * Fonction permettant d'ajouter un événement à la liste
	 * @param event
	 */
	public void addEvent(Event event) {
		this.listeEvent.add(event);
	}
	
	/**
	 * Fonction permettant d'accéder à l'événements d'indice indice
	 * @param indice
	 * @return l'événement d'indie indice
	 */
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
}
