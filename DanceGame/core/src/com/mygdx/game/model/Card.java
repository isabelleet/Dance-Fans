package com.mygdx.game.model;

/**
 * Card combines amount of steps a player can move with a pattern for how the dance floor will change.
 * It also has an id in order to match it up with the correct image.
 *
 * Is used by CardDeck,
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Hedy Pettersson
 * @author Johan Berg
 */

public class Card {
    private int id;
    private int[][] dancePattern;
    private int steps;

    /**
     * Creates a new card object.
     * @param id position of the card in the deck.
     * @param dancePattern an array containing the information needed to know which tiles should be changed when the card is used.
     * @param steps how far the player is allowed to move when using the card.
     */
    public Card(int id, int[][] dancePattern, int steps) {
        this.id = id;
        this.dancePattern = dancePattern;
        this.steps = steps;
    }

    // example card

    // 3 indicates the position of the maindancer, 1 indicates new dancefan, 0 = don't change this tile

    /**
     * Used for testing purposes.
     */
    public Card() {
        this.id = 1;
        this.dancePattern = new int[][]{{0, 1, 0}, {0, 3, 0}, {1, 0, 1}};
        this.steps = 2;
    }

    // example card

    /**
     * Used for testing purposes.
     * @param id card position in the deck.
     */
    public Card(int id) {
        this.id = id;
        this.dancePattern = new int[][]{{0, 1, 0}, {0, 3, 0}, {1, 0, 1}};
        this.steps = 2;
    }

    /**
     * Getter for a cards id.
     * @return an int of the cards id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for a cards dancePattern. 1 = dancefan, 3 = position of the MainDancer.
     * @return a matrix of the dancePattern.
     */
    public int[][] getDancePattern() {
        return this.dancePattern;
    }

    /**
     * Getter for the amount of steps on that card.
     * @return an int of the amount of steps.
     */
    public int getSteps() {
        return this.steps;
    }

}
