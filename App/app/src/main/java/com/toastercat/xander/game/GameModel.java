package com.toastercat.xander.game;

import android.graphics.Color;

import com.toastercat.xander.game.actor.Player;

import java.util.ArrayList;

/**
 * Data representation of a game in play.
 *
 * @author Dirk Hortensius [Dirker27]
 */
public class GameModel {

    // Game Objects
    private Player player;
    private ArrayList<GameObject> terrainObjects;
    private ArrayList<GameObject> enemyObjects;


    public GameModel () {
        // TODO: Replace hard-coded instantiation w/ game world factories

        Player p = new Player();
        p.setLocation(1, 1);
        this.player = p;

        this.terrainObjects = new ArrayList<>();
        for (int t = 0; t < 3; t++) {
            GameObject obj = new GameObject();
            obj.setLocation(t*10, t*2);
            obj.setSize(8, 1);
            obj.setSpriteColor(Color.GRAY);
            this.terrainObjects.add(obj);
        }

        this.enemyObjects = new ArrayList<>();
        for (int e = 0; e < 3; e++) {
            GameObject obj = new GameObject();
            obj.setLocation(10+e, 3+e);
            obj.setSpriteColor(Color.RED);
            this.enemyObjects.add(obj);
        }

        p = new Player();
        p.setLocation(7, 7);
        p.setScale(4);
        p.setSpriteColor(Color.YELLOW);
        this.enemyObjects.add(p);
    }


    //~ --------------------------------------------------------- ~//
    // ACCESSORS
    //~ --------------------------------------------------------- ~//

    // Player
    public Player getPlayer() {
        return this.player;
    }
    // Terrain
    public ArrayList<GameObject> getTerrainObjects() {
        return this.terrainObjects;
    }
    // Enemies
    public ArrayList<GameObject> getEnemyObjects() {
        return this.enemyObjects;
    }
}
