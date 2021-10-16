package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.model.Coordinates;
import com.mygdx.game.model.Model;

/**
 * Controller, calls on methods in model depending on what has been inputted. Part of the MVC pattern.
 *
 * Is used in DanceFans.
 *
 * Uses Model.
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

        Coordinates playerCoords = model.currentPlayer().getMainDancer().getCoordinates();

        System.out.println("key down" + keycode);

        if(keycode == Input.Keys.NUM_0){
            model.startNewGame();
            return true;
        }
        if(keycode == Input.Keys.N){
            System.exit(0);
        }
      
        if(keycode == Input.Keys.NUM_1){
            model.selectedCard = 0;
            try {
                model.moveMainDancerOfCurrentPlayerToCoords(playerCoords);
                model.selectionOnTileCoords = playerCoords;
                } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        if(keycode == Input.Keys.NUM_2){
            model.selectedCard = 1;

            try {
                model.moveMainDancerOfCurrentPlayerToCoords(playerCoords);
                model.selectionOnTileCoords = playerCoords;
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

        //movement
        if(keycode <= 22 && keycode >= 19) {
            if (model.hasPlayerStartedTheirTurn) {
                int x = 0;
                int y = 0;
                switch (keycode){
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
                    model.moveSelection(x, y);
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
