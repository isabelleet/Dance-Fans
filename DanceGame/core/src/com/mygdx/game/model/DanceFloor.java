package com.mygdx.game.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class DanceFloor {

    public DanceFloorTile[] danceFloorTiles;
    private Enum<PlayerTurnSlot> whichPlayersTurnItIs;
    // Map
    public TiledMap map;
    private AssetManager manager;

    // Map properties
    public int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            tileSideLength,
            mapWidthInPixels, mapHeightInPixels;


    public DanceFloor(Enum<PlayerTurnSlot> whichPlayersTurnItIs){
        this.whichPlayersTurnItIs = whichPlayersTurnItIs;
        // Used this guide: http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
        // Code: https://github.com/angelnavarro/Gdx-MyExamples/blob/master/gdx-tiled-draw-map/core/src/com/pixnbgames/tiled/draw_map/MyGdxTiledGame.java
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
    public DanceFloorTile[] initializeFullDanceFloor(int dancefloorWidth, int dancefloorHeight, Player player1, Player player2) {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {

            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer", player1);
            else if (i == ((this.danceFloorTiles.length*dancefloorHeight) - dancefloorWidth - 2) )
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer", player2);
            else if (i % 2 == 0)
                this.danceFloorTiles[i] = new DanceFloorTile("redDancer", player1);
            else
                this.danceFloorTiles[i] = new DanceFloorTile("greenDancer", player2);

        }
        return this.danceFloorTiles;
    }

    //TODO: variables for chosen MainDancers
    public DanceFloorTile[] initializeDanceFloor( Player player1, Player player2) {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {

            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer", player1);
            else if (i == ((this.mapWidthInTiles*this.mapHeightInTiles) - this.mapWidthInTiles - 2) )
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer", player2);

            else
                //TODO: an empty tiles shouldn't be a dancer, fix later. Prob. dancer just one case of "object on floor"
                // added player1 here just for the time being if it doesn't make sense
                this.danceFloorTiles[i] = new DanceFloorTile("transparent_tile", player1);

        }
        return this.danceFloorTiles;
    }





    public int getIndexOnDancefloorOfCurrentPlayerMainDancer() {


        int index;
        for (index = 0; index < this.danceFloorTiles.length; index++) {
            //TODO: om Dancer är en MainDancer
            //TODO: om Dancer är på samma team (dvs har samma playerTurnSlot) som den player vars tur det är nu
            //TODO: den låter alltid gå igenom här så det blir -1, så något fel med logiken.
            if ((this.danceFloorTiles[index].occupant.fanOfPlayer.playerTurnSlot).equals(this.whichPlayersTurnItIs) )
                return index;
        }
        //TODO: error? Each player must always have their main dancer on the dancefloor.
        //return -1;
        return 0;
    }

    public void removeDancerFromTileIndex(int tileIndex) {
        this.danceFloorTiles[tileIndex].setOccupant("transparent_tile"); //TODO: prob not just have empty string to mean no dancer...
    }

    //TODO: not sure if should update Dance Fan, or just replace it.
    // Depends on if Dance Fans have any data in them we want to keep even if they change which Main Dancer they're fan of
    public void newDancerOnTile(int tileIndex, Dancer dancer){
        this.danceFloorTiles[tileIndex].setOccupant(dancer.getSpriteName());
    }





}
