package com.untildawn.models.MapElements;

/*
    Every object in out game has a position specified by y and x values.
 */
public class Position {
    final int y;
    final int x;
    public Position(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
