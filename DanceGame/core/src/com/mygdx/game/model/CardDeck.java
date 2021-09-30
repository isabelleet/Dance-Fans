package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Stores the different piles of cards which a deck can be divided into.
 */
public class CardDeck {
    // thinking about using maps with enums instead, but not sure
    private List<Card> deck;
    private List<Card> open = new ArrayList<>();
    private List<Card> closed;
    private List<Card> discarded = new ArrayList<>();

    public int selected = 0;

    // should decks be predefined? or have a random selection of cards of the right type?
    public CardDeck(List<Card> deck){
        this.deck = deck;
        // if they are predefined we should probably shuffle them before playing
        shuffleDeck(this.deck);

        this.closed = copyCards(this.deck);
        // Starts with two open cards
        openCard(0);
        openCard(0);

    }

    public void useCard(){
        System.out.println("Card was used");
        discardCard(selected);
        openCard(selected);
        selected = 0;
    }

    // don't need to open cards outside.

    /**
     * takes a card from the deck and puts it among the cards that can be chosen
     */
    private void openCard(int i){
        if(this.closed.size() == 0){
            addDiscCards();
        }

        this.open.add(i, closed.remove(0));
    }

    /**
     * removes a chosen card and moves it to the discarded pile
     * @param i which of the chosen cards to remove
     */
    private void discardCard(int i){
        this.discarded.add(this.open.remove(i));
    }

    /**
     * after using up all cards this method puts them back in the pile
     */
    private void addDiscCards(){
        int l = this.discarded.size();
        shuffleDeck(discarded);
        for(int i = 0; i < l; i++){
            this.closed.add(this.discarded.remove(0));
        }
    }

    /**
     * should create a copy of all the cards in a deck.
     * @param deck to be copied
     * @return a copy of the deck
     */
    private List<Card> copyCards(List<Card> deck){
        int l = deck.size();

        return new ArrayList<>(deck);
    }

    /**
     * used to be able to look at what cards can be chosen
     * @return the list of cards that can be chosen
     */
    public List<Card> getOpen() {
        return open;
    }

    /**
     * used to set some starter decks
     * @param i 0 for a green deck, otherwise a red deck
     * @return either a deck with green cards or red cards
     */
    public static CardDeck initialDeck(int i){
        List<Card> cards = new ArrayList<>();
        if(i == 0) {
            // not allowed to write the pattern directly, must send it the long way.
            int[][] pattern = {
                    {1, 0, 1},
                    {0, 3, 0},
                    {1, 0, 1}};
            cards.add(new Card(2, pattern, 3));
            //cards.add(new Card(2,pattern,3 ));
            //cards.add(new Card(2,pattern,3 ));

            pattern = new int[][]{
                    {1, 1, 1},
                    {1, 3, 0},
                    {1, 0, 1}};
            cards.add(new Card(3, pattern, 1));
            //cards.add(new Card(3, pattern, 1));

            pattern = new int[][]{
                    {0, 1, 0},
                    {1, 3, 1},
                    {0, 1, 0}};
            cards.add(new Card(5, pattern, 2));
            //cards.add(new Card(5, pattern, 2));
            //cards.add(new Card(5, pattern, 2));

            pattern = new int[][]{
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1},
                    {0, 0, 3, 1, 1},
                    {0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 0}};
            cards.add(new Card(6, pattern, 3));
            //cards.add(new Card(6, pattern, 3));


        }else{
            // not allowed to write the pattern directly, must send it the long way.
            int[][] pattern = {
                    {1, 0, 1},
                    {1, 3, 1},
                    {1, 0, 1}};
            cards.add(new Card(4, pattern, 1));
            //cards.add(new Card(2,pattern,3 ));
            //cards.add(new Card(2,pattern,3 ));

            pattern = new int[][]{
                    {0, 1, 1},
                    {1, 3, 0},
                    {1, 0, 0}};
            cards.add(new Card(7, pattern, 2));
            //cards.add(new Card(3, pattern, 1));

            pattern = new int[][]{
                    {0, 0, 0, 1, 1, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 3, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0}};
            cards.add(new Card(1, pattern, 4));
            //cards.add(new Card(5, pattern, 2));
            //cards.add(new Card(5, pattern, 2));

            pattern = new int[][]{
                    {0, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 1, 3, 0, 0},
                    {1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}};
            cards.add(new Card(8, pattern, 3));
            //cards.add(new Card(6, pattern, 3));
        }


        return new CardDeck(cards);

    }

    /**
     * A shuffle method based on Fisher-Yates shuffle. So that the cards will be shuffled. Changes the original
     * deck of cards.
     * @param cards the cards to be shuffled
     */
    // Fisher-yates shuffle https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
    private void shuffleDeck(List<Card> cards){
        Random random = new Random();
        int l = cards.size();

        for(int i = l-1; i > 1; i--){
            int a = random.nextInt(i+1);
            Card temp = cards.get(a);
            cards.set(a, cards.get(i));
            cards.set(i, temp);
        }

    }

}

