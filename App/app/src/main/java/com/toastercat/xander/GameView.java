package com.toastercat.xander;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.toastercat.xander.game.GameModel;
import com.toastercat.xander.game.GameObject;
import com.toastercat.xander.input.InputAuthority;
import com.toastercat.xander.util.LogTag;

/**
 * Custom 2D Sprite View to render game world
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class GameView extends SurfaceView {
    private static final String TAG_INPUT  = "[Input]";
    private static final float RENDER_SCALE = 50f;

    // Game Components
    private GameModel model;
    private InputAuthority inputAuthority;

    // Drawing Utilities
    private final Paint paint;
    private final SurfaceHolder surfaceHolder;
    private Rect bounds;

    /**
     * Constructor chain
     */
    public GameView(final Context context) {
        this(context, null);
    }
    public GameView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public GameView(final Context context, final AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        surfaceHolder = getHolder();
    }

    public void draw() {
        Log.v(LogTag.RENDER_FRAME, "Rendering View Frame...");

        final Canvas canvas;
        if (surfaceHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = surfaceHolder.lockCanvas();
        } else {
            Log.e(LogTag.RENDER_FRAME, "Cannot complete render pass - canvas is null.");
            return;
        }

        _extractBoundaries(canvas);
        if (bounds == null) {
            Log.e(LogTag.RENDER_FRAME, "Cannot complete render pass - no boundaries are defined.");
            return;
        }

        // Draw Background
        paint.setColor(Color.BLACK);
        canvas.drawRect(bounds, paint);

        //- Draw Game Objects ----------------------------=
        //
        if (model == null) {
            Log.v(LogTag.RENDER_FRAME, "No model set - nothing left to draw.");
            return;
        }
        // Terrain
        Log.v(LogTag.RENDER_FRAME, "Rendering Terrain");
        for (GameObject obj : this.model.getTerrainObjects()) {
            _renderGameObject(obj, canvas);
        }
        // Player
        Log.v(LogTag.RENDER_FRAME, "Rendering Player");
        _renderGameObject(this.model.getPlayer(), canvas);
        // Enemies
        Log.v(LogTag.RENDER_FRAME, "Rendering Enemies");
        for (GameObject obj : this.model.getEnemyObjects()) {
            _renderGameObject(obj, canvas);
        }

        surfaceHolder.unlockCanvasAndPost(canvas);
        Log.v(LogTag.RENDER_FRAME, "Render Pass Complete.");
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

        Log.v(LogTag.RENDER_FRAME,
                String.format("Draw at [%s, %s], [%s, %s]", left, right, top, bottom));
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
