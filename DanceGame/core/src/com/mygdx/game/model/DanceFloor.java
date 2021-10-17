package com.mygdx.game.model;
/**
 * DanceFloor keeps track of the board which the game is played on. It can add / remove things on specific tiles.
 *
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Hedy Pettersson
 * @author Johan Berg
 * @author Isabelle Ermeryd Tankred
 */

public class DanceFloor{

    private final DanceFloorTile[][] dfTiles;

    // Map properties
    public final int tileWidth = 128;
    public final int tileHeight = 128;
    // for now the height and width of the danceFloor is pre-determined. In the future this might change.
    public final int mapWidthInTiles = 9;
    public final int mapHeightInTiles = 6;
    public final int tileSideLength = tileHeight;

    // Constructor
    public DanceFloor() {
        this.dfTiles = new DanceFloorTile[mapHeightInTiles + 1][mapWidthInTiles + 1];
        initializeDanceFloor();
    }

    // Constructor used to make copies of danceFloor.
    private DanceFloor(DanceFloorTile[][] tiles){
        this.dfTiles = tiles;
    }

    /**
     * Fills the DanceFloor with empty tiles.
     */
    private void initializeDanceFloor() {
        for(int row = 0; row < dfTiles.length; row++) {
            for(int col = 0; col < dfTiles[0].length; col++){
                this.dfTiles[row][col] = new DanceFloorTile(Color.NONE, Type.EMPTY);
            }
        }
    }

    /**
     * Creates and returns a copy of the danceFloor. Used to avoid pointing to the same object.
     * @return A copy of the danceFloor.
     */
    public DanceFloor copy() {
        DanceFloorTile[][] copyTiles = new DanceFloorTile[mapHeightInTiles + 1][mapWidthInTiles + 1];
        for(int row = 0; row < dfTiles.length; row++){
            for(int col = 0; col < dfTiles[0].length; col++){
                copyTiles[row][col] = new DanceFloorTile(dfTiles[row][col].getColor(), dfTiles[row][col].getType());
            }
        }

        return new DanceFloor(copyTiles);
    }

    /**
     * Sets the color and type of the tile to NONE and EMPTY, which is how the empty tile is represented.
     * @param coords which tile to update.
     */
    public void removeObjectFromTileIndex(Coordinates coords) {
        this.dfTiles[coords.getY()][coords.getX()] = new DanceFloorTile(Color.NONE, Type.EMPTY);
    }

    /**
     * Sets a new dancer on a tile.
     * @param coords which tile to change.
     * @param dancer which dancer to place on the tile.
     */
    public void newDancerOnTile(Coordinates coords, Dancer dancer) {
        this.dfTiles[coords.getY()][coords.getX()].setOccupant(dancer.getColor(), dancer.getType());
    }

    /**
     * Sets a mainDancer on a tile.
     * @param mDancer the dancer to place.
     */
    public void newDancerOnTile(MainDancer mDancer){
        int x = mDancer.getCoordinates().getX();
        int y = mDancer.getCoordinates().getY();

        this.dfTiles[y][x].setOccupant(mDancer.getColor(), mDancer.getType());
    }

    // Law of demeter handling

    /**
     * Returns what color the object on a specific tile has.
     * @param coords is the coordinates of the specific tile.
     * @return the color of the object on a specific tile.
     */
    public Color getColor(Coordinates coords){
        return dfTiles[coords.getY()][coords.getX()].getColor();
    }

    /**
     * Returns what type the object on a specific tile has.
     * @param coords is the coordinates of the specific tile.
     * @return the type of the object on a specific tile.
     */
    public Type getType(Coordinates coords){
        return dfTiles[coords.getY()][coords.getX()].getType();
    }
}
