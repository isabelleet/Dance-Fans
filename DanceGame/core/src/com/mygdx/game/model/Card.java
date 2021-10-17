package com.mygdx.game.model;

import com.mygdx.game.model.Enums.PatternOccupant;

/**
 * Card combines amount of steps a player can move with a pattern for how the dance floor will change.
 * It also has an id in order to match it up with the correct image.
 *
 * Is used in CardDeck, Model, Player.
 *
 * Uses PatternOccupant.
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Hedy Pettersson
 * @author Johan Berg
 */

public class Card {
    private final int id;
    private final PatternOccupant[][] dancePattern;
    private final int steps;

    /**
     * Constructor, creates a new card object.
     * @param id position of the card in the deck.
     * @param dancePattern an array containing the information needed to know which tiles should be changed when the card is used.
     * @param steps how far the player is allowed to move when using the card.
     */
    public Card(int id, PatternOccupant[][] dancePattern, int steps) {
        this.id = id;
        this.dancePattern = dancePattern;
        this.steps = steps;
    }

    /**
     * Getter for a cards id.
     * @return an int of the cards id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for a cards dancePattern.
     * @return a copy of the matrix of the dancePattern.
     */
    public PatternOccupant[][] getDancePattern() {
        return copy();
    }

    /**
     * Getter for the amount of steps on that card.
     * @return an int of the amount of steps.
     */
    public int getSteps() {
        return this.steps;
    }

    /**
     * Used to make the patterns of the cards immutable.
     * @return a copy of the dance pattern the card has.
     */
    private PatternOccupant[][] copy(){
        PatternOccupant[][] copy = new PatternOccupant[dancePattern.length][dancePattern[0].length];
        for(int row = 0; row < dancePattern.length; row++){
            for(int col = 0; col < dancePattern[0].length; col++){
                copy[row][col] = dancePattern[row][col];
            }
        }
        return copy;
    }

}
