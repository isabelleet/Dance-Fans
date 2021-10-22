package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.model.Game_Model;

/**
 * DanceFans is the application which connects all the MVC.
 * <p>
 * Is used in DesktopLauncher.
 * <p>
 * Uses View, Controller, Model.
 *
 * @author Joar Granström
 * @author Jakob Persson
 * @author Hedy Pettersson
 * @author Johan Berg
 * @author Isabelle Ermeryd Tankred
 */

public class DanceFans extends ApplicationAdapter {

    //# Main Modules

    // A model is a "save file" for a game instance of the Dance Fans game.
    // One can have multiple instances, to later be able to have multiple games active at once (in case you need to pause in the middle of a game etc.)
    // activeGameSession?
    //

    public Game_Model gameState;
    public Main_Game_View mainGameView;
    public Game_Controller gameController;

    // https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/ApplicationListener.html

    /**
     * Initializes everything needed for the game to function.
     */
    @Override
    public void create() {
        gameState = new Game_Model();
        gameState.startNewGame();
        mainGameView = new Main_Game_View();
        mainGameView.create(gameState);
        //Possible camera fix, but MVC is more importante!
        //Init renderer
        gameController = new Game_Controller(gameState);
        // controller.init();
        Gdx.input.setInputProcessor(this.gameController);
    }

    /**
     * Updates the viewport when a resize event is fired.
     *
     * @param width  New width of the window.
     * @param height New height of the window.
     */
    @Override
    public void resize(int width, int height) {
        mainGameView.viewport.update(width, height);
    }

    /**
     * LibGDX stuff.
     */
    @Override
    public void render() {
        mainGameView.render(gameState.getPreviewDanceFloor());
        //System.out.println(view.selectedTile_sprite.getX());
    }

    /**
     * LibGDX stuff.
     */
    @Override
    public void dispose() {
        mainGameView.dispose();
        // TODO: maybe need to dispose data in model too? Not sure how
        //gameState.dispose(); // ??? Kanske ska vara så med?
    }
}
