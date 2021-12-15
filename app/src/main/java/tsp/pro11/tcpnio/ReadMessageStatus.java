package tsp.pro11.tcpnio;
/*
 * $Id: ReadMessageStatus.java 30 2016-06-02 13:31:57Z conan $
 */
public enum ReadMessageStatus {
	ReadUnstarted,
	ReadHeaderStarted ,
	ReadHeaderCompleted ,
	ReadDataStarted , 
	ReadDataCompleted ,
	ChannelClosed
}
