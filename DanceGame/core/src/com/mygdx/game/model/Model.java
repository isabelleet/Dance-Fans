package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.Controller;
import com.mygdx.game.View;

public class Model {

    //TODO: DanceFans should be many
    private DanceFan redDancer = new DanceFan("redPlayer");
    private DanceFan greenDancer = new DanceFan("greenPlayer");
    public DanceFloor danceFloor;
    //TODO: Maybe later have multiple MainDancers per player
    private MainDancer playerOne;
    private MainDancer playerTwo;
    private Player whoseTurnItIs;
    
    //TODO: Add game logic and stuff

    public Model(){
        this.danceFloor = new DanceFloor();


    }

    public void startNewGame(){
        // Used this guide: http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
        // Code: https://github.com/angelnavarro/Gdx-MyExamples/blob/master/gdx-tiled-draw-map/core/src/com/pixnbgames/tiled/draw_map/MyGdxTiledGame.java
        this.danceFloor = new DanceFloor();
        danceFloor.initializeDanceFloor();

    }


}
