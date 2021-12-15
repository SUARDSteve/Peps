package tcpnio;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import csc4509.ReadMessageStatus;

/**
 * 
 * @author Denis Conan.
 * @author Christian Bac.
 * @author Éric Lallet.
 *
 */
public interface FullDuplexMsgWorkerSpecification {
	
	/**
	 * get the current channel of this worker.
	 * @return my channel
	 */
	SocketChannel getChannel();
	
	/**
	 * send a message using channel.
	 * @param type message type
	 * @param s the message content is a String
	 * @return size of the data send
	 * @throws IOException 
	 * 						 the exception thrown in case of IO problem.
	 */
	long sendMsg(int type, Serializable s) throws IOException;
	
	/**
	 * send the buffers through the connection.
	 * @throws IOException 
	 * 						 the exception thrown in case of IO problem.
	 */
	 void sendBuffers() throws IOException;
	
	
	/**
	 * 
	 * @param type message type
	 * @param size of the data send
	 */
	 void setHeader(int type, int size);
	
	
	/**
	 * initialize content with data to be send.
	 * @param outdata data to be send
	 */
	void setDataByteBuffer(ByteBuffer outdata);
	
	/**
	 * close the channel.
	 * @throws IOException 
	 * 						 the exception thrown in case of IO problem.
	 */
	 void close() throws IOException;
	
	
	 /**
	  * reads a message.
	  * @return a ReadMessageStatus to specify read progress
	  */
	 ReadMessageStatus readMessage();
	
	
	 /**
	  * return the Serializable data build out of the data part of the received message when the 
	  * readStat is ReadDataCompleted.
	  * This operation should be stateless for the ByteBuffers, meaning that we can getData and
	  * after write the ByteBuffer if necessary
	  * @throws IOException 
	  * 						 the exception thrown in case of IO problem.
	  * @return unserialized data
	  */	
	Serializable getData() throws IOException;
	
	
	/**
	 * get the message type.
	 * @return value of message type
	 */
	 int getMessType();
	
	/**
	 * get the message size if the status is at least ReadHeaderCompleted.
	 * @return message size
	 */
	int getMessSize();
	
	
	/**
	 * get direct access to byteBuffers .
	 * @return the ByteBuffers
	 */
	ByteBuffer[] getByteBuffers();
	
	/**
	 * passThroughOutBuffers is used to send prepared byteBuffers through a channel.
	 * @param setBbs ByteBuffers to send
	 * @throws IOException of Java NIO channel methodes
	 */
	void passThroughOutBuffers(ByteBuffer[] setBbs) throws IOException;

}
