package tsp.pro11.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import tsp.pro11.reseau.DemandeNombreParticipant;
import tsp.pro11.reseau.Desinscription;
import tsp.pro11.reseau.Event;
import tsp.pro11.reseau.Message;
import tsp.pro11.reseau.Participation;
import tsp.pro11.reseau.ReponseId;
import tsp.pro11.reseau.SupressionEvent;

/**
 * Cette activité permet d'afficher la description détaillée d'un événement après que l'utilisateur ait cliqué dessus dans un feed quelconque.
 */

public class DisplayActivity extends AppCompatActivity {

    private TextView nameEvent;
    private TextView nameOrga;
    private TextView price;
    private TextView date;
    private TextView nb_participants;
    //private ImageView imageView;
    private Button event_participate_button;
    private Button event_unparticipate_button;
    private Button event_delete_button;
    private Button show_participants_button;
    private static final String TAG = "pro11.DisplayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();

        final String pseudo = b.getString("pseudo");

        final String whichFragment = b.getString("whichFragment");

        switch (whichFragment) {
            case "HomeFragment":
                setContentView(R.layout.row_display_home);
                event_participate_button = findViewById(R.id.event_participate_button);
                break;
            case "FavFragment":
                setContentView(R.layout.row_display_fav);
                event_unparticipate_button = findViewById(R.id.event_unparticipate_button);
                break;
            default:
                setContentView(R.layout.row_display_my_events);
                event_delete_button = findViewById(R.id.event_delete_button);
        }

        //On récupère les arguments du Bundle.

        final String vh_nameEvent = b.getString("vh_nameEvent");
        String vh_nameOrga = b.getString("vh_nameOrga");
        double vh_prix = b.getDouble("vh_prix");
        String vh_date = b.getString("vh_date");

        final AlphaAnimation buttonClick = new AlphaAnimation(0.7F, 1F);
        buttonClick.setDuration(600);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Description détaillée de l'événement");
        setSupportActionBar(toolbar);

        nameEvent = findViewById(R.id.tv_nom_evenement);
        nameOrga = findViewById(R.id.tv_nom_organisateur);
        price = findViewById(R.id.tv_prix);
        date = findViewById(R.id.tv_date);
        nb_participants = findViewById(R.id.tv_nb_participants);
        //imageView = findViewById(R.id.img_event);

        nameEvent.setText(vh_nameEvent);
        nameOrga.setText(vh_nameOrga);
        price.setText(String.valueOf(vh_prix));
        date.setText(vh_date);

        show_participants_button = findViewById(R.id.show_participants_button);

        DemandeNombreParticipant demandeNombreParticipant;
        RequeteTcp requeteTcp;
        Message demande;
        Message reponse;
        ReponseId reponseId;
        int nb_participants_int;

        try{
            demandeNombreParticipant = new DemandeNombreParticipant(vh_nameEvent);
            requeteTcp = new RequeteTcp();
            demande = new Message(demandeNombreParticipant);
            Log.i(TAG,"Envoi de la demande");
            requeteTcp.execute(demande);

            Log.i(TAG,"Réception de la  réponse");
            reponse = requeteTcp.get();
            nb_participants_int = reponse.getEntier();
            nb_participants.setText(String.valueOf(nb_participants_int));
        }
        catch (Exception e) {
            Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
        }

        switch (whichFragment) {
            case "HomeFragment":
                event_participate_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(buttonClick);

                        Participation participation;
                        RequeteTcp requeteTcp;
                        ReponseId reponseId = null;
                        Message demande;
                        Message reponse;

                        //Code permettant l'utilisateur d'ajouter un événement dans ses favoris.

                        try {
                            participation = new Participation(pseudo, vh_nameEvent);
                            demande = new Message(participation);
                            requeteTcp = new RequeteTcp();
                            requeteTcp.execute(demande);
                            Log.i(TAG, "Envoi de la demande");

                            reponse = requeteTcp.get();
                            reponseId = reponse.getReponseId();
                        }

                        catch (Exception e) {
                            Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
                        }

                        if (!reponseId.isOk()) {
                            Context context = v.getContext();
                            CharSequence text2 = "L'événement se trouve maintenant dans vos favoris !";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text2, duration);
                            toast.show();

                            Intent MainActivityIntent = new Intent(DisplayActivity.this, MainActivity.class);
                            Bundle b = new Bundle();
                            b.putString("start_activity", "fav_activity");
                            b.putString("pseudo", pseudo);
                            MainActivityIntent.putExtras(b);
                            startActivity(MainActivityIntent);
                        }
                        else {
                            Context context = v.getContext();
                            CharSequence text2 = "Il y a eu une erreur";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text2, duration);
                            toast.show();
                        }
                    }
                });
                break;
            case "FavFragment":
                event_unparticipate_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(buttonClick);

                        Desinscription desinscription;
                        RequeteTcp requeteTcp;
                        ReponseId reponseId = null;
                        Message demande;
                        Message reponse;

                        //Code permettant à l'utilisateur de se désinscrire d'un événement.

                        try {
                            desinscription = new Desinscription(pseudo, vh_nameEvent);
                            demande = new Message(desinscription);
                            Log.i(TAG, "Envoi de la demande");
                            requeteTcp = new RequeteTcp();
                            requeteTcp.execute(demande);

                            Log.i(TAG, "Réception de la réponse");
                            reponse = requeteTcp.get();
                            reponseId = reponse.getReponseId();
                        }
                        catch (Exception e) {
                            Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
                        }

                        if (reponseId.isOk()){
                            Context context = v.getContext();
                            CharSequence text2 = "L'événement a été retiré de vos favoris !";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text2, duration);
                            toast.show();

                            Intent MainActivityIntent = new Intent(DisplayActivity.this, MainActivity.class);
                            Bundle b = new Bundle();
                            b.putString("start_activity", "fav_activity");
                            b.putString("pseudo", pseudo);
                            MainActivityIntent.putExtras(b);
                            startActivity(MainActivityIntent);
                        }
                        else {
                            Context context = v.getContext();
                            CharSequence text2 = "Il y a eu une erreur";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text2, duration);
                            toast.show();
                        }
                    }
                });
                break;
            default:
                event_delete_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(buttonClick);

                        SupressionEvent supressionEvent;
                        RequeteTcp requeteTcp;
                        ReponseId reponseId = null;
                        Message demande;
                        Message reponse;

                        //Code permettant à l'utilisateur de supprimer un événement qu'il a créé.

                        try{
                            supressionEvent = new SupressionEvent(vh_nameEvent);
                            requeteTcp = new RequeteTcp();
                            demande = new Message(supressionEvent);
                            Log.i(TAG, "Envoi de la demande");
                            requeteTcp.execute(demande);

                            Log.i(TAG, "Réception de la réponse");
                            reponse = requeteTcp.get();
                            reponseId = reponse.getReponseId();
                        }
                        catch (Exception e) {
                            Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
                        }

                        if (reponseId.isOk()){
                            Context context = v.getContext();
                            CharSequence text2 = "L'événement a été bien été supprimé !";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text2, duration);
                            toast.show();

                            Intent MainActivityIntent = new Intent(DisplayActivity.this, MainActivity.class);
                            Bundle b = new Bundle();
                            b.putString("pseudo", pseudo);
                            b.putString("start_activity", "profile_activity");
                            MainActivityIntent.putExtras(b);
                            startActivity(MainActivityIntent);
                        }
                        else {
                            Context context = v.getContext();
                            CharSequence text2 = "Il y a eu une erreur";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text2, duration);
                            toast.show();
                        }
                    }
                });
        }

        //Code permettant à l'utilisateur de voir les participants à un événement

        show_participants_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ShowParticipantsIntent = new Intent(DisplayActivity.this, ShowParticipantsActivity.class);
                Bundle b = new Bundle();
                b.putString("name_event", vh_nameEvent);
                ShowParticipantsIntent.putExtras(b);
                startActivity(ShowParticipantsIntent);
            }
        });
    }
}
