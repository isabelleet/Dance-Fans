package com.mygdx.game.model;

public class DanceFan extends ObjectOnFloor {
    private String spriteName = "";

    public void setSpriteName(String name) {
        this.spriteName = name;
    }

    // Constructor
    public DanceFan(String spriteName) {
        super(spriteName);
    }

    public void move(){
        //TODO: Add functionality that makes a dancer able to move
    }

}
