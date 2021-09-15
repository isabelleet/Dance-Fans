package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Arrays;


public class Card {
    private Enum<DanceType> danceType;
    private Texture img;
    // how to store pattern? for now an array
    private int[][] dancePattern;
    private int steps;

    public Card(Enum<DanceType> danceType, Texture img, int[][] dancePattern, int steps){
        this.danceType = danceType;
        this.img = img;
        this.dancePattern = dancePattern;
        this.steps = steps;
    }

    // example card
    public Card(){
        this.danceType = DanceType.Waltz;
        this.img = new Texture("original_images/dancePattern1.png");
        this.dancePattern = new int[][]{{0, 1, 0}, {0, 0, 0}, {1, 0, 1}};
        this.steps = 2;
    }

    public Texture getImg() {
        return img;
    }
}
