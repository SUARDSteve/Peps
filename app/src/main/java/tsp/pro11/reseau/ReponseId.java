package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * Classe de réponse à une demande d'identification.
 * @author Eric Lallet.
 *
 */
public class ReponseId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * statut de l'identification.
	 *      false = pas reconnu.
	 *      true = reconnu.
	 */   
	private boolean ok = false;
	
	/**
	 * Contruction de la réponse.
	 * @param ok
	 *        statut de l'identification.
	 */
	public ReponseId(boolean ok) {
		this.ok = ok;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * getter sur le statut.
	 * @return
	 *       true si reconnu, false sinon.
	 */
	public boolean isOk() {
		return ok;
	}
	
	
	

}
