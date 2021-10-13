package com.mygdx.game.model;

import java.io.Serializable;

/**
 * DanceFan extends Dancer, and represents the dancers on the dance floor which are not controlled by the player
 * directly. There might be more functionality added in the future.
 *
 * Is used in PLayer.
 *
 * @author Jakob Persson
 * @author Johan Berg
 */

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
