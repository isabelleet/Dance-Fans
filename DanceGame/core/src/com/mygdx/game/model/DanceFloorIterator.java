package com.mygdx.game.model;

public class DanceFloorIterator implements IDancefloorIterator{

    private final int mapWidthInTiles;
    private final int mapHeightInTiles;
    private final DanceFloorTile[][] dfTiles;
    private int current = 0;

    DanceFloorIterator(DanceFloorTile[][] dfTiles){
        this.mapHeightInTiles = dfTiles.length;
        this.mapWidthInTiles = dfTiles[0].length;
        this.dfTiles = dfTiles;
    }

    @Override
    public DanceFloorTile getNext() {
        int row = current / mapWidthInTiles;
        int col = current % mapWidthInTiles;

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
        return current < mapHeightInTiles * mapWidthInTiles;
    }
}
