package com.mygdx.game.model;

public class DanceFloorTile {
    private Dancer occupant;

    public String getOccupantName() {
        //occupant = new Dancer().setSpriteName(spriteName);
        return occupant.getSpriteName();
    }

    public void setOccupant(String spriteName) {
        //occupant = new Dancer().setSpriteName(spriteName);
        //this.occupant = this.occupant.setSpriteName(spriteName);
    }

    // Constructor
    public DanceFloorTile(String spriteName) {
        this.occupant = new DanceFan(spriteName); // TODO: replace with empty tile or something
    }
}