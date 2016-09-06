package com.toastercat.xander.game.actor;

import android.graphics.Color;

import com.toastercat.xander.game.GameObject;

/**
 * Don't hate the player.
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class Player extends GameObject {
    private boolean alive = true;

    public Player() {
        super();

        setSpriteColor(Color.CYAN);
    }

    public boolean isAlive() {
        return alive;
    }
}
