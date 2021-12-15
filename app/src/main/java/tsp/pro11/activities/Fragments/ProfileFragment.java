package tsp.pro11.activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import tsp.pro11.activities.LaunchActivity;
import tsp.pro11.activities.R;
import tsp.pro11.activities.RequeteTcp;
import tsp.pro11.activities.ShowMyEventsActivity;
import tsp.pro11.activities.ShowParticipantsActivity;
import tsp.pro11.reseau.DemandeListeCreation;
import tsp.pro11.reseau.DemandeNombreCreation;
import tsp.pro11.reseau.DemandeNombreParticipation;
import tsp.pro11.reseau.Desinscription;
import tsp.pro11.reseau.ListeEvent;
import tsp.pro11.reseau.Message;
import tsp.pro11.reseau.ReponseId;

/**
 * Cette classe permet à l'utilisateur de voir son profil.
 * C'est un Fragment, c'est une sous-activité, il est appelé dans la MainActivity.
 */

public class ProfileFragment extends Fragment {

    private TextView pseudo_tv;
    private TextView nb_creations;
    private TextView nb_participations;
    private Button logoff_button;
    private Button showmyevents_button;
    private static final String TAG = "pro11.ProfileFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle b = this.getArguments();
        final String pseudo = b.getString("pseudo");

        pseudo_tv = getView().findViewById(R.id.tv_name_profile);
        pseudo_tv.setText(pseudo);

        nb_creations = getView().findViewById(R.id.tv_nb_creations);
        nb_participations = getView().findViewById(R.id.tv_nb_participations);

        DemandeNombreCreation demandeNombreCreation;
        DemandeNombreParticipation demandeNombreParticipation;
        RequeteTcp requeteTcp;
        ReponseId reponseId = null;
        Message demande;
        Message reponse;

        //On demande ici le nombre d'événements que l'utilisateur a créés.

        try{
            demandeNombreCreation = new DemandeNombreCreation(pseudo);
            requeteTcp = new RequeteTcp();
            demande = new Message(demandeNombreCreation);
            Log.i(TAG, "Envoi de la demande");
            requeteTcp.execute(demande);

            Log.i(TAG, "Réception de la réponse");
            reponse = requeteTcp.get();
            nb_creations.setText(String.valueOf(reponse.getEntier()));
        }
        catch (Exception e) {
            Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
        }

        //On demande ici le nombre d'événements auxquels l'utilisateur participe.

        try{
            demandeNombreParticipation = new DemandeNombreParticipation(pseudo);
            requeteTcp = new RequeteTcp();
            demande = new Message(demandeNombreParticipation);
            Log.i(TAG, "Envoi de la demande");
            requeteTcp.execute(demande);

            Log.i(TAG, "Réception de la réponse");
            reponse = requeteTcp.get();
            nb_participations.setText(String.valueOf(reponse.getEntier()));
        }
        catch (Exception e) {
            Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
        }

        final AlphaAnimation buttonClick = new AlphaAnimation(0.7F, 1F);
        buttonClick.setDuration(600);

        final Context context = view.getContext();

        showmyevents_button = getView().findViewById(R.id.showmyevents_button);

        //Lorsque l'utilisateur clique sur ce bouton il a accès sous forme d'un feed à tous les événements qu'il a créé.

        showmyevents_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                Intent ShowMyEventsIntent = new Intent(context, ShowMyEventsActivity.class);
                Bundle b = new Bundle();
                b.putString("pseudo", pseudo);
                ShowMyEventsIntent.putExtras(b);
                startActivity(ShowMyEventsIntent);
            }
        });

        logoff_button = getView().findViewById(R.id.logoff_button);

        //Lorsque l'utilisateur clique sur ce bouton il se déconnecte et arrive sur la LaunchActivity avec le choix de s'inscrire ou de se connecter.

        logoff_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                Intent LaunchActivityIntent = new Intent(context, LaunchActivity.class);
                startActivity(LaunchActivityIntent);
                getActivity().finish();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
