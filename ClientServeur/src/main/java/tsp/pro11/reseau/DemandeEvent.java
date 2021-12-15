package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * 
 * Classe servant à demander une liste d'événement pour le feed
 *
 */
public class DemandeEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * nombre d'événements désirés
	 */
	private int nbrEvent;
	
	/**
	 * nom de l'utilisateur à qui ouvre le feed
	 */
	private String utilisateur;
	
	/**
	 * Constructeur
	 * @param nbrEvent
	 * @param utilisateur
	 */
	public DemandeEvent(int nbrEvent,String utilisateur) {
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
