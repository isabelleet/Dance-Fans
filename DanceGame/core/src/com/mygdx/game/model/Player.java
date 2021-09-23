package com.mygdx.game.model;

public class Player {

    public Enum <PlayerTurnSlot> playerTurnSlot;
    // handOfCards?
    // Timer left (if you use some clock that is saved between turns, so you sometimes can do fast turns and sometimes long.
    private MainDancer mainDancer;


    public Player(Enum<PlayerTurnSlot> playerTurnSlot, MainDancer mainDancer){
        this.playerTurnSlot = playerTurnSlot;
        this.mainDancer = mainDancer;
    }

    public MainDancer getMainDancer() {
        return mainDancer;
    }
}
