package tcpnio;

import static common.Log.COMM;
import static common.Log.LOGGER_NAME_COMM;
import static common.Log.LOG_ON;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.nio.channels.SocketChannel;

import org.apache.log4j.Level;

import common.Log;
import csc4509.MessType;



/**
 * 
 * @author Denis Conan
 * @author Christian Bac
 * @author Éric Lallet.
 *
 */
public final class MainMsgNioClient {
	/**
	 * number of expected arguments of main method.
	 */
	private static final int NBARGS = 5;
	/**
	 * position of the hostname in argv[].
	 */
	private static  final int HOSTARG = 0;
	/**
	 * position of the port number in argv[].
	 */
	private static  final int PORTARG = 1;
	/**
	 * position of the timer value in argv[].
	 */
	private static  final int TIMEOUTARG = 2;
	/**
	 * position of the number of messages in argv[].
	 */
	private static  final int NBMESGARG = 3;
	/**
	 * position of the pattern of messages in argv[].
	 */
	private static  final int PATTERNARG = 4;
	
	/**
	 * number of seconds in millisecond.
	 */
	private static  final int THOUSAND = 1000;
	
	/**
	 * 
	 */
	private MainMsgNioClient() {
		
	}

	/**
	 * 
	 * @param argv
	 *            argv[0] server hostname
	 *            argv[1] server port number
	 *            argv[2] timer between two send messages
	 *            argv[3] number of send messages
	 *            argv[4] message pattern
	 */
	public static void main(final String[] argv) {
		FullDuplexMsgWorker message;
		SocketChannel rwChan;
		InetSocketAddress rcvAddress;
		int timeout, nbmess;
		long count;

		if (argv.length != NBARGS) {
			System.out.println("usage: java MainMsgNbClient <machine> <port> "
					+ "<timeout> <nbmessages> <pattern>");
			return;
		}
		
		Log.configureALogger(LOGGER_NAME_COMM, Level.TRACE); // tout est logué pour la comm
		timeout = Integer.parseInt(argv[TIMEOUTARG]) * THOUSAND;
		nbmess = Integer.parseInt(argv[NBMESGARG]);


		
		try {
			rcvAddress = new InetSocketAddress(InetAddress.getByName(argv[HOSTARG]), Integer.parseInt(argv[PORTARG]));
			rwChan = SocketChannel.open(rcvAddress);
			message = new FullDuplexMsgWorker(rwChan);

			// envoi de la taille et des octets du tableau dataBuf

			for (int num = 0; num < nbmess; num++) {
				long sent = 0;
				count = message.sendMsg(MessType.STRINGTYPE, argv[PATTERNARG]);
				sent += count;
				if (LOG_ON && COMM.isInfoEnabled()) {
					COMM.info("Client: mess" + num + "/" + nbmess + "(" + sent
							+ " bytes sent).");
				}
				Thread.sleep(timeout);
			}

		} catch (SocketException se) {
			se.printStackTrace();
		} catch (UnknownHostException ue) {
			ue.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
