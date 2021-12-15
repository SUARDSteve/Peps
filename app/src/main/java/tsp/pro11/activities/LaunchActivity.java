package tsp.pro11.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

/**
 * Vue affichée au démarrage. Elle permet de soit sign up soit log in.
 */

public class LaunchActivity extends AppCompatActivity {

    private static final String TAG = "pro11.LaunchActivity";
    private Button LoginButton;
    private Button SignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"Lancement de LaunchActivity");
        setContentView(R.layout.activity_launch);

        final AlphaAnimation buttonClick = new AlphaAnimation(0.7F, 1F);
        buttonClick.setDuration(600);

        LoginButton = findViewById(R.id.login_button);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                Intent LoginActivityIntent = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(LoginActivityIntent);
            }
        });

        SignUpButton = findViewById(R.id.signup_button);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                Intent SignUpActivityIntent = new Intent(LaunchActivity.this, SignUpActivity.class);
                startActivity(SignUpActivityIntent);
            }
        });

    }

}
