package com.mygdx.game.model;

public class DanceFan extends Dancer {
    private String spriteName = "";

    public void setSpriteName(String name) {
        this.spriteName = name;
    }

    // Constructor
    public DanceFan(String spriteName, Player player) {
        super(spriteName, player);
    }

    public void move(){
        //TODO: Add functionality that makes a dancer able to move
    }

}
