package tcpnio;


import static common.Log.COMM;
import static common.Log.LOGGER_NAME_COMM;
import static common.Log.LOG_ON;

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

import common.Log;
import csc4509.MessType;
import csc4509.Personne;
import csc4509.ReadMessageStatus;

/**
 * 
 * @author Denis Conan
 * @author Christian Bac
 * @author Éric Lallet
 *
 */
public final class MainSelectMsgServer {
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
	private MainSelectMsgServer() {
		
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
			System.out.println("usage: java MainSelectMsgServer <port>");
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
								case MessType.STRINGTYPE:
									String message = (String) worker.getData();
									if (LOG_ON && COMM.isInfoEnabled()) {
										COMM.info("Server: receiving String: " + message);
									}
									break;
								case MessType.PERSONNETYPE:
									Personne personne = (Personne) worker.getData();
									if (LOG_ON && COMM.isInfoEnabled()) {
										COMM.info("Server: receiving Personne: " + personne);
									}
									break;
								default:
									if (LOG_ON && COMM.isEnabledFor(Level.WARN)) {
										COMM.warn("Server: reception of unknown type of messages: " +  worker.getMessType());
									}
								}
								
							}	
							if (LOG_ON && COMM.isEnabledFor(Level.TRACE)) {
								COMM.trace("Received message status:" + status);
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
