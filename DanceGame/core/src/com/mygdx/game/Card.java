package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Arrays;


public class Card {
    private Enum<DanceType> danceType;
    private Sprite img;
    // how to store pattern? for now an array
    private int[] dancePattern;
    private int steps;

    public Card(Enum<DanceType> danceType, Sprite img, int[] dancePattern, int steps){
        this.danceType = danceType;
        this.img = img;
        this.dancePattern = dancePattern;
        this.steps = steps;
    }

}
