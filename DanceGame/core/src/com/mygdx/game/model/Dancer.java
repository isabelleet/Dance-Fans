package com.mygdx.game.model;

public class Dancer {

    private int id;
    private String name;
    public Player fanOfPlayer;

    public Dancer(String name, Player player){
        this.name = name;
        this.fanOfPlayer = player;
    }

    public String getSpriteName() {
        return this.name;
    }
}



    //TODO: Add this for constructors to DanceFan class
    //private DanceFan redDancer = new DanceFan("redPlayer");
    //private DanceFan greenDancer = new DanceFan("greenPlayer");