package tsp.pro11.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import tsp.pro11.Exception.NombreEventException;
import tsp.pro11.reseau.DemandeEvent;
import tsp.pro11.reseau.DemandeListeCreation;
import tsp.pro11.reseau.DemandeNombreCreation;
import tsp.pro11.reseau.DemandeNombreParticipant;
import tsp.pro11.reseau.DemandeNombreParticipation;
import tsp.pro11.reseau.Desinscription;
import tsp.pro11.reseau.Event;
import tsp.pro11.reseau.Identification;
import tsp.pro11.reseau.Inscription;
import tsp.pro11.reseau.NomEvent;
import tsp.pro11.reseau.Participation;
import tsp.pro11.reseau.Photo;
import tsp.pro11.reseau.SupressionEvent;


public class Requete {

	/**
	 * Fonction permettant de vérifier si identification est dans la base de données.
	 * 
	 * @param identification
	 * 					contenant les champs identifiant et mot de passe.
	 * @return renvoie true si identification est dans la base de données et false sinon.
	 * @throws Exception
	 */
	public static boolean connecter(Identification identification) throws Exception {
		DataBase sgbd = new DataBase("jdbc:sqlite:Peps.db"); //chemin d'accès à la base de données
		boolean estDansLaBase = false; // vaut true si l'utilisateur existe et false sinon

		try (ResultSet userSql = sgbd.query("SELECT * FROM utilisateurs WHERE identifiant = '" + identification.getIdentifiant() + "' AND motdepasse = '" +  identification.getMotdepasse() + "'");) {
			estDansLaBase = userSql.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return estDansLaBase;
	}

	/**
	 * Fonction permettant d'inscrire un utilisateur dans la base de données.
	 * @param inscription
	 * 				contenant les champs identifiant et mot de passe.
	 * @return renvoie true si l'utilisateur est déja inscrit dans la base de données.
	 */
	public static boolean inscrire(Inscription inscription) {
		DataBase sgbd = new DataBase("jdbc:sqlite:Peps.db"); //chemin d'accès à la base de données
		boolean estInscrit = false; // vaut true si l'utilisateur était déjà dans la base de données 
		int id = 0; //id du dernier utilisateur inscrit

		//On vérifie que l'utilisateur n'est pas déja inscrit
		try (ResultSet userSql = sgbd.query("SELECT * FROM utilisateurs WHERE identifiant = '" + inscription.getIdentifiant() + "'");) {
			estInscrit = userSql.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// On inscrit l'utilisateur dans la base de donées si il n'y était pas déjà
		if (!(estInscrit)) {
			//on récupère le dernier id
			try (ResultSet dernierId = sgbd.query("SELECT idUtilisateur FROM utilisateurs ORDER BY idUtilisateur DESC LIMIT 1");) {
				dernierId.next();
				id = dernierId.getInt("idUtilisateur") + 1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				sgbd.insert("INSERT INTO utilisateurs(idUtilisateur, identifiant, motdepasse) " + "VALUES ('" + id + "', '" + inscription.getIdentifiant() + "', '" + inscription.getMotdepasse() + "')");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return estInscrit;
	}

	/**
	 * Fonction permettant d'ajouter un event dans la base de données
	 * @param event
	 * 			contenant les champs nom, organisateur, date, prix et éventuellement une photo
	 * @return renvoie true si l'event existe déjà dans la base de données
	 */
	public static boolean ajouter(Event event) {
		DataBase sgbd = new DataBase("jdbc:sqlite:Peps.db"); //chemin d'accès à la base de données
		boolean estDejaAjoute = false; // vaut true si l'event est déjà dans la base de données

		//On vérifie que le nom de l'événement n'est pas déja utilisé
		try (ResultSet eventSql = sgbd.query("SELECT * FROM events WHERE nom = '" + event.getNom() + "'");) {
			estDejaAjoute = eventSql.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//Si le nom de l'événement n'est pas déja pris, on le rajoute dans la base de données
		if (!(estDejaAjoute)) {
			try {
				// On distingue les cas selon si l'event a une photo ou non
				if (event.aUnePhoto()) {
					sgbd.insert("INSERT INTO events(nom, organisateur, date, prix, photo) "
							+ "VALUES ('" + event.getNom() + "', '" + event.getOrganisateur() + "', '" + event.getDate() + "', '" + event.getPrix() + "', '" + event.getPhoto() + "')");

				}
				// si l'event n'a pas de photo, on mets NULL à la place
				else {
					sgbd.insert("INSERT INTO events(nom, organisateur, date, prix, photo) "
							+ "VALUES ('" + event.getNom() + "', '" + event.getOrganisateur() + "', '" + event.getDate() + "', '" + event.getPrix() + "', NULL )");
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return estDejaAjoute;
	}

	/**
	 * Fonction retournant une liste d'événements tirées au hasard dans la base de données
	 * @param demandeEvent
	 * 				contenant les champs nombre d'événements à retourner ainsi que nomUtilisateur afin de ne pas retourner des event auxquels il participe déja
	 * @return la liste des événements
	 * @throws NombreEventException 
	 */

	public static ArrayList<Event> demanderListeEvents(DemandeEvent demandeEvent) throws NombreEventException {
		DataBase sgbd = new DataBase("jdbc:sqlite:Peps.db"); //chemin d'accès à la base de données
		int totEvent=0; //contient le nombre total d'événement dans la base de données
		ArrayList<Integer> listTirage = new ArrayList<Integer>(); //liste permet de tirer les nombre sans doublons
		ArrayList<Event> listeEvent = new ArrayList<Event>(); //liste contenant des événements pris de manière aléatoire
		ArrayList<String> listeNomEventParticipation = new ArrayList<>(); //liste contenant des noms d'événements auxquels l'utilisateur participe
		ArrayList<Event> listeParticipations = new ArrayList<>(); //liste des événements auxquels l'utilisateur participe
		Random random = new Random();

		// Permet de savoir le nombre total d'événement dans la base de données
		try (ResultSet eventSql = sgbd.query("Select COUNT(*) FROM events");) {	
			eventSql.next();
			totEvent = eventSql.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// on initialise notre liste de nombre
		for (int j =0; j < totEvent; j++) {
			listTirage.add(j);	
		}

		// Permet d'avoir la liste des nom des événement aux l'utilisateur participe
		try (ResultSet participantSql = sgbd.query("SELECT nomEvent FROM participations WHERE utilisateur = '" + demandeEvent.getUtilisateur() + "'");) {
			while (participantSql.next()) {
				listeNomEventParticipation.add(participantSql.getString("nomEvent"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Permet de construire listeParticipations grâce à listeNomEventParticipations
		for (String nomEvent:listeNomEventParticipation) {
			// Pour chaque nomEvent on cherche les autres informations qui lui sont associées 
			try (ResultSet eventSql = sgbd.query("SELECT nom, organisateur, date, prix, photo FROM events WHERE nom = '" + nomEvent + "'");) {
				eventSql.next();
				Photo photo = new Photo(eventSql.getBytes("photo"));
				Event event;
				// Distinction des cas selon qu'il y a une photo ou non pour le constructeur
				if (photo.estVide()) {
					event = new Event(eventSql.getString("nom"),eventSql.getString("organisateur"),eventSql.getString("date"),eventSql.getDouble("prix"));
				}
				else {
					event = new Event(eventSql.getString("nom"),eventSql.getString("organisateur"),eventSql.getString("date"),eventSql.getDouble("prix"),photo);
				}
				listeParticipations.add(event);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if ((demandeEvent.getNbrEvent() <= 0) || (demandeEvent.getNbrEvent() > totEvent - listeParticipations.size())) {
			throw new NombreEventException();
		}

		else {

			// Boucle permettant de remplir listeEvent
			while (listeEvent.size() < demandeEvent.getNbrEvent()) {
				//tirage d'un nombre aléatoire
				int pos = random.nextInt(listTirage.size()); // tirage d'une position aléatoire dans la liste des tirage
				int x = listTirage.get(pos); // on prend l'entier associer à la position
				listTirage.remove(pos); // on retire l'entier tiré pour éviter les doublons

				// On tire ensuite l'event à la ligne x pour le rajouter dans listeEvent
				try (ResultSet eventSql = sgbd.query("SELECT nom, organisateur, date, prix, photo FROM events LIMIT " + x + ", 1");) {
					eventSql.next();
					Photo photo = new Photo(eventSql.getBytes("photo"));
					Event event;
					// Distinction des cas selon qu'il y a une photo ou non pour le constructeur
					if (photo.estVide()) {
						event = new Event(eventSql.getString("nom"),eventSql.getString("organisateur"),eventSql.getString("date"),eventSql.getDouble("prix"));
					}
					else {
						event = new Event(eventSql.getString("nom"),eventSql.getString("organisateur"),eventSql.getString("date"),eventSql.getDouble("prix"),photo);
					}
					// On vérifie qu'il ne s'agit pas d'un event auxquels l'utilisateur à déja participer avant de le rajouter à listeEvent
					if (!(listeNomEventParticipation.contains(event.getNom()))) {
						listeEvent.add(event);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}

		return listeEvent;

	}

	/**
	 * Fonction permettant de faire participer un utilisateur à un événement
	 * @param participation
	 * 			contenant les champs utilisateur et nomEvent
	 * @return renvoie true si l'utilisateur participe déjà à cet event
	 */
	public static boolean participer(Participation participation) {
		DataBase sgbd = new DataBase("jdbc:sqlite:Peps.db"); // chemin d'accès à la base de données
		int id = 0; // contient l'id de la dernière participation enregistrer si un utilisateur a deja participer à cet event
		boolean aDejaParticiper = false; // boolean valant true si l'utilisateur participe déjà à cet event

		// On vérifie si l'utilisateur ne participe pas déjà à l'event
		try (ResultSet participantSql = sgbd.query("SELECT * FROM participations WHERE utilisateur = '" +  participation.getUtilisateur() + "' AND nomEvent = '" +  participation.getNomEvent() + "'");) {
			aDejaParticiper = participantSql.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Si l'utilisateur ne participe pas, on rajoute sa participation dans la base de données
		if (!(aDejaParticiper)) {
			// On récupère le dernier id
			try (ResultSet dernierid = sgbd.query("SELECT idParticipation FROM participations ORDER BY idParticipation DESC LIMIT 1");) {
				dernierid.next();
				id = dernierid.getInt("idParticipation") + 1;
			} catch (SQLException e ) {
				e.printStackTrace();
			}

			// On insert la participation
			try {
				sgbd.insert("INSERT INTO participations(idParticipation, utilisateur, nomEvent) " + "VALUES ('" + id + "', '" + participation.getUtilisateur() + "', '" + participation.getNomEvent() + "')");
			} catch (SQLException e ) {
				e.printStackTrace();
			}
		}

		return aDejaParticiper;
	}

	/**
	 * Fonction permettant d'obtenir la liste des participants à un event
	 * @param nomEvent
	 * @return la liste des noms des participants
	 */
	public static ArrayList<String> listeParticipants(NomEvent nomEvent) {
		DataBase sgbd = new DataBase("jdbc:sqlite:Peps.db"); //chemin d'accès à la base de données
		ArrayList<String> listeParticipants = new ArrayList<>(); //la liste des noms des participants

		try (ResultSet participantSql = sgbd.query("SELECT utilisateur FROM participations WHERE nomEvent = '" + nomEvent.getNomEvent() + "'");) {
			while (participantSql.next()) {
				listeParticipants.add(participantSql.getString("utilisateur"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeParticipants;
	}

	/**
	 * Fonction permettant d'avoir tous les events auxquels un utilisateur participe
	 * @param identifiant
	 * @return la liste des événements auqyels il participe
	 */
	public static ArrayList<Event> listeParticipations(String identifiant) {
		DataBase sgbd = new DataBase("jdbc:sqlite:Peps.db"); // chemin d'accès à la base de données
		ArrayList<String> listeNomEventParticipation = new ArrayList<>(); // la liste des noms des événements auxquels l'utilisateur participe
		ArrayList<Event> listeParticipations = new ArrayList<>(); // la liste des events auxquels l'utilisateur participe 

		// On récupère d'abord le nom des événements auquels l'utilisateur participe
		try (ResultSet participantSql = sgbd.query("SELECT nomEvent FROM participations WHERE utilisateur = '" + identifiant + "'");) {
			while (participantSql.next()) {
				listeNomEventParticipation.add(participantSql.getString("nomEvent"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// On reconstruit listeParticipations grace à listeNomEventParticipation
		for (String nomEvent:listeNomEventParticipation) {
			// Pour chaque nomEvent, on récupère les informations qui lui sont associées
			try (ResultSet eventSql = sgbd.query("SELECT nom, organisateur, date, prix, photo FROM events WHERE nom = '" + nomEvent + "'");) {
				eventSql.next();
				Photo photo = new Photo(eventSql.getBytes("photo"));
				Event event;
				// Distinction des cas selon qu'il y a une photo ou non pour le constructeur
				if (photo.estVide()) {
					event = new Event(eventSql.getString("nom"),eventSql.getString("organisateur"),eventSql.getString("date"),eventSql.getDouble("prix"));
				}
				else {
					event = new Event(eventSql.getString("nom"),eventSql.getString("organisateur"),eventSql.getString("date"),eventSql.getDouble("prix"),photo);
				}
				listeParticipations.add(event);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listeParticipations;
	}

	/**
	 * Fonction permettant de désinscrire un utilisateur d'un event
	 * @param desinscription
	 * 				contenant les champs utilisateur et nomEvent
	 * @return renvoie false si l'utilisateur ne participe pas à l'événements
	 */
	public static boolean desinscrire(Desinscription desinscription) {
		DataBase sgbd = new DataBase ("jdbc:sqlite:Peps.db"); // chemin d'accès à la base de données
		boolean aUneParticipation = false; // boolean valant false si l'utilisateur n'est pas dans la base de données

		// On vérifie que la participation est bien enregistrer
		try (ResultSet participationSql = sgbd.query("SELECT * FROM participations WHERE utilisateur = '" + desinscription.getUtilisateur() + "'AND nomEvent = '" + desinscription.getNomEvent() + "'");) {
			aUneParticipation = participationSql.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (aUneParticipation) {
			try {
				sgbd.delete("DELETE FROM participations WHERE utilisateur = '" + desinscription.getUtilisateur() + "'AND nomEvent = '" + desinscription.getNomEvent() + "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return aUneParticipation;
	}

	/**
	 * Fonction permettant d'avoir le nombre d'événements auquels un utilisateur participe
	 * @param demandeNombreParticipation
	 * 			contenant le nom du participant
	 * @return le nombre d'événement auxquels l'utilisateur participe
	 */
	public static int nombreParticipations(DemandeNombreParticipation demandeNombreParticipation) {
		DataBase sgbd = new DataBase ("jdbc:sqlite:Peps.db"); // chemin d'accès à la base de données
		int nombreParticipations = 0; // le nombre d'événement auxquels l'utilisateur participe

		try (ResultSet participantSql = sgbd.query("SELECT COUNT(*) FROM participations WHERE  utilisateur = '" + demandeNombreParticipation.getNomParticipant() + "'");) {
			participantSql.next();
			nombreParticipations = participantSql.getInt(1);			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nombreParticipations;
	}

	/**
	 * Fonction permettant d'avoir le nombre d'événements créer par un utilisateur
	 * @param demandeNombreCreation
	 * 			contenant le nom du participant
	 * @return le nombre d'événements créer
	 */
	public static int nombreCreation(DemandeNombreCreation demandeNombreCreation) {
		DataBase sgbd = new DataBase ("jdbc:sqlite:Peps.db"); // chemin d'accès à la base de données
		int nombreCreations = 0; // le nombre d'événements créer par l'utilisateur

		try (ResultSet participantSql = sgbd.query("SELECT COUNT(*) AS int FROM events WHERE organisateur = '" + demandeNombreCreation.getNomOrganisateur() + "'");) {
			participantSql.next();
			nombreCreations = participantSql.getInt(1);			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nombreCreations;
	}

	/**
	 * Fonction permettant d'avoir le nomrbe de participants à un événements
	 * @param demandeNombreParticipant
	 * 			contenant le nom de l'événement
	 * @return le nombre de participant à un événement
	 */
	public static int nombreParticipant(DemandeNombreParticipant demandeNombreParticipant) {
		DataBase sgbd = new DataBase ("jdbc:sqlite:Peps.db"); // chemin d'accès à la base de données
		int nombreParticipant = 0;

		try (ResultSet participantSql = sgbd.query("SELECT COUNT(*) FROM participations WHERE  nomEvent = '" + demandeNombreParticipant.getNomEvent() + "'");) {
			participantSql.next();
			nombreParticipant = participantSql.getInt(1);			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nombreParticipant;
	}

	/**
	 * Fonction permettant d'avoir la liste des événements créer par un utilisateur
	 * @param demandeListeCreation
	 * 			contenant le nom de l'organisateur
	 * @return la liste des événements créer
	 */
	public static ArrayList<Event> demanderListeCreation(DemandeListeCreation demandeListeCreation) {
		DataBase sgbd = new DataBase ("jdbc:sqlite:Peps.db"); // chemin d'accès à la base de données
		ArrayList<Event> listeCreations = new ArrayList<Event>();  // la liste des événements créer

		try (ResultSet eventSql = sgbd.query("SELECT nom, organisateur, date, prix, photo FROM events WHERE organisateur = '" + demandeListeCreation.getNomOrganisateur() + "'");) {
			while (eventSql.next()) {
				Photo photo = new Photo(eventSql.getBytes("photo"));
				Event event;
				// Distinction des cas selon qu'il y a une photo ou non pour le constructeur
				if (photo.estVide()) {
					event = new Event(eventSql.getString("nom"),eventSql.getString("organisateur"),eventSql.getString("date"),eventSql.getDouble("prix"));
				}
				else {
					event = new Event(eventSql.getString("nom"),eventSql.getString("organisateur"),eventSql.getString("date"),eventSql.getDouble("prix"),photo);
				}
				listeCreations.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeCreations;
	}

	/**
	 * Fonction permettant de supprimer un event
	 * @param supressionEvent
	 * 			contenant le nom de l'événement
	 * @return renvoie false si l'événement n'existe pas dans la base de données
	 */
	public static boolean suprimerEvent(SupressionEvent supressionEvent) {
		DataBase sgbd = new DataBase ("jdbc:sqlite:Peps.db"); //chemin d'accès à la base de données
		boolean eventExiste = false; // boolean valant false si l'événements n'est pas dans la base de données
		ArrayList<String> listeParticipants = new ArrayList<>(); //la liste des noms des participants

		// On récupère tous les participants à cet event
		try (ResultSet participantSql = sgbd.query("SELECT utilisateur FROM participations WHERE nomEvent = '" + supressionEvent.getNomEvent() + "'");) {
			while (participantSql.next()) {
				listeParticipants.add(participantSql.getString("utilisateur"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (String nom : listeParticipants) {
			try {
				sgbd.delete("DELETE FROM participations WHERE utilisateur = '" + nom + "'AND nomEvent = '" + supressionEvent.getNomEvent() + "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// On vérifie que l'event à suprimer existe
		try (ResultSet eventSql = sgbd.query("SELECT * FROM events WHERE nom = '" + supressionEvent.getNomEvent() + "'");) {
			eventExiste = eventSql.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Si il existe on le suprime
		if (eventExiste) {
			try {
				sgbd.delete("DELETE FROM events WHERE nom = '" + supressionEvent.getNomEvent() + "'");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return eventExiste;

	}
}
