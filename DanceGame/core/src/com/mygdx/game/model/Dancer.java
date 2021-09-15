package com.mygdx.game.model;

public class Dancer {

    private int id;
    private String name;

    public Dancer(String name){
        this.name = name;
    }

    public String getSpriteName() {
        return this.name;
    }
}
