package com.mygdx.game.model;
import java.lang.Math;

import java.util.ArrayList;
import java.util.List;

/**
 * Model combines many of the other classes in the package, and is what is accessed from outside of the package.
 * It can start new games, calculate if someone has won, move the MainDancer:s around.
 *
 * Is used by Controller, DanceFans, View.
 *
 * Uses CardDeck, DanceFloor, Player.
 *
 * @author Joar Granström
 * @author Hedy Pettersson
 * @author Johan Berg
 * @author Jakob Persson
 * @author David Salmo
 * @author Isabelle Ermeryd Tankred
 */

public class Model {

    private Player[] players;
    public Enum<PlayerTurnSlot> whichPlayersTurnItIs;
    private int turnNumber = 0;
    private final int maximumTurns = 20;
    public Boolean hasPlayerStartedTheirTurn;
    public DanceFloor danceFloor;
    // When the player moves around selection marker to understand their moves, we only update and show previewDanceFloor
    // When the move is confirmed, make danceFloor become previewDanceFloor.
    public DanceFloor previewDanceFloor;
    // TODO: test so selectionOnTileIndex is never outside of danceFloor
    public int selectionOnTileIndex;

    public int selectedCard = 0;

    List<Integer> tileIndexes = new ArrayList();


    public Model(){
    }


    public Player currentPlayer(){
        if (this.whichPlayersTurnItIs == PlayerTurnSlot.ONE)
            return this.players[0];
        else
            return this.players[1];
    }

    /**
     * Initializes everything needed to start a new game, sets up Players, the DanceFloor and sets player 1's turn.
     */
    public void startNewGame(){
        turnNumber = 0;


        this.players = new Player[2];

        Player player1 = new Player(PlayerTurnSlot.ONE, new MainDancer(Color.RED, Type.MD, 50), CardDeck.initialDeck(0));
        Player player2 = new Player(PlayerTurnSlot.TWO, new MainDancer(Color.GREEN, Type.MD, 0), CardDeck.initialDeck(1));

        this.players[0] = player1;
        this.players[1] = player2;

        this.danceFloor = new DanceFloor();

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

        //The first turn the cards are drawn right away.
        this.hasPlayerStartedTheirTurn = true;
    }


    /**
     * Updates the state of the DanceFloor to match the state of the PreviewDanceFloor and changes whose turn it is.
     * @throws ArrayIndexOutOfBoundsException If something went wrong with while copying the previewDanceFloor.
     */
    public void playerConfirmedDanceMove() throws ArrayIndexOutOfBoundsException {

        try {
            // check if move is allowed
            // E.g. can't move to location where another main Dancer already stands
            // Can only move as far away as card allows
            // etc
            // update model based on the card, where the selection cursor on the dancefloor currently is
            //this.danceFloor = previewDanceFloor;

            // Update tiles of the previewed transparent dancefans to dancefans when the player ends the turn
            for (int i = 0; i < tileIndexes.size(); i++) {
                this.previewDanceFloor.newDancerOnTile(tileIndexes.get(i), currentPlayer().getDanceFan());
            }

            System.out.println();

            this.danceFloor = previewDanceFloor.deepCopy();
            this.currentPlayer().getMainDancer().setIndex(this.currentPlayer().getMainDancer().getPreviewIndex());

            changeWhichPlayersTurnItIs();
            this.selectionOnTileIndex = currentPlayer().getMainDancer().getIndex();
            this.hasPlayerStartedTheirTurn = false;

            selectedCard = 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // No need to make this more sophisticated until potential decision to add more players.

    /**
     * Changes whose turn it is.
     */
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

    /**
     * Sets hasPlayerStartedTheirTurn to true and draws cards.
     */
    public void playerDrewCardsToStartTurn(){
        this.hasPlayerStartedTheirTurn = true;
        currentPlayer().useCard(selectedCard);
    }

    // TODO: Not sure in which class this should be, since it should only update previewDancerFloor, not danceFloor.
    // the danceFloor (not previewDanceFloor) is only updated as copy of previewDanceFloor at end of a turn,
    // so all of this is for previewDanceFloor only

    /**
     * Moves the MainDancer of the player whose turn it is currently to the specified index supplied to the method.
     * @param indexMovedTo - Which index the MainDancer should be moved to.
     * @throws Exception DeepCopies the DanceFloor which can generate an ArrayOutOfBoundsException.
     */
    public void moveMainDancerOfCurrentPlayerToIndex(int indexMovedTo) throws Exception {

        // Clear list before the player moves so only the last preview indexes are stored in the list
        tileIndexes.clear();

        // Reset the preview to the last state of the dancefloor and maindancer positions

        // Sets currentplayers maindancer preview index to last turns index
        currentPlayer().getMainDancer().setPreviewIndex(currentPlayer().getMainDancer().getIndex());
        //each time we try a new preview, previewDanceFloor should reset to dancerfloor from previous completed turn.

        int mainDancerTileIndex = currentPlayer().getMainDancer().getIndex();

        try {

            this.previewDanceFloor = danceFloor.deepCopy();
               // Check for player collision
              if (!((whichPlayersTurnItIs == PlayerTurnSlot.ONE && players[1].getMainDancer().getIndex() == indexMovedTo)
              || (whichPlayersTurnItIs == PlayerTurnSlot.TWO && players[0].getMainDancer().getIndex() == indexMovedTo))) {
                // Update index on dancefloor for main dancer preview, according to input
                  this.currentPlayer().getMainDancer().setPreviewIndex(indexMovedTo);
                // Update dancefloor
                previewDanceFloor.removeDancerFromTileIndex(mainDancerTileIndex);
                previewDanceFloor.removeDancerFromTileIndex(currentPlayer().getMainDancer().getPreviewIndex());
                previewDanceFloor.newDancerOnTile(indexMovedTo, currentPlayer().getMainDancer());
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }


        this.addDanceFansFromPattern(currentPlayer().getPattern(selectedCard));
    }

    // visuell förklaring: https://miro.com/app/board/o9J_luo5ozI=/
    // d = 1, M = 3, blank = 0 i bilderna.

    /**
     * Displays previews of the DanceFans around the MainDancer from the card supplied as a parameter.
     * @param pattern A matrix that represents the 8 tiles around a MainDancer and contains which tiles should have DanceFans added and which are unmodified.
     */
    public void addDanceFansFromPattern(PatternOccupant[][] pattern){
        int tileIndex = 0;

        int mainDancerDanceFloorIndex = currentPlayer().getMainDancer().getPreviewIndex();
        int[] mainDancerCoords = indexToCoords(mainDancerDanceFloorIndex);


        // loop through the pattern to find where the main dancer is and get the offset from top left corner of pattern
        int mainDancerOffsetInColumnIndex = 0;
        int mainDancerOffsetInRowIndex = 0;

        {
            int rowIndex, columnIndex = 0;
            for (rowIndex = 0; rowIndex < pattern.length; rowIndex++) {
                for (columnIndex = 0; columnIndex < pattern[0].length; columnIndex++) {
                    if (pattern[rowIndex][columnIndex] == PatternOccupant.MAINDANCER) {
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

                    if (pattern[rowIndex][columnIndex] == PatternOccupant.DANCEFAN) {
                        int columnInDanceFloor = mainDancerCoords[0] - mainDancerOffsetInColumnIndex + columnIndex;
                        int rowInDanceFloor = mainDancerCoords[1] - mainDancerOffsetInRowIndex + rowIndex;
                        tileIndex = tileIndexFromCoordinatesInTiles(columnInDanceFloor, rowInDanceFloor);



                        // Logic to check if dancer in pattern would be outside of the dancefloor edges
                        if (
                                 ( columnInDanceFloor < danceFloor.mapWidthInTiles)
                            &&   ( columnInDanceFloor >= 0)
                            &&   ( rowInDanceFloor < danceFloor.mapHeightInTiles)
                            &&   ( rowInDanceFloor >= 0)
                            //TODO: Maybe not check spritenames but Id or something!
                            && !(Type.MD == previewDanceFloor.danceFloorTiles[tileIndex].getType())
                        )
                        {
                            // Store indexes in a list to use them when the player ends their turn
                            tileIndexes.add(tileIndex);
                            // Show transparent DanceFans based on the card before the turn ends
                            previewDanceFloor.newDancerOnTile(tileIndex, currentPlayer().getTransparentDanceFan());

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

    private int moveDistanceFromMainDancer(int index){
        int[] coordsToCheck = indexToCoords(index);
        int[] startIndexFromLastMove = indexToCoords(this.currentPlayer().getMainDancer().getIndex());
        int distance = Math.abs(startIndexFromLastMove[0] - coordsToCheck[0]) + Math.abs(startIndexFromLastMove[1] - coordsToCheck[1]);
        return distance;
    }

    /**
     * Selects which direction the MainDancer should move in depending on the parameter. Makes sure the MainDancer can't move outside of the DanceFloor.
     * @param keycode Supplied from Controller, tells which button has been pressed.
     * @throws Exception ArrayIndexOutOfBoundsException if something goes wrong while copying the DanceFloor.
     */
    public void moveSelection(int keycode) throws Exception {

        // TODO: update this to selected card, not first card in deck
        int selectedCardMoveDistanceLimit = currentPlayer().getSteps(selectedCard);

        //TODO: also needs to check if there is another Main Dancer on the tile you try to move to, shouldn't be able
        // to go there then!
        switch (keycode){
            //case Input.Keys.UP:
            case 19:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX(), selectionOnTileIndex.getY() + h);
                int indexUp = selectionOnTileIndex - danceFloor.mapWidthInTiles;

                // Move up, If selectionOnTile is not in the top row
                // also checks for player collision
                if ((selectionOnTileIndex > (danceFloor.mapWidthInTiles - 1 ))
                    && (moveDistanceFromMainDancer(selectionOnTileIndex - danceFloor.mapWidthInTiles) <= selectedCardMoveDistanceLimit)
                        && (!((whichPlayersTurnItIs == PlayerTurnSlot.ONE && players[1].getMainDancer().getIndex() == indexUp)
                        || (whichPlayersTurnItIs == PlayerTurnSlot.TWO && players[0].getMainDancer().getIndex() == indexUp)))
                )

                {
                    int updatedIndex = selectionOnTileIndex - danceFloor.mapWidthInTiles;
                    selectionOnTileIndex = updatedIndex;
                    //TODO: maybe later if you have multiple main dancers per player, you also check which is selected here.
                    // TODO: only move if within tile is within reach based on dance move card move distance prop.
                    moveMainDancerOfCurrentPlayerToIndex(updatedIndex);
                }
                break;


           // case Input.Keys.DOWN:
            case 20:

                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX() , selectionOnTileIndex.getY() - h);
                int indexDown = selectionOnTileIndex + danceFloor.mapWidthInTiles;

                // Move down, If selectionOnTile is not in the bottom row
                // also checks for player collision
                if ((selectionOnTileIndex < (danceFloor.mapWidthInTiles * (danceFloor.mapHeightInTiles - 1) ))
                    && (moveDistanceFromMainDancer(selectionOnTileIndex + danceFloor.mapWidthInTiles) <= selectedCardMoveDistanceLimit)
                        && (!((whichPlayersTurnItIs == PlayerTurnSlot.ONE && players[1].getMainDancer().getIndex() == indexDown)
                        || (whichPlayersTurnItIs == PlayerTurnSlot.TWO && players[0].getMainDancer().getIndex() == indexDown)))
                )
                {


                    int updatedIndex = selectionOnTileIndex + danceFloor.mapWidthInTiles;
                    selectionOnTileIndex = updatedIndex;
                    //TODO: maybe later if you have multiple main dancers per player, you also check which is selected here.
                    // TODO: only move if within tile is within reach based on dance move card move distance prop.
                    moveMainDancerOfCurrentPlayerToIndex(updatedIndex);
                }
                break;

            //case Input.Keys.LEFT:
            case 21:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX() - w,selectionOnTileIndex.getY());
                int indexLeft = selectionOnTileIndex - 1;

                // Move left, If selectionOnTile is not in the leftmost column
                // also checks for player collision
                if (
                    ((selectionOnTileIndex) % danceFloor.mapWidthInTiles != 0 )
                    && (moveDistanceFromMainDancer(selectionOnTileIndex - 1) <= selectedCardMoveDistanceLimit)
                        && (!((whichPlayersTurnItIs == PlayerTurnSlot.ONE && players[1].getMainDancer().getIndex() == indexLeft)
                        || (whichPlayersTurnItIs == PlayerTurnSlot.TWO && players[0].getMainDancer().getIndex() == indexLeft)))
                )

                {

                    int updatedIndex = selectionOnTileIndex - 1;
                    selectionOnTileIndex = updatedIndex;
                    //TODO: maybe later if you have multiple main dancers per player, you also check which is selected here.
                    // TODO: only move if within tile is within reach based on dance move card move distance prop.
                    moveMainDancerOfCurrentPlayerToIndex(updatedIndex);
                }
                break;

            // case Input.Keys.RIGHT:
            case 22:
                //selectionOnTileIndex.setPosition(selectionOnTileIndex.getX() + w, selectionOnTileIndex.getY());
                int indexRight = selectionOnTileIndex + 1;

                // Move right, If selectionOnTile is not in the rightmost column
                // also checks for player collision
                if (
                    ((selectionOnTileIndex ) % danceFloor.mapWidthInTiles !=  danceFloor.mapWidthInTiles - 1)
                    && (moveDistanceFromMainDancer(selectionOnTileIndex + 1) <= selectedCardMoveDistanceLimit)
                            && (!((whichPlayersTurnItIs == PlayerTurnSlot.ONE && players[1].getMainDancer().getIndex() == indexRight)
                            || (whichPlayersTurnItIs == PlayerTurnSlot.TWO && players[0].getMainDancer().getIndex() == indexRight)))
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
    }

    // E.g. get column 4 from row 2 to get the index in the array.
    private int tileIndexFromCoordinatesInTiles(int x, int y){
        int heightInTiles = danceFloor.mapHeightInTiles;
        int widthInTiles = danceFloor.mapWidthInTiles;

        System.out.println("height " + heightInTiles + " width " + widthInTiles);

        return y * widthInTiles + x;
    }

    public List<Card> currentlyOpenCards(){
        return currentPlayer().getHand();
    }

    private int countTotalTiles(){
        int i = 0;
        for(DanceFloorTile dft: previewDanceFloor.danceFloorTiles){
            if(dft.getType() != Type.EMPTY){
                i++;
            }
        }
        return i;
    }

    private int countTiles(Player player){
        int i = 0;
        for(DanceFloorTile dft: previewDanceFloor.danceFloorTiles){
            if(dft.getType() == player.getMainDancer().getType() || dft.getType() == player.getDanceFan().getType())
                i++;
        }
        return i;
    }

    /**
     * Checks whether the game has reached the turn limit or the board is filled.
     * @return true or false.
     */
    public boolean gameIsDone(){
        // return true when game is finish
        if(countTotalTiles() ==54 || turnNumber == maximumTurns){
            return true;
        }
        return false;
    }

    public Player isLeading(){
        if (countTiles(players[0]) > countTiles(players[1])){
            return players[0];
        }
        else{
            return players[1];
        }
    }

    /**
     * Getter for the turn number.
     * @return which turn it is.
     */
    public int numberTurns(){

        return turnNumber/2;
    }




}
