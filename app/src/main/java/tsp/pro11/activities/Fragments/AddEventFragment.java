package tsp.pro11.activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.io.File;

import tsp.pro11.activities.InscriptionReussie;
import tsp.pro11.activities.R;
import tsp.pro11.activities.RequeteTcp;
import tsp.pro11.activities.SignUpActivity;
import tsp.pro11.reseau.Event;
import tsp.pro11.reseau.Inscription;
import tsp.pro11.reseau.Message;
import tsp.pro11.reseau.ReponseId;

/**
 * Cette classe permet à l'utilisateur d'aller sur la page permettant d'ajouter un événement dans la base de données.
 * C'est un Fragment, c'est une sous-activité, il est appelé dans la MainActivity.
 */

public class AddEventFragment extends Fragment {

    private static final String TAG = "pro11.AddEventFragment";
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private Button AddEventButton;
    private Button AddImageButton;
    private EditText NameEventText;
    private EditText NameOrgaText;
    private EditText DateEventText;
    private EditText PriceEventText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          return inflater.inflate(R.layout.fragment_addevent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Ajouter un événement");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        NameEventText = view.findViewById(R.id.NameEventText);
        NameOrgaText = view.findViewById(R.id.NameOrgaText);
        DateEventText = view.findViewById(R.id.DateEventText);
        PriceEventText = view.findViewById(R.id.PrixEventText);
        AddEventButton = view.findViewById(R.id.AddEventButton);
        AddImageButton = view.findViewById(R.id.AddImageButton);

        Bundle b = this.getArguments();
        String pseudo = b.getString("pseudo");
        NameOrgaText.setFocusable(false);

        NameOrgaText.setText(pseudo);

        NameEventText.addTextChangedListener(textWatcher);
        DateEventText.addTextChangedListener(textWatcher);
        PriceEventText.addTextChangedListener(textWatcher);

        checkFieldsForEmptyValues();

        final AlphaAnimation buttonClick = new AlphaAnimation(0.7F, 1F);
        buttonClick.setDuration(600);

        /**
         * Le code ci-dessous se lance lorsque l'utilisateur appuie sur le bouton "Ajouter l'événement".
         */

        AddEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                String nameEvent = NameEventText.getText().toString().trim();
                String nameOrga = NameOrgaText.getText().toString().trim();
                String dateEvent = DateEventText.getText().toString().trim();
                double priceEvent = Double.parseDouble(PriceEventText.getText().toString());

                //Récupération des données saisies par l'utilisateur.

                Event event;
                RequeteTcp requeteTcp;
                ReponseId reponseId;
                Message demande;
                Message reponse;

                //Ajout de l'événement dans la base de données.

                try {
                    event =  new Event(nameEvent,nameOrga,dateEvent,priceEvent);
                    demande = new Message(event);
                    Log.i(TAG,"Envoi de la demande");
                    requeteTcp =  new RequeteTcp();
                    requeteTcp.execute(demande);

                    Log.i(TAG,"Réception de la  réponse");
                    reponse = requeteTcp.get();
                    reponseId = reponse.getReponseId();

                    String reponsetxt = "La réponse est " + reponseId.isOk();
                    Log.i(TAG, "réponse reçue: "  + reponseId.isOk());

                } catch (Exception e) {
                    Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
                }

                Context context = v.getContext();
                CharSequence text2 = "Votre événement a été ajouté !";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text2, duration);
                toast.show();

                NameEventText.getText().clear();
                NameOrgaText.getText().clear();
                DateEventText.getText().clear();
                PriceEventText.getText().clear();

            }

        });

        /**
         * Ce code permet d'ouvrir le fichier Photos qui se trouve sur le smartphone.
         * Le code permettant de récupérer la photo sélectionnée n'est pas implémenté.
         */

        AddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();

                Uri data = Uri.parse(pictureDirectoryPath);

                photoPickerIntent.setDataAndType(data, "image/*");

                startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){
        String s1 = NameEventText.getText().toString();
        String s2 = DateEventText.getText().toString();
        String s3 = PriceEventText.getText().toString();

        if (s1.equals("")|| s2.equals("")|| s3.equals("")){
            AddEventButton.setEnabled(false);
            AddEventButton.setAlpha(0.5f);
        }
        else {
            AddEventButton.setEnabled(true);
            AddEventButton.setAlpha(1f);
        }
    }

}
