package com.mygdx.game.model;

import java.io.Serializable;

public class DanceFloorTile implements Serializable {
    public Dancer occupant;

    /**
     * Getter for the name of the sprite on the tile.
     * @return A string with the name of the occupant.
     */
    public String getOccupantName() {
        //occupant = new Dancer().setSpriteName(spriteName);
        return occupant.getSpriteName();
    }

    /**
     * Gives a Dancer to the tile.
     * @param dancer the Dancer that is occupying the tile.
     */
    public void setOccupant(Dancer dancer) {
        this.occupant = dancer;
        //this.occupant = this.occupant.setSpriteName(spriteName);
    }

    // Constructor

    /**
     * Creates a new DanceFloorTile with an occupant.
     * @param spriteName the name of the occupant.
     */
    public DanceFloorTile(String spriteName) {
        this.occupant = new DanceFan(spriteName); // TODO: replace with empty tile or something
    }
}
