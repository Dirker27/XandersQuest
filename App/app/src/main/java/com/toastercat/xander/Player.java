package com.toastercat.xander;

import android.graphics.Color;

/**
 * Don't hate the player.
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class Player extends GameObject {
    private boolean alive = true;

    public Player() {
        super();

        setLocation(75, 75);
        setSpriteColor(Color.CYAN);
    }

    public boolean isAlive() {
        return alive;
    }
}