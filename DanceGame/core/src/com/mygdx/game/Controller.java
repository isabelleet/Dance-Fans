package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public class Controller {

    private EventListener listener = new EventListener() {
        @Override
        public boolean handle(Event event) {
            return false;
        }
    };



    //TODO: Throw in a bunch of listeners and based on what buttons are detected call functions in model.
    // TODO: Replace stuff below with listeners

    public void detectInput(){
        int xOffset = (Model.tileSideLength + Gdx.graphics.getWidth() - Model.mapWidthInPixels)/2;
        int yOffset = (Model.tileSideLength + Gdx.graphics.getHeight() - Model.mapHeightInPixels)/2;
        // TODO: seems weird to include inputs inside of render function. I guess render is the main loop. Perhaps get this out somehow?


        // Inputs from mouse buttons
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            //TODO: check which tile was clicked
            //TODO: find that square's coordinates
            //TODO: selectedTile_sprite.setPosition(X, Y)
            selectedTile_sprite.setPosition(Gdx.input.getX() - xOffset, Gdx.graphics.getHeight() - Gdx.input.getY() - yOffset);
            //TODO: mapWidth/height are not dynamic with screensize, implement a fix for this and the camera/input/problem should be fixed
        }

        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            //TODO: Undo on right click maybe?
            selectedTile_sprite.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        }
    }
}
