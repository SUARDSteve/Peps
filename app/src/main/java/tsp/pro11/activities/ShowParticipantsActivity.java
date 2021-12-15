package tsp.pro11.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.pro11.reseau.ListeParticipants;
import tsp.pro11.reseau.Message;
import tsp.pro11.reseau.NomEvent;

/**
 * Cette classe permet d'afficher sous forme de liste les participants à un événement donné.
 */

public class ShowParticipantsActivity extends AppCompatActivity {

    private static final String TAG = "pro11.ParticipActivity";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_participants);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Liste des participants");
        setSupportActionBar(toolbar);

        String name_event = getIntent().getExtras().getString("name_event");

        listView = (ListView) findViewById(R.id.listview);

        RequeteTcp requeteTcp;
        Message demande;
        Message reponse;
        NomEvent nomEvent;
        ListeParticipants listeParticipants;

        try {

            //Requête Tcp pour avoir accès à la liste des participants.

            nomEvent = new NomEvent(name_event);
            demande = new Message(nomEvent);
            requeteTcp = new RequeteTcp();
            requeteTcp.execute(demande);
            Log.i(TAG,"Envoi de la demande");

            reponse = requeteTcp.get();
            listeParticipants = reponse.getListeParticipants();
            ArrayList<String> List = new ArrayList<>();
            for (int i=0; i<listeParticipants.size(); i++) {
                List.add(listeParticipants.getUtilisateur(i));
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, List);
            arrayAdapter.notifyDataSetChanged();
            listView.setAdapter(arrayAdapter);

        }

        catch (Exception e) {
            Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
        }

    }
}
