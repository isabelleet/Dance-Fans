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

    // Choose to start simple with Boolean for twoplayer game
    private Enum<PlayerSlot> whoseTurnItIs;
    public DanceFloor danceFloor;
    private MainDancer playerOne;
    private MainDancer playerTwo;


    
    //TODO: Add game logic and stuff

    public Model(){
        this.danceFloor = new DanceFloor();


    }

    public void startNewGame(){
        // Used this guide: http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
        // Code: https://github.com/angelnavarro/Gdx-MyExamples/blob/master/gdx-tiled-draw-map/core/src/com/pixnbgames/tiled/draw_map/MyGdxTiledGame.java
        this.danceFloor = new DanceFloor();
        danceFloor.initializeDanceFloor();
        this.whoseTurnItIs = ONE;

    }


}
