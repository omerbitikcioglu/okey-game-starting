package com.example;

import java.util.ArrayList;
import java.util.Random;

/**
 * Helper class for some static methods.
 *
 * @author Ömer Faruk Bitikçioğlu
 */
public class Helper {

    /**
     * Shuffles the given array of tiles
     *
     * @param tiles The array of okey tiles.
     */
    public static void shuffleTiles(ArrayList<OkeyTile> tiles) {
        Random rand = new Random();
        int randIndex;
        for (int i = 0; i < tiles.size(); i++) {
            randIndex = rand.nextInt(tiles.size());
            // Swap the tile with the tile at random index.
            OkeyTile tempTile = tiles.get(i);
            tiles.set(i, tiles.get(randIndex));
            tiles.set(randIndex, tempTile);
        }
    }


}
