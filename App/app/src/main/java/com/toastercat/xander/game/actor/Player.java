package com.toastercat.xander.game.actor;

import android.graphics.Color;
import android.util.Log;

import com.toastercat.xander.game.GameObject;

/**
 * Don't hate the player.
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class Player extends GameObject {
    private boolean alive;

    private float jumpHeight = 5f; // [m]
    private float jumpSpeed = 0.04f; // [m/s]
    private float jumpBase;
    private boolean jumping;
    private boolean jumpPeaked;

    public Player() {
        super();

        this.alive = true;

        this.jumping = false;
        this.jumpPeaked = false;
        this.jumpBase = this.getLocation()[1];

        setSpriteColor(Color.CYAN);
    }

    @Override
    public void update(final long clockTick) {
        super.update(clockTick);

        if (jumping) {
            if (jumpPeaked) {
                move(0f, -jumpSpeed);
                if (getLocation()[1] < jumpBase) {
                    jumping = false;
                }
            } else {
                move(0f, jumpSpeed);
                if (getLocation()[1] > jumpBase + jumpHeight) {
                    jumpPeaked = true;
                    Log.d("Jump", "Jump Peaked.");
                }
            }
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void jump() {
        this.jumping = true;
        this.jumpPeaked = false;
        this.jumpBase = getLocation()[1];
    }
}
