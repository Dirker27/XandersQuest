package com.toastercat.xander.game;

import com.toastercat.xander.input.InputAuthority;

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
    private InputAuthority inputAuthority;

    public GameManager() {
        this.model = new GameModel();
        this.inputAuthority = new InputAuthority(this.model);
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
    public InputAuthority getInputAuthority() {
        return this.inputAuthority;
    }
}
