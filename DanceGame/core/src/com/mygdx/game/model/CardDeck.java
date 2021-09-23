package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardDeck {
    // thinking about using maps with enums instead, but not sure
    private List<Card> deck;
    private List<Card> open;
    private List<Card> closed;
    private List<Card> discarded;

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
    private void OpenCard(){
        this.open.add(this.closed.get(0));
        closed.remove(0);
    }

    private void DiscardCard(int i){
        this.discarded.add(this.open.remove(i));
    }

    //might need to be public later
    private void AddDiscCards(){
        int l = this.discarded.size();
        for(int i = 0; i < l; i++){
            this.deck.add(this.discarded.remove(i));
        }
    }

    // not sure if necessary or not
    private List<Card> CopyCards(List<Card> deck){
        int l = deck.size();

        return new ArrayList<>(deck);
    }

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

