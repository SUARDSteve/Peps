package tsp.pro11.reseau;



import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;



/**
 * 
 * @author Denis Conan
 * @author Christian Bac
 * @author Éric Lallet.
 *
 */
public final class AppliClient {
	/**
	 * 
	 */
	private AppliClient() {

	}

	/**
	 * 
	 * @param argv
	 *            argv[0] server hostname
	 *            argv[1] server port number
	 */
	public static void main(final String[] argv) {
		try {
			// connexion du client
			ClientTcp clientTcp = new ClientTcp("localhost", 5005);

			// envoi d'une première requête d'identification.
			//System.out.println("Connais-tu Steve?");
			//Identification identification = new Identification("steve","ok");
			//Message message = new Message(identification);
			//clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de le réponse de la requête
			//message = (Message) clientTcp.getData();
			//ReponseId reponseId = message.getReponseId();
			//System.out.println("La réponse est " + reponseId.isOk());
			
			
			// Pour inscrire un utilisateur on fait pratiquement la même chose que pour connexion, on remplace juste Identification par Inscription
			// reponseId vaut true si l'identifiant de l'utilisateur est déja utilisé
			
			
			// envoi d'une première requête d'inscription.
			//System.out.println("Inscrit harold");
			//Inscription inscription = new Inscription("harold","okk");
			//message = new Message(inscription);
			//clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de la réponse de la requête
			//message = (Message) clientTcp.getData();
			//reponseId = message.getReponseId();
			//System.out.println("La réponse est " + reponseId.isOk());
			
			
			//Pour ajouter un événement on fait comme ce qui suit si il y a une photo. On mets rien à la place de photo sinon
			
			// envoi d'une première requête d'inscription
			// reponseId vaut true si le nom d'événement est déjà utilisé
			//System.out.println("Inscrit Vacance");
			//Photo photo = new Photo("/home/anebessagedai/Téléchargements/plage.png");
			//photo.readPhoto("plage2.png");
			//Event ajoutevent= new Event("Vacance","Moi","13/04/2020",20,photo);
			//message = new Message(ajoutevent);
			//clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de la réponse de la requête
			//message = (Message) clientTcp.getData();
			//reponseId = message.getReponseId();
			//System.out.println("La réponse est " + reponseId.isOk());
			
			//envoi d'une requête de demande de liste d'événement
			System.out.println("Demande event");
			DemandeEvent demandeEvent = new DemandeEvent(10,"steve");
			Message message = new Message(demandeEvent);
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			//réception de la réponse de la requête
			message = (Message) clientTcp.getData();
			ListeEvent ListeEvent = message.getListeEvent();
			System.out.println(ListeEvent.toString());
			
			//envoi d'une première requête pour participer à un événement
			System.out.println("faire participer steve à event1");
			Participation participation = new Participation("steve","event1");
			message = new Message(participation);
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de la réponse de la requête
			message = (Message) clientTcp.getData();
			ReponseId reponseId = message.getReponseId();
			System.out.println("La réponse est " + reponseId.isOk());
			
			//envoi d'une requête de demande de liste d'événement
			System.out.println("Demande la liste des participants pour event1");
			NomEvent nomEvent = new NomEvent("event1");
			message = new Message(nomEvent);
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de la réponse de la requête
			message = (Message) clientTcp.getData();
			ListeParticipants listeParticipants = message.getListeParticipants();
			System.out.println(listeParticipants.toString());
			
			//envoi d'une requête de demande de liste d'événement
			System.out.println("Demande la liste des participations de steve");
			message = new Message("steve");
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de la réponse de la requête
			message = (Message) clientTcp.getData();
			ListeEvent listeParticipations = message.getListeEvent();
			System.out.println(listeParticipations.toString());
			
			//envoie d'une requête de désinscription
			System.out.println("Desinscrire steve de event2");
			Desinscription desinscription = new Desinscription("steve","event2");
			message = new Message(desinscription);
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de la réponse de la requête
			// renvoie true si il est déjà suprimer de la base de données
			message = (Message) clientTcp.getData();
			ReponseId reponse = message.getReponseId();
			System.out.println(reponse.isOk());
			
			//envoie d'une requête de demande de nombre de participation
			System.out.println("A combien d'event steve participe ?");
			DemandeNombreParticipation demandeNombreParticipation = new DemandeNombreParticipation("steve");
			message = new Message(demandeNombreParticipation);
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de la réponse de la requête
			message = (Message) clientTcp.getData();
			int nombreParticipation = message.getEntier();
			System.out.println(nombreParticipation);
			
			//envoie d'une requête de demande de nombre de participation
			System.out.println("Combien d'event steve a créer ?");
			DemandeNombreCreation demandeNombreCreation = new DemandeNombreCreation("steve");
			message = new Message(demandeNombreCreation);
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de la réponse de la requête
			message = (Message) clientTcp.getData();
			int nombreCreation = message.getEntier();
			System.out.println(nombreCreation);
			
			//envoie d'une requête de demande de la liste des événements créer par un utilisateur
			System.out.println("Quels events steve a créer ?");
			DemandeListeCreation demandeListeCreation = new DemandeListeCreation("steve");
			message = new Message(demandeListeCreation);
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de la réponse de la requête
			message = (Message) clientTcp.getData();
			ListeEvent listeCreation = message.getListeEvent();
			System.out.println(listeCreation);
			
			//envoie d'une requête de supression d'event
			System.out.println("Suprimer event2");
			SupressionEvent supressionEvent = new SupressionEvent("event2");
			message = new Message(supressionEvent);
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de la réponse de la requête
			// renvoie true si il est déjà suprimer de la base de données
			message = (Message) clientTcp.getData();
			reponse = message.getReponseId();
			System.out.println(reponse.isOk());
			
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (UnknownHostException ue) {
			ue.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		} 
		};
}
