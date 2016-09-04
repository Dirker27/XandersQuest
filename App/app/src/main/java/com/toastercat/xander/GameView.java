package com.toastercat.xander;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Custom 2D Sprite View to render game world
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class GameView extends View {
    private static final String TAG = "GameView";

    private final Paint paint;

    private Rect bounds;

    public GameView(final Context context) {
        this(context, null);
    }
    public GameView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public GameView(final Context context, final AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.paint = new Paint();
    }

    @Override
    public void onDraw(final Canvas canvas) {
        Log.v(TAG, "Rendering View Frame...");

        extractBoundaries(canvas);
        if (this.bounds == null) {
            Log.e(TAG, "Cannot complete render pass - no boundaries are defined");
            return;
        }

        // Draw Background
        this.paint.setColor(Color.GREEN);
        canvas.drawRect(this.bounds, this.paint);

        // TODO: Draw world according to current game state

        Log.v(TAG, "Render Pass Complete.");
    }

    private void extractBoundaries(final Canvas canvas) {
        this.bounds = canvas.getClipBounds();
    }
}
