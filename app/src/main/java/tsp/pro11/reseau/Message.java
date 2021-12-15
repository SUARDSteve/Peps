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
	 * objet pour les réponses aux requêtes.
	 */
	private ReponseId reponseId = null;

	/**
	 * objet pour les requetes d'ajout d'evenement;
	 */
	private Event event;

	/**
	 * objet pour les requetes de demande d'event pour le feed
	 */
	private DemandeEvent demandeEvent;

	/**
	 * Liste des messages envoyés par l'application
	 */
	private ListeEvent listeEvent;

	/**
	 * objet pour les requêtes de participation à un event
	 */
	private Participation participation;

	/**
	 * objet pour les requetes de demande de liste des participants à un event
	 */
	private NomEvent nomEvent;

	/**
	 * objet pour les requêtes de demande de liste des événements auxquels l'utilisateur participe
	 */
	private String identifiant;

	/**
	 * objet pour les requêtes d'obtention de la liste des événements auxquels un utilisateur à participer
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
	 * requête pour connaitre le nombre de participation d'un utilisateur à des events
	 */
	private DemandeNombreParticipation demandeNombreParticipation;

	/**
	 * requête pour connaitre le nombre de participant à un events
	 */
	private DemandeNombreParticipant demandeNombreParticipant;

	/**
	 * réponse contenant le nombre de participation ou de cration
	 */
	private int entier;

	/**
	 * requête pour connaitre le nombre de creation d'un utilisateur à des events
	 */
	private DemandeNombreCreation demandeNombreCreation;

	private DemandeListeCreation demandeListeCreation;

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
	 * Constrcuteur pour les messages transportant une requête de demande d'événement.
	 * @param AjoutEvent
	 *        la requête d'ajout d'événement.
	 */
	public Message(DemandeEvent demandeEvent) {
		this.demandeEvent = demandeEvent;
		this.contenu = Contenu.DEMANDELISTEEVENT;
	}

	/**
	 * Constrcuteur pour les messages transportant une requête de demande d'événement.
	 * @param AjoutEvent
	 *        la requête d'ajout d'événement.
	 */
	public Message(ListeEvent listeEvent) {
		this.listeEvent = listeEvent;
		this.contenu = Contenu.LISTEEVENT;
	}

	/**
	 * COnstructeur pour les messages transportant une requête de participation à un event
	 * @param participation
	 */
	public Message(Participation participation) {
		this.participation = participation;
		this.contenu = Contenu.PARTICIPATION;
	}

	public Message(NomEvent nomEvent) {
		this.nomEvent = nomEvent;
		this.contenu = Contenu.DEMANDELISTEPARTICIPANTS;
	}

	public Message(String identifiant) {
		this.identifiant = identifiant;
		this.contenu = Contenu.DEMANDELISTEPARTICIPATIONS;
	}

	public Message(ListeParticipants listeParticipants) {
		this.listeParticipants = listeParticipants;
		this.contenu = Contenu.LISTEPARTICIPANTS;
	}

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

	public Message(DemandeNombreParticipation demandeNombreParticipation) {
		this.demandeNombreParticipation = demandeNombreParticipation;
		this.contenu = Contenu.DEMANDENOMBREPARTICIPATION;
	}

	public Message(DemandeNombreParticipant demandeNombreParticipant) {
		this.demandeNombreParticipant = demandeNombreParticipant;
		this.contenu = Contenu.DEMANDENOMBREPARTICIPANT;
	}

	public Message(int entier) {
		this.entier = entier;
		this.contenu = Contenu.ENTIER;
	}

	public Message(DemandeNombreCreation demandeNombreCreation) {
		this.demandeNombreCreation = demandeNombreCreation;
		this.contenu = Contenu.DEMANDENOMBRECREATION;
	}

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

	public NomEvent getNomEvent() {
		return nomEvent;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public ListeParticipants getListeParticipants() {
		return listeParticipants;
	}

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

	public DemandeNombreParticipation getDemandeNombreParticipation() {
		return demandeNombreParticipation;
	}

	public DemandeNombreParticipant getDemandeNombreParticipant() {
		return demandeNombreParticipant;
	}

	public int getEntier() {
		return entier;
	}

	public DemandeNombreCreation getDemandeNombreCreation() {
		return demandeNombreCreation;
	}

	public DemandeListeCreation getDemandeListeCreation() {
		return demandeListeCreation;
	}

	/**
	 * getter sur la réponse à la requête d'identification.
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
