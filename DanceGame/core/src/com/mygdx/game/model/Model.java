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

    private List<Player> players;
    private Enum<PlayerTurnSlot> whichPlayersTurnItIs;
    // TODO: show UI to let player know they should click to start their turn if hasPlayerStartedTheirTurn is False.
    private Boolean hasPlayerStartedTheirTurn;
    public DanceFloor danceFloor;
    // When the player moves around selection marker to understand their moves, we only update and show previewDanceFloor
    // When the move is confirmed, make danceFloor become previewDanceFloor.
    // TODO: to make a simple undo is to just store all the previous states of danceFloor etc
    public DanceFloor previewDanceFloor;
    //private MainDancer playerOne;
    //private MainDancer playerTwo;


    public Model(){
        this.danceFloor = new DanceFloor();
    }



    public void startNewGame(){

        this.players = new ArrayList();
        player1 = new Player(PlayerTurnSlot.ONE);
        player2 = new Player(PlayerTurnSlot.TWO);
        this.players.add(player1);
        this.players.add(player2);

        this.danceFloor = new DanceFloor();
        danceFloor.initializeDanceFloor();
        // Player ONE starts
        this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
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
        changeWhoseTurnItIs();
        this.hasPlayerStartedTheirTurn = False;
        // show feedback for next player that it is their turn
    }


    // No need to make this more sophisticated until potential decision to add more players.
    public void changeWhoseTurnItIs(){
        if (whichPlayersTurnItIs == PlayerTurnSlot.ONE)
            this.whichPlayersTurnItIs = PlayerTurnSlot.TWO;
        else
            this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
    }



    // Since the game might have a clock for your turn, the timer doesn't start until you draw your cards.
    public void startPlayerTurn(){
        this.hasPlayerStartedTheirTurn = True;
    }

    // TODO: Not sure in which class this should be, since it should only update previewDancerFloor, not danceFloor.
    public void moveMainDancerUp() {
        //Check if possible to move up, e.g. not outside edge of dance floor
        int currentMainDancer = model.danceFloor.getIndexOnDancefloorOfCurrentPlayerMainDancer();
        int currentMainDancerLocation = model.danceFloor.getIndexOnDancefloorOfCurrentPlayerMainDancer();

        removeDancerFromDancefloorIndex(currentMainDancerLocation);




    }



}
