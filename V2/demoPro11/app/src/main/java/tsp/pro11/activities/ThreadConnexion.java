package tsp.pro11.activities;


import android.app.Activity;
import android.widget.TextView;

import java.io.IOException;

import tsp.pro11.reseau.ClientTcp;
import tsp.pro11.reseau.Identification;
import tsp.pro11.reseau.MessType;
import tsp.pro11.reseau.Message;
import tsp.pro11.reseau.ReponseId;

public class ThreadConnexion implements Runnable {
    private static final int PORT = 5005;
    private static final String HOST = "192.168.0.40";

    private Activity activity;


    public ThreadConnexion(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void run() {
        ClientTcp clientTcp = null;
        ReponseId reponseId;
        Identification identification;
        Message message;
        TextView text = new TextView(activity);
        text.append("Thread client démaré\n");
        activity.setContentView(text);

        try {
            text.append("Connexion ....\n");
            activity.setContentView(text);

            clientTcp = new ClientTcp(HOST, PORT);
            text.append("Connexion ok\n" );
            activity.setContentView(text);

            // envoi d'une première requête d'identification.
            text.append("Connais-tu John Do?\n");
            activity.setContentView(text);
            identification = new Identification("Do", "John");
            message = new Message(identification);
            clientTcp.sendMsg(MessType.MESSAGETYPE, message);

            // réception de le réponse de la requête
            message = (Message) clientTcp.getData();
            reponseId = message.getReponseId();
            String reponsetxt = "La réponse est " + reponseId.isOk();
            text.append(reponsetxt);
            activity.setContentView(text);




        } catch (IOException e) {
            text.append("Connexion KO " + e.getMessage() + "\n");
            activity.setContentView(text);
        }


    }
}
