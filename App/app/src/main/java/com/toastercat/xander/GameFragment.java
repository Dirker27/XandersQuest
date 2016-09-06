package com.toastercat.xander;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toastercat.xander.game.GameManager;

/**
 * Persistable Fragment to manage UI during gameplay
 *
 * @author Dirk Hortensius
 */
public class GameFragment extends Fragment {
    private static final String TAG = "GameFragment";

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
        Log.v(TAG, "Generating new Instance.");
        return new GameFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        GameView gameView = (GameView) view.findViewById(R.id.view_game_portal);
        gameView.setModel(this.gm.getModel());

        return view;
    }
}
