package com.mygdx.game;

public class Dancer  {
    private String spriteName = "";

    public void setSpriteName(String name) {
        this.spriteName = name;
    }

    public String getSpriteName(){
        return this.spriteName;
    }
    // Constructor
    public Dancer(String spriteName) {
        this.spriteName = spriteName;
    }

}
