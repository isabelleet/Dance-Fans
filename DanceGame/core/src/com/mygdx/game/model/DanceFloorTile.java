package com.mygdx.game.model;

public class DanceFloorTile {
    public Dancer occupant;

    public String getOccupantName() {
        //occupant = new Dancer().setSpriteName(spriteName);
        return occupant.getSpriteName();
    }

    public void setOccupant(String spriteName) {
        //occupant = new Dancer().setSpriteName(spriteName);
        //this.occupant = this.occupant.setSpriteName(spriteName);
    }

    // Constructor
    public DanceFloorTile(String spriteName, Player player) {
        this.occupant = new DanceFan(spriteName, player); // TODO: replace with empty tile or something
    }
}
