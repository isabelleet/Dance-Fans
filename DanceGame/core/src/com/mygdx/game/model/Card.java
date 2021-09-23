package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Card {
    private Enum<DanceType> danceType;
    private int id;
    // how to store pattern? for now an array
    private int[][] dancePattern;
    private int steps;

    public Card(int id, int[][] dancePattern, int steps){
        this.id = id;
        this.dancePattern = dancePattern;
        this.steps = steps;
    }

    // example card
    public Card(){
        this.id = 1;
        this.dancePattern = new int[][]{{0, 1, 0}, {0, 0, 0}, {1, 0, 1}};
        this.steps = 2;
    }

    // example card
    public Card(int id){
        this.id = id;
        this.dancePattern = new int[][]{{0, 1, 0}, {0, 0, 0}, {1, 0, 1}};
        this.steps = 2;
    }

    public int getId(){
        return id;
    }

    public void cardClicked() {
        System.out.println("Clicked on card");
    }
}
