package tsp.pro11.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
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

import tsp.pro11.reseau.Inscription;
import tsp.pro11.reseau.Message;
import tsp.pro11.reseau.ReponseId;

/**
 * Cette activité permet de s'inscrire.
 */

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "pro11.SignUpActivity";

    private EditText SignUpPseudo;
    private EditText Password;
    private EditText ConfirmPassword;
    private Button confirmSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Formulaire d'inscription");
        setSupportActionBar(toolbar);

        confirmSignUpButton = findViewById(R.id.ConfirmSignUpButton);
        SignUpPseudo = findViewById(R.id.pseudoText);
        Password = findViewById(R.id.passwordText);
        ConfirmPassword = findViewById(R.id.ConfirmPasswordText);
        final TextView tv_diffmdp = findViewById(R.id.tv_mdpdiff);
        final TextView tv_pseudotaken = findViewById(R.id.tv_pseudotaken);

        tv_diffmdp.setVisibility(View.GONE);
        tv_pseudotaken.setVisibility(View.GONE);

        SignUpPseudo.addTextChangedListener(textWatcher);
        Password.addTextChangedListener(textWatcher);
        ConfirmPassword.addTextChangedListener(textWatcher);

        checkFieldsForEmptyValues();

        final AlphaAnimation buttonClick = new AlphaAnimation(0.7F, 1F);
        buttonClick.setDuration(600);

        confirmSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                Inscription inscription;
                RequeteTcp requeteTcp;
                ReponseId reponseId;
                Message demande;
                Message reponse;

                if(Password.getText().toString().equals(ConfirmPassword.getText().toString())) {
                    try {
                        inscription = new Inscription(SignUpPseudo.getText().toString(), Password.getText().toString());
                        demande = new Message(inscription);
                        Log.i(TAG, "Envoi de la demande");
                        requeteTcp = new RequeteTcp();
                        requeteTcp.execute(demande);

                        Log.i(TAG, "Réception de la  réponse");
                        reponse = requeteTcp.get();
                        reponseId = reponse.getReponseId();

                        Log.i(TAG, "réponse reçue: " + reponseId.isOk());

                        if(!reponseId.isOk()) {
                            tv_diffmdp.setVisibility(View.GONE);
                            tv_pseudotaken.setVisibility(View.GONE);

                            Intent InscritpionReussieIntent = new Intent(SignUpActivity.this, InscriptionReussie.class);

                            String pseudo = SignUpPseudo.getText().toString();
                            Bundle b = new Bundle();
                            b.putString("pseudo", pseudo);
                            InscritpionReussieIntent.putExtras(b);

                            startActivity(InscritpionReussieIntent);
                        }
                        else {
                            tv_pseudotaken.setVisibility(View.VISIBLE);
                            tv_diffmdp.setVisibility(View.GONE);
                            SignUpPseudo.getText().clear();
                            ConfirmPassword.getText().clear();
                            Password.getText().clear();
                        }
                    }
                    catch (Exception e) {
                        Log.e(TAG,"Echec de la requête TCP: " + e.getMessage());
                    }
                }
                else {
                    tv_pseudotaken.setVisibility(View.GONE);
                    tv_diffmdp.setVisibility(View.VISIBLE);
                    ConfirmPassword.getText().clear();
                    Password.getText().clear();
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
        String s1 = SignUpPseudo.getText().toString();
        String s2 = Password.getText().toString();
        String s3 = ConfirmPassword.getText().toString();

        if (s1.equals("")|| s2.equals("")|| s3.equals("")){
            confirmSignUpButton.setEnabled(false);
            confirmSignUpButton.setAlpha(0.5f);
        }
        else {
            confirmSignUpButton.setEnabled(true);
            confirmSignUpButton.setAlpha(1f);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
