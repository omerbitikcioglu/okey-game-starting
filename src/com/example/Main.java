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
            System.out.print(representedNum + ", ");
        }
        System.out.println();

        // Shuffle the array.
        System.out.println("Shuffled tiles:");
        Helper.shuffleTiles(tiles);
        for (OkeyTile tile : tiles
        ) {
            System.out.print(tile.getRepresentedNum() + ", ");
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
        System.out.println("Indicator tile:\n" + indicatorTile);

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
        System.out.println("Actual tiles:\n" + tiles);

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
        System.out.println("Hands:\nPlayer1: " + player1_tiles);
        System.out.println("Player2: " + player2_tiles);
        System.out.println("Player3: " + player3_tiles);
        System.out.println("Player4: " + player4_tiles);

        // Find the best hand
        int bestHand = findBestHand(player1_tiles, player2_tiles, player3_tiles, player4_tiles);
        System.out.println("\nPlayer " + bestHand + " has the best hand!");

    }

    private static int findBestHand(ArrayList<OkeyTile> player1_tiles, ArrayList<OkeyTile> player2_tiles, ArrayList<OkeyTile> player3_tiles, ArrayList<OkeyTile> player4_tiles) {

        // Sort the tiles by their actual numbers
        TileComparator comparator = new TileComparator();
        player1_tiles.sort(comparator);
        player2_tiles.sort(comparator);
        player3_tiles.sort(comparator);
        player4_tiles.sort(comparator);

        // Print sorted hands
        System.out.println("Sorted hands:");
        System.out.println(player1_tiles);
        System.out.println(player2_tiles);
        System.out.println(player3_tiles);
        System.out.println(player4_tiles);

        int player1_point = findPlayerPoint(player1_tiles);
        int player2_point = findPlayerPoint(player2_tiles);
        int player3_point = findPlayerPoint(player3_tiles);
        int player4_point = findPlayerPoint(player4_tiles);

        int maxPoint = player1_point;
        if (maxPoint < player2_point) maxPoint = player2_point;
        else if (maxPoint < player3_point) maxPoint = player3_point;
        else if (maxPoint < player4_point) maxPoint = player4_point;

        if (maxPoint == player1_point) return 1;
        if (maxPoint == player2_point) return 2;
        if (maxPoint == player3_point) return 3;
        return 4;
    }

    private static int findPlayerPoint(ArrayList<OkeyTile> playerHand) {
        int playerPoint = 0;
        for (int i = 0; i < playerHand.size() - 1; ++i) {
            // Player has the same tiles
            if (playerHand.get(i).equals(playerHand.get(i + 1)) &&
                    !playerHand.get(i).isJoker() &&
                    !playerHand.get(i + 1).isJoker()
            ) {
                playerPoint += 2;
            }

            // Find groups of sequential tiles
            boolean flag = true;
            int sequentialTiles = 0;
            int j;
            for (j = i; j < playerHand.size() - 1 && flag; ++j) {
                OkeyTile tile = playerHand.get(j);
                OkeyTile nextTile = playerHand.get(j + 1);
                if (!((tile.getColor() == nextTile.getColor() &&
                        tile.getActualNum() + 1 == nextTile.getActualNum()) ||
                        tile.isJoker() ||
                        nextTile.isJoker())) {
                    flag = false;
                }
            }
            sequentialTiles = j - i;
            if (sequentialTiles >= 2) playerPoint += Math.pow(sequentialTiles, 2);

            // Find color collections of the same number
            flag = true;
            int colorGroup = 0;
            for (j = i; j < playerHand.size() - 1 && flag; ++j) {
                OkeyTile tile = playerHand.get(j);
                OkeyTile nextTile = playerHand.get(j + 1);
                if (!((tile.getColor() != nextTile.getColor() && tile.getActualNum() == nextTile.getActualNum()) ||
                        tile.isJoker() ||
                        nextTile.isJoker())) {
                    flag = false;
                }
            }
            colorGroup = j - i;
            if (colorGroup >= 2) playerPoint += Math.pow(colorGroup, 2);

            if (playerHand.get(i).isJoker()) playerPoint += 5;

        }
        return playerPoint;
    }
}
