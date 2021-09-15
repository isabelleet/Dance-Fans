package com.mygdx.game.model;

public class ObjectOnFloor {

    private int id;
    private String name;

    public ObjectOnFloor(String name){
        this.name = name;
    }

    public String getSpriteName() {
        return this.name;
    }
}
