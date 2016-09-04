package com.toastercat.xander;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Activity to manage application lifecycle during gameplay.
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.mFragmentManager = this.getSupportFragmentManager();

        //------------------------------------------------=
        // Load UI Fragment into Root Container
        //------------------------------------------------=
        Log.v(TAG, "Retrieving UI Fragment...");
        Fragment fragment = this.mFragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            Log.v(TAG, "UI Fragment is not present - Generating new instance...");
            fragment = this.createFragment();
            this.mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
            Log.v(TAG, "UI fragment loaded into container.");
        } else {
            Log.v(TAG, "UI Fragment already present.");
        }
    }

    private Fragment createFragment() {
        return GameFragment.newInstance();
    }
}
