package tsp.pro11.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import tsp.pro11.Exception.NombreEventException;
import tsp.pro11.database.Requete;
import tsp.pro11.reseau.*;

public class RequeteTest {

	/**
	 * Test unitaire permettant de vérifier si la fonction Connecter produit les résultats attendus. 
	 * Connecter prend en argument un objet de type Identification contenant les champs identifiant et motdepasse.
	 * Inscrie a pour but de vérifier si un identifiant et un mot de passe sont dans la base de données.
	 */
	@Test
	public final void testConnecter() throws Exception {
		Identification identification;
		
		// Test le case où l'identification est bien dans la base de données
		identification = new Identification("steve","ok");
		assertTrue("Le résultat est dans la base",Requete.connecter(identification));
		
		// Test le cas où l'utilisateur n'est pas dans le base de données
		identification = new Identification("steve","okk");
		assertFalse("Le résultat n'est pas dans la base",Requete.connecter(identification));

	}

	/**
	 * Test unitaire permettant de vérifier si la fonction Inscrire produit les résultats attendus. 
	 * Inscrire prend en argument un objet de type Inscription contenant les champs identifiant et motdepasse.
	 * Inscrire a pour but d'inscrire un nouvel utilisateur dans la base de données.
	 */
	//Ne pas oublier de suprimer Test de la base de données avant d'éxécuter ce test
	@Test
	public final void testInscrire() throws Exception {
		Inscription inscription;
		
		// Test le cas où l'identifiant est déjà utilisé. L'utilisateur n'est pas ajouté à la base de données
		inscription = new Inscription("steve","ok");
		assertTrue("L'identifiant est déjà utilisé",Requete.inscrire(inscription));

		// Test le cas où l'identifiant n'est pas utilisé
		inscription = new Inscription("Test","test");
		assertFalse("L'identifiant n'est pas déjà utilisé",Requete.inscrire(inscription));
		
		// Vérification de la présence de la nouvelle inscription dans la base de données
		Identification identification = new Identification("Test","test");
		assertTrue("Il est bien enregistré dans la base de données",Requete.connecter(identification));
	}

	/**
	 * Test unitaires permettant de vérifier si la fonction Ajouter produit les résultats attendus.
	 * Ajouter prend en argument un objet de type Event contenant les champs nomEvent, organisateur, date, prix et photo (la photo est optionnel)
	 * Ajouter a pour but d'ajouter un évènement dans la base de donnés
	 */
	@Test
	public final void testAjouter() throws Exception {
		Event event;

		// Test dans le cas où le nom de l'évènement est déjà utilisé. L'event n'est pas ajouté
		event = new Event("event1","steve","21 mai",20);
		assertTrue("Nom de l'event déjà utilisé",Requete.ajouter(event));

		event = new Event("Test","steve","21 mai",20);
		assertFalse("Le nom de l'event n'est pas déjà utilisé",Requete.ajouter(event));

		//On suprime l'event pour les prochaine execution du test
		SupressionEvent supressionEvent = new SupressionEvent("Test");
		assertTrue(Requete.suprimerEvent(supressionEvent));
	}
	
	/**
	 * Test unitaires permettant de vérifier si la fonction DemandeListeEvent lève bien les Exception attendus.
	 * DemandeEvent prend en argument un objet de Type DemandeEvent contenant les champs nbrEvent et identifiant
	 * DemandeEvent a pour but de renvoyer une liste connenant nbrEvent évènements auxquels l'utilisateur ne participe pas encore
	 */
	@Test (expected = NombreEventException.class)
	public final void testDemandeListeEventsException() throws Exception {
		DemandeEvent demandeEvent;

		// Test si on demande un nombre négatif d'event
		demandeEvent = new DemandeEvent(-2,"steve");
		Requete.demanderListeEvents(demandeEvent);
		
		// Test si on demande trop d'event
		demandeEvent = new DemandeEvent(100,"steve");
		Requete.demanderListeEvents(demandeEvent);
	}

	/**
	 * Test unitaires permettant de vérifier si la fonction DemandeListeEvent produit les résultats attendus.
	 * DemandeEvent prend en argument un objet de Type DemandeEvent contenant les champs nbrEvent et identifiant
	 * DemandeEvent a pour but de renvoyer une liste connenant nbrEvent évènements auxquels l'utilisateur ne participe pas encore
	 */
	@Test
	public final void testDemandeListeEvents() throws Exception {
		DemandeEvent demandeEvent;

		// Test si il y a bien nbrEvent
		demandeEvent = new DemandeEvent(2,"steve");
		ArrayList<Event> listeEvent = Requete.demanderListeEvents(demandeEvent);
		assertEquals("Il y a bien le nombre d'event désiré",listeEvent.size(),2);

		//Test pour vérifier si l'utilisateur ne participe bien a aucun de ces event
		ArrayList<Event> listeParticipation = Requete.listeParticipations("steve");
		for (Event event : listeEvent) {
			if (listeParticipation.contains(event)) {
				fail("Ne contient aucun events auquels l'utilisateur participe");
			}
		}

	}

	/**
	 * Test unitaires permettant de vérifier si la fonction participer produit les résultats attendus.
	 * Participer prend en argument un objet de Type Participation contenant les champs identifiant et nomEvent
	 * Participer a pour but de faire participer un utilisateur à un event
	 */
	@Test
	public final void testParticiper() throws Exception {
		Participation participation;

		// Test pour le cas où l'utilisateur participe déjà à l'event
		participation = new Participation("steve","event1");
		assertTrue("L'utilisateur participe déjà à l'event",Requete.participer(participation));

		// Test pour le cas où l'utilisateur ne participe pas déjà à l'event
		participation = new Participation("steve","event15");
		assertFalse("L'utilisateur ne participe pas déjà à l'event",Requete.participer(participation));

		// On vérifie qu'il est bien dans la base de données et on le suprime pour les prochaines éxecutions du test
		Desinscription desinscription = new Desinscription("steve","event15");
		assertTrue("L'utilisateur à bien été inscrit",Requete.desinscrire(desinscription));

	}

	/**
	 * Test unitaires permettant de vérifier si la fonction listeParticipants produit les résultats attendus.
	 * listeParticipants prend en argument un objet de Type NomEvent contenant le champs nomEvent
	 * listeParticipants a pour but de renvoyer la liste des participants à l'event
	 */
	@Test
	public final void testListeParticipants() throws Exception {
		NomEvent nomEvent;

		Event event = new Event("Test","steve","21 mai",20); // On crèe un nouvel event
		Requete.ajouter(event); // On rajoute l'event à la base de donnée
		Participation participation = new Participation("steve","Test"); // On ajoute trois participant
		Requete.participer(participation);
		participation = new Participation("harold","Test");
		Requete.participer(participation);
		participation = new Participation("Yax","Test");
		Requete.participer(participation);

		// On verifie qu'il y a bien les trois participants rajouter
		nomEvent = new NomEvent("Test");
		ArrayList<String> listeParticipants = Requete.listeParticipants(nomEvent);
		assertEquals("Il doit y avoir trois participants",3,listeParticipants.size());

		// On suprime l'event pour les prochaines executions du test
		SupressionEvent supressionEvent = new SupressionEvent("Test");
		Requete.suprimerEvent(supressionEvent);

	}

	/**
	 * Test unitaires permettant de vérifier si la fonction listeParticipations produit les résultats attendus.
	 * listeParticipations prend en argument un identifiant
	 * listeParticipations a pour but de renvoyer la liste des event auxquels l'utilisateur participe
	 */
	@Test
	public final void testListeParticipations() throws Exception {
		String identifiant;

		// on fait participer Test à trois événements
		Participation participation = new Participation("Test","event1"); // On ajoute trois participant
		Requete.participer(participation);
		participation = new Participation("Test","event2");
		Requete.participer(participation);
		participation = new Participation("Test","event3");
		Requete.participer(participation);

		// On verifie qu'il y a bien trois participations
		identifiant = "Test";
		ArrayList<Event> listeParticipations = Requete.listeParticipations(identifiant);
		assertEquals("Il doit y avoir trois participations",3,listeParticipations.size());

		//On suprime les participations pour les prochaines executions du test
		Desinscription desinscription = new Desinscription("Test","event1");
		Requete.desinscrire(desinscription);
		desinscription = new Desinscription("Test","event2");
		Requete.desinscrire(desinscription);
		desinscription = new Desinscription("Test","event3");
		Requete.desinscrire(desinscription);

	}

	/**
	 * Test unitaires permettant de vérifier si la fonction demanderListeCreation produit les résultats attendus.
	 * demanderListeCreation prend en argument un objet de Type DemandeListeCreation contenant le champ identifiant
	 * demanderListeCreation a pour but de renvoyer la liste des évènement créer par un utilisateur
	 */
	@Test
	public final void testListeCreation() throws Exception {
		DemandeListeCreation demandeListeCreation;

		// On fait créer à Test quatre événements
		Event event = new Event("test1","Test","21 mai",20);
		Requete.ajouter(event);
		event = new Event("test2","Test","21 mai",20);
		Requete.ajouter(event);
		event = new Event("test3","Test","21 mai",20);
		Requete.ajouter(event);
		event = new Event("test4","Test","21 mai",20);
		Requete.ajouter(event);

		// On vérifie que Test a bien créer quatres event
		demandeListeCreation = new DemandeListeCreation("Test");
		ArrayList<Event> listeCreation = Requete.demanderListeCreation(demandeListeCreation);
		assertEquals("Il doit y avoir quatres creations",4,listeCreation.size());

		// On suprime les creations pour les prochaines executions du test
		SupressionEvent supressionEvent = new SupressionEvent("test1");
		Requete.suprimerEvent(supressionEvent);
		supressionEvent = new SupressionEvent("test2");
		Requete.suprimerEvent(supressionEvent);
		supressionEvent = new SupressionEvent("test3");
		Requete.suprimerEvent(supressionEvent);
		supressionEvent = new SupressionEvent("test4");
		Requete.suprimerEvent(supressionEvent);
	}

	/**
	 * Test unitaires permettant de vérifier si la fonction desinscrire produit les résultats attendus.
	 * desinscrire prend en argument un objet de Type Desinscription contenant les champs identifiant et nomEvent
	 * desinscrire a pour but de faire désinscrire un utilisateur à un event
	 */
	@Test
	public final void testDesinscrire() throws Exception {
		Desinscription desinscription;

		// On essaie de désinscrire un utilisateur pas encore inscrit
		desinscription = new Desinscription("Test","Test");
		assertFalse("On désinscrit une participation inexistante",Requete.desinscrire(desinscription));

		// On fait participer à un événement
		Participation participation = new Participation("Test","Test");
		Requete.participer(participation);
		
		// On désinscrit l'utilisateur
		desinscription = new Desinscription("Test","Test");
		assertTrue("On désinscrit la participation à l'event",Requete.desinscrire(desinscription));

	}
	
	/**
	 * Test unitaires permettant de vérifier si la fonction nombreParticipations produit les résultats attendus.
	 * nombreParticipations prend en argument un objet de Type demandeNombreParticipation contenant le champ identifiant
	 * nombreParticipations a pour but de donner le nombre d'events auxquels un utilisateur participe
	 */
	@Test
	public final void testNombreParticipations() throws Exception {
		DemandeNombreParticipation demandeNombreParticipation;

		// On fait participer l'utilisateur Test à trois événements
		Participation participation = new Participation("Test","event1");
		Requete.participer(participation);
		participation = new Participation("Test","event2");
		Requete.participer(participation);
		participation = new Participation("Test","event3");
		Requete.participer(participation);
		
		// On vérifie qu'il participe bien à trois events
		demandeNombreParticipation = new DemandeNombreParticipation("Test");
		int nombreParticipations = Requete.nombreParticipations(demandeNombreParticipation);
		assertEquals("Il doit y avoir trois participations",3,nombreParticipations);

		// On suprime les participations pour les prochaines executions du test
		Desinscription desinscription = new Desinscription("Test","event1");
		Requete.desinscrire(desinscription);
		desinscription = new Desinscription("Test","event2");
		Requete.desinscrire(desinscription);
		desinscription = new Desinscription("Test","event3");
		Requete.desinscrire(desinscription);
	}
	
	/**
	 * Test unitaires permettant de vérifier si la fonction nombreCreation produit les résultats attendus.
	 * nombreCreation prend en argument un objet de Type demandeNombreCreation contenant le champ identifiant
	 * nombreParticipations a pour but de donner le nombre d'events qu'un utilisateur a créé
	 */
	@Test
	public final void testNombreCreation() throws Exception {
		DemandeNombreCreation demandeNombreCreation;

		// On fait créer à Test quatre événements
		Event event = new Event("test1","Test","21 mai",20);
		Requete.ajouter(event);
		event = new Event("test2","Test","21 mai",20);
		Requete.ajouter(event);
		event = new Event("test3","Test","21 mai",20);
		Requete.ajouter(event);
		event = new Event("test4","Test","21 mai",20);
		Requete.ajouter(event);

		// On vérifie qu'il a bien créé quatres events
		demandeNombreCreation = new DemandeNombreCreation("Test");
		int nombreCreation = Requete.nombreCreation(demandeNombreCreation);
		assertEquals("Il doit y avoir quatres creations ",4,nombreCreation);

		// On suprime les creations pour les prochaines executions du test
		SupressionEvent supressionEvent = new SupressionEvent("test1");
		Requete.suprimerEvent(supressionEvent);
		supressionEvent = new SupressionEvent("test2");
		Requete.suprimerEvent(supressionEvent);
		supressionEvent = new SupressionEvent("test3");
		Requete.suprimerEvent(supressionEvent);
		supressionEvent = new SupressionEvent("test4");
		Requete.suprimerEvent(supressionEvent);
	}
	
	/**
	 * Test unitaires permettant de vérifier si la fonction nombreParticipant produit les résultats attendus.
	 * nombreParticipant prend en argument un objet de Type DemandeNombreParticipant contenant le champ nomEvent
	 * nombreParticipant a pour but de donner le nombre de participant à un event
	 */
	@Test
	public final void testNombreParticipants() throws Exception {
		DemandeNombreParticipant demandeNombreParticipant;

		// On fait participer deux utilisateur à l'événement Test
		Participation participation = new Participation("steve","Test");
		Requete.participer(participation);
		participation = new Participation("harold","Test");
		Requete.participer(participation);

		// On verifie qu'il y a bien deux participants
		demandeNombreParticipant = new DemandeNombreParticipant("Test");
		int nombreParticipants = Requete.nombreParticipant(demandeNombreParticipant);
		assertEquals("Il doit y avoir deux Participants",2,nombreParticipants);

		// On suprime les Participants pour les prochaines executions du test
		Desinscription desinscription = new Desinscription("steve","Test");
		Requete.desinscrire(desinscription);
		desinscription = new Desinscription("harold","Test");
		Requete.desinscrire(desinscription);
	}
	
	/**
	 * Test unitaires permettant de vérifier si la fonction suprimerEvent produit les résultats attendus.
	 * suprimerEvent prend en argument un objet de Type SupressionEvent contenant le champ nomEvent
	 * suprimerEvent a pour but de suprimer un event
	 */
	@Test
	public final void testSuprimerEvent() throws Exception {
		SupressionEvent supressionEvent;
		
		// On essaie de suprimer un event pas encore inscrit dans la base de données
		supressionEvent = new SupressionEvent("test1");
		assertFalse("L'événement n'existe pas",Requete.suprimerEvent(supressionEvent));
		
		
		// On crée un évènement
		Event event = new Event("test1","Test","21 mai",20);
		Requete.ajouter(event);
		
		// On suprime l'évènement que l'on viens de créer
		supressionEvent = new SupressionEvent("test1");
		assertTrue("L'événement a été suprimer",Requete.suprimerEvent(supressionEvent));
		
	}

}
