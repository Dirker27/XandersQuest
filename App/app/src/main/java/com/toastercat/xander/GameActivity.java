package com.toastercat.xander;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.toastercat.xander.util.LogTag;

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

        //------------------------------------------------=
        // Load UI Fragment into Root Container
        //------------------------------------------------=
        Log.v(LogTag.APP_LIFECYCLE, "Retrieving UI Fragment...");
        Fragment fragment = this.mFragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            Log.v(LogTag.APP_LIFECYCLE, "UI Fragment is not present - Generating new instance...");
            fragment = this.createFragment();
            this.mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
            Log.v(LogTag.APP_LIFECYCLE, "UI fragment loaded into container.");
        } else {
            Log.v(LogTag.APP_LIFECYCLE, "UI Fragment already present.");
        }
    }

    private Fragment createFragment() {
        return GameFragment.newInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LogTag.APP_LIFECYCLE, "Activity onStart.");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(LogTag.APP_LIFECYCLE, "Activity onPause.");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(LogTag.APP_LIFECYCLE, "Activity onResume.");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(LogTag.APP_LIFECYCLE, "Activity onStop.");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LogTag.APP_LIFECYCLE, "Activity onDestroy.");
    }
}
