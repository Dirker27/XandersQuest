package com.toastercat.xander.game.actor;

import android.graphics.Color;

import com.toastercat.xander.game.GameObject;

/**
 * Don't hate the player.
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class Player extends GameObject {
    private boolean alive;

    public Player() {
        super();

        this.alive = true;

        setSpriteColor(Color.CYAN);
    }

    @Override
    public void update(final long clockTick) {
        super.update(clockTick);
    }

    public boolean isAlive() {
        return alive;
    }
}
