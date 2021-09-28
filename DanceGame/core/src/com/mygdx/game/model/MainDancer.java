package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;

public class MainDancer extends Dancer implements Serializable {

    private Sprite dancerImg;
    private int previewIndex;

    public MainDancer(String name) {
        super(name);
        //greenDancer = textureAtlas.createSprite("greenDanceFan");
    }

    public MainDancer(String name, int index){
        super(name, index);
    }

    public int getPreviewIndex(){
        return previewIndex;
    }

    public void setPreviewIndex(int index){
        this.previewIndex = index;
    }
}
