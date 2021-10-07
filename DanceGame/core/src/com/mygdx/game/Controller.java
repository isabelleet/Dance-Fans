package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.model.Model;

public class Controller implements InputProcessor {

    Model model;

    public Controller(Model model){
        this.model = model;
    }

    @Override
    public boolean keyDown(int keycode) {


        System.out.println("key down" + keycode);

        if(keycode == Input.Keys.NUM_1){
            //TODO: also update preview
            //TODO: if maindancer stood on tile too far away, move it back when changing card
            model.currentPlayer().getCardDeck().selected = 0;
            int i= model.currentPlayer().getMainDancer().getPreviewIndex();
            model.currentPlayer().getMainDancer().setPreviewIndex(i);


            try {

                model.moveMainDancerOfCurrentPlayerToIndex(model.previewCardF());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(keycode == Input.Keys.NUM_2){
            model.currentPlayer().getCardDeck().selected = 1;
            int i= model.currentPlayer().getMainDancer().getPreviewIndex();
            model.currentPlayer().getMainDancer().setPreviewIndex(i);
            try {
                model.moveMainDancerOfCurrentPlayerToIndex(model.previewCardF());
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        if(keycode <= 22 && keycode >=19) {
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
