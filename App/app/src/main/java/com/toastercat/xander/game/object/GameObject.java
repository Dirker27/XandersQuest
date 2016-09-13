package com.toastercat.xander.game.object;

import android.graphics.Bitmap;
import android.graphics.Color;

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
        /* NO-OP STUB */
    }

    /**
     * Actions taken upon collision.
     *
     * Can modify only self (BL encapsulation).
     */
    public void onCollide(final GameObject collisionObject) {
        /* NO-OP STUB */
    }

    /**
     * Detect if we've collided with a given object.
     *
     * @return true iff object transforms overlap.
     */
    public boolean detectCollision(final GameObject obj) {
        //- Right-Most left bound ------------------------=
        //
        final float rightMostLeftBound;
        {
            float myLeftBound = this.getLeftBound();
            float theirLeftBound = obj.getLeftBound();
            rightMostLeftBound = (myLeftBound > theirLeftBound)
                    ? myLeftBound : theirLeftBound;
        }

        //- Left-Most right bound ------------------------=
        //
        final float leftMostRightBound;
        {
            float myRightBound = this.getRightBound();
            float theirRightBound = obj.getRightBound();
            leftMostRightBound = (myRightBound < theirRightBound)
                    ? myRightBound : theirRightBound;
        }

        //- Top-Most bottom bound ------------------------=
        //
        final float topMostBottomBound;
        {
            float myBottomBound = this.getBottomBound();
            float theirBottomBound = obj.getBottomBound();
            topMostBottomBound = (myBottomBound > theirBottomBound)
                    ? myBottomBound : theirBottomBound;
        }

        //- Bottom-Most top bound ------------------------=
        //
        final float bottomMostTopBound;
        {
            float myTopBound = this.getTopBound();
            float theirTopBound = obj.getTopBound();
            bottomMostTopBound = (myTopBound < theirTopBound)
                    ? myTopBound : theirTopBound;
        }

        return rightMostLeftBound < leftMostRightBound
                && topMostBottomBound < bottomMostTopBound;
    }

    //~ --------------------------------------------------------- ~//
    // OBJECT ACTIONS
    //~ --------------------------------------------------------- ~//
    public void move(final float dx, final float dy) {
        this.move(dx, dy, 0f);
    }
    public void move(final float dx, final float dy, final float dz) {
        this.location[0] += dx;
        this.location[1] += dy;
        this.location[2] += dz;
    }

    //~ --------------------------------------------------------- ~//
    // INDIRECT ACCESSORS
    //~ --------------------------------------------------------- ~//

    // Bounds
    public float getLeftBound() {
        return this.location[0] - ((this.size[0]/2f) * this.scale);
    }
    public float getRightBound() {
        return this.location[0] + ((this.size[0]/2f) * this.scale);
    }
    public float getTopBound() {
        return this.location[1] + ((this.size[1]/2f) * this.scale);
    }
    public float getBottomBound() {
        return this.location[1] - ((this.size[1]/2f) * this.scale);
    }

    //~ --------------------------------------------------------- ~//
    // DIRECT ACCESSORS
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
