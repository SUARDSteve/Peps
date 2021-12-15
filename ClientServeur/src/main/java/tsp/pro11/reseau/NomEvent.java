package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * 
 * Classe permettant de demander la liste des participants à un événements
 *
 */
public class NomEvent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * le nom de l'événement
	 */
	private String nomEvent;

	public String getNomEvent() {
		return nomEvent;
	}

	public NomEvent(String nomEvent) {
		this.nomEvent = nomEvent;
	}

}
