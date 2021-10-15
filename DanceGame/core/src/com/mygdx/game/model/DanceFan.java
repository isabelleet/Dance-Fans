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

public class DanceFan extends Dancer {
    private Type type;

    // Constructor
    public DanceFan(Color color, Type type) {
        super(color, type);
    }

    public void move() {
        //TODO: Add functionality that makes a dancer able to move
    }

}
