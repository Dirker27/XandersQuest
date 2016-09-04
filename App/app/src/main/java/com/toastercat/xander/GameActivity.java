package com.toastercat.xander;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Activity to manage application lifecycle during gameplay.
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class GameActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.mFragmentManager = this.getSupportFragmentManager();

        // Load UI Fragment into root container
        Fragment fragment = this.mFragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = this.createFragment();
            this.mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private Fragment createFragment() {
        return GameFragment.newInstance();
    }
}
