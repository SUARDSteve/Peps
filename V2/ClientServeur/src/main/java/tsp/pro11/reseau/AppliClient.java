package tsp.pro11.reseau;



import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.nio.channels.SocketChannel;

import tsp.pro11.tcpnio.FullDuplexMsgWorker;
import tsp.pro11.tcpnio.ReadMessageStatus;



/**
 * 
 * @author Denis Conan
 * @author Christian Bac
 * @author Éric Lallet.
 *
 */
public final class AppliClient {
	/**
	 * number of expected arguments of main method.
	 */
	private static final int NBARGS = 2;
	/**
	 * position of the hostname in argv[].
	 */
	private static  final int HOSTARG = 0;
	/**
	 * position of the port number in argv[].
	 */
	private static  final int PORTARG = 1;


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
		ClientTcp clientTcp;
		ReponseId reponseId;
		Identification identification;
		Message message;


		if (argv.length != NBARGS) {
			System.out.println("usage: java MainMsgNbClient <machine> <port> ");
			return;
		}

	



		try {
			// connexion du client
			clientTcp = new ClientTcp(argv[HOSTARG], Integer.parseInt(argv[PORTARG]));

			// envoi d'une première requête d'identification.
			System.out.println("Connais-tu John Do?");
			identification = new Identification("Do", "John");
			message = new Message(identification);
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de le réponse de la requête
			message = (Message) clientTcp.getData();
			reponseId = message.getReponseId();
			System.out.println("La réponse est " + reponseId.isOk());

			// envoi d'une première requête d'identification.
			System.out.println("Connais-tu Anne Onime?");
			identification = new Identification("Onime", "Anne");
			message = new Message(identification);
			clientTcp.sendMsg(MessType.MESSAGETYPE, message);

			// réception de le réponse de la requête
			message = (Message) clientTcp.getData();
			reponseId = message.getReponseId();
			System.out.println("La réponse est " + reponseId.isOk());


		} catch (SocketException se) {
			se.printStackTrace();
		} catch (UnknownHostException ue) {
			ue.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		} 
	}
}
