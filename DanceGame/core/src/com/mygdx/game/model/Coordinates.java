package com.mygdx.game.model;

/**
 * Simple coordinates to help place and move / navigate dancers and things on the dance floor.
 *
 * Is used by Dancer, DanceFan, DanceFloor, FloorObject, MainDancer, Model, Player, and View.
 *
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
