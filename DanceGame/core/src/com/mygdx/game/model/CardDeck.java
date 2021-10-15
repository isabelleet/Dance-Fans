package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * CardDeck stores the different piles of cards which a deck is divided into. It also handles moving the cards between
 * the different piles.
 *
 * Is used by Model,
 *
 * Uses Card.
 *
 * @author Hedy Pettersson
 * @author Jakob Persson
 */

public class CardDeck {
    // thinking about using maps with enums instead, but not sure
    private final List<Card> deck;
    private final List<Card> open = new ArrayList<>();
    private final List<Card> closed;
    private final List<Card> discarded = new ArrayList<>();

    private final static PatternOccupant E = PatternOccupant.EMPTY;
    private final static PatternOccupant DF = PatternOccupant.DANCEFAN;
    private final static PatternOccupant MD = PatternOccupant.MAINDANCER;

    // should decks be predefined? or have a random selection of cards of the right type?

    /**
     * Creates a new object with a List of cards.
     * @param deck a list of cards.
     */
    public CardDeck(List<Card> deck) {
        this.deck = deck;
        // if they are predefined we should probably shuffle them before playing
        shuffleDeck(this.deck);

        this.closed = copyCards(this.deck);
        // Starts with two open cards
        openCard(0);
        openCard(0);

    }

    /**
     * Discards the selected card and adds it to the discard pile.
     */
    public void useCard(int selected) {
        System.out.println("Card was used");
        discardCard(selected);
        openCard(selected);
        selected = 0;
    }

    /**
     * Gets the steps from the currently selected card.
     * @return the steps from the currently selected card.
     */
    public int getSteps(int selected){
        return open.get(selected).getSteps();
    }

    /**
     * Gets the dance pattern of the currently selected card.
     * @return the dance pattern of the currently selected card.
     */
    public PatternOccupant[][] getPattern(int selected){
        return open.get(selected).getDancePattern();
    }

    /**
     * takes a card from the deck and puts it among the cards that can be chosen
     */
    private void openCard(int i) {
        if (this.closed.size() == 0) {
            addDiscCards();
        }

        this.open.add(i, closed.remove(0));
    }

    /**
     * removes a chosen card and moves it to the discarded pile
     *
     * @param i which of the chosen cards to remove
     */
    private void discardCard(int i) {
        this.discarded.add(this.open.remove(i));
    }

    /**
     * after using up all cards this method puts them back in the pile
     */
    private void addDiscCards() {
        int l = this.discarded.size();
        shuffleDeck(discarded);
        for (int i = 0; i < l; i++) {
            this.closed.add(this.discarded.remove(0));
        }
    }

    /**
     * should create a copy of all the cards in a deck.
     *
     * @param deck to be copied
     * @return a copy of the deck
     */
    private List<Card> copyCards(List<Card> deck) {
        int l = deck.size();

        return new ArrayList<>(deck);
    }

    /**
     * used to be able to look at what cards can be chosen
     *
     * @return the list of cards that can be chosen
     */
    public List<Card> getOpen() {
        return open;
    }

    /**
     * used to set some starter decks
     *
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
            //cards.add(new Card(2,pattern,3 ));
            //cards.add(new Card(2,pattern,3 ));

            pattern = new PatternOccupant[][]{
                    {E, DF, DF},
                    {DF, MD, E},
                    {DF, E, E}};
            cards.add(new Card(7, pattern, 2));
            //cards.add(new Card(3, pattern, 1));

            pattern = new PatternOccupant[][]{
                    {E, E, E, DF, DF, E, E},
                    {E, E, E, DF, E, E, E},
                    {E, E, E, DF, E, E, E},
                    {E, E, E, MD, E, E, E},
                    {E, E, E, E, E, E, E},
                    {E, E, E, E, E, E, E},
                    {E, E, E, E, E, E, E}};
            cards.add(new Card(1, pattern, 4));
            //cards.add(new Card(5, pattern, 2));
            //cards.add(new Card(5, pattern, 2));

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
            //cards.add(new Card(2,pattern,3 ));
            //cards.add(new Card(2,pattern,3 ));

            pattern = new PatternOccupant[][]{
                    {DF, DF, DF},
                    {DF, MD, E},
                    {DF, E, DF}};
            cards.add(new Card(3, pattern, 1));
            //cards.add(new Card(3, pattern, 1));

            pattern = new PatternOccupant[][]{
                    {E, DF, E},
                    {DF, MD, DF},
                    {E, DF, E}};
            cards.add(new Card(5, pattern, 2));
            //cards.add(new Card(5, pattern, 2));
            //cards.add(new Card(5, pattern, 2));

            pattern = new PatternOccupant[][]{
                    {E, E, E, E, E},
                    {E, E, E, E, DF},
                    {E, E, MD, DF, DF},
                    {E, E, E, E, DF},
                    {E, E, E, E, E}};
            cards.add(new Card(6, pattern, 3));
            //cards.add(new Card(6, pattern, 3));
        }


        return new CardDeck(cards);

    }

    /**
     * A shuffle method based on Fisher-Yates shuffle. So that the cards will be shuffled. Changes the original
     * deck of cards.
     *
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

