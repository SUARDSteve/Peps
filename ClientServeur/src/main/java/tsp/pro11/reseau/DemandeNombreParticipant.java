package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * 
 * Classe permettant de demander le nombre de participant à un événement
 *
 */
public class DemandeNombreParticipant implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * le nom de l'événement
	 */
	private String nomEvent;
	
	/**
	 * Constructeur
	 * @param nomEvent
	 */
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
