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

    private GameModel model;

    public GameView(final Context context) {
        this(context, null);
    }
    public GameView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public GameView(final Context context, final AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
    }

    @Override
    public void onDraw(final Canvas canvas) {
        Log.v(TAG, "Rendering View Frame...");

        _extractBoundaries(canvas);
        if (bounds == null) {
            Log.e(TAG, "Cannot complete render pass - no boundaries are defined.");
            return;
        }

        // Draw Background
        this.paint.setColor(Color.BLACK);
        canvas.drawRect(bounds, paint);

        //- Draw Game Objects ----------------------------=
        //
        if (model == null) {
            Log.v(TAG, "No model set - nothing left to draw.");
            return;
        }
        // Terrain
        Log.v(TAG, "Rendering Terrain");
        for (GameObject obj : this.model.getTerrainObjects()) {
            _renderGameObject(obj, canvas);
        }
        // Player
        Log.v(TAG, "Rendering Player");
        _renderGameObject(this.model.getPlayer(), canvas);
        // Enemies
        Log.v(TAG, "Rendering Enemies");
        for (GameObject obj : this.model.getEnemyObjects()) {
            _renderGameObject(obj, canvas);
        }

        Log.v(TAG, "Render Pass Complete.");
    }
    private void _extractBoundaries(final Canvas canvas) {
        bounds = canvas.getClipBounds();
    }
    private void _renderGameObject(final GameObject object, final Canvas canvas) {
        paint.setColor(object.getSpriteColor());

        float adaptedSizeX = object.getSize()[0] * 50f;
        float adaptedSizeY = object.getSize()[1] * 50f;

        //- Transform Game -> Canvas Coordinates ---------=
        //
        // X [left, right]
        float left  = bounds.left + (object.getLocation()[0] - (adaptedSizeX / 2f));
        float right = bounds.left + (object.getLocation()[0] + (adaptedSizeX / 2f));
        // Y [top, bottom]
        float top    = bounds.bottom - (object.getLocation()[1] + (adaptedSizeY / 2f));
        float bottom = bounds.bottom - (object.getLocation()[1] - (adaptedSizeY / 2f));

        Log.v(TAG, String.format("Draw at [%s, %s], [%s, %s]", left, right, top, bottom));
        canvas.drawRect(left, top, right, bottom, paint);
    }

    public void setModel(final GameModel gameModel) {
        this.model = gameModel;
    }
}
