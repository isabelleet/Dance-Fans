package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.model.Model;

/**
 * Controller, calls on methods in model depending on what has been inputted. Part of the MVC pattern.
 *
 * @author Joar Granström
 * @author Hedy Pettersson
 * @author Johan Berg
 * @author Jakob Persson
 * @author David Salmo
 */

public class Controller implements InputProcessor {

    Model model;
    public Controller(Model model){
        this.model = model;
    }

    /**
     * Checks which key was pressed and tells the model what the user wants to do.
     * @param keycode the key that was pressed.
     * @return returns true when the input has been processed, otherwise returns false.
     */
    @Override
    public boolean keyDown(int keycode) {

        int playerIndex = model.currentPlayer().getMainDancer().getIndex();

        System.out.println("key down" + keycode);

        if(keycode == Input.Keys.NUM_0){
            model.startNewGame();
            return true;
        }
      
        if(keycode == Input.Keys.NUM_1){
            model.currentPlayer().getCardDeck().selected = 0;
            try {
                model.moveMainDancerOfCurrentPlayerToIndex(playerIndex);
                model.selectionOnTileIndex = playerIndex;
                } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        if(keycode == Input.Keys.NUM_2){
            model.currentPlayer().getCardDeck().selected = 1;

            try {
                model.moveMainDancerOfCurrentPlayerToIndex(playerIndex);
                model.selectionOnTileIndex = playerIndex;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return true;
        }

        if(keycode == Input.Keys.D) {
            model.playerDrewCardsToStartTurn();
            return true;
          
        }
        if(keycode == Input.Keys.ENTER) {
            System.out.println("Player clicked enter to confirm Dance move");
            if (model.hasPlayerStartedTheirTurn) {
                try {
                    // Protected code
                    model.playerConfirmedDanceMove();
                    // TODO: probably change what kind of exception, I have no Idea which is right
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Exception thrown  :" + e);
                }
            }
            return true;
        }

        if(keycode <= 22 && keycode >= 19) {
            if (model.hasPlayerStartedTheirTurn) {
                try {
                    model.moveSelection(keycode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        System.out.println(character);
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
