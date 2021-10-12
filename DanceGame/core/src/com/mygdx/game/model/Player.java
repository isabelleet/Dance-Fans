package com.mygdx.game.model;

/**
 * Player combines the different things a player should have, such as a MainDancer,
 * a danceFan (as well as a transparent version of it), and a CardDeck.
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Johan Berg
 * @author Hedy Pettersson
 */

public class Player {

    public Enum<PlayerTurnSlot> playerTurnSlot;
    // Timer left (if you use some clock that is saved between turns, so you sometimes can do fast turns and sometimes long.
    private MainDancer mainDancer;
    private CardDeck cardDeck;
    private DanceFan danceFan;
    private DanceFan transparentDanceFan;

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
     * Getter for the players deck of cards.
     * @return a CardDeck.
     */
    public CardDeck getCardDeck() {
        return cardDeck;
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