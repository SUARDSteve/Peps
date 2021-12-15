package tsp.pro11.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import tsp.pro11.activities.Fragments.FavoritesFragment;
import tsp.pro11.activities.Fragments.AddEventFragment;
import tsp.pro11.activities.Fragments.HomeFragment;
import tsp.pro11.activities.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Cette classe est la MainActivity. C'est l'activité qui est la plus souvent affichée.
 * C'est celle qui permet la navigation entre les différents Fragment.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        String start_activity = getIntent().getExtras().getString("start_activity");

        Bundle b = new Bundle();
        b.putString("pseudo", getIntent().getExtras().getString("pseudo"));

        Fragment StartFragment;

        switch (start_activity) {
            case "home_activity":
                StartFragment = new HomeFragment();
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                break;
            case "fav_activity":
                StartFragment = new FavoritesFragment();
                bottomNavigationView.setSelectedItemId(R.id.nav_favorites);
                break;
            default:
                StartFragment = new ProfileFragment();
                bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        }

        StartFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, StartFragment).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Bundle b = new Bundle();
                    b.putString("pseudo", getIntent().getExtras().getString("pseudo"));

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            selectedFragment.setArguments(b);
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new FavoritesFragment();
                            selectedFragment.setArguments(b);
                            break;
                        case R.id.nav_addevent:
                            selectedFragment = new AddEventFragment();
                            selectedFragment.setArguments(b);
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            selectedFragment.setArguments(b);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
    }
}
