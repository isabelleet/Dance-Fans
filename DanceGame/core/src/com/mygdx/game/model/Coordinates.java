package com.mygdx.game.model;

/**
 * Simple coordinates
 * @author Hedy Pettersson
 */
public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x , int y){
        this.x = x;
        this.y = y;
    }

    public Coordinates(Coordinates coordinates){
        this.x = coordinates.getX();
        this.y = coordinates.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
