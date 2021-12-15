package tsp.pro11.reseau;

import java.io.Serializable;
import java.util.ArrayList;

public class ListeParticipants implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * liste des utilisateurs qui ont participé à cet événement
	 */
	private ArrayList<String> listeParticipants;
	
	/**
	 * Constructeur
	 * @param listeParticipants
	 */
	public ListeParticipants(ArrayList<String> listeParticipants) {
		this.listeParticipants = listeParticipants;
	}
	
	public ArrayList<String> getlisteParticipants() {
		return listeParticipants;
	}
	
	/**
	 * Fonction permettant d'ajouter un participant à la liste
	 * @param utilisateur
	 */
	public void addUtilisateur(String utilisateur) {
		this.listeParticipants.add(utilisateur);
	}
	
	/**
	 * Fonction permmettant d'accéder au participant d'indice indice
	 * @param indice
	 * @return le participant d'indice indice
	 */
	public String getUtilisateur(int indice) {
		return this.listeParticipants.get(indice);
	}
	
	public String toString() {
		String resultat = "Liste des participants : ";
		for (int i=0;i<this.listeParticipants.size();i++) {
			resultat = resultat + "\n" + listeParticipants.get(i).toString();
		}
		resultat = resultat + "\n";
		return resultat;
	}
}
