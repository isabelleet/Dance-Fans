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
}
