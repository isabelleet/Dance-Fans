package com.mygdx.game.model;

public class Player {

    public Enum <PlayerTurnSlot> playerTurnSlot;
    // handOfCards?
    // Timer left (if you use some clock that is saved between turns, so you sometimes can do fast turns and sometimes long.
    private MainDancer mainDancer;

    private CardDeck cardDeck;


    public Player(Enum<PlayerTurnSlot> playerTurnSlot, MainDancer mainDancer, CardDeck cardDeck){
        this.playerTurnSlot = playerTurnSlot;
        this.mainDancer = mainDancer;
        this.cardDeck =  cardDeck;
    }

    public MainDancer getMainDancer() {
        return mainDancer;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }
}
