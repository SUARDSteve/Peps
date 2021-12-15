package csc4509;

import java.io.Serializable;

public class Personne implements  Serializable {
	

	private static final long serialVersionUID = 9028432328352894308L;
	
	String nom;
	String prenom;
	
	public Personne(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	@Override
	public String toString() {
		return "Personne [nom=" + nom + ", prenom=" + prenom + "]";
	}
	
}
