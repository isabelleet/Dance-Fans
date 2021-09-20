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

    public Sprite select = new Sprite();

    //TODO: Add game logic and stuff

    public Model(){
        this.danceFloor = new DanceFloor();
    }

    public void startNewGame(){
        // Used this guide: http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
        // Code: https://github.com/angelnavarro/Gdx-MyExamples/blob/master/gdx-tiled-draw-map/core/src/com/pixnbgames/tiled/draw_map/MyGdxTiledGame.java
        this.danceFloor = new DanceFloor();
        danceFloor.initializeDanceFloor();
        this.playerOne = new MainDancer("greenMainDancer", 1, 0);
        this.playerTwo = new MainDancer("redMainDancer", 2, 0);

        this.select.setPosition(0,0);



        //just testing if it draws at all
        danceFloor.setTile(1, playerOne);
        danceFloor.setTile(2, playerTwo);

    }

    public void moveSelect(int keycode){
        int h = danceFloor.mapHeightInPixels/danceFloor.mapHeightInTiles;
        int w = danceFloor.mapWidthInPixels/danceFloor.mapWidthInTiles;


        switch (keycode){
            case 19:
                select.setPosition(select.getX(), select.getY() + h);
                break;
            case 20:
                select.setPosition(select.getX() , select.getY() - h);
                break;
            case 21:
                select.setPosition(select.getX() - w,select.getY());
                break;
            case 22:
                select.setPosition(select.getX() + w, select.getY());
                break;
        }

        System.out.println("x " + select.getX() + " y " + select.getY());
    }

    private int tileNumber(int x, int y){
        int h = danceFloor.mapHeightInTiles;
        int w = danceFloor.mapWidthInTiles;

        System.out.println("h " + h + " w " + w);

        return y*w + x;
    }

}
