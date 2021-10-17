package com.mygdx.game.model;

import com.mygdx.game.model.Enums.Color;
import com.mygdx.game.model.Enums.Type;

/**
 * Game objects which can be on tiles. Needs to have a color, a type and coordinates. Extend for new classes that can
 * be on the dance floor.
 *
 * Is used by DanceFloor.
 *
 * Uses Color, Type and Coordinates.
 *
 * @author Hedy Pettersson
 */
public abstract class FloorObject {
    private final Color color;
    private final Type type;
    private Coordinates coordinates;

    public FloorObject(Color color, Type type, Coordinates coords){
        this.color = color;
        this.type = type;
        this.coordinates = coords;
    }

    /**
     * Sets the coordinates of the object.
     * @param coordinates which index the dancer should be set to.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = new Coordinates(coordinates);
    }

    /**
     * Getter for the objects coordinates.
     * @return the coordinates of the object.
     */
    public Coordinates getCoordinates() {
        return new Coordinates(coordinates);
    }

    /**
     * Getter for the type of the object.
     * @return the type of the object.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Getter for the color of the object.
     * @return the color of the object.
     */
    public Color getColor() {
        return this.color;
    }

}
