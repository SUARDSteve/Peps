package tsp.pro11.activities.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tsp.pro11.activities.Adapter;
import tsp.pro11.activities.RequeteTcp;
import tsp.pro11.reseau.DemandeEvent;
import tsp.pro11.reseau.Event;
import tsp.pro11.activities.R;
import tsp.pro11.reseau.ListeEvent;
import tsp.pro11.reseau.Message;

/**
 * Cette classe permet à l'utilisateur de voir les événements dans le feed principal.
 * C'est un Fragment, c'est une sous-activité, il est appelé dans la MainActivity.
 */

public class HomeFragment extends Fragment {

    private static final String TAG = "pro11.HomeFragment" ;
    RecyclerView recyclerView;
    ArrayList<Event> EventArrayList = new ArrayList<>();
    Adapter adapterHomeFeed;
    String pseudo;

    public HomeFragment() {
    }

    /**
     * Nous créons en dessous la RecyclerView. C'est ce qui permet l'affichage des événements sous forme de feed.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle b = this.getArguments();
        pseudo = b.getString("pseudo");

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_home_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterHomeFeed = new Adapter(EventArrayList, getActivity(), pseudo, "HomeFragment");

        recyclerView.setAdapter(adapterHomeFeed);

        populateRecyclerView();

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    /**
     * Le code ci-dessous permet d'alimenter la RecyclerView d'événements avec un nombre d'événements donné."
     */

    public void populateRecyclerView() {

       RequeteTcp requeteTcp;
       int nbrEvent = 4;
       DemandeEvent demandeEvent;
       Message demande;
       Message reponse;
       try {
           System.out.println("Demande event");
           demandeEvent = new DemandeEvent(nbrEvent, pseudo);
           demande = new Message(demandeEvent);
           requeteTcp = new RequeteTcp();
           Log.i(TAG, "Envoi de la demande");
           requeteTcp.execute(demande);

           Log.i(TAG, "Réception de la réponse");
           reponse = requeteTcp.get();
           ListeEvent listeEvent = reponse.getListeEvent();

           for(int i=0; i<listeEvent.size(); i++) {
               Event event = listeEvent.getEvent(i);
               EventArrayList.add(event);

           }
           adapterHomeFeed.notifyDataSetChanged();
       } catch (Exception e) {
           Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
       }

    }
}