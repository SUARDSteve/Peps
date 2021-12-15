package tsp.pro11.reseau;

import static tsp.pro11.log.Log.COMM;
import static tsp.pro11.log.Log.LOGGER_NAME_COMM;
import static tsp.pro11.log.Log.LOG_ON;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;

import tsp.pro11.log.Log;
import tsp.pro11.database.Requete;
import tsp.pro11.tcpnio.FullDuplexMsgWorker;
import tsp.pro11.tcpnio.ReadMessageStatus;


public final class AppliServeur {
	/**
	 * number of expected arguments of main method.
	 */
	private static final int NBARGS = 1;
	/**
	 * position of the port number in argv[].
	 */
	private static  final int PORTARG = 0;

	/**
	 * 
	 */
	private AppliServeur() {

	}

	/**
	 * 
	 * @param argv
	 *            argv[0]: server port number.
	 * @throws Exception 
	 */
	public static void main(final String[] argv) throws Exception {
		Selector selector;
		ServerSocketChannel listenChannel;
		SocketChannel rwChan;


		Map<SelectionKey, FullDuplexMsgWorker> theReceivers = new HashMap<SelectionKey, FullDuplexMsgWorker>();
		

		if (argv.length != NBARGS) {
			System.out.println("usage: java Serveur <port>");
			return;
		}
		Log.configureALogger(LOGGER_NAME_COMM, Level.INFO); // attention le worker est logué
           													// au niveau TRACE

		try {
			selector = Selector.open();
			listenChannel = ServerSocketChannel.open();
			listenChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			listenChannel.bind(new InetSocketAddress(Integer.parseInt(argv[PORTARG])));
			listenChannel.configureBlocking(false);
			listenChannel.register(selector, SelectionKey.OP_ACCEPT);
			if (LOG_ON && COMM.isInfoEnabled()) {
				COMM.info("Server is ready");
			}
			while (true) {
				selector.select();
				Set<SelectionKey> readyKeys = selector.selectedKeys();
				if (LOG_ON && COMM.isInfoEnabled()) {
					COMM.info("Server: Nb Ready keys:" + readyKeys.size());
				}

				for (SelectionKey curKey: readyKeys) {
					FullDuplexMsgWorker worker;
					if (curKey.isAcceptable()) {
						try {
							SelectionKey newKey;
							rwChan = listenChannel.accept();
							if (rwChan != null) {
								rwChan.configureBlocking(false);
								try {
									newKey = rwChan.register(selector,
											SelectionKey.OP_READ);
								} catch (ClosedChannelException e) {
									e.printStackTrace();
									return;
								}
								worker = new FullDuplexMsgWorker(rwChan);
								theReceivers.put(newKey, worker);
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						if (curKey.isReadable()) {
							worker = theReceivers.get(curKey);
							ReadMessageStatus status;
							status = worker.readMessage();
							if (status == ReadMessageStatus.ChannelClosed) { // closed channel
								if (LOG_ON && COMM.isInfoEnabled()) {
									COMM.info("Server: closed channel detected");
								}
								theReceivers.remove(curKey);
								curKey.cancel();
								worker.close();
							}
							if (status == ReadMessageStatus.ReadDataCompleted) {
								switch (worker.getMessType()) {
								// le seul type de message possible est MESSAGETYPE
								case MessType.MESSAGETYPE:
									Message message = (Message) worker.getData();
									switch (message.getContenu()) {
									case IDENTIFICATION: // cas où l'on taite une requếte visant à identifier quelqu'un
										Identification identification = message.getIdentification(); // récupération de l'identification (identifiant, motdepasse)
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Identifiacation: " + identification);
										}
										ReponseId reponseId = new ReponseId(Requete.connecter(identification)); // message de réponse valant true si l'identifiant et le mot de passe sont corrects
										message = new Message(reponseId); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
										
									case INSCRIPTION: // cas où l'on taite une requếte visant à inscrire quelqu'un
										Inscription inscription = message.getInscription(); // récupération d'inscription (identifiant, motdepasse)
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Inscription: " + inscription);
										}
										ReponseId estInscrit = new ReponseId(Requete.inscrire(inscription)); // message de réponse valant false si l'identifiant est déja dans la base
										message = new Message(estInscrit); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
										
									case AJOUTEVENT: // cas où l'on taite une requếte visant à ajouter un event
										Event event = message.getEvent(); // récupération de l'event (nom, organisateur, date, prix, photo)
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Events: " + event.toString());
										}
										ReponseId estAjoute = new ReponseId(Requete.ajouter(event)); // message de réponse valant true si l'event existe déjà dans la base de données
										message = new Message(estAjoute); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
										
									case DEMANDELISTEEVENT: // cas où l'on taite une requếte visant à demander un liste d'événement pour le feed
										DemandeEvent demandeEvent = message.getDemandeEvent(); // récupération de la demande d'évent (nombre d'événement voulue, utilisateur)
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving DemandeListeEvents: " + demandeEvent.toString());
										}
										ListeEvent listeEvent = new ListeEvent(Requete.demanderListeEvents(demandeEvent)); // message de réponse avec la liste des événements
										message = new Message(listeEvent); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
										
									case PARTICIPATION: // cas où l'on taite une requếte visant à faire participer quelqu'un à un event
										Participation participation = message.getParticipation(); // récupération de la participation (nomEvent, utilisateur)
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Participations: " + participation.toString());
										}
										ReponseId aDejaParticiper = new ReponseId(Requete.participer(participation)); // message de réponse valant true si l'utilisateur participe déjà à cet event
										message = new Message(aDejaParticiper); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
										
									case DEMANDELISTEPARTICIPANTS: // cas où l'on taite une requếte visant à avoir la liste des participants à un événements
										NomEvent nomEvent = message.getNomEvent(); // récupération du nom de l'événements
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Requete DemandeListeParticipants: " + nomEvent.getNomEvent());
										}
										ListeParticipants listeParticipants = new ListeParticipants(Requete.listeParticipants(nomEvent)); // message de réponse contenant la liste des participants
										message = new Message(listeParticipants); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
										
									case DEMANDELISTEPARTICIPATIONS: // cas où l'on taite une requếte visant à avoir la liste des participations
										String identifiant = message.getIdentifiant(); // récupération de l'identifiant de l'utilisateur
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Requete Demande liste de Participations: " + identifiant);
										}
										ListeEvent listeParticipations = new ListeEvent(Requete.listeParticipations(identifiant)); // message de réponse contenant la liste des participations
										message = new Message(listeParticipations); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
										
									case DEMANDELISTECREATION: // cas où l'on taite une requếte visant à avoir la liste des événements créer par un utilisateur
										DemandeListeCreation demandeListeCreation = message.getDemandeListeCreation(); // récupération de la demande de la liste (utilisateur)
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Requete de demande de la liste des event créer par un utilisateur: " + demandeListeCreation);
										}
										ListeEvent listeCreation = new ListeEvent(Requete.demanderListeCreation(demandeListeCreation)); // message de réponse contenant la liste des événements créer
										message = new Message(listeCreation); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
									
									case DESINSCRIPTION: // cas où l'on taite une requếte visant à désinscrire un utilisateur d'un événements
										Desinscription desinscription = message.getDesinscription(); // récupération de la désinscription (nomEvent,utilisateur)
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Requete Désinscription: " + desinscription);
										}
										ReponseId estPasInscrit = new ReponseId(Requete.desinscrire(desinscription)); // message de réponse valant false si l'utilisateur n'est pas dans la base de données
										message = new Message(estPasInscrit); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
										
									case SUPRESSIONEVENT: // cas où l'on taite une requếte visant à suprimer un événement
										SupressionEvent supressionEvent = message.getSupressionEvent(); // récupération de la supression (nomEvent)
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Requete SupressionEvent: " + supressionEvent);
										}
										ReponseId eventInexistant = new ReponseId(Requete.suprimerEvent(supressionEvent)); // message de réponse valant false si l'événement n'existe pas dans la base de données
										message = new Message(eventInexistant); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
									
									case DEMANDENOMBREPARTICIPATION: // cas où l'on taite une requếte visant à demander le nombre de participations d'un utilisateur
										DemandeNombreParticipation demandeNombreParticipation = message.getDemandeNombreParticipation(); // récupération de la demande du nombre de participations (utilisateur)
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Requete de demande du nombre de participations d'un utilisateur: " + demandeNombreParticipation);
										}
										int nombreParticipation = Requete.nombreParticipations(demandeNombreParticipation); // message de réponse contenant le nombre de participations
										message = new Message(nombreParticipation); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
										
									case DEMANDENOMBREPARTICIPANT: // cas où l'on taite une requếte visant à demander le nombre de participants à un événements
										DemandeNombreParticipant demandeNombreParticipant = message.getDemandeNombreParticipant(); // récupération de la demande du nombre de participants (nomEvent)
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Requete de demande du nombre de participant à un event: " + demandeNombreParticipant);
										}
										int nombreParticipant = Requete.nombreParticipant(demandeNombreParticipant); // message de réponse contenant le nombre de participants
										message = new Message(nombreParticipant); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
									
									case DEMANDENOMBRECREATION: // cas où l'on taite une requếte visant à demander le nombre d'événements qu'un utilisateur à créer
										DemandeNombreCreation demandeNombreCreation = message.getDemandeNombreCreation(); // récupération de la demande du nombre de création
										if (LOG_ON && COMM.isInfoEnabled()) {
											COMM.info("Server: receiving Requete de demande du nombre d'event créer par un utilisateur: " + demandeNombreCreation);
										}
										int nombreCreation = Requete.nombreCreation(demandeNombreCreation); // message de réponse comntenant le nombre d'événements créer
										message = new Message(nombreCreation); // construction du message de réponse
										worker.sendMsg(MessType.MESSAGETYPE, message); // envoie de la réponse
										break;
										
									default:
										System.err.println("Le serveur ne sait pas traiter le contenu de ce message (" + message.getContenu() + ")");
									}// fin du switch (message.getContenu())
									break;
								default: 
									System.err.println("Erreur: le serveur ne sait recevoir que des messages de type MESSAGETYPE");
								} // fin switch (worker.getMessType())

							}	

						}
					}
				}
				readyKeys.clear();
			}
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
