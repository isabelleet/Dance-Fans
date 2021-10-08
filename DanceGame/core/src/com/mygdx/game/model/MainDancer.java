package com.mygdx.game.model;


import java.io.Serializable;

public class MainDancer extends Dancer implements Serializable {

    private int previewIndex;

    public MainDancer(String name) {
        super(name);
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
