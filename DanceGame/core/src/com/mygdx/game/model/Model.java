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

    //TODO: Add game logic and stuff

    public Model(){
        this.danceFloor = new DanceFloor();


    }

    public void startNewGame(){
        // Used this guide: http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
        // Code: https://github.com/angelnavarro/Gdx-MyExamples/blob/master/gdx-tiled-draw-map/core/src/com/pixnbgames/tiled/draw_map/MyGdxTiledGame.java
        this.danceFloor = new DanceFloor();
        danceFloor.initializeDanceFloor();
        this.playerOne = new MainDancer("greenMainDancer", 1, 1);
        this.playerTwo = new MainDancer("redMainDancer", 3, 4);

        //just testing if it draws at all
        danceFloor.setTile(1, playerOne);
        danceFloor.setTile(2, playerTwo);

    }

    public void moveP1(int keycode){
        switch (keycode){
            case 19:
                playerOne.setCoord(playerOne.getX(), playerOne.getY() + 1);
                break;
            case 20:
                playerOne.setCoord(playerOne.getX() , playerOne.getY() -1);
                break;
            case 21:
                playerOne.setCoord(playerOne.getX() - 1, playerOne.getY()+1);
                break;
            case 22:
                playerOne.setCoord(playerOne.getX() + 1, playerOne.getY());
                break;
        }

        danceFloor.setTile(tileNumber(playerOne.getX(), playerOne.getY()), playerOne);
        danceFloor.setTile(tileNumber(playerOne.getX(), playerOne.getY()));
        System.out.println("x " + playerOne.getX() + " y " + playerOne.getY());
    }

    private int tileNumber(int x, int y){
        int h = danceFloor.mapHeightInTiles;
        int w = danceFloor.mapWidthInTiles;
        System.out.println("h " + h + " w " + w);

        return y*w + x;
    }

}
