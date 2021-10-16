package com.mygdx.game.model;
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

public class DanceFloor{

    private final DanceFloorTile[][] danceFloorTiles;

    // Map properties
    public final int tileWidth = 128;
    public final int tileHeight = 128;
    public final int mapWidthInTiles = 9;
    public final int mapHeightInTiles = 6;
    public final int tileSideLength = tileHeight;

    public DanceFloor() {
        this.danceFloorTiles = new DanceFloorTile[mapHeightInTiles + 1][mapWidthInTiles + 1];
    }

    private DanceFloor(DanceFloorTile[][] tiles){
        this.danceFloorTiles = tiles;
    }

    /**
     * Creates and returns a copy of the danceFloor.
     * @return A copy of the danceFloor.
     */
    public DanceFloor copy() {
        DanceFloorTile[][] copyTiles = new DanceFloorTile[mapHeightInTiles + 1][mapWidthInTiles + 1];
        for(int row = 0; row < danceFloorTiles.length; row++){
            for(int col = 0; col < danceFloorTiles[0].length; col++){
                copyTiles[row][col] = new DanceFloorTile(danceFloorTiles[row][col].getColor(), danceFloorTiles[row][col].getType());
            }
        }

        DanceFloor copy = new DanceFloor(copyTiles);

        return copy;
    }

    public Color getColor(Coordinates coords){
        return danceFloorTiles[coords.getY()][coords.getX()].getColor();
    }

    public Type getType(Coordinates coords){
        return danceFloorTiles[coords.getY()][coords.getX()].getType();
    }

    //TODO: Use this to test end of game conditions e.g.

    /**
     * Fills the DanceFloor with empty tiles.
     */
    public void initializeDanceFloor() {
        for(int row = 0; row < danceFloorTiles.length; row++) {
            for(int col = 0; col < danceFloorTiles[0].length; col++){
                this.danceFloorTiles[row][col] = new DanceFloorTile(Color.NONE, Type.EMPTY);
            }
        }
    }

    /**
     * Sets the occupant of a tile to a transparent tile.
     * @param coords which tile to update.
     */
    public void removeDancerFromTileIndex(Coordinates coords) {
        this.danceFloorTiles[coords.getY()][coords.getX()] = new DanceFloorTile(Color.NONE, Type.EMPTY);
    }

    /**
     * Sets a new dancer on a tile.
     * @param coords which tile to change.
     * @param dancer which dancer to place on the tile.
     */
    public void newDancerOnTile(Coordinates coords, Dancer dancer) {
        this.danceFloorTiles[coords.getY()][coords.getX()].setOccupant(dancer.getColor(), dancer.getType());
    }


}
