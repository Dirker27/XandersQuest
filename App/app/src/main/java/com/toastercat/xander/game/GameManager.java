package com.toastercat.xander.game;

/**
 * Manages a game instance.
 *
 * Responsibilities include:
 *   - initialization + component assembly
 *   - dispatching world/ui threads
 *   - evaluating game state (win/lose/score)
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class GameManager {
    private static final String TAG_GAME = "[Game]";

    private GameModel model;

    public GameManager() {
        this.model = new GameModel();
    }

    public void startNewSession() {
        // Init World
        // Start Threads
    }

    public void endSession() {
        // End Threads
    }

    public GameModel getModel() {
        return this.model;
    }
}
