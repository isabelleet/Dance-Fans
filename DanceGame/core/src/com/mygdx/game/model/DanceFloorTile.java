package com.mygdx.game.model;

import com.mygdx.game.Enums.Color;
import com.mygdx.game.Enums.Type;

/**
 * DanceFloorTile keeps track of what is on that specific tile. Can change and tell other classes what is
 * currently on it. The thing on the tile kept track of using Color and Type.
 * <p>
 * Is used by DanceFloor.
 * <p>
 * Uses Color and Type.
 *
 * @author Jakob Persson
 * @author Joar Granström
 * @author Johan Berg
 * @author Hedy Pettersson
 */

public class DanceFloorTile {
    private Color color;
    private Type type;

    /**
     * Creates a new DanceFloorTile with an occupant.
     *
     * @param type the name of the occupant.
     */
    DanceFloorTile(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    Type getType() {
        return type;
    }

    Color getColor() {
        return color;
    }

    /**
     * Gives a floorObject to the tile.
     *
     * @param floorObject the floorObject that is occupying the tile.
     */
    void setOccupant(FloorObject floorObject) {
        this.color = floorObject.getColor();
        this.type = floorObject.getType();
    }


}
