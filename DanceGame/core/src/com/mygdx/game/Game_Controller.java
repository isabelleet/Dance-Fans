package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.model.Game_Model;

/**
 * Controller, calls on methods in model depending on what has been inputted. Part of the MVC pattern.
 * <p>
 * Is used in DanceFans.
 * <p>
 * Uses Game_Model.
 *
 * @author Joar Granström
 * @author Hedy Pettersson
 * @author Johan Berg
 * @author Jakob Persson
 * @author David Salmo
 */

public class Game_Controller implements InputProcessor {

    Game_Model gameModel;

    public Game_Controller(Game_Model gameModel) {
        this.gameModel = gameModel;
    }

    /**
     * Checks which key was pressed and tells the model what the user wants to do.
     *
     * @param keycode the key that was pressed.
     * @return returns true when the input has been processed, otherwise returns false.
     */
    @Override
    public boolean keyDown(int keycode) {

        if (gameModel.isGameDone()) {
            if (keycode == Input.Keys.D) {
                gameModel.startNewGame();
                return true;
            }
        }


        if (keycode == Input.Keys.NUM_1 && !gameModel.isGameDone()) {
            gameModel.selectedCard = 0;
            gameModel.resetDancer();
            return true;
        }

        if (keycode == Input.Keys.NUM_2 && !gameModel.isGameDone()) {
            gameModel.selectedCard = 1;
            gameModel.resetDancer();
            return true;
        }

        if (keycode == Input.Keys.NUM_3 && !gameModel.isGameDone()) {
            gameModel.selectedCard = 2;
            gameModel.resetDancer();
            return true;
        }

        if (keycode == Input.Keys.D && !gameModel.isGameDone()) {
            gameModel.playerDrewCardsToStartTurn();
            return true;

        }
        if (keycode == Input.Keys.ENTER && !gameModel.isGameDone()) {
            System.out.println("Player clicked enter to confirm Dance move");
            if (gameModel.getHasPlayerStartedTheirTurn()) {
                gameModel.playerConfirmedDanceMove();
            }
            return true;
        }

        //movement of the player
        if (keycode <= 22 && keycode >= 19 && !gameModel.isGameDone()) {
            if (gameModel.getHasPlayerStartedTheirTurn()) {
                int x = 0;
                int y = 0;
                switch (keycode) {
                    case Input.Keys.UP:
                        y = -1;
                        break;
                    case Input.Keys.DOWN:
                        y = 1;
                        break;
                    case Input.Keys.LEFT:
                        x = -1;
                        break;
                    case Input.Keys.RIGHT:
                        x = 1;
                        break;
                }
                gameModel.moveSelection(x, y);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
