package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.model.Model;

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
    public void create() {
        gameState = new Model();
        gameState.startNewGame();
        view = new View();
        view.create(gameState);
        //Possible camera fix, but MVC is more importante!
        view.initCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Init renderer
        view.initRenderer();
        controller = new Controller(gameState);
        // controller.init();
        Gdx.input.setInputProcessor(this.controller);
    }

    /**
     * Updates the viewport when a resize event is fired.
     *
     * @param width  New width of the window.
     * @param height New height of the window.
     */
    @Override
    public void resize(int width, int height) {
        view.viewport.update(width, height);
    }

    /**
     *
     */
    @Override
    public void render() {
        view.render(gameState.previewDanceFloor);
        //System.out.println(view.selectedTile_sprite.getX());
    }

    /**
     *
     */
    @Override
    public void dispose() {
        view.dispose();
        // TODO: maybe need to dispose data in model too? Not sure how
        //gameState.dispose(); // ??? Kanske ska vara så med?
    }
}
