package com.mygdx.game.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class DanceFloor {

    public DanceFloorTile[] danceFloorTiles;
    // Map
    public TiledMap map;
    private AssetManager manager;

    // Map properties
    public int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            tileSideLength,
            mapWidthInPixels, mapHeightInPixels;

    public DanceFloor(){
        manager = new AssetManager();
        manager.setLoader(TiledMap .class, new TmxMapLoader());
        manager.load("maps/BasicDanceFloor.tmx", TiledMap.class);
        manager.finishLoading();

        map = manager.get("maps/BasicDanceFloor.tmx", TiledMap.class);

        // Read properties
        MapProperties properties = map.getProperties();
        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight        = properties.get("tileheight", Integer.class);
        tileSideLength = tileHeight;
        mapWidthInTiles   = properties.get("width", Integer.class);
        mapHeightInTiles  = properties.get("height", Integer.class);
        mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
        this.danceFloorTiles = new DanceFloorTile[mapHeightInTiles * mapWidthInTiles];
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


    public DanceFloorTile[] initializeDanceFloor() {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {
/*
            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer");
            else if (i == ((this.mapWidthInTiles*this.mapHeightInTiles) - this.mapWidthInTiles - 2) )
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer");

            else

 */
                this.danceFloorTiles[i] = new DanceFloorTile("transparent_tile");

        }
        return this.danceFloorTiles;
    }

    public void resetTile(int tile){
        this.danceFloorTiles[tile] = new DanceFloorTile("transparent_tile");
    }

    public void setTile(int tile, Dancer dancer){
        this.danceFloorTiles[tile] = new DanceFloorTile(dancer.getSpriteName());
    }

}
