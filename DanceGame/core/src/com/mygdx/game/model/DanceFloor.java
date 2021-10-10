package com.mygdx.game.model;


import java.io.*;

public class DanceFloor implements Serializable {

    public DanceFloorTile[] danceFloorTiles;
    //TODO: maybe remove if not used?
    private Enum<PlayerTurnSlot> whichPlayersTurnItIs;
    // Map

    // Map properties
    public int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            tileSideLength;

    public DanceFloor(Enum<PlayerTurnSlot> whichPlayersTurnItIs) {
        //Kanske flytta en del av detta till View, då det har med View att göra?
        this.whichPlayersTurnItIs = whichPlayersTurnItIs;
        // Used this guide: http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
        // Code: https://github.com/angelnavarro/Gdx-MyExamples/blob/master/gdx-tiled-draw-map/core/src/com/pixnbgames/tiled/draw_map/MyGdxTiledGame.java

        // Read properties



        tileWidth = 128;
        tileHeight = 128;

        tileSideLength = tileHeight;
        mapWidthInTiles = 9;
        mapHeightInTiles = 6;
        this.danceFloorTiles = new DanceFloorTile[mapHeightInTiles * mapWidthInTiles];
    }

    // this is used to make a copy of a dancefloor, for our previews
    // https://stackoverflow.com/a/9834683
    // https://howtodoinjava.com/java/serialization/how-to-do-deep-cloning-using-in-memory-serialization-in-java/

    /**
     * Creates a new object with the same properties as the one that should be copied, to avoid weird pointer errors.
     * @return A new DanceFloor with the properties of an old one.
     * @throws Exception if something goes wrong while copying.
     */
    public DanceFloor deepCopy() throws Exception {
        //Serialization of object
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(this);

        //De-serialization of object
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        DanceFloor copied = (DanceFloor) in.readObject();

        //Verify that object is not corrupt

        //validateNameParts(fName);
        //validateNameParts(lName);

        return copied;
    }


    //TODO: Use this to test end of game conditions e.g.
   /* public DanceFloorTile[] initializeFullDanceFloor(int dancefloorWidth, int dancefloorHeight) {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {

            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer");
            else if (i == ((this.danceFloorTiles.length * dancefloorHeight) - dancefloorWidth - 2))
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer");
            else if (i % 2 == 0)
                this.danceFloorTiles[i] = new DanceFloorTile("redDancer");
            else
                this.danceFloorTiles[i] = new DanceFloorTile("greenDancer");

        }
        return this.danceFloorTiles;
    } */

   /* public DanceFloorTile[] initializeDanceFloorWithStartPositions() {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {

            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer");
            else if (i == ((this.mapWidthInTiles * this.mapHeightInTiles) - this.mapWidthInTiles - 2))
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer");

            else
                this.danceFloorTiles[i] = new DanceFloorTile("transparent_tile");

        }
        return this.danceFloorTiles;
    } */

    /**
     * Fills the DanceFloor with empty tiles.
     * @return an array containing all tiles.
     */
    public DanceFloorTile[] initializeDanceFloor() {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {

/*            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer");
            else if (i == ((this.mapWidthInTiles*this.mapHeightInTiles) - this.mapWidthInTiles - 2) )
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer");

            else
*/

            //TODO: an empty tiles shouldn't be a dancer, fix later. Prob. dancer just one case of "object on floor"
            // added player1 here just for the time being if it doesn't make sense
            this.danceFloorTiles[i] = new DanceFloorTile("transparent_tile");

        }
        return this.danceFloorTiles;
    }

    /**
     * Sets the occupant of a tile to a transparent tile.
     * @param tileIndex which tile to update.
     */
    public void removeDancerFromTileIndex(int tileIndex) {
        //TODO: prob not just have empty string to mean no dancer...
        this.danceFloorTiles[tileIndex] = new DanceFloorTile("transparent_tile");
    }

    //TODO: not sure if should update Dance Fan, or just replace it.
    // Depends on if Dance Fans have any data in them we want to keep even if they change which Main Dancer they're fan of

    /**
     * Sets a new dancer on a tile.
     * @param tileIndex which tile to change.
     * @param dancer which dancer to place on the tile.
     */
    public void newDancerOnTile(int tileIndex, Dancer dancer) {
        this.danceFloorTiles[tileIndex].setOccupant(dancer);
    }


}
