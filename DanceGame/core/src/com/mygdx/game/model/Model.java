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

import java.util.ArrayList;
import java.util.List;

public class Model {

    private Player[] players;
    // HELLO
    //Player currentPlayer = ;
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
    }

    //TODO: simplifiera som Hedy och Joar pratade om i mån om tid
    public Player currentPlayer(){
        if (this.whichPlayersTurnItIs == PlayerTurnSlot.ONE)
            return this.players[0];
        else
            return this.players[1];
    }

    public void startNewGame(){

        this.players = new Player[2];
        Player player1 = new Player(PlayerTurnSlot.ONE, new MainDancer("redMainDancer", 50), initialDeck(), new DanceFan("redDanceFan"));
        Player player2 = new Player(PlayerTurnSlot.TWO, new MainDancer("greenMainDancer", 0), initialDeck(), new DanceFan("greenDanceFan"));
        this.players[0] = player1;
        this.players[1] = player2;


        this.danceFloor = new DanceFloor(whichPlayersTurnItIs);
        danceFloor.initializeDanceFloor();
        // Player ONE starts
        this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
        this.selectionOnTileIndex = danceFloor.mapWidthInTiles; //danceFloor.mapWidthInTiles + 1;
        System.out.println("selection tile on " + danceFloor.mapWidthInTiles);

        danceFloor.newDancerOnTile(player1.getMainDancer().getIndex(), player1.getMainDancer());
        danceFloor.newDancerOnTile(player2.getMainDancer().getIndex(), player2.getMainDancer());
    }



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
        // if they press button for playerDrewCardsToStartTurn(), then their turn begins.
        // Not enter since that ends the last players turn, might be problem.
    }


    // No need to make this more sophisticated until potential decision to add more players.
    public void changeWhichPlayersTurnItIs(){
        if (whichPlayersTurnItIs == PlayerTurnSlot.ONE) {
            this.whichPlayersTurnItIs = PlayerTurnSlot.TWO;
            System.out.println("Player 2, it's your turn!");
        }
        else {
            this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
            System.out.println("Player 1, it's your turn!");
        }
    }


    public void playerDrewCardsToStartTurn(){
       this.hasPlayerStartedTheirTurn = true;
    }



    // Since the game might have a clock for your turn, the timer doesn't start until you draw your cards.
    public void startPlayerTurn(){
        this.hasPlayerStartedTheirTurn = true;
    }

    // TODO: Not sure in which class this should be, since it should only update previewDancerFloor, not danceFloor.
    // the danceFloor (not previewDanceFloor) is only updated as copy of previewDanceFloor at end of a turn,
    // so all of this is for previewDanceFloor only
    public void moveMainDancerOfCurrentPlayerToIndex(int indexMovedTo) {

        //each time we try a new preview, previewDanceFloor should
        //TODO: make sure this is not pointer, but copied value of danceFloor.
        this.previewDanceFloor = danceFloor;
        //TODO: reset where mainDancerIndex is from danceFloor: currentPlayer().getMainDancer().setIndex(something);

        int mainDancerTileIndex = currentPlayer().getMainDancer().getIndex();

        previewDanceFloor.removeDancerFromTileIndex(currentPlayer().getMainDancer().getIndex());
        //TODO: show ghost/grayed out dancer at first position, to help player recall where they started the dance move from?

        previewDanceFloor.newDancerOnTile( indexMovedTo, currentPlayer().getMainDancer() );
        System.out.println("Dancer on selection tile:" +  danceFloor.danceFloorTiles[indexMovedTo].occupant);
        //TODO: add function of currently active Dance Move Card, to also show preview of Dance fans added by move.

    }

    public void addDanceFansFromPattern(int[][] pattern){
        int tileIndex = 0;
        int r, c = 0;
        int mainDancerIndex = currentPlayer().getMainDancer().getIndex();
        DanceFloorTile[][] danceFloorMatrix = convertToMatrix(danceFloor.danceFloorTiles);
        int[] mainDancerCoords = indexToCoords(mainDancerIndex);
        for(r = 0; r <= pattern.length; r++){
            for(c = 0; c <= pattern[r].length; c++){
                if(pattern[r][c] == 3){
                    break;
                }
            }
        }
        for(int i = 0; i <= pattern.length; i++){
            for(int j = 0; j <= pattern[i].length; j++){
                if(pattern[i][j] == 1){
                    tileIndex = tileIndexFromCoordinatesInTiles(r - 1 + mainDancerIndex, c - 1 + mainDancerIndex);
                    danceFloor.newDancerOnTile(tileIndex, currentPlayer().getDanceFan());
                }
            }

        }

    }

    private DanceFloorTile[][] convertToMatrix(DanceFloorTile[] danceFloorArray){
        DanceFloorTile[][] danceFloorMatrix = new DanceFloorTile[danceFloor.mapWidthInTiles][danceFloor.mapHeightInTiles];
        for(int i = 0; i <= danceFloorArray.length; i++){
            danceFloorMatrix[i / danceFloor.mapWidthInTiles][i % danceFloor.mapWidthInTiles] = danceFloorArray[i];
        }
        return danceFloorMatrix;
    }

    private DanceFloorTile[] convertToArray(DanceFloorTile[][] danceFloorMatrix){
        DanceFloorTile[] danceFloorArray = new DanceFloorTile[danceFloor.mapHeightInTiles * danceFloor.mapWidthInTiles];
        for(int i = 0; i <= danceFloorMatrix.length; i++){
            for(int j = 0; j <= danceFloorMatrix[i].length; j++){
                danceFloorArray[i * j] = danceFloorMatrix[i][j];
            }
        }
        return danceFloorArray;
    }

    private int[] indexToCoords(int index){
        int[] coords = new int[2];
        int c = index % danceFloor.mapWidthInTiles;
        int r = index / danceFloor.mapWidthInTiles;
        coords[0] = c;
        coords[1] = r;
        return coords;
    }


    //TODO: Kanske skriva tester om detta
    public void moveSelection(int keycode){
        //int h = danceFloor.mapHeightInPixels/danceFloor.mapHeightInTiles;
        //int w = danceFloor.mapWidthInPixels/danceFloor.mapWidthInTiles;

        //TODO: also needs to check if there is another Main Dancer on the tile you try to move to, shouldn't be able
        // to go there then!

        switch (keycode){
            //case 19:
            case Input.Keys.UP:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX(), selectionOnTileIndex.getY() + h);

                // Move up, If selectionOnTile is not in the top row
                if (selectionOnTileIndex > (danceFloor.mapWidthInTiles - 1 )){
                    int updatedIndex = selectionOnTileIndex - (danceFloor.mapWidthInTiles );
                    selectionOnTileIndex = updatedIndex;
                    //TODO: maybe later if you have multiple main dancers per player, you also check which is selected here.
                    // TODO: only move if within tile is within reach based on dance move card move distance prop.
                    moveMainDancerOfCurrentPlayerToIndex(updatedIndex);

                }
                break;

            //case 20:
            case Input.Keys.DOWN:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX() , selectionOnTileIndex.getY() - h);

                // Move down, If selectionOnTile is not in the bottom row
                if (selectionOnTileIndex < (danceFloor.mapWidthInTiles * (danceFloor.mapHeightInTiles - 1) )){


                    int updatedIndex = selectionOnTileIndex + danceFloor.mapWidthInTiles;
                    selectionOnTileIndex = updatedIndex;
                    //TODO: maybe later if you have multiple main dancers per player, you also check which is selected here.
                    // TODO: only move if within tile is within reach based on dance move card move distance prop.
                    moveMainDancerOfCurrentPlayerToIndex(updatedIndex);
                }
                break;

            //case 21:
            case Input.Keys.LEFT:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX() - w,selectionOnTileIndex.getY());

                // Move left, If selectionOnTile is not in the leftmost column
                if ((selectionOnTileIndex) % danceFloor.mapWidthInTiles != 0 ){

                    int updatedIndex = selectionOnTileIndex = selectionOnTileIndex - 1;
                    selectionOnTileIndex = updatedIndex;
                    //TODO: maybe later if you have multiple main dancers per player, you also check which is selected here.
                    // TODO: only move if within tile is within reach based on dance move card move distance prop.
                    moveMainDancerOfCurrentPlayerToIndex(updatedIndex);
                }
                break;

            //case 22:
            case Input.Keys.RIGHT:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX() + w, selectionOnTileIndex.getY());

                // Move right, If selectionOnTile is not in the rightmost column
                if ((selectionOnTileIndex ) % danceFloor.mapWidthInTiles !=  danceFloor.mapWidthInTiles - 1){
                    int updatedIndex  = selectionOnTileIndex + 1;
                    selectionOnTileIndex = updatedIndex;
                    //TODO: add like the other above
                    moveMainDancerOfCurrentPlayerToIndex(updatedIndex);
                }
                break;
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


    // testing by creating a basic card deck
    private CardDeck initialDeck(){
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(2));
        cards.add(new Card(3));
        cards.add(new Card(4));
        cards.add(new Card(6));


        return new CardDeck(cards);

    }

    public List<Card> currentlyOpenCards(){
        return currentPlayer().getCardDeck().getOpen();
    }


}
