package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * 
 * Classe permettant de demander la désinscription d'un utilisateur à un événement
 *
 */
public class Desinscription implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * le nom de l'utilisateur à désinscrire
	 */
	private String utilisateur;
	
	/**
	 * le nom de l'événements auxquels l'utilisateur se désinscrit
	 */
	private String nomEvent;
	
	/**
	 * Constructeur
	 * @param utilisateur
	 * @param nomEvent
	 */
	public Desinscription(String utilisateur,String nomEvent) {
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
