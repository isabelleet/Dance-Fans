package com.mygdx.game.model;

import com.mygdx.game.Enums.Color;
import com.mygdx.game.Enums.PatternOccupant;
import com.mygdx.game.Enums.Type;

import java.util.List;

/**
 * Player combines the different things a player should have, such as a MainDancer,
 * a danceFan (as well as a transparent version of it), and a CardDeck.
 * <p>
 * Is used in Model.
 * <p>
 * Uses Color, PatternOccupant, Type, Card, CardDeck, Coordinates, DanceFan, MainDancer.
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Johan Berg
 * @author Hedy Pettersson
 */

public class Player {
    private final MainDancer mainDancer;
    private final CardDeck cardDeck;
    private final DanceFan danceFan;
    private final DanceFan transDanceFan;

    /**
     * Creates a new Player object.
     *
     * @param mainDancer the players MainDancer.
     * @param cardDeck   the players deck of cards.
     */
    protected Player(MainDancer mainDancer, CardDeck cardDeck) {
        this.mainDancer = mainDancer;
        this.cardDeck = cardDeck;
        this.danceFan = new DanceFan(mainDancer.getColor(), Type.DF, new Coordinates(0, 0));
        this.transDanceFan = new DanceFan(mainDancer.getColor(), Type.TRANSDF, new Coordinates(0, 0));
    }

    /**
     * Getter for the players MainDancer.
     *
     * @return a MainDancer.
     */
    protected MainDancer getMainDancer() {
        return mainDancer;
    }

    /**
     * Getter for the players DanceFan.
     *
     * @return a copy of the DanceFan.
     */
    protected DanceFan getDanceFan() {
        return new DanceFan(danceFan.getColor(), danceFan.getType(), danceFan.getCoordinates());
    }

    /**
     * Getter for the players DanceFans used to preview moves.
     *
     * @return a copy of the DanceFan.
     */
    public DanceFan getTransparentDanceFan() {
        return new DanceFan(transDanceFan.getColor(), transDanceFan.getType(), transDanceFan.getCoordinates());
    }

    // law of demeter handling

    /**
     * Getter of the mainDancers color.
     *
     * @return the color of the mainDancer.
     */
    public Color getColor() {
        return mainDancer.getColor();
    }

    /**
     * Getter of the mainDancers coordinates
     *
     * @return the coordinates of the mainDancer.
     */
     public Coordinates getCoordinates() {
        return mainDancer.getCoordinates();
    }

    /**
     * Getter of the mainDancers preview Coordinates.
     *
     * @return the coordinates of the mainDancer in the preview.
     */
    public Coordinates getPreviewCoordinates() {
        return mainDancer.getPreviewCoordinates();
    }

    /**
     * Setter of the mainDancers coordinates.
     *
     * @param coords the coordinates to change to.
     */
     public void setCoordinates(Coordinates coords) {
        mainDancer.setCoordinates(coords);
    }

    /**
     * Setter of the mainDancers coordinates.
     *
     * @param coords the coordinates to change to.
     */
     void setPreviewCoordinates(Coordinates coords) {
        mainDancer.setPreviewCoordinates(coords);
    }

    /**
     * A getter for how many steps a player can take with the selected card.
     *
     * @param selected which of the cards on hand one wants to get the steps from
     * @return an int with the amount of steps a player can take.
     */
     int getSteps(int selected) {
        return cardDeck.getSteps(selected);
    }

    /**
     * Gets the dance pattern from the card selected
     *
     * @param selected which of the cards on hand one wants to get the dance pattern from
     * @return the dance patter of the selected card
     */
     PatternOccupant[][] getPattern(int selected) {
        return cardDeck.getPattern(selected);
    }

    /**
     * Gets the cards a player can currently use.
     *
     * @return the cards a player can currently use.
     */
     List<Card> getHand() {
        return cardDeck.getOpen();
    }

    /**
     * Uses the card selected in the cardDeck.
     *
     * @param selected which card on hand one wants to use
     */
     void useCard(int selected) {
        cardDeck.useCard(selected);
    }
}