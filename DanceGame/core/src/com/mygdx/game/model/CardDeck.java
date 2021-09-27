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

    // should decks be predefined? or have a random selection of cards of the right type?
    public CardDeck(List<Card> deck){
        this.deck = deck;
        // if they are predefined we should probably shuffle them before playing
        ShuffleDeck(this.deck);

        this.closed = CopyCards(this.deck);
        // Starts with two open cards
        OpenCard();
        OpenCard();

    }

    public void UseCard(int i){
        DiscardCard(i);
        OpenCard();
    }

    // don't need to open cards outside.

    /**
     * takes a card from the deck and puts it among the cards that can be chosen
     */
    private void OpenCard(){
        if(this.closed.size() == 0){
            addDiscCards();
        }

        this.open.add(this.closed.get(0));
        closed.remove(0);
    }

    /**
     * removes a chosen card and moves it to the discarded pile
     * @param i which of the chosen cards to remove
     */
    private void DiscardCard(int i){
        this.discarded.add(this.open.remove(i));
    }

    /**
     * after using up all cards this method puts them back in the pile
     */
    private void addDiscCards(){
        int l = this.discarded.size();
        for(int i = 0; i < l; i++){
            this.closed.add(this.discarded.remove(0));
        }
    }

    /**
     * should create a copy of all the cards in a deck.
     * @param deck to be copied
     * @return a copy of the deck
     */
    private List<Card> CopyCards(List<Card> deck){
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
     * A shuffle method based on Fisher-Yates shuffle. So that the cards will be shuffled. Changes the original
     * deck of cards.
     * @param cards the cards to be shuffled
     */
    // Fisher-yates shuffle https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
    private void ShuffleDeck(List<Card> cards){
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

