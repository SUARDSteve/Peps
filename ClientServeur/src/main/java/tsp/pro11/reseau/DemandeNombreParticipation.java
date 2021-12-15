package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * 
 * Classe permettant de demander le nombre d'événement auxquels un utilisateur participe
 *
 */
public class DemandeNombreParticipation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * le nom du participant
	 */
	private String nomParticipant;
	
	/**
	 * Constructeur
	 * @param nomParticipant
	 */
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
