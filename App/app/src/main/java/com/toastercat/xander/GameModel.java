package com.toastercat.xander;

import android.graphics.Color;

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

        this.player = new Player();

        this.terrainObjects = new ArrayList<>();
        for (int t = 0; t < 10; t++) {
            GameObject obj = new GameObject();
            obj.setLocation(t*50f, 0);
            obj.setSpriteColor(Color.GRAY);
            this.terrainObjects.add(obj);
        }

        this.enemyObjects = new ArrayList<>();
        for (int e = 0; e < 5; e++) {
            GameObject obj = new GameObject();
            obj.setLocation(e*50f, 250);
            obj.setSpriteColor(Color.RED);
            this.enemyObjects.add(obj);
        }
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
