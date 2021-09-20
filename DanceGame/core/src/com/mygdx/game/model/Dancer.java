package com.mygdx.game.model;

public class Dancer {

    private int id;
    private String name;

    private int x;
    private int y;

    public Dancer(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Dancer(String name){
        this.name = name;
        this.x = 0;
        this.y = 0;
    }

    public void setCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }


    public String getSpriteName() {
        return this.name;
    }
}
