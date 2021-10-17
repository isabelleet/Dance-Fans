package com.mygdx.game.model;

import com.mygdx.game.Enums.PatternOccupant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * CardDeck stores the different piles of cards which a deck is divided into. It also handles moving the cards between
 * the different piles.
 *
 * Is used by Model, Player.
 *
 * Uses PatternOccupant, Card.
 *
 *
 * @author Hedy Pettersson
 * @author Jakob Persson
 */

public class CardDeck {
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> pile;
    private final List<Card> discarded = new ArrayList<>();

    private final static PatternOccupant E = PatternOccupant.EMPTY;
    private final static PatternOccupant DF = PatternOccupant.DANCEFAN;
    private final static PatternOccupant MD = PatternOccupant.MAINDANCER;

    /**
     * Constructor, initially opens two of the cards from the pile.
     * @param deck a list of cards the deck will contain.
     */
    protected CardDeck(List<Card> deck) {
        this.pile = copyCards(deck);
        shuffleDeck(deck);

        openCard(0);
        openCard(0);
    }

    /**
     * Takes the first card from the pile and puts it on hand.
     * @param i the location which the new card will be placed in the hand.
     */
    private void openCard(int i) {
        if (this.pile.size() == 0) {
            addDiscCards();
        }

        this.hand.add(i, pile.remove(0));
    }

    /**
     * Moves the selected card from the hand to the discard pile and replaces it.
     * @param selected which of the cards on hand to use and then replace.
     */
    public void useCard(int selected) {
        discardCard(selected);
        openCard(selected);
    }

    /**
     * Removes the selected from hand and adds it to the discarded pile.
     * @param i which of the cards on hand is selected.
     */
    private void discardCard(int i) {
        this.discarded.add(this.hand.remove(i));
    }

    /**
     * Returns a copy of the cards on hand.
     * @return a copy of the list of cards on hand.
     */
    public List<Card> getOpen() {
        return copyCards(hand);
    }

    /**
     * Gets the steps from the currently selected card.
     * @return the steps from the currently selected card.
     */
    protected int getSteps(int selected){
        return hand.get(selected).getSteps();
    }

    /**
     * Gets the dance pattern of the currently selected card.
     * @return the dance pattern of the currently selected card.
     */
    protected PatternOccupant[][] getPattern(int selected){
        return hand.get(selected).getDancePattern();
    }

    /**
     * After opening up all cards in the pile this method adds cards back from the discarded pile.
     */
    private void addDiscCards() {
        int l = this.discarded.size();
        shuffleDeck(discarded);
        for (int i = 0; i < l; i++) {
            this.pile.add(this.discarded.remove(0));
        }
    }

    /**
     * Create a copy of all the cards in a list. Used to get immutability outside of the class.
     * @param deck the list to be copied
     * @return a copy of the list
     */
    private List<Card> copyCards(List<Card> deck) {
        return new ArrayList<>(deck);
    }

    /**
     * Used to get a starter deck.
     * @param i 0 for a green deck, otherwise a red deck
     * @return either a deck with green cards or red cards
     */
    public static CardDeck initialDeck(int i) {
        List<Card> cards = new ArrayList<>();
        if (i == 0) {
            // not allowed to write the pattern directly, must send it the long way.
            PatternOccupant[][] pattern = {
                    {DF, E, DF},
                    {DF, MD, DF},
                    {DF, E, DF}};
            cards.add(new Card(4, pattern, 1));

            pattern = new PatternOccupant[][]{
                    {E, DF, DF},
                    {DF, MD, E},
                    {DF, E, E}};
            cards.add(new Card(7, pattern, 2));

            pattern = new PatternOccupant[][]{
                    {E, E, E, DF, DF, E, E},
                    {E, E, E, DF, E, E, E},
                    {E, E, E, DF, E, E, E},
                    {E, E, E, MD, E, E, E},
                    {E, E, E, E, E, E, E},
                    {E, E, E, E, E, E, E},
                    {E, E, E, E, E, E, E}};
            cards.add(new Card(1, pattern, 4));

            pattern = new PatternOccupant[][]{
                    {E, E, E, E, E},
                    {DF, E, E, E, E},
                    {DF, DF, MD, E, E},
                    {DF, E, E, E, E},
                    {E, E, E, E, E}};
            cards.add(new Card(8, pattern, 3));
        } else {
            // not allowed to write the pattern directly, must send it the long way.
            PatternOccupant[][] pattern = {
                    {DF, E, DF},
                    {E, MD, E},
                    {DF, E, DF}};
            cards.add(new Card(2, pattern, 3));

            pattern = new PatternOccupant[][]{
                    {DF, DF, DF},
                    {DF, MD, E},
                    {DF, E, DF}};
            cards.add(new Card(3, pattern, 1));

            pattern = new PatternOccupant[][]{
                    {E, DF, E},
                    {DF, MD, DF},
                    {E, DF, E}};
            cards.add(new Card(5, pattern, 2));

            pattern = new PatternOccupant[][]{
                    {E, E, E, E, E},
                    {E, E, E, E, DF},
                    {E, E, MD, DF, DF},
                    {E, E, E, E, DF},
                    {E, E, E, E, E}};
            cards.add(new Card(6, pattern, 3));
        }
        return new CardDeck(cards);
    }

    /**
     * A shuffle method based on Fisher-Yates shuffle. Changes the original deck of cards.
     * @param cards the cards to be shuffled
     */
    // Fisher-yates shuffle https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
    private void shuffleDeck(List<Card> cards) {
        Random random = new Random();
        int l = cards.size();

        for (int i = l - 1; i > 1; i--) {
            int a = random.nextInt(i + 1);
            Card temp = cards.get(a);
            cards.set(a, cards.get(i));
            cards.set(i, temp);
        }

    }

}

