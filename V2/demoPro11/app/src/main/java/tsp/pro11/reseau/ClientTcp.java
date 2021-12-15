package tsp.pro11.reseau;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import tsp.pro11.tcpnio.FullDuplexMsgWorker;
import tsp.pro11.tcpnio.ReadMessageStatus;

public class ClientTcp {
    private FullDuplexMsgWorker worker;

    /**
     * Construit un FullDuplexMsgWorker pour le coté client d'une connexion TCP.
     * @param hostname
     * 				  nom de la machine du serveur.
     * @param port
     *                port du serveur.
     * @throws IOException
     *                exceptions pour la résolution du nom de la machine, et pour la connexion TCP.
     */
    public ClientTcp(String hostname, int port) throws IOException {
        SocketChannel rwChan;
        InetSocketAddress rcvAddress;
        rcvAddress = new InetSocketAddress(InetAddress.getByName(hostname), port);
        rwChan = SocketChannel.open(rcvAddress);
        worker = new FullDuplexMsgWorker(rwChan);
    }

    /**
     * Envoi d'un objet au server.
     * @param type
     *        type de l'objet. La valeur doit être placée dans le fichier MessType.java.
     * @param s
     *        reférence sur l'objet.
     * @return
     *        nombre d'octets envoyés au serveur.
     * @throws IOException
     *        exceptions d'entrée/sortie lors de l'envoi.
     */
    public long sendMsg(final int type, final Serializable s) throws IOException {
        return worker.sendMsg(type, s);
    }


    /**
     * Réception d'un objet émis par le serveur. Cette méthode bloque le client tant que
     * l'objet n'est pas intégralement reçu.
     * @return
     *        l'objet reçu est reconstruit.
     * @throws IOException
     *         exceptions d'entrée/sortie lors de la réception.
     */
    public Serializable getData() throws IOException {
        while(worker.readMessage() != ReadMessageStatus.ReadDataCompleted) {

        }
        return worker.getData();
    }

}