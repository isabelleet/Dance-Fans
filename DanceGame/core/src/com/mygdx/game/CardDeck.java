package com.mygdx.game;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class CardDeck {
    private Enum<DanceType> danceType;
    private List<Card> deck;
    private List<Card> open;
    private List<Card> closed;
    private List<Card> discarded;

    // should decks be predefined? or have a random selection of cards of the right type?
    public CardDeck(Enum<DanceType> danceType, List<Card> deck){
        this.danceType = danceType;
        this.deck = deck;
        // if they are predefined we should probably shuffle them before playing
        ShuffleDeck(this.deck);

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

