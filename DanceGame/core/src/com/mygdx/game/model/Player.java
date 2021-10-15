package com.mygdx.game.model;

import java.util.List;

/**
 * Player combines the different things a player should have, such as a MainDancer,
 * a danceFan (as well as a transparent version of it), and a CardDeck.
 *
 * Is used in Model.
 *
 * Uses DanceFan, MainDancer.
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Johan Berg
 * @author Hedy Pettersson
 */

public class Player {

    public Enum<PlayerTurnSlot> playerTurnSlot;
    // Timer left (if you use some clock that is saved between turns, so you sometimes can do fast turns and sometimes long.
    private final MainDancer mainDancer;
    private final CardDeck cardDeck;
    private final DanceFan danceFan;
    private final DanceFan transparentDanceFan;

    /**
     * Cretes a new Player object.
     * @param playerTurnSlot if the player goes first or second.
     * @param mainDancer the players MainDancer.
     * @param cardDeck the players deck of cards.
     * @param danceFan the type of fans the player wants.
     * @param transparentDanceFan the dancefans used to preview a dancemove.
     */
    public Player(Enum<PlayerTurnSlot> playerTurnSlot, MainDancer mainDancer, CardDeck cardDeck, DanceFan danceFan, DanceFan transparentDanceFan) {
        this.playerTurnSlot = playerTurnSlot;
        this.mainDancer = mainDancer;
        this.cardDeck = cardDeck;
        this.danceFan = danceFan;
        this.transparentDanceFan = transparentDanceFan;
    }

    /**
     * Getter for the players MainDancer object.
     * @return a MainDancer.
     */
    public MainDancer getMainDancer() {
        return mainDancer;
    }

    /**
     * A getter for how many steps a player can take with the selected card.
     * @param selected which of the cards on hand one wants to get the steps from
     * @return an int with the amount of steps a player can take.
     */
    public int getSteps(int selected){
        return cardDeck.getSteps(selected);
    }

    /**
     * Gets the dance pattern from the card selected
     * @param selected which of the cards on hand one wants to get the dance pattern from
     * @return the dance patter of the selected card
     */
    public PatternOccupant[][] getPattern(int selected){
        return cardDeck.getPattern(selected);
    }

    /**
     * Gets the cards a player can currently use.
     * @return the cards a player can currently use.
     */
    public List<Card> getHand(){
        return cardDeck.getOpen();
    }

    /**
     * Uses the card selected in the cardDeck.
     * @param selected which card on hand one wants to use
     */
    public void useCard(int selected){
        cardDeck.useCard(selected);
    }

    /**
     * Getter for the players DanceFans.
     * @return a DanceFan.
     */
    public DanceFan getDanceFan() {
        return danceFan;
    }

    /**
     * Getter for the players DanceFans used to preview moves.
     * @return a DanceFan.
     */
    public DanceFan getTransparentDanceFan() {
        return transparentDanceFan;
    }
}