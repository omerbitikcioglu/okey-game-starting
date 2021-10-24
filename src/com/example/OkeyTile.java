package com.example;

/**
 * Class of the Okey tiles.
 *
 * @author Ömer Faruk Bitikçioğlu
 */
public class OkeyTile {
    /**
     * The number represents the tile.
     * Yellow tiles represented as 0 to 12,
     * Blue: 13 to 25,
     * Black: 26 to 38,
     * Red: 39 to 51
     * Fake okey is represented as 52.
     */
    private final int representedNum;

    /**
     * Actual number of the tile.
     * It can be 1 to 13.
     */
    private final int actualNum;

    /**
     * Color of the tile.
     * It can be yellow, blue, black, red or fake_okey
     * if it is the case.
     */
    private final TileColor color;

    public OkeyTile(int representedNum) {
        this.representedNum = representedNum;
        this.actualNum = calculateActualNum(representedNum);
        this.color = calculateColor(representedNum);
    }

    private TileColor calculateColor(int representedNum) {
        if (representedNum == 52) return TileColor.FAKE_OKEY;

        int colorNum = representedNum / 13;
        switch (colorNum) {
            case 0: return TileColor.YELLOW;
            case 1: return TileColor.BLUE;
            case 2: return TileColor.BLACK;
            case 3: return TileColor.RED;
            default:
                throw new IllegalStateException("Unexpected value: " + colorNum);
        }
    }

    private int calculateActualNum(int representedNum) {
        return (representedNum % 13) + 1;
    }

    /**
     * Getter for representedNum.
     * @return the number represents the tile.
     */
    public int getRepresentedNum() {
        return representedNum;
    }

    /**
     * Getter for actualNum.
     * @return the actual number of the tile.
     */
    public int getActualNum() {
        return actualNum;
    }

    public TileColor getColor() {
        return color;
    }
}
