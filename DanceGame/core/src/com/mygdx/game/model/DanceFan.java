package com.mygdx.game.model;

import java.io.Serializable;

public class DanceFan extends Dancer implements Serializable {
    private String spriteName = "";

    public void setSpriteName(String name) {
        this.spriteName = name;
    }

    // Constructor
    public DanceFan(String spriteName) {
        super(spriteName);
    }

    public void move() {
        //TODO: Add functionality that makes a dancer able to move
    }

}
