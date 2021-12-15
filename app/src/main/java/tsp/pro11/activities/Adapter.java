package tsp.pro11.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import tsp.pro11.reseau.Event;
import tsp.pro11.reseau.ListeParticipants;
import tsp.pro11.reseau.Message;
import tsp.pro11.reseau.NomEvent;

/**
 * Le but de cette classe est "d'adapter".
 * Ce qu'elle fait principalement est prendre un fichier .xml en argument, le fichier qui sert de vignette, vignette qui est affichée dans le feed.
 * Elle prend également un "Event" un argument.
 * Elle change les informations présentes sur la vignette de base (nom de l'événement, nom de l'organisateur) par les informations réelles de l'événement.
 *
 * C'est aussi cette classe qui permet de cliquer sur une vignette et d'afficher la description détaillée d'un événement.
 * Suivant le Fragment dans lequel nous nous trouvons, ce qui est affiché après avoir cliqué sur la vignette est différent.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<Event> EventArrayList;
    RequestManager glide;
    Context context;
    String pseudo;
    String fragmentPicked;
    private static final String TAG = "pro11.AdapterActivity" ;

    public Adapter(ArrayList<Event> EventArrayList, Context context, String pseudo, String fragmentPicked) {
        this.EventArrayList = EventArrayList;
        this.context = context;
        this.pseudo = pseudo;
        this.fragmentPicked = fragmentPicked;
        glide = Glide.with(context);
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_v2, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    /**
     * C'est dans le code ci-dessous que nous remplaçons les données.
     * @param viewHolder
     * @param position
     */

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final Event event = EventArrayList.get(position);

        //viewHolder.tv_date.setText(event.getDate());
        viewHolder.tv_name_orga.setText(String.valueOf(event.getOrganisateur()));
        //viewHolder.tv_price.setText(event.getPrix() + " €");
        viewHolder.tv_name_event.setText(String.valueOf(event.getNom()));

        //glide.load(event.getPhoto()).into(viewHolder.imgView_postPic);
        final AlphaAnimation buttonClick = new AlphaAnimation(0.7F, 1F);
        buttonClick.setDuration(600);

        //Le code ci-dessous intervient lorsque l'utilisateur clique sur une vignette.

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.itemView.startAnimation(buttonClick);
                Context context = v.getContext();
                Intent displayActivityIntent = new Intent(context, DisplayActivity.class);;
                String whichFragment;

                switch (fragmentPicked) {
                    case "HomeFragment":
                        whichFragment = "HomeFragment";
                        break;
                    case "FavFragment":
                        whichFragment = "FavFragment";
                        break;
                    default:
                        whichFragment = "ShowMyEventsActivity";
                }

                //Un Bundle permet de passer des objets en argument de la prochaine activité.
                //Ici nous passons donc comme arguments les informations relatives à l'événement ainsi que le Fragment dans lequel nous nous trouvons.

                Bundle b = new Bundle();
                b.putString("pseudo", pseudo);
                b.putString("whichFragment", whichFragment);

                b.putString("vh_nameEvent", event.getNom());
                b.putString("vh_nameOrga", event.getOrganisateur());
                b.putDouble("vh_prix", event.getPrix());
                b.putString("vh_date", event.getDate());

                displayActivityIntent.putExtras(b);

                context.startActivity(displayActivityIntent);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_name_event, tv_name_orga, tv_price;
        public ImageView imgView_postPic;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            imgView_postPic = (ImageView) itemView.findViewById(R.id.img_event_v2);

            //tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_name_event = (TextView) itemView.findViewById(R.id.tv_nom_evenement_v2);
            tv_name_orga = (TextView) itemView.findViewById(R.id.tv_nom_organisateur_v2);
            //tv_price = (TextView) itemView.findViewById(R.id.tv_prix);
        }
    }

    @Override
    public int getItemCount() {
        return EventArrayList.size(); }
}


