package com.mygdx.game.model;

public class DanceFloorIterator implements IDancefloorIterator{

    private final int mapWidthInTiles;
    private final int mapHeightInTiles;
    private final DanceFloorTile[][] dfTiles;
    private int current = 0;

    DanceFloorIterator(int height, int width, DanceFloorTile[][] dfTiles){
        this.mapHeightInTiles = height;
        this.mapWidthInTiles = width;
        this.dfTiles = dfTiles;
    }

    @Override
    public DanceFloorTile getNext() {
        int col = current / mapWidthInTiles;
        int row = current % mapWidthInTiles;

        if(hasMore()){
            current++;
            return dfTiles[row][col];
        }
        else {
            return null;
        }
    }

    @Override
    public boolean hasMore() {
        return current < mapHeightInTiles*mapWidthInTiles - 1;
    }
}
