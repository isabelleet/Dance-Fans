package com.mygdx.game.model;
import java.io.*;

/**
 * DanceFloor keeps track of the board which the game is played on. It can add things to specific tiles.
 *
 * Is used in Model.
 *
 * Uses DanceFloorTile, Dancer,
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Hedy Pettersson
 * @author Johan Berg
 * @author Isabelle Ermeryd Tankred
 */

public class DanceFloor implements Serializable {

    private DanceFloorTile[] danceFloorTiles;


    // Map properties
    public final int tileWidth = 128;
    public final int tileHeight = 128;
    public final int mapWidthInTiles = 9;
    public final int mapHeightInTiles = 6;
    public final int tileSideLength = tileHeight;

    public DanceFloor() {
        this.danceFloorTiles = new DanceFloorTile[mapHeightInTiles * mapWidthInTiles];
    }

    private DanceFloor(DanceFloorTile[] tiles){
        this.danceFloorTiles = tiles;
    }

    // this is used to make a copy of a dancefloor, for our previews
    // https://stackoverflow.com/a/9834683
    // https://howtodoinjava.com/java/serialization/how-to-do-deep-cloning-using-in-memory-serialization-in-java/

    /**
     * Creates a new object with the same properties as the one that should be copied, to avoid weird pointer errors.
     * @return A new DanceFloor with the properties of an old one.
     */
    public DanceFloor deepCopy() {

        DanceFloorTile[] copyTiles = new DanceFloorTile[danceFloorTiles.length];
        for(int i = 0; i < danceFloorTiles.length; i++){
            copyTiles[i] = new DanceFloorTile(danceFloorTiles[i].getColor(), danceFloorTiles[i].getType());
        }

        DanceFloor copy = new DanceFloor(copyTiles);

        return copy;
    }

    public Color getColor(int i){
        return danceFloorTiles[i].getColor();
    }

    public Type getType(int i ){
        return danceFloorTiles[i].getType();
    }

    //TODO: Use this to test end of game conditions e.g.

    /**
     * Fills the DanceFloor with empty tiles.
     * @return an array containing all tiles.
     */
    public DanceFloorTile[] initializeDanceFloor() {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {
            this.danceFloorTiles[i] = new DanceFloorTile(Color.NONE, Type.EMPTY);
        }
        return this.danceFloorTiles;
    }

    /**
     * Sets the occupant of a tile to a transparent tile.
     * @param tileIndex which tile to update.
     */
    public void removeDancerFromTileIndex(int tileIndex) {
        this.danceFloorTiles[tileIndex] = new DanceFloorTile(Color.NONE, Type.EMPTY);
    }

    //TODO: not sure if should update Dance Fan, or just replace it.
    // Depends on if Dance Fans have any data in them we want to keep even if they change which Main Dancer they're fan of

    /**
     * Sets a new dancer on a tile.
     * @param tileIndex which tile to change.
     * @param dancer which dancer to place on the tile.
     */
    public void newDancerOnTile(int tileIndex, Dancer dancer) {
        this.danceFloorTiles[tileIndex].setOccupant(dancer.getColor(), dancer.getType());
    }


}
