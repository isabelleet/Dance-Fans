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
import java.lang.Math;

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
        Player player1 = new Player(PlayerTurnSlot.ONE, new MainDancer("redMainDancer", 50), initialDeck(0), new DanceFan("redDanceFan"));
        Player player2 = new Player(PlayerTurnSlot.TWO, new MainDancer("greenMainDancer", 0), initialDeck(1), new DanceFan("greenDanceFan"));
        this.players[0] = player1;
        this.players[1] = player2;


        this.danceFloor = new DanceFloor(whichPlayersTurnItIs);

        danceFloor.initializeDanceFloor();

        // Player ONE starts
        this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
        this.selectionOnTileIndex = currentPlayer().getMainDancer().getIndex();
        System.out.println("selection tile on " + danceFloor.mapWidthInTiles);

        danceFloor.newDancerOnTile(player1.getMainDancer().getIndex(), player1.getMainDancer());
        danceFloor.newDancerOnTile(player2.getMainDancer().getIndex(), player2.getMainDancer());

        // Initialize previewdance floor
        try {
        this.previewDanceFloor = this.danceFloor.deepCopy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void playerConfirmedDanceMove() throws ArrayIndexOutOfBoundsException {

        try {
            // check if move is allowed
            // E.g. can't move to location where another main Dancer already stands
            // Can only move as far away as card allows
            // etc
            // update model based on the card, where the selection cursor on the dancefloor currently is
            //this.danceFloor = previewDanceFloor;

            //DanceFloor deepCopiedInstance = previewDanceFloor.deepCopy();
            //this.danceFloor = deepCopiedInstance;
            this.danceFloor = previewDanceFloor.deepCopy();
            this.currentPlayer().getMainDancer().setIndex(this.currentPlayer().getMainDancer().getPreviewIndex());
            // Animations or something to give the user feedback?
            changeWhichPlayersTurnItIs();
            this.selectionOnTileIndex = currentPlayer().getMainDancer().getIndex();
            this.hasPlayerStartedTheirTurn = false;
            // show feedback for next player that it is their turn
            // if they press button for playerDrewCardsToStartTurn(), then their turn begins.
            // Not enter since that ends the last players turn, might be problem.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    int turnNumber=0;
    // No need to make this more sophisticated until potential decision to add more players.
    public void changeWhichPlayersTurnItIs(){
        turnNumber++;
        if (whichPlayersTurnItIs == PlayerTurnSlot.ONE) {
            this.whichPlayersTurnItIs = PlayerTurnSlot.TWO;
            System.out.println("Player 2, it's your turn!");
        }
        else {
            this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
            System.out.println("Player 1, it's your turn!");
        }
    }



    // Since the game might have a clock for your turn, the timer doesn't start until you draw your cards.
    public void playerDrewCardsToStartTurn(){
        this.hasPlayerStartedTheirTurn = true;
    }

    // TODO: Not sure in which class this should be, since it should only update previewDancerFloor, not danceFloor.
    // the danceFloor (not previewDanceFloor) is only updated as copy of previewDanceFloor at end of a turn,
    // so all of this is for previewDanceFloor only
    public void moveMainDancerOfCurrentPlayerToIndex(int indexMovedTo) throws Exception {

        // Reset the preview to the last state of the dancefloor and maindancer positions

        //sets currentplayers maindancer preview index to last turns index
        currentPlayer().getMainDancer().setPreviewIndex(currentPlayer().getMainDancer().getIndex());
        //each time we try a new preview, previewDanceFloor should reset to dancerfloor from previous completed turn.

        int mainDancerTileIndex = currentPlayer().getMainDancer().getIndex();

        try {
            //DanceFloor deepCopiedInstance = danceFloor.deepCopy();
            //this.previewDanceFloor = deepCopiedInstance;
            this.previewDanceFloor = danceFloor.deepCopy();


            // Update index on dancefloor for main dancer preview, according to input
            this.currentPlayer().getMainDancer().setPreviewIndex(indexMovedTo);
            //Update dancefloor
            previewDanceFloor.removeDancerFromTileIndex(mainDancerTileIndex);
            previewDanceFloor.newDancerOnTile(indexMovedTo, currentPlayer().getMainDancer());

            //deepCopiedInstance.removeDancerFromTileIndex(mainDancerTileIndex);
            // deepCopiedInstance.newDancerOnTile(indexMovedTo, currentPlayer().getMainDancer());

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        //TODO: reset where mainDancerIndex is from danceFloor: currentPlayer().getMainDancer().setIndex(something);

        //TODO: show ghost/grayed out dancer at first position, to help player recall where they started the dance move from?

        System.out.println("mainDancerPreviewIndex: " + currentPlayer().getMainDancer().getPreviewIndex());
        System.out.println("mainDancerIndex: " + currentPlayer().getMainDancer().getIndex());
        System.out.println("Dancer on selection tile:" +  danceFloor.danceFloorTiles[indexMovedTo].occupant);


        this.addDanceFansFromPattern(currentPlayer().getCardDeck().getOpen().get(currentPlayer().getCardDeck().selected).getDancePattern());
    }

    // visuell förklaring: https://miro.com/app/board/o9J_luo5ozI=/
    // d = 1, M = 3, blank = 0 i bilderna.
    public void addDanceFansFromPattern(int[][] pattern){
        System.out.println("mainDancerPreviewIndex: " + currentPlayer().getMainDancer().getPreviewIndex());
        System.out.println("mainDancerDancefloorIndex: " + currentPlayer().getMainDancer().getIndex());
        int tileIndex = 0;

        int mainDancerDanceFloorIndex = currentPlayer().getMainDancer().getPreviewIndex();
        DanceFloorTile[][] danceFloorMatrix = convertToMatrix(danceFloor.danceFloorTiles);
        int[] mainDancerCoords = indexToCoords(mainDancerDanceFloorIndex);
        System.out.println("column: " + mainDancerCoords[0]);
        System.out.println("row: " + mainDancerCoords[1]);

        // loop through the pattern to find where the main dancer is and get the offset from top left corner of pattern
        int mainDancerOffsetInColumnIndex = 0;
        int mainDancerOffsetInRowIndex = 0;

        {
            int rowIndex, columnIndex = 0;
            for (rowIndex = 0; rowIndex < pattern.length; rowIndex++) {
                for (columnIndex = 0; columnIndex < pattern[0].length; columnIndex++) {
                    if (pattern[rowIndex][columnIndex] == 3) {
                        System.out.println("pattern.length" + pattern.length);
                        System.out.println("pattern[rowIndex].length" + pattern[rowIndex].length);
                        System.out.println("maindancer column: " + columnIndex + " maindancer row: " + rowIndex);
                        mainDancerOffsetInColumnIndex = columnIndex;
                        mainDancerOffsetInRowIndex = rowIndex;
                        break;
                    }
                }
            }
        }
        //


        // loop through pattern to read where dance fans should be placed, then write onto dance floor
        // I just scopes instead of new names for rowIndex and columnIndex
        {
            for (int rowIndex = 0; rowIndex < pattern.length; rowIndex++) {
                for (int columnIndex = 0; columnIndex < pattern[0].length; columnIndex++) {

                    if (pattern[rowIndex][columnIndex] == 1) {
                        int columnInDanceFloor = mainDancerCoords[0] - mainDancerOffsetInColumnIndex + columnIndex;
                        int rowInDanceFloor = mainDancerCoords[1] - mainDancerOffsetInRowIndex + rowIndex;
                        tileIndex = tileIndexFromCoordinatesInTiles(columnInDanceFloor, rowInDanceFloor);
                        System.out.println("mainDancerColumn: " + mainDancerCoords[0]);
                        System.out.println("mainDancerOffsetInColumnIndex: " + mainDancerOffsetInColumnIndex);
                        System.out.println("rowIndex: " + rowIndex);


                        // Logic to check if dancer in pattern would be outside of the dancefloor edges
                        if (
                                 ( columnInDanceFloor < danceFloor.mapWidthInTiles)
                            &&   ( columnInDanceFloor >= 0)
                            &&   ( rowInDanceFloor < danceFloor.mapHeightInTiles)
                            &&   ( rowInDanceFloor >= 0)
                            //TODO: Maybe not check spritenames but Id or something!
                            && !"redMainDancer".equals(previewDanceFloor.danceFloorTiles[tileIndex].getOccupantName())
                            && !"greenMainDancer".equals(previewDanceFloor.danceFloorTiles[tileIndex].getOccupantName())

                        )
                        {
                            previewDanceFloor.newDancerOnTile(tileIndex, currentPlayer().getDanceFan());
                        }
                    }
                }
            }
        }

    }

    private DanceFloorTile[][] convertToMatrix(DanceFloorTile[] danceFloorArray){
        DanceFloorTile[][] danceFloorMatrix = new DanceFloorTile[danceFloor.mapWidthInTiles][danceFloor.mapHeightInTiles];
        for(int i = 0; i < danceFloorArray.length; i++){
            danceFloorMatrix[i % danceFloor.mapWidthInTiles][i / danceFloor.mapWidthInTiles] = danceFloorArray[i];
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

    // since we start from top left, end at bottom right row and column is probably more fitting than x y. Since y would be upside down.
    // rename to arrayIndexToMatrixIndexes or something?
    private int[] indexToCoords(int index){
        int[] coords = new int[2];
        int column = index % danceFloor.mapWidthInTiles;
        int row = index / danceFloor.mapWidthInTiles;
        coords[0] = column;
        coords[1] = row;
        return coords;
    }

    public int moveDistanceFromMainDancer(int index){
        int[] coordsToCheck = indexToCoords(index);
        int[] startIndexFromLastMove = indexToCoords(this.currentPlayer().getMainDancer().getIndex());
        int distance = Math.abs(startIndexFromLastMove[0] - coordsToCheck[0]) + Math.abs(startIndexFromLastMove[1] - coordsToCheck[1]);
        return distance;



    }


    //TODO: Kanske skriva tester om detta
    public void moveSelection(int keycode) throws Exception {
        //int h = danceFloor.mapHeightInPixels/danceFloor.mapHeightInTiles;
        //int w = danceFloor.mapWidthInPixels/danceFloor.mapWidthInTiles;


        // TODO: update this to selected card, not first card in deck
        int selectedCardMoveDistanceLimit = currentPlayer().getCardDeck().getOpen().get(currentPlayer().getCardDeck().selected).getSteps();

        //TODO: also needs to check if there is another Main Dancer on the tile you try to move to, shouldn't be able
        // to go there then!
        switch (keycode){
            //case 19:
            case Input.Keys.UP:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX(), selectionOnTileIndex.getY() + h);

                // Move up, If selectionOnTile is not in the top row
                if ((selectionOnTileIndex > (danceFloor.mapWidthInTiles - 1 ))
                    && (moveDistanceFromMainDancer(selectionOnTileIndex - danceFloor.mapWidthInTiles) <= selectedCardMoveDistanceLimit)
                )

                {
                    int updatedIndex = selectionOnTileIndex - danceFloor.mapWidthInTiles;
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
                if ((selectionOnTileIndex < (danceFloor.mapWidthInTiles * (danceFloor.mapHeightInTiles - 1) ))
                    && (moveDistanceFromMainDancer(selectionOnTileIndex + danceFloor.mapWidthInTiles) <= selectedCardMoveDistanceLimit)
                )
                {


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
                if (
                    ((selectionOnTileIndex) % danceFloor.mapWidthInTiles != 0 )
                    && (moveDistanceFromMainDancer(selectionOnTileIndex - 1) <= selectedCardMoveDistanceLimit)
                )

                {

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
                if (
                    ((selectionOnTileIndex ) % danceFloor.mapWidthInTiles !=  danceFloor.mapWidthInTiles - 1)
                    && (moveDistanceFromMainDancer(selectionOnTileIndex + 1) <= selectedCardMoveDistanceLimit)
                )

                {
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


    /**
     * used to set some starter decks
     * @param i 0 for a green deck, otherwise a red deck
     * @return either a deck with green cards or red cards
     */
    private CardDeck initialDeck(int i){
        List<Card> cards = new ArrayList<>();
        if(i == 0) {
            // not allowed to write the pattern directly, must send it the long way.
            int[][] pattern = {{1, 0, 1}, {0, 3, 0}, {1, 0, 1}};
            cards.add(new Card(2, pattern, 3));
            //cards.add(new Card(2,pattern,3 ));
            //cards.add(new Card(2,pattern,3 ));

            pattern = new int[][]{{1, 1, 1}, {1, 3, 0}, {1, 0, 1}};
            cards.add(new Card(3, pattern, 1));
            //cards.add(new Card(3, pattern, 1));

            pattern = new int[][]{{0, 1, 0}, {1, 3, 1}, {0, 1, 0}};
            cards.add(new Card(5, pattern, 2));
            //cards.add(new Card(5, pattern, 2));
            //cards.add(new Card(5, pattern, 2));

            pattern = new int[][]{{0, 0, 0, 0, 0}, {0, 0, 0, 0, 1}, {0, 0, 3, 1, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 0}};
            cards.add(new Card(6, pattern, 3));
            //cards.add(new Card(6, pattern, 3));


        }else{
            // not allowed to write the pattern directly, must send it the long way.
            int[][] pattern = {{1, 0, 1}, {1, 3, 1}, {1, 0, 1}};
            cards.add(new Card(4, pattern, 1));
            //cards.add(new Card(2,pattern,3 ));
            //cards.add(new Card(2,pattern,3 ));

            pattern = new int[][]{{0, 1, 1}, {1, 3, 0}, {1, 0, 0}};
            cards.add(new Card(7, pattern, 2));
            //cards.add(new Card(3, pattern, 1));

            pattern = new int[][]{
                    {0, 0, 0, 1, 1, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 3, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0}};
            cards.add(new Card(1, pattern, 4));
            //cards.add(new Card(5, pattern, 2));
            //cards.add(new Card(5, pattern, 2));

            pattern = new int[][]{
                    {0, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 1, 3, 0, 0},
                    {1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}};
            cards.add(new Card(8, pattern, 3));
            //cards.add(new Card(6, pattern, 3));
        }


        return new CardDeck(cards);

    }

    public List<Card> currentlyOpenCards(){
        return currentPlayer().getCardDeck().getOpen();
    }

    public int countRedTiles(){
        int i = 1;
        for(DanceFloorTile dft: danceFloor.danceFloorTiles){
            if(dft.occupant.getSpriteName()==("redDanceFan")||dft.occupant.getSpriteName()==("redMainDancer"))i++;

        }
        return i;
    }

    public int countGreenTiles(){                       // number of green occupants
        int i=1 ;
        for(DanceFloorTile dft: danceFloor.danceFloorTiles){
            if(dft.occupant.getSpriteName().equals("greenDanceFan")||dft.occupant.getSpriteName().equals("greenMainDancer")){
                i++;
            }
        }
        return i;
    }


    public boolean gameIsDone(){                        // return true when game is finish
        if(countGreenTiles()+countRedTiles()==54    ||  turnNumber==10){
            return true;
        }
        return false;
    }





    public String isWinner(){
        if (turnNumber==10||countRedTiles()+countGreenTiles()==54){
            if (countGreenTiles() > countRedTiles()){
                return " green is winner ";
            }
            else{
                return " red is winner ";

            }
        } return "";
    }



}
