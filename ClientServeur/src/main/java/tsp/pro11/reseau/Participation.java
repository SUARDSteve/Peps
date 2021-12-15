package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * 
 * Classe permettant de demander d'inscire un nouveau participant à un événement
 *
 */
public class Participation implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * le nom de l'utilisateur à inscrire
	 */
	private String utilisateur;

	/**
	 * le nom de l'événement auxquels l'utilisateur veut s'inscrire
	 */
	private String nomEvent;
	
	/**
	 * Constructeur
	 * @param utilisateur
	 * @param nomEvent
	 */
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
