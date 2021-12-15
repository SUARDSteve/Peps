package tsp.pro11.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tsp.pro11.activities.Adapter;
import tsp.pro11.activities.RequeteTcp;
import tsp.pro11.reseau.DemandeListeCreation;
import tsp.pro11.reseau.Event;
import tsp.pro11.activities.R;
import tsp.pro11.reseau.ListeEvent;
import tsp.pro11.reseau.Message;
import tsp.pro11.reseau.ReponseId;

/**
 * Classe utilisée pour afficher les événements créés par un utilisateur.
 */

public class ShowMyEventsActivity extends AppCompatActivity {

    private static final String TAG = "pro11.MyEventsActivity" ;
    private RecyclerView recyclerView;
    private ArrayList<Event> EventArrayList = new ArrayList<>();
    private Adapter adapterShowEventsFeed;
    private RecyclerView.LayoutManager layoutManager;
    private String pseudo;

    public ShowMyEventsActivity() {
    }

    /**
     * Nous créons en dessous la RecyclerView. C'est ce qui permet l'affichage des événements sous forme de feed.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_events);

        Bundle b = getIntent().getExtras();
        pseudo = b.getString("pseudo");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Mes événements");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.activity_showmyevents_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapterShowEventsFeed = new Adapter(EventArrayList, this, pseudo, "ShowMyEventsActivity");
        recyclerView.setAdapter(adapterShowEventsFeed);

        populateRecyclerView();
    }

    /**
     * Le code ci-dessous permet d'alimenter la RecyclerView avec tous les événements que l'utilisateur a créés."
     */

    public void populateRecyclerView() {

        DemandeListeCreation demandeListeCreation;
        RequeteTcp requeteTcp;
        Message demande;
        Message reponse;
        ReponseId reponseId;

        try{
            demandeListeCreation = new DemandeListeCreation(pseudo);
            requeteTcp = new RequeteTcp();
            demande = new Message(demandeListeCreation);
            Log.i(TAG, "Envoi de la demande");

            requeteTcp.execute(demande);
            Log.i(TAG, "Réception de la réponse");
            reponse = requeteTcp.get();
            ListeEvent listeEvent = reponse.getListeEvent();

            for(int i=0; i<listeEvent.size(); i++) {
                Event event = listeEvent.getEvent(i);
                EventArrayList.add(event);
            }
            adapterShowEventsFeed.notifyDataSetChanged();
        }
        catch (Exception e) {
            Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
        }

    }
}