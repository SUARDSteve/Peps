package tsp.pro11.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import tsp.pro11.reseau.Identification;
import tsp.pro11.reseau.Message;
import tsp.pro11.reseau.ReponseId;

/**
 * Classe permettant de se log in.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "pro11.LoginActivity";

    private EditText pseudoText;
    private EditText passwordText;
    private Button loginButton;
    private TextView champ_erro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "Début de Login activity");
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Formulaire de connexion");
        setSupportActionBar(toolbar);

        pseudoText = findViewById(R.id.pseudoText);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        champ_erro = findViewById(R.id.tv_champerro);

        champ_erro.setVisibility(View.GONE);

        pseudoText.addTextChangedListener(textWatcher);
        passwordText.addTextChangedListener(textWatcher);

        checkFieldsForEmptyValues();

        final AlphaAnimation buttonClick = new AlphaAnimation(0.7F, 1F);
        buttonClick.setDuration(600);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                Identification identification;
                RequeteTcp requeteTcp;
                ReponseId reponseId;
                Message demande;
                Message reponse;

                try {
                    identification =  new Identification(pseudoText.getText().toString(), passwordText.getText().toString());
                    demande = new Message(identification);
                    Log.i(TAG,"Envoi de la demande");
                    requeteTcp =  new RequeteTcp();
                    requeteTcp.execute(demande);
                    Log.i(TAG,"Réception de la  réponse");
                    reponse = requeteTcp.get();
                    reponseId = reponse.getReponseId();

                    String reponsetxt = "La réponse est " + reponseId.isOk();
                    Log.i(TAG, "réponse reçue: "  + reponseId.isOk());

                    if (reponseId.isOk()) {
                        Intent MainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);

                        String pseudo = pseudoText.getText().toString();
                        Bundle b = new Bundle();
                        b.putString("pseudo", pseudo);
                        b.putString("start_activity", "home_activity");
                        MainActivityIntent.putExtras(b);
                        startActivity(MainActivityIntent);
                        finish();
                    }
                    else {
                        champ_erro.setVisibility(View.VISIBLE);
                        pseudoText.getText().clear();
                        passwordText.getText().clear();
                    };
                } catch (Exception e) {
                    Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
                }
            }
        });
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
        String s1 = pseudoText.getText().toString();
        String s2 = passwordText.getText().toString();

        if (s1.equals("")|| s2.equals("")){
            loginButton.setEnabled(false);
            loginButton.setAlpha(0.5f);
        }
        else {
            loginButton.setEnabled(true);
            loginButton.setAlpha(1f);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
