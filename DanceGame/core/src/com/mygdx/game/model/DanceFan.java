package com.mygdx.game.model;


/**
 * DanceFan extends Dancer, and represents the dancers on the dance floor which are not controlled by the player
 * directly. There might be more functionality added in the future.
 *
 *
 * @author Jakob Persson
 * @author Johan Berg
 * @author Hedy Pettersson
 */

public class DanceFan extends Dancer {

    // Constructor
    public DanceFan(Color color, Type type, Coordinates coords) {
        super(color, type, coords);
    }

    public DanceFan(DanceFan danceFan){
        super(danceFan.getColor(), danceFan.getType(), danceFan.getCoordinates());
    }

}
