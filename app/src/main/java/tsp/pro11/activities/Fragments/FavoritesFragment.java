package tsp.pro11.activities.Fragments;

import android.os.Bundle;
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
import tsp.pro11.reseau.Event;
import tsp.pro11.activities.R;
import tsp.pro11.reseau.ListeEvent;
import tsp.pro11.reseau.Message;

/**
 * Cette classe permet à l'utilisateur d'accéder à ses événements favoris.
 * C'est un Fragment, c'est une sous-activité, il est appelé dans la MainActivity.
 */

public class FavoritesFragment extends Fragment {

    private static final String TAG = "pro11.HomeFragment" ;
    RecyclerView recyclerView;
    ArrayList<Event> EventArrayList = new ArrayList<>();
    Adapter adapterFavFeed;
    String pseudo;

    public FavoritesFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_favorites_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterFavFeed = new Adapter(EventArrayList, getActivity(), pseudo, "FavFragment");

        recyclerView.setAdapter(adapterFavFeed);

        populateRecyclerView();

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Événements favoris");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Le code ci-dessous permet d'alimenter la RecyclerView avec tous les événements auxquels participe l'utilisateur."
     */

    public void populateRecyclerView() {

        RequeteTcp requeteTcp;
        Message demande;
        Message reponse;
        try {
            //envoi d'une requête de demande de liste d'événement
            System.out.println("Demande liste event de user");
            demande = new Message(pseudo);
            requeteTcp = new RequeteTcp();
            requeteTcp.execute(demande);
            // réception de la réponse de la requête
            reponse = requeteTcp.get();
            ListeEvent listeEvent = reponse.getListeEvent();
            for(int i=0; i<listeEvent.size(); i++) {
                Event event = listeEvent.getEvent(i);
                EventArrayList.add(event);

            }
            adapterFavFeed.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
        }

    }
}