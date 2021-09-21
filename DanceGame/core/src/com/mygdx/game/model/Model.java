package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.Controller;
import com.mygdx.game.View;
import com.badlogic.gdx.Input;

public class Model {

    private Player[] players;
    public Enum<PlayerTurnSlot> whichPlayersTurnItIs;
    // TODO: show UI to let player know they should click to start their turn if hasPlayerStartedTheirTurn is False.
    private Boolean hasPlayerStartedTheirTurn;
    public DanceFloor danceFloor;
    // When the player moves around selection marker to understand their moves, we only update and show previewDanceFloor
    // When the move is confirmed, make danceFloor become previewDanceFloor.
    // TODO: to make a simple undo is to just store all the previous states of danceFloor etc
    public DanceFloor previewDanceFloor;
    // TODO: test so selectionOnTileIndex is never outside of danceFloor
    public int selectionOnTileIndex;
    //private MainDancer playerOne;
    //private MainDancer playerTwo;


    public Model(){
        this.danceFloor = new DanceFloor(whichPlayersTurnItIs);
    }



    public void startNewGame(){

        this.players = new Player[3];
        Player player1 = new Player(PlayerTurnSlot.ONE);
        Player player2 = new Player(PlayerTurnSlot.TWO);
        this.players[1] = player1;
        this.players[2] = player2;

        this.danceFloor = new DanceFloor(whichPlayersTurnItIs);
        danceFloor.initializeDanceFloor();
        // Player ONE starts
        this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
        this.selectionOnTileIndex = danceFloor.mapWidthInTiles; //danceFloor.mapWidthInTiles + 1;
        System.out.println("selection tile on " + danceFloor.mapWidthInTiles);
    }


    //TODO: Add game logic and stuff

    public void playerConfirmedDanceMove(){
        // check if move is allowed
            // E.g. can't move to location where another main Dancer already stands
            // Can only move as far away as card allows
            // etc
        // update model based on the card, where the selection cursor on the dancefloor currently is
        this.danceFloor = previewDanceFloor;
        // Animations or something to give the user feedback?
        changeWhichPlayersTurnItIs();
        this.hasPlayerStartedTheirTurn = false;
        // show feedback for next player that it is their turn
    }


    // No need to make this more sophisticated until potential decision to add more players.
    public void changeWhichPlayersTurnItIs(){
        if (whichPlayersTurnItIs == PlayerTurnSlot.ONE)
            this.whichPlayersTurnItIs = PlayerTurnSlot.TWO;
        else
            this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
    }



    // Since the game might have a clock for your turn, the timer doesn't start until you draw your cards.
    public void startPlayerTurn(){
        this.hasPlayerStartedTheirTurn = true;
    }

    // TODO: Not sure in which class this should be, since it should only update previewDancerFloor, not danceFloor.
    public void moveMainDancerUp() {
        //Check if possible to move up, e.g. not outside edge of dance floor
        int currentMainDancer = danceFloor.getIndexOnDancefloorOfCurrentPlayerMainDancer();
        int currentMainDancerLocation = danceFloor.getIndexOnDancefloorOfCurrentPlayerMainDancer();

        danceFloor.removeDancerFromTileIndex(currentMainDancerLocation);




    }


    public void moveSelection(int keycode){
        //int h = danceFloor.mapHeightInPixels/danceFloor.mapHeightInTiles;
        //int w = danceFloor.mapWidthInPixels/danceFloor.mapWidthInTiles;

        switch (keycode){
            //case 19:
            case Input.Keys.UP:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX(), selectionOnTileIndex.getY() + h);


                // Move up, If selectionOnTile is not in the top row
                if (selectionOnTileIndex > (danceFloor.mapWidthInTiles - 1 ))
                    selectionOnTileIndex = selectionOnTileIndex - (danceFloor.mapWidthInTiles );
                else
                    break;

            //case 20:
            case Input.Keys.DOWN:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX() , selectionOnTileIndex.getY() - h);

                // Move down, If selectionOnTile is not in the bottom row
                if (selectionOnTileIndex > (danceFloor.mapWidthInTiles * (danceFloor.mapHeightInTiles * (danceFloor.mapWidthInTiles -1)) ))
                    selectionOnTileIndex = selectionOnTileIndex + danceFloor.mapWidthInTiles;
                else
                    selectionOnTileIndex = selectionOnTileIndex;


            //case 21:
            case Input.Keys.LEFT:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX() - w,selectionOnTileIndex.getY());

                // Move left, If selectionOnTile is not in the leftmost column
                if (selectionOnTileIndex % danceFloor.mapWidthInTiles == 0 )
                    selectionOnTileIndex = selectionOnTileIndex - 1;
                else
                    selectionOnTileIndex = selectionOnTileIndex;

            //case 22:
            case Input.Keys.RIGHT:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX() + w, selectionOnTileIndex.getY());

                // Move right, If selectionOnTile is not in the rightmost column
                if (selectionOnTileIndex % danceFloor.mapWidthInTiles ==  danceFloor.mapWidthInTiles)
                    selectionOnTileIndex = selectionOnTileIndex + 1;
                else
                    selectionOnTileIndex = selectionOnTileIndex;
        }
        System.out.println(selectionOnTileIndex);
        //System.out.println("x " + selectionOnTileIndex.getX() + " y " + selectionOnTileIndex.getY());
    }

    // E.g. get column 4 from row 2 to get the index in the array.
    private int tileIndexFromCoordinatesInTiles(int x, int y){
        int heightInTiles = danceFloor.mapHeightInTiles;
        int widthInTiles = danceFloor.mapWidthInTiles;

        System.out.println("height " + heightInTiles + " width " + widthInTiles);

        return y * widthInTiles + x;
    }



}
