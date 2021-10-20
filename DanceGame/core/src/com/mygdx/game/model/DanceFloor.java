package com.mygdx.game.model;

import com.mygdx.game.Enums.Color;
import com.mygdx.game.Enums.PatternOccupant;
import com.mygdx.game.Enums.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * DanceFloor keeps track of the board which the game is played on. It can add / remove things on specific tiles.
 *
 * Is used by View, Model.
 *
 * Uses Color, Type, PatternOccupant., Coordinates, DanceDan, DanceFloorTile, FloorObject, MainDancer.
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
    protected DanceFloor() {
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
    protected void initializeDanceFloor() {
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
    protected DanceFloor copy() {
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
    protected void removeObjectFromTileIndex(Coordinates coords) {
        this.dfTiles[coords.getY()][coords.getX()] = new DanceFloorTile(Color.NONE, Type.EMPTY);
    }

    /**
     * Sets a new dancer on a tile.
     * @param coords which tile to change.
     * @param floorObject which object to place on the tile.
     */
    protected void newObjectOnTile(Coordinates coords, FloorObject floorObject) {
        this.dfTiles[coords.getY()][coords.getX()].setOccupant(floorObject);
    }

    /**
     * Sets a mainDancer on a tile.
     * @param mDancer the dancer to place.
     */
    protected void newObjectOnTile(MainDancer mDancer){
        int x = mDancer.getCoordinates().getX();
        int y = mDancer.getCoordinates().getY();

        this.dfTiles[y][x].setOccupant(mDancer);
    }

    /**
     * Adds transparent dance fans to the danceFloor according to a given pattern and in a certain location.
     * @param mdCoords coordinates of the mainDancer
     * @param transDF the type of transparent danceFan to draw
     * @param pattern the pattern which new fans are to be added
     */
    protected void addDFromPattern(Coordinates mdCoords, DanceFan transDF, PatternOccupant[][] pattern){
        Coordinates offset = offsetCoordinates(pattern);

        for (int row = 0; row < pattern.length; row++) {
            for (int col = 0; col < pattern[0].length; col++) {

                if (pattern[row][col] == PatternOccupant.DANCEFAN) {
                    int colInDanceFloor = mdCoords.getX() - offset.getX() + col;
                    int rowInDanceFloor = mdCoords.getY() - offset.getY() + row;
                    Coordinates danceFanCoord = new Coordinates(colInDanceFloor, rowInDanceFloor);

                    if (insideDanceFloor(danceFanCoord) && !(Type.MD == this.getType(danceFanCoord) || Type.MDDF == this.getType(danceFanCoord))){
                        this.newObjectOnTile(danceFanCoord, transDF);
                        this.newObjectOnTile(mdCoords, (new MainDancer(getColor(mdCoords), Type.MDDF, mdCoords)));
                    }
                }
            }
        }
    }

    /**
     * Gets a list of the coordinates for all transparent dancers in the dance floor.
     * @return a list with the coordinates of all the transparent dancers.
     */
    protected List<Coordinates> getTransparentCoordinates(){
        List<Coordinates> coordinatesList = new ArrayList<>();
        for(int row = 0; row < mapHeightInTiles; row++){
            for(int col = 0; col < mapWidthInTiles; col++){
                if(dfTiles[row][col].getType() == Type.TRANSDF){
                    Coordinates coordinates = new Coordinates(col, row);
                    coordinatesList.add(coordinates);
                }
            }
        }
        return coordinatesList;
    }

    /**
     * Counts the total amount of tiles which are occupied by something.
     * @return the tiótal amount of tiles which are occupied by.
     */
    protected int countTotalTiles(){
        int sum = 0;
        for(int row = 0; row < mapHeightInTiles; row++){
            for(int col = 0; col < mapWidthInTiles; col++){
                if(dfTiles[row][col].getType() != Type.EMPTY){
                    sum++;
                }
            }

        }
        return sum;
    }

    /**
     * Counts the total amount of tiles of a certain color.
     * @param color the color to be counted
     * @return the total amount of tiles of a certain color.
     */
    protected int countTiles(Color color){
        int sum = 0;
        for(int row = 0; row < mapHeightInTiles; row++){
            for(int col = 0; col < mapWidthInTiles; col++){
                if(dfTiles[row][col].getColor() == color){
                    sum++;
                }
            }
        }
        return sum;
    }

    /**
     * A boolean for checking if the given coordinates are inside of the danceFloor.
     * @param coords the coordinates to be checked
     * @return true if the coordinates are inside, otherwise false.
     */
    public boolean insideDanceFloor(Coordinates coords){
        return ( coords.getX()< mapWidthInTiles)
                &&   ( coords.getX() >= 0)
                &&   ( coords.getY() < mapHeightInTiles)
                &&   ( coords.getY() >= 0);
    }

    // private helper methods

    private Coordinates offsetCoordinates(PatternOccupant[][] pattern){
        int offsetX = 0;
        int offsetY = 0;

        for (int row = 0; row < pattern.length; row++) {
            for (int col = 0; col < pattern[0].length; col++) {
                if (pattern[row][col] == PatternOccupant.MAINDANCER) {
                    offsetX = col;
                    offsetY = row;
                    break;
                }
            }
        }

        return new Coordinates(offsetX, offsetY);
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
