package tsp.pro11.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Vue affichée lorsque l'inscription est réussie.
 */

public class InscriptionReussie extends AppCompatActivity {

    Button StartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_reussie);

        Bundle b = getIntent().getExtras();
        final String pseudo = b.getString("pseudo");

        StartButton = findViewById(R.id.commencer_btn);
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainActivityIntent = new Intent(InscriptionReussie.this, MainActivity.class);

                Bundle b = new Bundle();
                b.putString("pseudo", pseudo);
                b.putString("start_activity", "home_activity");
                MainActivityIntent.putExtras(b);

                startActivity(MainActivityIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
