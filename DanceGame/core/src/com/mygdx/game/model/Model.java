package com.mygdx.game.model;
import com.mygdx.game.Enums.Color;
import com.mygdx.game.Enums.PatternOccupant;
import com.mygdx.game.Enums.PlayerTurnSlot;
import com.mygdx.game.Enums.Type;

import java.lang.Math;

import java.util.ArrayList;
import java.util.List;

/**
 * Model combines many of the other classes in the package, and is what is accessed from outside of the package.
 * It can start new games, calculate if someone has won, move the MainDancer:s around.
 *
 * Is used by Controller, DanceFans, View.
 *
 * Uses Color, PatternOccupant, PlayerTurnSlot, Type, Card, CardDeck, Coordinates, DanceFloor, Player.
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
    public Coordinates selectedCoordinates;
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

        Player player1 = new Player(PlayerTurnSlot.ONE, new MainDancer(Color.RED, new Coordinates(2,4)), CardDeck.initialDeck(0));
        Player player2 = new Player(PlayerTurnSlot.TWO, new MainDancer(Color.GREEN, new Coordinates(0,0)), CardDeck.initialDeck(1));

        this.players[0] = player1;
        this.players[1] = player2;

        this.danceFloor = new DanceFloor();

        // Player ONE starts
        this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
        this.selectedCoordinates = currentPlayer().getCoordinates();

        this.danceFloor.newObjectOnTile(player1.getMainDancer());
        this.danceFloor.newObjectOnTile(player2.getMainDancer());

        // Initialize previewdance floor
        this.previewDanceFloor = this.danceFloor.copy();

        //The first turn the cards are drawn right away.
        this.hasPlayerStartedTheirTurn = true;
        //TODO: temporary way to display the preview immediately
        resetDancer();
    }

    /**
     * Updates the state of the DanceFloor to match the state of the PreviewDanceFloor and changes whose turn it is.
     */
    public void playerConfirmedDanceMove() {
        currentPlayer().useCard(selectedCard);
        selectedCard = 0;

        // Update tiles of the previewed transparent dancefans to dancefans when the player ends the turn
        for (int i = 0; i < tileCoords.size(); i++) {
            this.previewDanceFloor.newObjectOnTile(tileCoords.get(i), currentPlayer().getDanceFan());
        }

        this.danceFloor = previewDanceFloor.copy();
        this.currentPlayer().setCoordinates(this.currentPlayer().getPreviewCoordinates());

        changeWhichPlayersTurnItIs();
        this.selectedCoordinates = currentPlayer().getCoordinates();
        this.hasPlayerStartedTheirTurn = false;
    }

    /**
     * Changes which players turn it is.
     */
    public void changeWhichPlayersTurnItIs(){
        turnNumber++;

        if (whichPlayersTurnItIs == PlayerTurnSlot.ONE) {
            this.whichPlayersTurnItIs = PlayerTurnSlot.TWO;
        }
        else {
            this.whichPlayersTurnItIs = PlayerTurnSlot.ONE;
        }
    }

    /**
     * Sets hasPlayerStartedTheirTurn to true.
     */
    public void playerDrewCardsToStartTurn(){
        this.hasPlayerStartedTheirTurn = true;
        //TODO: uses resetDancer to get the preview to display immediately
        resetDancer();
    }

    /**
     * moves the current players mainDancer back to the position it had at the start of the players turn.
     */
    public void resetDancer(){
        selectedCoordinates = currentPlayer().getCoordinates();
        moveMainDancerOfCurrentPlayerToCoords(selectedCoordinates);
    }

    /**
     * Moves the MainDancer of the player whose turn it is currently to the specified coordinates.
     * @param coordsMovedTo - Which index the MainDancer should be moved to.
     */
    public void moveMainDancerOfCurrentPlayerToCoords(Coordinates coordsMovedTo){
        // Clear list before the player moves so only the last preview indexes are stored in the list
        tileCoords.clear();
        Coordinates mdCoords = currentPlayer().getCoordinates();

        // Reset the preview to the last state of the dancefloor and maindancer positions
        // Sets currentplayers maindancer preview index to last turns index
        currentPlayer().setPreviewCoordinates(mdCoords);

        //each time we try a new preview, previewDanceFloor should reset to dancerfloor from previous completed turn.
        this.previewDanceFloor = danceFloor.copy();
        // Update index on dancefloor for main dancer preview, according to input
        this.currentPlayer().setPreviewCoordinates(coordsMovedTo);
        // Update dancefloor
        previewDanceFloor.removeObjectFromTileIndex(mdCoords);
        previewDanceFloor.removeObjectFromTileIndex(currentPlayer().getPreviewCoordinates());
        previewDanceFloor.newObjectOnTile(coordsMovedTo, currentPlayer().getMainDancer());

        this.previewDanceFloor.addDanceFansFromPattern(currentPlayer().getPreviewCoordinates(), currentPlayer().getTransparentDanceFan(), currentPlayer().getPattern(selectedCard));
        tileCoords = previewDanceFloor.getTransparentCoordinates();
    }

    /**
     * Moves the current players main dancer a certain amount of steps in x and y direction.
     * @param x how much to move the player along x
     * @param y how much to move the player along y
     */
    public void moveSelection(int x, int y){
        int moveLimit = currentPlayer().getSteps(selectedCard);
        Coordinates newCoords = new Coordinates(selectedCoordinates.getX() + x, selectedCoordinates.getY() + y);

        if (danceFloor.insideDanceFloor(newCoords) && (distanceToMainDancer(newCoords) <= moveLimit)
                && (!(collisionOtherPlayer(newCoords))))
        {
            selectedCoordinates = newCoords;
            moveMainDancerOfCurrentPlayerToCoords(newCoords);
        }
    }

    /**
     * Getter of the cards currently on hand for the current player.
     * @return a list of the cards on hand for the current player.
     */
    public List<Card> cardsOnHand(){
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

    /**
     * Returns the playerTurnSlot representing which player is currently in the lead.
     * @return the playerTurnSlot of the player which is currently in the lead.
     */
    public PlayerTurnSlot isLeading(){
        if (countTiles(players[0]) > countTiles(players[1])){
            return players[0].playerTurnSlot;
        }
        else{
            return players[1].playerTurnSlot;
        }
    }

    /**
     * Getter for the turn number.
     * @return which turn it is.
     */
    public int numberTurns(){
        return turnNumber/2;
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

}
