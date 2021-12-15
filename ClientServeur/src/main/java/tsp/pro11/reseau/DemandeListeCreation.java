package tsp.pro11.reseau;

import java.io.Serializable;

/**
 * 
 * Classe permettant de demander la liste des événements créé par un utilisateur
 *
 */
public class DemandeListeCreation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * le nom de l'oraganisateur
	 */
	private String nomOrganisateur;
	
	/**
	 * Constructeur
	 * @param nomOrganisateur
	 */
	public DemandeListeCreation(String nomOrganisateur) {
		this.nomOrganisateur = nomOrganisateur;
	}

	public String getNomOrganisateur() {
		return nomOrganisateur;
	}

	@Override
	public String toString() {
		return "DemandeListeCreation [nomOrganisateur=" + nomOrganisateur + "]";
	}
}
