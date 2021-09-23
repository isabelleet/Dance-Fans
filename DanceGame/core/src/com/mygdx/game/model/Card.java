package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Card {
    private Enum<DanceType> danceType;
    private Sprite spritImg;
    // how to store pattern? for now an array
    private int[][] dancePattern;
    private int steps;

    public Card(Sprite spritImg, int[][] dancePattern, int steps){
        this.spritImg = spritImg;
        this.dancePattern = dancePattern;
        this.steps = steps;
    }

    // example card
    public Card(){
        this.spritImg = new Sprite();
        this.spritImg.setTexture(new Texture("original_images/dancePattern1.png"));
        this.dancePattern = new int[][]{{0, 1, 0}, {0, 0, 0}, {1, 0, 1}};
        this.steps = 2;
    }


    public void cardClicked() {
        System.out.println("Clicked on card");
    }
}
