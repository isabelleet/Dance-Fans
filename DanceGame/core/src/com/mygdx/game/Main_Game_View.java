package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.game.model.Game_Model;
import com.mygdx.game.model.DanceFloor;
import com.mygdx.game.model.Coordinates;
import com.mygdx.game.Enums.*;

/**
 * View, handles everything visual. Part of the MVC pattern.
 * <p>
 * Is used by DanceFans.
 * <p>
 * Uses Color, Type, Coordinates, DanceFloor, Game_Model.
 *
 * @author Joar Granström
 * @author Hedy Pettersson
 * @author David Salmo
 * @author Jakob Persson
 * @author Johan Berg
 * @author Isabelle Ermeryd Tankred
 */

public class Main_Game_View {

    BitmapFont font = new BitmapFont();
    //TODO: maybe add custom font
    //BitmapFont font = new BitmapFont(Gdx.files.internal("Calibri.fnt"),Gdx.files.internal("Calibri.png"),false);
    SpriteBatch batch;

    public Sprite selectedTile_sprite;
    Sprite winner;
    public Sprite background;
    public Sprite instructions;


    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    private final TextureAtlas textureAtlas = new TextureAtlas("sprites.txt");
    private final TextureAtlas textureAtlasCards = new TextureAtlas("cardSprites.txt");
    private final TextureAtlas textureAtlasButtons = new TextureAtlas("buttonSprites.txt");
    private final TextureAtlas textureAtlasWinner = new TextureAtlas("winners.txt");
    private final TextureAtlas textureAtlasBackground = new TextureAtlas("background.txt");
    private final TextureAtlas textureAtlasInstructions = new TextureAtlas("instructions.txt");


    private final HashMap<String, Sprite> cards = new HashMap<String, Sprite>();
    private final HashMap<String, Sprite> buttonSprites = new HashMap<String, Sprite>();

    // Camera and render
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    public Viewport viewport;

    float width = (Gdx.graphics.getWidth() / 2);
    float height = (Gdx.graphics.getHeight());

    private Game_Model gameModel;

    private AssetManager manager;
    private TiledMap map;

    // Used this guide: http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
    // Code: https://github.com/angelnavarro/Gdx-MyExamples/blob/master/gdx-tiled-draw-map/core/src/com/pixnbgames/tiled/draw_map/MyGdxTiledGame.java
    private void initManagers() {
        manager = new AssetManager();
        map = new TiledMap();
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("maps/BasicDanceFloor.tmx", TiledMap.class);
        manager.finishLoading();

        map = manager.get("maps/BasicDanceFloor.tmx", TiledMap.class);
    }

    private void initCamera(int width, int height) {
        // Set up the camera
        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        camera = new OrthographicCamera(1600, 900);
        camera.position.set(width, 0, 0);
        camera.zoom = 2;


        viewport = new FitViewport(1600, 900, camera);
        viewport.apply();

    }

    /**
     * Initializes the LibGDX renderer for the map.
     */
    private void initRenderer() {
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    /**
     * Initializes TextureAtlases for buttons, cards and dancers, creates a sprite for each object that should be displayed. Also gives view a Model to draw things from.
     *
     * @param gameModel The model object created on startup.
     */
    public void create(Game_Model gameModel) {

        this.gameModel = gameModel;
        initManagers();

        //# Things to draw
        batch = new SpriteBatch();
        initCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initRenderer();

        // load images for helper UI
        selectedTile_sprite = textureAtlas.createSprite("selectionBorder");
        selectedTile_sprite.setPosition(0, 0);

        addSprites();
    }

    private void addSprites() {

        Array<AtlasRegion> regions = textureAtlas.getRegions();
        for (AtlasRegion region : regions) {
            Sprite sprite = textureAtlas.createSprite(region.name);
            sprites.put(region.name, sprite);
        }


        Array<AtlasRegion> regionsCards = textureAtlasCards.getRegions();
        for (AtlasRegion region : regionsCards) {
            Sprite sprite = textureAtlasCards.createSprite(region.name);
            cards.put(region.name, sprite);
        }


        Array<AtlasRegion> regionsButtons = textureAtlasButtons.getRegions();
        for (AtlasRegion region : regionsButtons) {
            Sprite sprite = textureAtlasButtons.createSprite(region.name);
            buttonSprites.put(region.name, sprite);
        }
    }


    private void drawSprite(String name, float x, float y) {
        Sprite sprite = sprites.get(name);
        sprite.setPosition(x, y);
        if (name.contains("Transparent")) {
            sprite.setAlpha(0.5f);
        }
        sprite.draw(batch);
    }

    // based on the previous draw method, but for cards.
    private void drawCard(String name, float x, float y) {
        Sprite sprite = cards.get(name);

        sprite.setPosition(x, y);

        sprite.setScale(0.75f);

        sprite.draw(batch);
    }

    private void drawButton(String name, float x, float y) {
        Sprite sprite = buttonSprites.get(name);

        sprite.setPosition(x, y);

        sprite.setScale(0.75f);

        sprite.draw(batch);
    }

    /**
     * "The game loop", this method is responsible for drawing all the sprites visible on the screen.
     *
     * @param danceFloor the DanceFloor the model is using.
     */
    public void render(DanceFloor danceFloor) {
        ScreenUtils.clear(1, 0, 0, 1);

        batch.begin();

        background = textureAtlasBackground.createSprite("background");
        //background.draw(batch);
        batch.draw(background, 0, 0);
        Gdx.gl.glClearColor(218f, 187f, 145f, 225f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        instructions = textureAtlasInstructions.createSprite("instructionAlt1");
        batch.draw(instructions, width + 50, height - 442, 420, 432);

        int distanceFromBottomToTop = (danceFloor.mapHeightInTiles - 1) * danceFloor.tileSideLength;
        for (int colIndex = 0; colIndex < danceFloor.mapWidthInTiles; colIndex++) {
            for (int rowIndex = 0; rowIndex < danceFloor.mapHeightInTiles; rowIndex++) {
                Coordinates coordsInDanceFloor = new Coordinates(colIndex, rowIndex);
                Color color = danceFloor.getColor(coordsInDanceFloor);
                Type type = danceFloor.getType(coordsInDanceFloor);
                drawSprite(stringDancer(color, type), danceFloor.tileSideLength * colIndex, distanceFromBottomToTop - (danceFloor.tileSideLength * rowIndex));
                if (coordsInDanceFloor.getX() == gameModel.getSelectedCoordinates().getX() && coordsInDanceFloor.getY() == gameModel.getSelectedCoordinates().getY()) {
                    batch.draw(selectedTile_sprite, danceFloor.tileSideLength * colIndex, distanceFromBottomToTop - (danceFloor.tileSideLength * rowIndex));
                }
            }
        }

        //TODO: Draw UI that help player play


        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.end();

        // batch for drawing cards
        batch.begin();


        if (gameModel.isGameDone()) {
            String strWinner = whoWon(gameModel.whichPlayerIsLeading());
            winner = textureAtlasWinner.createSprite(strWinner);

            winner.setPosition(160, 300);
            winner.draw(batch);
        }


        displayText();

        int spacing = 195;
        int cardsBottomY = 40;
        int xAdjustment = 85;


        if (!gameModel.isGameDone()) {
            drawButton("emojione-monotone_keycap-1", 1 * spacing + xAdjustment, 10);
            drawButton("emojione-monotone_keycap-2", 2 * spacing + xAdjustment, 10);
            drawButton("emojione-monotone_keycap-3", 3 * spacing + xAdjustment, 10);

            // Draw current players cards
            String cardback;

            if (gameModel.isItPlayerOnesTurn()) {
                cardback = "cardback_red";
            } else {
                cardback = "cardback_green";
            }
            for (int i = 0; i < gameModel.getCardsOnHand().size(); i++) {
                String card;
                if (!gameModel.getHasPlayerStartedTheirTurn()) {
                    card = cardback;
                    drawButton(card, i * spacing + 220, cardsBottomY);
                    break;
                } else if (i == gameModel.selectedCard) {
                    card = "id=" + gameModel.getCardsOnHand().get(i).getId() + ", selected=True";
                } else {
                    card = "id=" + gameModel.getCardsOnHand().get(i).getId() + ", selected=False";
                }
                drawCard(card, i * spacing + 220, cardsBottomY);
            }
        }


        //TODO: refactor in better way, this was quick just ot get it working
        String startTurnUIForCurrentPlayer;
        if (!gameModel.isGameDone()) {
            if (gameModel.isItPlayerOnesTurn())
                startTurnUIForCurrentPlayer = "startTurn_keyboard_redPlayer";
            else
                startTurnUIForCurrentPlayer = "startTurn_keyboard_greenPlayer";

            if (!gameModel.getHasPlayerStartedTheirTurn()) {
                drawButton(startTurnUIForCurrentPlayer, -55, cardsBottomY + 220);
            }

            //TODO: refactor in better way, this was quick just ot get it working
            String currentPlayerDeckImageName;
            if (gameModel.isItPlayerOnesTurn())
                currentPlayerDeckImageName = "deck_red";
            else
                currentPlayerDeckImageName = "deck_green";


            //TODO: refactor in better way, this was quick just ot get it working
            int currentPlayerNumber;
            if (gameModel.isItPlayerOnesTurn())
                currentPlayerNumber = 1;
            else
                currentPlayerNumber = 2;
            drawButton(currentPlayerDeckImageName, 10, cardsBottomY);
            font.setColor(com.badlogic.gdx.graphics.Color.BLACK);
            font.draw(batch, "Player " + currentPlayerNumber + "'s turn.", 20, height - 30);
        }
        batch.end();
    }

    private void displayText() {

        if (!gameModel.isGameDone()) {
            int turnNumbers = gameModel.getTurns() + 1;
            String s = turnNumbers + "    rounds played";
            font.draw(batch, s, 20, height - 10);
            int turnsLeft = gameModel.getMaximumTurns() / 2 - turnNumbers + 1;
            String t = turnsLeft + " ";
            font.draw(batch, t, width + 322, height - 382);
        }


        //TODO: if enter is pressed, show it as feedback?
        //TODO: show active when it is possible to press button to get an effect
        //TODO: show inactive when not possible to press button to get an effect

    }

    private String whoWon(int playerNumber) {
        String s = "";
        switch (playerNumber) {
            case 0:
                s = "redWinner";
                break;
            case 1:
                s = "greenWinner";
                break;
            case 2:
                s = "stalemate";
        }
        return s;
    }

    private String stringDancer(Color color, Type type) {
        String s = "";
        switch (color) {
            case RED:
                s = "red";
                break;
            case GREEN:
                s = "green";
                break;
        }
        switch (type) {
            case MD:
                s += "MainDancer";
                return s;
            case DF:
                s += "DanceFan";
                return s;
            case TRANSDF:
                s += "DanceFanTransparent";
                return s;
            default:
                return "transparent_tile";
        }
    }

    /**
     * Releases all resources (batches, sprites and textureatlases) of this object.
     */
    public void dispose() {
        batch.dispose();
        sprites.clear();
        textureAtlas.dispose();
    }
}


