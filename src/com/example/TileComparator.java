package com.example;

import java.util.Comparator;

/**
 * Comparator for OkeyTile
 *
 * @author Ömer Faruk Bitikçioğlu
 */
public class TileComparator implements Comparator<OkeyTile> {
    @Override
    public int compare(OkeyTile o1, OkeyTile o2) {
        if (o1.getActualNum() < o2.getActualNum()) return -1;
        else if (o1.getActualNum() > o2.getActualNum()) return 1;
        else return 0;
    }
}
