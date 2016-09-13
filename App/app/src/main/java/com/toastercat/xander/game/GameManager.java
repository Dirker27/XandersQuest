package com.toastercat.xander.game;

import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import com.toastercat.xander.GameView;
import com.toastercat.xander.game.actor.Player;
import com.toastercat.xander.game.object.GameObject;
import com.toastercat.xander.input.InputAuthority;
import com.toastercat.xander.util.LogTag;

/**
 * Manages a game instance.
 *
 * Responsibilities include:
 *   - initialization + component assembly
 *   - dispatching world update thread
 *   - evaluating game state (win/lose/score)
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class GameManager {
    private static final String TAG_GAME = "[Game]";

    private GameModel model;
    private GameView view;
    private InputAuthority inputAuthority;

    private Thread gameThread;
    private volatile boolean _runGameThread;

    private Thread renderThread;
    private volatile boolean _runRenderThread;

    public GameManager() {
        this.model = new GameModel();
        this.inputAuthority = new InputAuthority(this.model);

        //- Init threads as INACTIVE ---------------------=
        //
        this.gameThread = null;
        this._runGameThread = false;
        //
        this.renderThread = null;
        this._runGameThread = false;
    }

    public void startNewSession() {
        // TODO: Init World

        this.startThreads();
    }

    public void endSession() {
        this.closeThreads();
    }

    private void startThreads() {
        this._runGameThread = true;
        this.gameThread = new Thread(new GameFrame());
        this.gameThread.start();

        this._runRenderThread = true;
        this.renderThread = new Thread(new RenderFrame());
        this.renderThread.start();
    }
    private void closeThreads() {
        // Send shutdown
        this._runGameThread = false;
        this._runRenderThread = false;

        // Finish current frame + re-join UI thread
        if (this.gameThread != null) {
            try {
                this.gameThread.join();
                this.renderThread.join();
            } catch (final InterruptedException e) {
                Log.e(TAG_GAME, "THREAD JOIN FAILED - the world is spinning out of control!");
            }
        }
    }

    /**
     * Facilitate Game World updates.
     */
    private class GameFrame implements Runnable {
        private static final long FRAME_INTERVAL = 2500000; // [nanoseconds]

        private long lastTick;

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);

            // Start the clock
            this.tick();

            while (_runGameThread) {
                long clockTick = this.getNanosElapsed();

                if (clockTick > FRAME_INTERVAL) {
                    Log.v(LogTag.UPDATE_FRAME, String.format("GameFrame start with tick[%s].", clockTick));
                    this.tick();

                    // Update Player
                    Player p = model.getPlayer();
                    p.update(clockTick);

                    // Update Enemies
                    for (GameObject obj : model.getEnemyObjects()) {
                        obj.update(clockTick);
                    }

                    // Detect + Apply Floor Collision
                    for (GameObject terrain : model.getTerrainObjects()) {
                        if (terrain.detectCollision(p)) {
                            p.onCollide(terrain);
                            break;
                        }
                    }
                }
            }
        }

        /*
         * TODO: Move to shared superclass (code repetition)
         */
        private void tick() {
            if (android.os.Build.VERSION.SDK_INT > 17) {
                lastTick = SystemClock.elapsedRealtimeNanos();
            }
            else {
                lastTick = System.nanoTime();
            }
        }
        private long getNanosElapsed() {
            final long currentTick;
            if (android.os.Build.VERSION.SDK_INT > 17) {
                currentTick = SystemClock.elapsedRealtimeNanos();
            } else {
                currentTick = System.nanoTime();
            }

            return currentTick - lastTick;
        }
    }

    /**
     * Facilitate render passes.
     */
    private class RenderFrame implements Runnable {
        private static final long FRAME_INTERVAL = 5000000; // [nanoseconds]

        private long lastTick;

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_MORE_FAVORABLE);

            // Start the clock
            this.tick();

            while (_runRenderThread) {
                long clockTick = this.getNanosElapsed();

                if (clockTick > FRAME_INTERVAL) {
                    Log.v(LogTag.RENDER_FRAME, String.format("RenderFrame start with tick[%s].", clockTick));
                    this.tick();

                    // Start Render Frame
                    view.draw();
                }
            }
        }

        /*
         * TODO: Move to shared superclass (code repetition)
         */
        private void tick() {
            if (android.os.Build.VERSION.SDK_INT > 17) {
                lastTick = SystemClock.elapsedRealtimeNanos();
            }
            else {
                lastTick = System.nanoTime();
            }
        }
        private long getNanosElapsed() {
            final long currentTick;
            if (android.os.Build.VERSION.SDK_INT > 17) {
                currentTick = SystemClock.elapsedRealtimeNanos();
            } else {
                currentTick = System.nanoTime();
            }

            return currentTick - lastTick;
        }
    }

    public GameModel getModel() {
        return this.model;
    }
    public InputAuthority getInputAuthority() {
        return this.inputAuthority;
    }

    public void setView(final GameView view) {
        this.view = view;
    }
}
