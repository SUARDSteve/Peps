package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * 
 * Classe permettant de demander le nombre d'événements créé par un utilisateur
 *
 */
public class DemandeNombreCreation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Le nom de l'organisateur
	 */
	private String nomOrganisateur;
	
	/**
	 * Constructeur
	 * @param nomOrganisateur
	 */
	public DemandeNombreCreation(String nomOrganisateur) {
		this.nomOrganisateur = nomOrganisateur;
	}

	public String getNomOrganisateur() {
		return nomOrganisateur;
	}

	@Override
	public String toString() {
		return "DemandeNombreCreation [nomOrganisateur=" + nomOrganisateur + "]";
	}
}
