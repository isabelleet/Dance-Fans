package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.model.DanceFloor;
import com.mygdx.game.model.Model;
import com.mygdx.game.View;

public class DanceFans extends ApplicationAdapter {

    //# Main Modules

    // A model is a "save file" for a game instance of the Dance Fans game.
    // One can have multiple instances, to later be able to have multiple games active at once (in case you need to pause in the middle of a game etc.)
    // activeGameSession?
    //

    public Model gameState;
    public View view;
    public Controller controller;

    // https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/ApplicationListener.html


    @Override
    public void create(){
        gameState = new Model();
        gameState.startNewGame();
        view = new View();
        view.create();
        //Possible camera fix, but MVC is more importante!
        view.initCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Init renderer
        view.initRenderer(gameState.danceFloor.map);
        controller = new Controller();
        // controller.init();
        Gdx.input.setInputProcessor(this.controller);
    }

    @Override
    public void render () {
        view.render(gameState.danceFloor);
        //System.out.println(view.selectedTile_sprite.getX());
    }

    @Override
    public void dispose(){
        view.dispose();
        //Model.dispose() // ??? Kanske ska vara så med?
    }
}