package com.toastercat.xander.game;

import android.os.SystemClock;
import android.util.Log;

import com.toastercat.xander.GameView;
import com.toastercat.xander.game.actor.Player;
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
    private InputAuthority inputAuthority;
    private GameView view;

    private Thread gameThread;
    private volatile boolean gameThreadRunning;

    public GameManager() {
        this.model = new GameModel();
        this.inputAuthority = new InputAuthority(this.model);

        this.gameThread = null;
        this.gameThreadRunning = false;
    }

    public void startNewSession() {
        // TODO: Init World

        this.startGameThread();
    }

    public void endSession() {
        this.stopGameThread();
    }

    private void startGameThread() {
        this.gameThreadRunning = true;

        this.gameThread = new Thread(new GameFrame());
        this.gameThread.start();
    }
    private void stopGameThread() {
        this.gameThreadRunning = false;

        if (this.gameThread != null) {
            try {
                this.gameThread.join();
            } catch (final InterruptedException e) {
                Log.e(TAG_GAME, "THREAD JOIN FAILED - the world clock did not stop");
            }
        }
    }

    /**
     * Facilitate Game World updates.
     */
    private class GameFrame implements Runnable {
        private static final long FRAME_RATE = 5000000; // [nanoseconds]

        private long lastTick;

        @Override
        public void run() {
            // Start the clock
            this.tick();

            while (gameThreadRunning) {
                long clockTick = this.getNanosElapsed();

                if (clockTick > FRAME_RATE) {
                    Log.v(LogTag.UPDATE_FRAME, String.format("Frame start with tick[%s].", clockTick));
                    this.tick();

                    Player p = model.getPlayer();
                    p.update(clockTick);

                    for (GameObject obj : model.getEnemyObjects()) {
                        obj.update(clockTick);
                    }

                    // Start Render Frame
                    view.draw();
                }
            }
        }

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
