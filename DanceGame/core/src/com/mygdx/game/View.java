package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.model.DanceFloor;
import com.mygdx.game.model.Model;
import com.mygdx.game.model.PlayerTurnSlot;

public class View {

	BitmapFont font = new BitmapFont();
	//TODO: maybe add custom font
	//BitmapFont font = new BitmapFont(Gdx.files.internal("Calibri.fnt"),Gdx.files.internal("Calibri.png"),false);
	SpriteBatch batch;
	Texture selectedTile;

	TextureAtlas textureAtlas;
	Sprite greenDanceFan;
	Sprite redDanceFan;
	Sprite greenMainDancer;
	Sprite redMainDancer;
	public Sprite selectedTile_sprite;

	final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

	// card atlas?
	TextureAtlas textureAtlasCards;
	final HashMap<String, Sprite> cards = new HashMap<String, Sprite>();

	TextureAtlas textureAtlasButtons;
	final HashMap<String, Sprite> buttonSprites = new HashMap<String, Sprite>();

	// Camera and render
	//private PerspectiveCamera camera;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer mapRenderer;
	public Viewport viewport;



	private Model model;

	final float GAME_WORLD_WIDTH = 1600; //1600f before
	final float GAME_WORLD_HEIGHT = 900; //900f before




    public void initCamera(int width, int height){
        // Set up the camera
		float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
        camera = new OrthographicCamera(1600, 800);


		viewport = new FitViewport(1600, 800,  camera);
		viewport.apply();
		camera.position.set(width, 0, 0);
		camera.zoom = 2.0f;

    }

    public void initRenderer(TiledMap map){
		mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

	public void create(Model model) {

    	this.model = model;

		//# Things to draw
		batch = new SpriteBatch();

		// load images for dancers and main characters
		//https://www.codeandweb.com/texturepacker/start-download?os=mac&bits=64&download=true
		//tiled
		// Guide: https://www.codeandweb.com/texturepacker/tutorials/libgdx-physics
		textureAtlas = new TextureAtlas("sprites.txt");

		// card atlas?
		textureAtlasCards = new TextureAtlas("cardSprites.txt");

		textureAtlasButtons = new TextureAtlas("buttonSprites.txt");

		greenDanceFan = textureAtlas.createSprite("greenDanceFan");
		redDanceFan = textureAtlas.createSprite("redDanceFan");
		greenMainDancer = textureAtlas.createSprite("greenMainDancer");
		redMainDancer = textureAtlas.createSprite("redMainDancer");

		// load images for helper UI
		//selectedTile = new Texture(Gdx.files.internal("selectionBorder.png"));
		selectedTile_sprite = textureAtlas.createSprite("selectionBorder");
		selectedTile_sprite.setPosition(0, 0);

		addSprites();


		//# Which things to draw Where

		// Instantiation of the render for the map object

	}

	private void addSprites() {
		Array<AtlasRegion> regions = textureAtlas.getRegions();

		for (AtlasRegion region : regions) {
			Sprite sprite = textureAtlas.createSprite(region.name);

			sprites.put(region.name, sprite);
		}

		// card atlas?
		Array<AtlasRegion> regionsCards = textureAtlasCards.getRegions();
		for(AtlasRegion region : regionsCards){
			Sprite sprite = textureAtlasCards.createSprite(region.name);
			cards.put(region.name, sprite);
		}


		//buttons atlas?
		Array<AtlasRegion> regionsButtons = textureAtlasButtons.getRegions();
		for(AtlasRegion region : regionsButtons){
			Sprite sprite = textureAtlasButtons.createSprite(region.name);
			buttonSprites.put(region.name, sprite);
		}
	}


	private void drawSprite(String name, float x, float y) {
		Sprite sprite = sprites.get(name);

		sprite.setPosition(x, y);

		sprite.draw(batch);
	}

	// based on the previous draw method, but for cards.
	private void drawCard(String name, float x, float y){
    	Sprite sprite = cards.get(name);

    	sprite.setPosition(x,y);

		sprite.setScale(0.75f);

    	sprite.draw(batch);
	}

	private void drawButton(String name, float x, float y){
		Sprite sprite = buttonSprites.get(name);

		sprite.setPosition(x,y);

		sprite.setScale(0.75f);

		sprite.draw(batch);
	}

	public void render (DanceFloor danceFloor) {
		ScreenUtils.clear(1, 0, 0, 1);

		batch.begin();



		// Draw Dance Floor and things on it
		// TODO: Make this prettier but added this to draw from top left corner so one can think about it like a matrix
		// [STARTS DRAWING HERE] [] [] []
		// ...
		// [] [] [] [STARTS DRAWING HERE]
		int distanceFromBottomToTop = (danceFloor.mapHeightInTiles - 1) * danceFloor.tileSideLength;
		for (int rowIndex = 0; rowIndex < danceFloor.mapWidthInTiles; rowIndex++){
			for (int columnIndex = 0; columnIndex < danceFloor.mapHeightInTiles; columnIndex++){
				int currentIndexInDanceFloorArray = rowIndex + (columnIndex * danceFloor.mapWidthInTiles);
				String spriteName = danceFloor.danceFloorTiles[currentIndexInDanceFloorArray].getOccupantName();
			    drawSprite(spriteName, danceFloor.tileSideLength * rowIndex, distanceFromBottomToTop-(danceFloor.tileSideLength * columnIndex) );
				if (currentIndexInDanceFloorArray == model.selectionOnTileIndex)
				batch.draw(selectedTile_sprite, danceFloor.tileSideLength * rowIndex, distanceFromBottomToTop-(danceFloor.tileSideLength * columnIndex) );


			}

		}

		//TODO: Draw UI that help player play
		//batch.draw(selectedTile_sprite, selectedTile_sprite.getX(), selectedTile_sprite.getY());

		Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		mapRenderer.setView(camera);
		mapRenderer.render();

		batch.end();

		// drawing cards
		batch.begin();

		float width=(Gdx.graphics.getWidth());
		float height=(Gdx.graphics.getHeight());


		String strWinner= model.isWinner();
		font.draw(batch, strWinner, 400 , 825);

		int turnNumbers=model.numberTurns()+1;
		String s = turnNumbers + "    rounds played";
		if(turnNumbers<=10) {
			//font.draw(batch, s, 400, 850);
			font.draw(batch, s, width/2, height-40);
		}



		int tempXAdjustment = 50;
		int buttonAdjustment= 300;

		font.draw(batch, "Win by having the most dance fans", (width/2), height-100) ;
		font.draw(batch, "(squares in the same color as your main dancer)", (width/2), height-120);
		font.draw(batch, "when the dance floor is full, or when the song has ended.", (width/2), height-140) ;


		//TODO: if enter is pressed, show it as feedback?
		//TODO: show active when it is possible to press button to get an effect
		//TODO: show inactive when not possible to press button to get an effect
		font.draw(batch, "Controls", (width/2+210), height-180);

		font.draw(batch, "Move your Main Dancer",  (width/2), height-270);

		drawButton("emojione-monotone_keycap-downArrow", (width/2)+210, height-308);
		drawButton("emojione-monotone_keycap-upArrow", (width/2)+210 , height-260);
		drawButton("emojione-monotone_keycap-leftArrow", (width/2)+162 , height-308);
		drawButton("emojione-monotone_keycap-rightArrow", (width/2+258) , height-308);

		font.draw(batch, "Confirm you planned dance move", (width/2), height-370);
		drawButton("emojione-monotone_keycap-enter", (width/2)+210, height-420);

		//int maxCardSlots = 7;
		//for(int i = 0; i < maxCardSlots; i++){
		//	String numberButton = "emojione-monotone_keycap-" + i;
		//	drawButton(numberButton, 150, i*150);
		//}

		font.draw(batch, "Change what dance move to consider", (width/2), height-480);

		int spacing = 195;
		int cardsBottomY = 40;
		int xAdjustment = 85;

		drawButton("emojione-monotone_keycap-1", 1*spacing + xAdjustment , 10);
		drawButton("emojione-monotone_keycap-2", 2*spacing + xAdjustment, 10);
		//drawButton("emojione-monotone_keycap-3", 3*spacing + xAdjustment, 10);
		//drawButton("emojione-monotone_keycap-4", 4*spacing + xAdjustment, 10);
		//drawButton("emojione-monotone_keycap-5", 5*spacing + xAdjustment, 10);
		//drawButton("emojione-monotone_keycap-6", 6*spacing , cardsBottomY);
		//drawButton("emojione-monotone_keycap-7", 7*spacing , cardsBottomY);
		//TODO: have keys up to 7 but probably not needed now


		// Draw current players cards
		String cardback_spriteName_currentPlayer;
		if (model.currentPlayer().playerTurnSlot == PlayerTurnSlot.ONE)
			cardback_spriteName_currentPlayer = "cardback_red";
		else
			cardback_spriteName_currentPlayer = "cardback_green";

		for(int i = 0; i < model.currentlyOpenCards().size(); i++){
			String card;
			if (model.hasPlayerStartedTheirTurn == false){
				card = cardback_spriteName_currentPlayer;
				drawButton(card, i* spacing + 220, cardsBottomY);
				break;
			}
			else if(i == model.currentPlayer().getCardDeck().selected){
				card = "id=" + model.currentlyOpenCards().get(i).getId() + ", selected=True";
			} else{
				card = "id=" + model.currentlyOpenCards().get(i).getId() + ", selected=False";
			}
			drawCard(card, i* spacing + 220, cardsBottomY);
		}

		//TODO: refactor in better way, this was quick just ot get it working
		String startTurnUIForCurrentPlayer;
		if (model.currentPlayer().playerTurnSlot == PlayerTurnSlot.ONE)
			startTurnUIForCurrentPlayer = "startTurn_keyboard_redPlayer";
		else
			startTurnUIForCurrentPlayer = "startTurn_keyboard_greenPlayer";

		if (model.hasPlayerStartedTheirTurn == false) {
			drawButton(startTurnUIForCurrentPlayer,  xAdjustment, cardsBottomY +110 );
		}

		//TODO: refactor in better way, this was quick just ot get it working
		String currentPlayerDeckImageName;
		if (model.currentPlayer().playerTurnSlot == PlayerTurnSlot.ONE)
			currentPlayerDeckImageName = "deck_red";
		else
			currentPlayerDeckImageName = "deck_green";


		//TODO: refactor in better way, this was quick just ot get it working
		int currentPlayerNumber;
		if (model.currentPlayer().playerTurnSlot == PlayerTurnSlot.ONE)
			currentPlayerNumber = 1;
		else
			currentPlayerNumber = 2;

		drawButton(currentPlayerDeckImageName, 10 , cardsBottomY);
		font.draw(batch, "Player " + currentPlayerNumber + "'s turn.", width/2 , height-20);



		batch.end();

	}


	public void dispose () {
		batch.dispose();
		sprites.clear();
		textureAtlas.dispose();
	}
}


