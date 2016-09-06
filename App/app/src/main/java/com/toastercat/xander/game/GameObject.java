package com.toastercat.xander.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.toastercat.xander.util.LogTag;

/**
 * Base Game Object - any entity that exists in, can affect, or be affected by
 *   the game world.
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class GameObject {
    //- Transform Attrs ----------------------------------=
    //
    // TODO: create Transform object
    //
    private Float[] location; // [X, Y, Z] - Z doesn't do anything yet
    private Float[] size;     // (X, Y)
    private float scale;

    //- Render Attrs -------------------------------------=
    //
    // TODO: Abstract to "Graphics Object"
    //
    private int spriteColor;
    private Bitmap spriteImage;

    /**
     * PUBLIC CONSTRUCTOR
     *
     * Default State:
     *  - Located at [0,0,0]
     *  - 1m x 1m in size
     *  - Natural Scale (1)
     *  - Simple Purple Box as Sprite
     */
    public GameObject() {
        this.location = new Float[3];
        this.location[0] = 0f;
        this.location[1] = 0f;
        this.location[2] = 0f;

        this.size = new Float[2];
        this.size[0] = 1f;
        this.size[1] = 1f;

        this.scale = 1f;

        this.spriteColor = Color.MAGENTA;
        this.spriteImage = null;
    }

    /**
     * Called by the master update thread.
     */
    public void update(final long clockTick) {
        /* STUB */
    }


    //~ --------------------------------------------------------- ~//
    // ACCESSORS
    //~ --------------------------------------------------------- ~//

    // Location
    public void setLocation(final float x,
                            final float y) {
        this.location[0] = x;
        this.location[1] = y;
    }
    public void setLocation(final float x,
                            final float y,
                            final float z) {
        this.location[0] = x;
        this.location[1] = y;
        this.location[2] = z;
    }
    public Float[] getLocation() {
        return this.location;
    }

    // Size
    public void setSize(final float x,
                        final float y) {
        this.size[0] = x;
        this.size[1] = y;
    }
    public Float[] getSize() {
        return this.size;
    }

    // Scale
    public void setScale(final float scale) {
        this.scale = scale;
    }
    public float getScale() {
        return this.scale;
    }

    // Sprite Color
    public void setSpriteColor(final int spriteColor) {
        this.spriteColor = spriteColor;
    }
    public int getSpriteColor() {
        return this.spriteColor;
    }

    // Sprite Image
    public void setSpriteImage(final Bitmap spriteImage) {
        this.spriteImage = spriteImage;
    }
    public Bitmap getSpriteImage() {
        return this.spriteImage;
    }
}
