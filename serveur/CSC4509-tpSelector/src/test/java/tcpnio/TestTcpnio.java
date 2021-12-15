package tcpnio;


import static common.Log.LOGGER_NAME_TEST;
import static common.Log.LOG_ON;
import static common.Log.TEST;

import org.apache.log4j.Level;
import org.junit.Ignore;
import org.junit.Test;

import common.Log;


public class TestTcpnio {
	
	@Ignore
	@Test
	public void constructAndRun() throws Exception {
		Log.configureALogger(LOGGER_NAME_TEST, Level.INFO);
		if (LOG_ON && TEST.isInfoEnabled()) {
			TEST.info("starting the server...");
		}
		ProcessBuilder pb = new ProcessBuilder("java", MainSelectMsgServer.class.getName(), "5005").inheritIO();
		Process server = pb.start();
		
		
		// wait for the creation of server socket.
		Thread.sleep(3000);
		
		if (LOG_ON && TEST.isInfoEnabled()) {
			TEST.info("starting the client...");
		}
		pb = new ProcessBuilder("java", MainMsgNioClient.class.getName(), "localhost", "5005", "2", "7", "AAA").inheritIO();
		Process client1 = pb.start();
		
		pb = new ProcessBuilder("java", MainMsgNioClient.class.getName(), "localhost", "5005", "5", "4", "BBBBBB").inheritIO();
		Process client2 = pb.start();
		
		Thread.sleep(20000); // the 2nd client sends 5 messages whith a timeout of 4 seconds (total: 16 seconds)
		
		
		
		
		// wait and then flush stdout (necessary for IDEs such as Eclipse)
		// without flush, no output in Eclipse for instance
		// without sleep, not all outputs
		System.out.flush();
	}

}
