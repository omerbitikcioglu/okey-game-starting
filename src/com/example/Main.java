package com.example;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        // Tiles on the table
        ArrayList<OkeyTile> tiles = new ArrayList<>(106);

        // Tiles of the players
        ArrayList<OkeyTile> player1_tiles = new ArrayList<>(15);
        ArrayList<OkeyTile> player2_tiles = new ArrayList<>(15);
        ArrayList<OkeyTile> player3_tiles = new ArrayList<>(15);
        ArrayList<OkeyTile> player4_tiles = new ArrayList<>(15);

        // Fill the array with tiles
        System.out.println("Tiles:");
        for (int i = 0; i < 106; ++i) {
            int representedNum = i % 53;
            tiles.add(new OkeyTile(representedNum));
            System.out.print(representedNum + " ");
        }
        System.out.println();

        // Shuffle the array.
        System.out.println("Shuffled tiles:");
        Helper.shuffleTiles(tiles);
        for (OkeyTile tile: tiles
             ) {
            System.out.print(tile.getRepresentedNum() + " ");
        }
        System.out.println();

        // Select indicator tile
        Random rand = new Random();
        int indicatorIndex = rand.nextInt(106);
        OkeyTile indicatorTile = tiles.remove(indicatorIndex);
        while (indicatorTile.getColor() == TileColor.FAKE_OKEY) {
            tiles.add(indicatorTile);
            indicatorIndex = rand.nextInt(106);
            indicatorTile = tiles.remove(indicatorIndex);
        }
        System.out.println("Indicator tile:\n" + indicatorTile.getColor() + " " + indicatorTile.getActualNum());

        // Select joker tile
        int jokerActualNum = indicatorTile.getActualNum()+1;
        if (jokerActualNum == 14) jokerActualNum = 1;
        TileColor jokerColor = indicatorTile.getColor();
        System.out.println("Joker tile:\n" + jokerColor + " " + jokerActualNum);

        // Change colors and actual values of the fake jokers
        for (int i = 0, count=0; i < tiles.size() && count!=2; ++i) {
            if (tiles.get(i).getRepresentedNum() == 52) {
                count++;
                tiles.set(i, new OkeyTile(jokerActualNum, jokerColor));
            }
        }

        // Print actual tiles
        System.out.println("Actual tiles:");
        for (OkeyTile tile: tiles
        ) {
            System.out.print(tile.getColor() + " " + tile.getActualNum() + ", ");
        }
    }
}
