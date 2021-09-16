package com.mygdx.game.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.Controller;

public class Model {

    //TODO: DanceFans should be many
    private DanceFan redDancer = new DanceFan("redPlayer");
    private DanceFan greenDancer = new DanceFan("greenPlayer");
    public DanceFloor danceFloor;
    //TODO: Maybe later have multiple MainDancers per player
    private MainDancer playerOne;
    private MainDancer playerTwo;

    // Map
    public TiledMap map;
    private AssetManager manager;

    // Map properties
    public int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            tileSideLength,
            mapWidthInPixels, mapHeightInPixels;

    //TODO: Add game logic and stuff

    public Model(){
        this.danceFloor = new DanceFloor(mapWidthInTiles, mapHeightInTiles);
    }

    public void startNewGame(){

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

        this.danceFloor = new DanceFloor(mapWidthInTiles, mapHeightInTiles);
        danceFloor.initializeDanceFloor(mapWidthInTiles, mapHeightInTiles);


    }


}
