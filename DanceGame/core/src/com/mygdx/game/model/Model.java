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
    public PlayerTurnSlot whichPlayersTurnItIs;
    private int turnNumber = 0;
    private final int maximumTurns = 20;
    public Boolean hasPlayerStartedTheirTurn;
    public DanceFloor danceFloor;
    // When the player moves around selection marker to understand their moves, we only update and show previewDanceFloor
    // When the move is confirmed, make danceFloor become previewDanceFloor.
    public DanceFloor previewDanceFloor;
    // TODO: test so selectionOnTileIndex is never outside of danceFloor
    public Coordinates selectionOnTileCoords;

    public int selectedCard = 0;

    List<Coordinates> tileCoords = new ArrayList();

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

        Player player1 = new Player(PlayerTurnSlot.ONE, new MainDancer(Color.RED, Type.MD, new Coordinates(2,4)), CardDeck.initialDeck(0));
        Player player2 = new Player(PlayerTurnSlot.TWO, new MainDancer(Color.GREEN, Type.MD, new Coordinates(0,0)), CardDeck.initialDeck(1));

        this.players[0] = player1;
        this.players[1] = player2;

        this.danceFloor = new DanceFloor();

        danceFloor.initializeDanceFloor();

        // Player ONE starts
        this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
        this.selectionOnTileCoords = currentPlayer().getMainDancer().getCoordinates();
        System.out.println("selection tile on " + danceFloor.mapWidthInTiles);


        danceFloor.newDancerOnTile(player1.getMainDancer().getCoordinates(), player1.getMainDancer());
        danceFloor.newDancerOnTile(player2.getMainDancer().getCoordinates(), player2.getMainDancer());

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
        currentPlayer().useCard(selectedCard);
        selectedCard = 0;

        // check if move is allowed
        // E.g. can't move to location where another main Dancer already stands
        // Can only move as far away as card allows
        // etc
        // update model based on the card, where the selection cursor on the dancefloor currently is
        //this.danceFloor = previewDanceFloor;

        // Update tiles of the previewed transparent dancefans to dancefans when the player ends the turn
        for (int i = 0; i < tileCoords.size(); i++) {
            this.previewDanceFloor.newDancerOnTile(tileCoords.get(i), currentPlayer().getDanceFan());
        }


        this.danceFloor = previewDanceFloor.deepCopy();
        this.currentPlayer().getMainDancer().setCoordinates(this.currentPlayer().getMainDancer().getPreviewCoords());

        changeWhichPlayersTurnItIs();
        this.selectionOnTileCoords = currentPlayer().getMainDancer().getCoordinates();
        this.hasPlayerStartedTheirTurn = false;
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
    }

    // TODO: Not sure in which class this should be, since it should only update previewDancerFloor, not danceFloor.
    // the danceFloor (not previewDanceFloor) is only updated as copy of previewDanceFloor at end of a turn,
    // so all of this is for previewDanceFloor only

    /**
     * Moves the MainDancer of the player whose turn it is currently to the specified index supplied to the method.
     * @param coordsMovedTo - Which index the MainDancer should be moved to.
     * @throws Exception DeepCopies the DanceFloor which can generate an ArrayOutOfBoundsException.
     */
    public void moveMainDancerOfCurrentPlayerToCoords(Coordinates coordsMovedTo){
        // Clear list before the player moves so only the last preview indexes are stored in the list
        tileCoords.clear();
        Coordinates mdCoords = currentPlayer().getMainDancer().getCoordinates();

        // Reset the preview to the last state of the dancefloor and maindancer positions
        // Sets currentplayers maindancer preview index to last turns index
        currentPlayer().getMainDancer().setPreviewCoords(mdCoords);

        //each time we try a new preview, previewDanceFloor should reset to dancerfloor from previous completed turn.
        this.previewDanceFloor = danceFloor.deepCopy();
        // Update index on dancefloor for main dancer preview, according to input
        this.currentPlayer().getMainDancer().setPreviewCoords(coordsMovedTo);
        // Update dancefloor
        previewDanceFloor.removeDancerFromTileIndex(mdCoords);
        previewDanceFloor.removeDancerFromTileIndex(currentPlayer().getMainDancer().getPreviewCoords());
        previewDanceFloor.newDancerOnTile(coordsMovedTo, currentPlayer().getMainDancer());


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

        Coordinates mdCoords = currentPlayer().getMainDancer().getPreviewCoords();


        // loop through the pattern to find where the main dancer is and get the offset from top left corner of pattern
        int mdOffsetInCol = 0;
        int mdOffsetInRow = 0;

        {
            int row, col = 0;
            for (row = 0; row < pattern.length; row++) {
                for (col = 0; col < pattern[0].length; col++) {
                    if (pattern[row][col] == PatternOccupant.MAINDANCER) {
                        mdOffsetInCol = col;
                        mdOffsetInRow = row;
                        break;
                    }
                }
            }
        }
        //


        // loop through pattern to read where dance fans should be placed, then write onto dance floor
        // I just scopes instead of new names for rowIndex and columnIndex
        {
            for (int row = 0; row < pattern.length; row++) {
                for (int col = 0; col < pattern[0].length; col++) {

                    if (pattern[row][col] == PatternOccupant.DANCEFAN) {
                        int colInDanceFloor = mdCoords.getX() - mdOffsetInCol + col;
                        int rowInDanceFloor = mdCoords.getY() - mdOffsetInRow + row;
                        Coordinates danceFanCoord = new Coordinates(colInDanceFloor, rowInDanceFloor);

                        // Logic to check if dancer in pattern would be outside of the dancefloor edges
                        if (insideDanceFloor(danceFanCoord)
                            && !(Type.MD == previewDanceFloor.getType(danceFanCoord))){
                            {
                                // Store indexes in a list to use them when the player ends their turn
                                tileCoords.add(danceFanCoord);
                                // Show transparent DanceFans based on the card before the turn ends
                                previewDanceFloor.newDancerOnTile(danceFanCoord, currentPlayer().getTransparentDanceFan());

                            }
                        }
                    }
                }
            }
        }
    }

    private boolean insideDanceFloor(Coordinates coords){
        return ( coords.getX()< danceFloor.mapWidthInTiles)
                &&   ( coords.getX() >= 0)
                &&   ( coords.getY() < danceFloor.mapHeightInTiles)
                &&   ( coords.getY() >= 0);
    }

    private boolean collisionOtherPlayer(Coordinates coordinates){
        for(Player player: players){

            if(player != currentPlayer() &&
                    player.getMainDancer().getCoordinates().getX() == coordinates.getX()
                    && player.getMainDancer().getCoordinates().getY() == coordinates.getY()){
                System.out.println("collision!");
                return true;
            }
        }
        return false;
    }

    private int distanceToMainDancer(Coordinates coords){
        Coordinates startCoordsFromLastMove = currentPlayer().getMainDancer().getCoordinates();
        int distance = Math.abs(startCoordsFromLastMove.getX() - coords.getX()) + Math.abs(startCoordsFromLastMove.getY() - coords.getY());
        return distance;
    }

    /**
     * Moves the current players main dancer a certain amount of steps in x and y direction.
     * @param x how much to move the player along x
     * @param y how much to move the player along y
     */
    public void moveSelection(int x, int y){
        int moveLimit = currentPlayer().getSteps(selectedCard);
        Coordinates newCoords = new Coordinates(selectionOnTileCoords.getX() + x, selectionOnTileCoords.getY() + y);

        if (insideDanceFloor(newCoords) && (distanceToMainDancer(newCoords) <= moveLimit)
                && (!(collisionOtherPlayer(newCoords))))
        {
            selectionOnTileCoords = newCoords;
            moveMainDancerOfCurrentPlayerToCoords(newCoords);
        }
    }

    public List<Card> currentlyOpenCards(){
        return currentPlayer().getHand();
    }

    private int countTotalTiles(){
        int sum = 0;
        for(int row = 0; row < danceFloor.mapHeightInTiles; row++){
            for(int col = 0; col < danceFloor.mapWidthInTiles; col++){
                if(danceFloor.getType(new Coordinates(col, row)) != Type.EMPTY){
                    sum++;
                }
            }

        }
        return sum;
    }

    private int countTiles(Player player){
        int sum = 0;
        for(int row = 0; row < danceFloor.mapHeightInTiles; row++){
            for(int col = 0; col < danceFloor.mapWidthInTiles; col++){
                if(danceFloor.getColor(new Coordinates(col, row)) == player.getColor()){
                    sum++;
                }
            }
        }
        return sum;
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
