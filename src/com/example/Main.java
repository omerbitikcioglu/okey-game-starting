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
        for (OkeyTile tile : tiles
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
        int jokerActualNum = indicatorTile.getActualNum() + 1;
        if (jokerActualNum == 14) jokerActualNum = 1;
        TileColor jokerColor = indicatorTile.getColor();
        System.out.println("Joker tile:\n" + jokerColor + " " + jokerActualNum);
        OkeyTile fakeJokey = new OkeyTile(jokerActualNum, jokerColor);

        // Change colors and actual values of the fake jokers
        // Also indicate joker tiles as changing their isJoker attribute
        for (int i = 0; i < tiles.size(); ++i) {
            if (tiles.get(i).getRepresentedNum() == 52) {
                tiles.set(i, fakeJokey);
            } else if (tiles.get(i).equals(fakeJokey)) {
                tiles.get(i).setJoker();
            }
        }

        // Print actual tiles
        System.out.println("Actual tiles:");
        for (OkeyTile tile : tiles
        ) {
            if (tile.isJoker()) {
                System.out.print("J-");
            } else if (tile.getRepresentedNum() == 52) {
                System.out.print("FJ-");
            }
            System.out.print(tile.getColor() + " " + tile.getActualNum() + ", ");
        }
        System.out.println();

        // Deal the tiles
        for (int i = 0; i < 14; ++i) {
            player1_tiles.add(tiles.remove(tiles.size() - 1));
            player2_tiles.add(tiles.remove(tiles.size() - 1));
            player3_tiles.add(tiles.remove(tiles.size() - 1));
            player4_tiles.add(tiles.remove(tiles.size() - 1));
        }
        int randPlayer = rand.nextInt(4);
        switch (randPlayer) {
            case 0:
                player1_tiles.add(tiles.remove(tiles.size() - 1));
                break;
            case 1:
                player2_tiles.add(tiles.remove(tiles.size() - 1));
                break;
            case 2:
                player3_tiles.add(tiles.remove(tiles.size() - 1));
                break;
            case 3:
                player4_tiles.add(tiles.remove(tiles.size() - 1));
                break;
        }

        // Print hands
        System.out.print("Hands:\nPlayer1: ");
        for (OkeyTile tile : player1_tiles) System.out.print(tile + ", ");
        System.out.print("\nPlayer2: ");
        for (OkeyTile tile : player2_tiles) System.out.print(tile + ", ");
        System.out.print("\nPlayer3: ");
        for (OkeyTile tile : player3_tiles) System.out.print(tile + ", ");
        System.out.print("\nPlayer4: ");
        for (OkeyTile tile : player4_tiles) System.out.print(tile + ", ");

    }
}
