package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * Classe de requête du client pour demander si une identité est connue.
 * @author Eric Lallet.
 *
 */
public class Identification implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9028432328352894308L;
	
	/**
	 * nom de l'identité.
	 */
	private String nom;
	/**
	 * prénom de l'identité.
	 */
	private String prenom;

	/**
	 * Contuction de l'identité.
	 * @param nom
	 *         nom de l'identité.
	 * @param prenom
	 *         prénom de l'identité.
	 */
	public Identification(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	/**
	 * getter sur le nom.
	 * @return
	 *      le nom de l'identité.
	 */
	public String getNom() {
		return nom;
	}


	/**
	 * getter sur le prénom.
	 * @return
	 *      le prénom de l'identité.
	 */
	public String getPrenom() {
		return prenom;
	}
	
	@Override
	public String toString() {
		return "Personne [nom=" + nom + ", prenom=" + prenom + "]";
	}
	
}
