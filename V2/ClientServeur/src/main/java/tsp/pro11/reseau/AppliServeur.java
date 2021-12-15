package tsp.pro11.reseau;




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

import tsp.pro11.tcpnio.FullDuplexMsgWorker;
import tsp.pro11.tcpnio.ReadMessageStatus;

/**
 * 
 * @author Denis Conan
 * @author Christian Bac
 * @author Éric Lallet
 *
 */
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
	 */
	public static void main(final String[] argv) {
		Selector selector;
		ServerSocketChannel listenChannel;
		SocketChannel rwChan;


		Map<SelectionKey, FullDuplexMsgWorker> theReceivers = new HashMap<SelectionKey, FullDuplexMsgWorker>();
		if (argv.length != NBARGS) {
			System.out.println("usage: java Serveur <port>");
			return;
		}


		try {
			selector = Selector.open();
			listenChannel = ServerSocketChannel.open();
			listenChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			listenChannel.bind(new InetSocketAddress(Integer.parseInt(argv[PORTARG])));
			listenChannel.configureBlocking(false);
			listenChannel.register(selector, SelectionKey.OP_ACCEPT);

			while (true) {
				selector.select();
				Set<SelectionKey> readyKeys = selector.selectedKeys();


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
								System.out.println("Nouveau client connecté");
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
									case IDENTIFICATION:
										Identification identification = message.getIdentification();
										ReponseId reponseId;
										// je ne connais que John Do
										if (identification.getNom().equals("Do") && identification.getPrenom().equals("John")) {
											reponseId = new ReponseId(true);
											System.out.println("Je connais " + identification);
										}
										else {
											reponseId = new ReponseId(false);
											System.out.println("Je ne connais pas " + identification);
										}
										message = new Message(reponseId);
										worker.sendMsg(MessType.MESSAGETYPE, message);
										break;
									default:
										System.err.println("Le serveur ne sait pas traiter le contenu de ce message (" + message.getContenu() + ")");
									}// fin du switch (message.getContenu())
									break;
								default: 
									System.err.println("Erreur: le serveur ne sait recevoir que des messages de type MESSAGETYPE");
								} // fin switch (worker.getMessType()) {

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
