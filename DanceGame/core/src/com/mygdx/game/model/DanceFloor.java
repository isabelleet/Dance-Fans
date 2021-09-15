package com.mygdx.game.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class DanceFloor {

    private DanceFloorTile[] danceFloorTiles;

    public DanceFloor(int x, int y){
        this.danceFloorTiles = new DanceFloorTile[x*y];
    }



    //TODO: Use this to test end of game conditions e.g.
    public DanceFloorTile[] initializeFullDanceFloor(int dancefloorWidth, int dancefloorHeight) {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {

            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer");
            else if (i == ((this.danceFloorTiles.length*dancefloorHeight) - dancefloorWidth - 2) )
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer");
            else if (i % 2 == 0)
                this.danceFloorTiles[i] = new DanceFloorTile("redDancer");
            else
                this.danceFloorTiles[i] = new DanceFloorTile("greenDancer");

        }
        return this.danceFloorTiles;
    }

    public DanceFloorTile[] initializeDanceFloor(int dancefloorWidth, int dancefloorHeight) {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {

            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer");
            else if (i == ((dancefloorWidth*dancefloorHeight) - dancefloorWidth - 2) )
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer");

            else
                this.danceFloorTiles[i] = new DanceFloorTile("transparent_tile");

        }
        return this.danceFloorTiles;
    }

}
