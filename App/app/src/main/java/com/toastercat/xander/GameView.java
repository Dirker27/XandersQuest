package com.toastercat.xander;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.toastercat.xander.game.GameModel;
import com.toastercat.xander.game.GameObject;
import com.toastercat.xander.input.InputAuthority;

/**
 * Custom 2D Sprite View to render game world
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class GameView extends View {
    private static final String TAG_RENDER = "[Frame Render]";
    private static final String TAG_INPUT  = "[Input]";
    private static final float RENDER_SCALE = 50f;

    private InputAuthority inputAuthority;

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
        Log.v(TAG_RENDER, "Rendering View Frame...");

        _extractBoundaries(canvas);
        if (bounds == null) {
            Log.e(TAG_RENDER, "Cannot complete render pass - no boundaries are defined.");
            return;
        }

        // Draw Background
        paint.setColor(Color.BLACK);
        canvas.drawRect(bounds, paint);

        //- Draw Game Objects ----------------------------=
        //
        if (model == null) {
            Log.v(TAG_RENDER, "No model set - nothing left to draw.");
            return;
        }
        // Terrain
        Log.v(TAG_RENDER, "Rendering Terrain");
        for (GameObject obj : this.model.getTerrainObjects()) {
            _renderGameObject(obj, canvas);
        }
        // Player
        Log.v(TAG_RENDER, "Rendering Player");
        _renderGameObject(this.model.getPlayer(), canvas);
        // Enemies
        Log.v(TAG_RENDER, "Rendering Enemies");
        for (GameObject obj : this.model.getEnemyObjects()) {
            _renderGameObject(obj, canvas);
        }

        Log.v(TAG_RENDER, "Render Pass Complete.");
    }
    private void _extractBoundaries(final Canvas canvas) {
        bounds = canvas.getClipBounds();
    }
    private void _renderGameObject(final GameObject object, final Canvas canvas) {
        paint.setColor(object.getSpriteColor());

        float adaptedSizeX = object.getSize()[0] * object.getScale() * RENDER_SCALE;
        float adaptedSizeY = object.getSize()[1] * object.getScale() * RENDER_SCALE;

        float adaptedLocX = object.getLocation()[0] * RENDER_SCALE;
        float adaptedLocY = object.getLocation()[1] * RENDER_SCALE;

        //- Transform Game -> Canvas Coordinates ---------=
        //
        // X [left, right]
        float left  = bounds.left + (adaptedLocX - (adaptedSizeX / 2f));
        float right = bounds.left + (adaptedLocX + (adaptedSizeX / 2f));
        // Y [top, bottom]
        float top    = bounds.bottom - (adaptedLocY + (adaptedSizeY / 2f));
        float bottom = bounds.bottom - (adaptedLocY - (adaptedSizeY / 2f));

        Log.v(TAG_RENDER, String.format("Draw at [%s, %s], [%s, %s]", left, right, top, bottom));
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent e) {
        postInvalidate();

        if (this.inputAuthority != null)
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG_INPUT, "Input: Touch Down--v--v--v--v");
                this.inputAuthority.actionDown(e);
                break;
            case MotionEvent.ACTION_UP:
                Log.v(TAG_INPUT, "Input: Touch Up--^--^--^--^");
                this.inputAuthority.actionUp(e);
                break;
        }

        return true;
    }

    public void setModel(final GameModel gameModel) {
        this.model = gameModel;
    }
    public void setInputAuthority(final InputAuthority inputAuthority) {
        this.inputAuthority = inputAuthority;
    }
}
