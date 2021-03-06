package com.mygdx.game.model;

import com.mygdx.game.Enums.Color;
import com.mygdx.game.Enums.Type;

/**
 * DanceFan extends Dancer, and represents the dancers on the dance floor which are not controlled by the player
 * directly. There might be more functionality added in the future.
 * <p>
 * Is used by Player, DanceFloor.
 * <p>
 * Uses Color, Type, Coordinates, Dancer, FloorObject.
 *
 * @author Jakob Persson
 * @author Johan Berg
 * @author Hedy Pettersson
 */

public class DanceFan extends Dancer {

    /**
     * Constructor, needs a color and coordinates. Type is already decided since it is a danceFan.
     *
     * @param color
     * @param coords
     */
    DanceFan(Color color, Type type, Coordinates coords) {
        super(color, type, coords);
    }


}
