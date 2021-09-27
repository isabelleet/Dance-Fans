package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class MainDancer extends Dancer {

    private Sprite dancerImg;
    private int previewIndex;

    public MainDancer(String name) {
        super(name);
        //greenDancer = textureAtlas.createSprite("greenDancer");
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
