package com.toastercat.xander.input;

import android.util.Log;
import android.view.MotionEvent;

import com.toastercat.xander.game.GameModel;
import com.toastercat.xander.game.actor.Player;
import com.toastercat.xander.util.LogTag;

/**
 * The Authoritative source of all actions input.
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class InputAuthority {
    private GameModel model;

    private boolean screenTouch = false;

    public InputAuthority(final GameModel model) {
        this.model = model;
    }

    public void actionDown(final MotionEvent e) {
        screenTouch = true;
        jumpStart();
    }

    public void actionUp(final MotionEvent e) {
        screenTouch = false;
    }

    private void jumpStart() {
        Log.d(LogTag.USER_ACTION, "Player Jump.");

        Player p = model.getPlayer();
        p.jump();
    }
}
