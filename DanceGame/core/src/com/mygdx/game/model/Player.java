package com.mygdx.game.model;

public class Player {

    public Enum<PlayerTurnSlot> playerTurnSlot;
    // Timer left (if you use some clock that is saved between turns, so you sometimes can do fast turns and sometimes long.
    private MainDancer mainDancer;
    private CardDeck cardDeck;
    private DanceFan danceFan;
    private DanceFan transparentDanceFan;


    public Player(Enum<PlayerTurnSlot> playerTurnSlot, MainDancer mainDancer, CardDeck cardDeck, DanceFan danceFan, DanceFan transparentDanceFan) {
        this.playerTurnSlot = playerTurnSlot;
        this.mainDancer = mainDancer;
        this.cardDeck = cardDeck;
        this.danceFan = danceFan;
        this.transparentDanceFan = transparentDanceFan;
    }

    public MainDancer getMainDancer() {
        return mainDancer;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public DanceFan getDanceFan() { return danceFan; }

    public DanceFan getTransparentDanceFan() {return transparentDanceFan;}
}