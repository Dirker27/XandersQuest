package com.toastercat.xander;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toastercat.xander.game.GameManager;
import com.toastercat.xander.util.LogTag;

/**
 * Persistable Fragment to manage UI during gameplay
 *
 * @author Dirk Hortensius
 */
public class GameFragment extends Fragment {

    private GameManager gm;

    public GameFragment() {
        super();

        this.gm = new GameManager();
    }

    /**
     * External Static Constructor
     *
     * Generate instance ready for consumption by root activities.
     */
    public static Fragment newInstance() {
        Log.v(LogTag.APP_LIFECYCLE, "Generating new Fragment Instance.");
        return new GameFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        GameView gameView = (GameView) view.findViewById(R.id.view_game_portal);
        gameView.setModel(this.gm.getModel());
        gameView.setInputAuthority(this.gm.getInputAuthority());

        this.gm.startNewSession();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LogTag.APP_LIFECYCLE, "Fragment onStart.");

        this.gm.startNewSession();
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(LogTag.APP_LIFECYCLE, "Fragment onPause.");

        this.gm.endSession();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(LogTag.APP_LIFECYCLE, "Fragment onResume.");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(LogTag.APP_LIFECYCLE, "Fragment onStop.");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LogTag.APP_LIFECYCLE, "Fragment onDestroy.");
    }
}
