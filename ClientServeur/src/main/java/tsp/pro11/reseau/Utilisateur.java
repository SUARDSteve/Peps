package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * Classe de requête du client pour un utilisateur.
 *
 */
public class Utilisateur implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9028432328352894308L;
	
	/**
	 * identifiant de l'utilisateur.
	 */
	private String identifiant;
	/**
	 * mot de passe de l'utilisateur.
	 */
	private String motdepasse;

	/**
	 * Contuction de l'utilisateur.
	 * @param identifiant
	 *         identifiant de l'utilisateur.
	 * @param motdepasse
	 *         mot de passe de l'utilisateur.
	 */
	public Utilisateur(String identifiant, String motdepasse) {
		this.identifiant = identifiant;
		this.motdepasse = motdepasse;
	}

	/**
	 * getter sur l'identifiant.
	 * @return
	 *      l'identifiant de l'utilisateur.
	 */
	public String getIdentifiant() {
		return identifiant;
	}


	/**
	 * getter sur le mot de passe.
	 * @return
	 *      le mot de passe de l'utilisateur.
	 */
	public String getMotdepasse() {
		return motdepasse;
	}
	
	@Override
	public String toString() {
		return "Personne [Identifiant=" + identifiant + ", Motdepasse=" + motdepasse + "]";
	}
	
}
