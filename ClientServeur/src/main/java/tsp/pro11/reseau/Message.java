package tsp.pro11.reseau;


import java.io.Serializable;


public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * objet pour les requêtes d'identification.
	 */
	private Identification identification = null;
	
	/**
	 * objet pour les requêtes d'inscription.
	 */
	private Inscription inscription = null;
	
	/**
	 * objet pour les requêtes d'ajout d'evenement.
	 */
	private Event event;
	
	/**
	 * objet pour les requêtes de demande d'events pour le feed
	 */
	private DemandeEvent demandeEvent;
	
	/**
	 * objet pour les réponses à une demande d'events pour le feed
	 */
	private ListeEvent listeEvent;
	
	/**
	 * objet pour les requêtes de participation à un event
	 */
	private Participation participation;
	
	/**
	 * objet pour les requêtes de demande de liste des participants à un event
	 */
	private NomEvent nomEvent;
	
	/**
	 * objet pour les requêtes de demande de liste des événements auxquels l'utilisateur participe
	 */
	private String identifiant;

	/**
	 * objet pour les réponses aux requêtes pour avoir la liste des événements auxquels un utilisateur à participer
	 */
	private ListeParticipants listeParticipants;
	
	/**
	 * objet pour les requêtes de désinscription à un event
	 */
	private Desinscription desinscription;
	
	/**
	 * objet pour les requêtes de supression d'un event
	 */
	private SupressionEvent supressionEvent;
	
	/**
	 * objet pour les requêtes afin de connaitre le nombre de participation d'un utilisateur à des events
	 */
	private DemandeNombreParticipation demandeNombreParticipation;
	
	/**
	 * objet pour les requêtes afin de connaitre le nombre de participant à un event
	 */
	private DemandeNombreParticipant demandeNombreParticipant;
	
	/**
	 * objet pour les requêtes afin de connaitre le nombre d'event créer par un utilisateur 
	 */
	private DemandeNombreCreation demandeNombreCreation;
	
	/**
	 * objet pour les réponses aux requêtes pour avoir le nombre de participation, de cration ou de participant
	 */
	private int entier;
	
	/**
	 * objet pour les requêtes demandant la liste des events créés par un utilisateur
	 */
	private DemandeListeCreation demandeListeCreation;
	
	/**
	 * objet pour les réponses aux requêtes à réponse fermer.
	 */
	private ReponseId reponseId = null;
	
	/**
	 * Descripteur du contenu du message.
	 */
	private Contenu contenu;
	
	/**
	 * Constrcuteur pour les messages transportant une requête d'identification.
	 * @param identification
	 *        la requête d'identification.
	 */
	public Message(Identification identification) {
		this.identification = identification;
		this.contenu = Contenu.IDENTIFICATION;
	}
	
	/**
	 * Constrcuteur pour les messages transportant une requête d'inscription.
	 * @param inscription
	 *        la requête d'inscription.
	 */
	public Message(Inscription inscription) {
		this.inscription = inscription;
		this.contenu = Contenu.INSCRIPTION;
	}
	
	/**
	 * Constrcuteur pour les messages transportant une requête d'ajout d'un événement.
	 * @param AjoutEvent
	 *        la requête d'ajout d'événement.
	 */
	public Message(Event event) {
		this.event = event;
		this.contenu = Contenu.AJOUTEVENT;
	}
	
	/**
	 * Constrcuteur pour les messages transportant une requête de demande d'événements.
	 * @param AjoutEvent
	 *        la requête d'ajout d'événement.
	 */
	public Message(DemandeEvent demandeEvent) {
		this.demandeEvent = demandeEvent;
		this.contenu = Contenu.DEMANDELISTEEVENT;
	}
	
	/**
	 * Constrcuteur pour les messages transportant une réponse aux requêtes de demande d'événement.
	 * @param AjoutEvent
	 *        la requête d'ajout d'événement.
	 */
	public Message(ListeEvent listeEvent) {
		this.listeEvent = listeEvent;
		this.contenu = Contenu.LISTEEVENT;
	}
	
	/**
	 * Constructeur pour les messages transportant une requête de participation à un event
	 * @param participation
	 */
	public Message(Participation participation) {
		this.participation = participation;
		this.contenu = Contenu.PARTICIPATION;
	}
	
	/**
	 * Constructeur pour les messages transportant une requête de demande de la liste des participant à un event
	 * @param nomEvent
	 */
	public Message(NomEvent nomEvent) {
		this.nomEvent = nomEvent;
		this.contenu = Contenu.DEMANDELISTEPARTICIPANTS;
	}
	
	/**
	 * Constructeur pour les messages transportant une requête de demande de la liste des participation d'un utilisateur
	 * @param identifiant
	 */
	public Message(String identifiant) {
		this.identifiant = identifiant;
		this.contenu = Contenu.DEMANDELISTEPARTICIPATIONS;
	}
	
	/**
	 * Constructeur pour les messages transportant une réponse aux requêtes de demande de la liste des participants
	 * @param listeParticipants
	 */
	public Message(ListeParticipants listeParticipants) {
		this.listeParticipants = listeParticipants;
		this.contenu = Contenu.LISTEPARTICIPANTS;
	}
	
	/**
	 * Constructeur pour les messages transportant une requête de demande de désinscription à un event
	 * @param desinscription
	 */
	public Message(Desinscription desinscription) {
		this.desinscription = desinscription;
		this.contenu = Contenu.DESINSCRIPTION;
	}
	
	/**
	 * Constructeur pour les messages transportant une requête de demande de supression d'un event
	 * @param supressionEvent
	 */
	public Message(SupressionEvent supressionEvent) {
		this.supressionEvent = supressionEvent;
		this.contenu = Contenu.SUPRESSIONEVENT;
	}
	
	/**
	 * Constructeur pour les messages transportant une requête de demande du nombre de participation d'un utilisateur
	 * @param demandeNombreParticipation
	 */
	public Message(DemandeNombreParticipation demandeNombreParticipation) {
		this.demandeNombreParticipation = demandeNombreParticipation;
		this.contenu = Contenu.DEMANDENOMBREPARTICIPATION;
	}
	
	/**
	 * Constructeur pour les messages transportant une requête de demande du nombre de participant à un event
	 * @param demandeNombreParticipant
	 */
	public Message(DemandeNombreParticipant demandeNombreParticipant) {
		this.demandeNombreParticipant = demandeNombreParticipant;
		this.contenu = Contenu.DEMANDENOMBREPARTICIPANT;
	}
	
	/**
	 * Constructeur pour les messages transportant une requête de demande du nombre d'events créés par un utilisateur
	 * @param demandeNombreCreation
	 */
	public Message(DemandeNombreCreation demandeNombreCreation) {
		this.demandeNombreCreation = demandeNombreCreation;
		this.contenu = Contenu.DEMANDENOMBRECREATION;
	}
	
	/**
	 * Constructeur pour les messages transportant une réponse aux requêtes de demande d'un nombre
	 * @param entier
	 */
	public Message(int entier) {
		this.entier = entier;
		this.contenu = Contenu.ENTIER;
	}
	
	/**
	 * Constructeur pour les messages transportant un requête de demande de la liste des events créés par un utilisateur
	 * @param demandeListeCreation
	 */
	public Message(DemandeListeCreation demandeListeCreation) {
		this.demandeListeCreation = demandeListeCreation;
		this.contenu = Contenu.DEMANDELISTECREATION;
	}
	
	/**
	 * Constructeur pour les messages transportant la réponse pour l'identification.
	 * @param reponseId
	 *       la réponse à la requête.
	 */
	public Message(ReponseId reponseId) {
		this.reponseId = reponseId;
		this.contenu = Contenu.REPONSEID;
		
	}

	/**
	 * getter sur la requête d'identification.
	 * @return
	 *        la requête d'identification.
	 */
	public Identification getIdentification() {
		return identification;
	}
	
	/**
	 * getter sur la requête d'inscription.
	 * @return
	 *        la requête d'inscription.
	 */
	public Inscription getInscription() {
		return inscription;
	}
	
	/**
	 * getter sur la requête d'ajout d'événement.
	 * @return
	 *        la requête d'ajout d'événement.
	 */
	public Event getEvent() {
		return event;
	}
	
	/**
	 * getter sur la requête de demande d'événement.
	 * @return
	 *        la requête donnant le nombre d'événement à ajouter.
	 */
	public DemandeEvent getDemandeEvent() {
		return demandeEvent;
	}
	
	/**
	 * getter sur la requête de de réponse à la demande d'événement.
	 * @return
	 *        la requête de demande d'événement.
	 */
	public ListeEvent getListeEvent() {
		return listeEvent;
	}
	
	/**
	 * getter sur la requête de participation à un event
	 * @return
	 * 		la requête de participation à un event
	 */
	public Participation getParticipation() {
		return participation;
	}
	
	/**
	 * getter sur la requête de demande de la liste des participant à l'event.
	 * @return
	 *        la requête du nom de l'event.
	 */
	public NomEvent getNomEvent() {
		return nomEvent;
	}
	
	/**
	 * getter sur la requête de demande la liste des participation d'un utilisateur.
	 * @return
	 *        la requête de l'identifiant de l'utilisateur.
	 */
	public String getIdentifiant() {
		return identifiant;
	}
	
	/**
	 * getter sur la réponse aux requêtes de demande de la liste des participants.
	 * @return
	 *        la réponse à la requête.
	 */
	public ListeParticipants getListeParticipants() {
		return listeParticipants;
	}
	
	/**
	 * getter sur la requête de désinscription à un event.
	 * @return
	 *        la requête de désinscription.
	 */
	public Desinscription getDesinscription() {
		return desinscription;
	}
	
	/**
	 * getter sur la requête de supression d'un event.
	 * @return
	 *        la requête de supression.
	 */
	public SupressionEvent getSupressionEvent() {
		return supressionEvent;
	}
	
	/**
	 * getter sur la requête de demande du nombre de participations d'un utilisateur.
	 * @return
	 *        la requête de demande du nombre de participations.
	 */
	public DemandeNombreParticipation getDemandeNombreParticipation() {
		return demandeNombreParticipation;
	}
	
	/**
	 * getter sur la requête de demande du nombre de participants à un event.
	 * @return
	 *        la requête de demande du nombre de participants.
	 */
	public DemandeNombreParticipant getDemandeNombreParticipant() {
		return demandeNombreParticipant;
	}
	
	/**
	 * getter sur la requête de demande du nombre d'events créés par un utilisateur.
	 * @return
	 *        la requête de demande du nombre de créations.
	 */
	public DemandeNombreCreation getDemandeNombreCreation() {
		return demandeNombreCreation;
	}
	
	/**
	 * getter sur la réponse aux requêtes à réponse un entier.
	 * @return
	 *      la réponse à la requête.
	 */
	public int getEntier() {
		return entier;
	}
	
	/**
	 * getter sur la requête de demande d'une liste des events créés par un utilisateur
	 * @return
	 * 		  la requête de demande la liste des créatoins
	 */
	public DemandeListeCreation getDemandeListeCreation() {
		return demandeListeCreation;
	}

	/**
	 * getter sur la réponse aux requêtes à réponses fermer.
	 * @return
	 *      la réponse à la requête.
	 */
	public ReponseId getReponseId() {
		return reponseId;
	}

	/**
	 * getter sur le contenu du message.
	 * @return
	 *      le descripteur du contenu du message.
	 */
	public Contenu getContenu() {
		return contenu;
	}


}
