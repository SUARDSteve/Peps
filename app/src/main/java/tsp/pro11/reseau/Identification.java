package tsp.pro11.reseau;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Classe de requête du client pour demander si un utilisateur est connu.
 *
 */
public class Identification extends Utilisateur{

	public Identification(String identifiant, String motdepasse) {
		super(identifiant, motdepasse);
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;



}
