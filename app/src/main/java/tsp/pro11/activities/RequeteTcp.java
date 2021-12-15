package tsp.pro11.activities;

import android.os.AsyncTask;
import android.util.Log;




import java.io.IOException;

import tsp.pro11.reseau.ClientTcp;
import tsp.pro11.reseau.MessType;
import  tsp.pro11.reseau.Message;

public class RequeteTcp extends AsyncTask<Message,Void, Message> {
    private static final int PORT = 5005;
    private static final String HOST = "192.168.0.12";
    private static final String TAG = "pro11.RequeteTcp";

    private Message reponse;

    @Override
    public Message doInBackground(Message... demandes) {
        Log.i(TAG,"Début de la requête TCP");
        try {

            ClientTcp clientTcp = new ClientTcp(HOST, PORT);
            Log.i(TAG,"Connexion ok");

            clientTcp.sendMsg(MessType.MESSAGETYPE, demandes[0]);
            Log.i(TAG,"Demande envoyée");

            reponse = (Message) clientTcp.getData();
            Log.i(TAG,"Réponse reçue");

        } catch (IOException e) {
            Log.e(TAG,"Connexion KO " + e.getMessage());
        }

        return reponse;
    }


}
