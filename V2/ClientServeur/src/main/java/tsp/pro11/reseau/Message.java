package tsp.pro11.reseau;


import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;


public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * objet pour les requêtes d'identification.
	 */
	private Identification identification = null;
	
	/**
	 * objet pour les réponses aux requêtes d'identification.
	 */
	private ReponseId reponseId = null;
	
	
	/**
	 * Descripteur du contenu du message.
	 */
	private Contenu contenu;
	
	/**
	 * Constrcuteur pour les messages transportant une requête d'identification.
	 * @param identification
	 *        la requête d'identification.
	 */
	Message(Identification identification) {
		this.identification = identification;
		this.contenu = Contenu.IDENTIFICATION;
	}

	/**
	 * Constructeur pour les messages transportant la réponse pour l'identification.
	 * @param reponseId
	 *       la réponse à la requête.
	 */
	Message(ReponseId reponseId) {
		this.reponseId = reponseId;
		this.contenu = Contenu.REPONSEID;
		
	}

	/**
	 * getter sur la requête d'identification.
	 * @return
	 *        la requête d'identification.
	 */
	public Identification getIdentification() {
		return identification;
	}

	/**
	 * getter sur la réponse à la requête d'identification.
	 * @return
	 *      la réponse à la requête.
	 */
	public ReponseId getReponseId() {
		return reponseId;
	}

	/**
	 * getter sur le contenu du message.
	 * @return
	 *      le descripteur du contenu du message.
	 */
	public Contenu getContenu() {
		return contenu;
	}


}
