package com.toastercat.xander;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Persistable Fragment to manage UI during gameplay
 *
 * @author Dirk Hortensius
 */
public class GameFragment extends Fragment {

    /**
     * External Static Constructor
     *
     * Generate instance ready for consumption by root activities.
     */
    public static Fragment newInstance() {
        return new GameFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        // TODO: Initialization

        return view;
    }
}
