package com.mygdx.game.model;

public class Player {

    public Enum <PlayerTurnSlot> playerTurnSlot;
    // handOfCards
    //private MainDancer mainDancer;


    public Player(Enum<PlayerTurnSlot> playerTurnSlot
    //        , MainDancer mainDancer
    ){
        this.playerTurnSlot = playerTurnSlot;
        //this.mainDancer = mainDancer;
    }
}
