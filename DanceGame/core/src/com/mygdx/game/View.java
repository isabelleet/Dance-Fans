package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;



	private Model model;




    public void initCamera(int mapWidthInTiles, int mapHeightInTiles){
        // Set up the camera
        camera = new OrthographicCamera(1600.f, 900.f);
        camera.position.x = mapWidthInTiles * .5f;
        camera.position.y = mapHeightInTiles * .5f;

    }

    public void initRenderer(TiledMap map){
        renderer = new OrthogonalTiledMapRenderer(map);
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

    	sprite.draw(batch);
	}

	private void drawButton(String name, float x, float y){
		Sprite sprite = buttonSprites.get(name);

		sprite.setPosition(x,y);

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
		renderer.setView(camera);
		renderer.render();

		batch.end();

		// drawing cards
		batch.begin();

		font.draw(batch, "Turns before dance ends: [TODO: get from model]", 400 , 825);



		font.draw(batch, "Win by having the most dance fans", danceFloor.tileWidth* danceFloor.mapWidthInTiles + 50, 820);
		font.draw(batch, "(squares in the same color as your main dancer)", danceFloor.tileWidth* danceFloor.mapWidthInTiles + 50, 800);
		font.draw(batch, "when the dance floor is full, or when the song has ended.", danceFloor.tileWidth* danceFloor.mapWidthInTiles + 50, 780);


		//TODO: if enter is pressed, show it as feedback?
		//TODO: show active when it is possible to press button to get an effect
		//TODO: show inactive when not possible to press button to get an effect
		int tempXAdjustment = 300;
		font.draw(batch, "Controls", danceFloor.tileWidth* danceFloor.mapWidthInTiles + tempXAdjustment, 650);

		font.draw(batch, "Move your Main Dancer", danceFloor.tileWidth* danceFloor.mapWidthInTiles+ 50, 550);

		drawButton("emojione-monotone_keycap-downArrow", danceFloor.tileWidth* danceFloor.mapWidthInTiles + tempXAdjustment, 490);
		drawButton("emojione-monotone_keycap-upArrow", danceFloor.tileWidth* danceFloor.mapWidthInTiles + tempXAdjustment, 550);
		drawButton("emojione-monotone_keycap-leftArrow", danceFloor.tileWidth* danceFloor.mapWidthInTiles + tempXAdjustment - 60, 490);
		drawButton("emojione-monotone_keycap-rightArrow", danceFloor.tileWidth* danceFloor.mapWidthInTiles + tempXAdjustment + 60, 490);

		font.draw(batch, "Confirm you planned dance move", danceFloor.tileWidth* danceFloor.mapWidthInTiles + 50, 375);
		drawButton("emojione-monotone_keycap-enter", danceFloor.tileWidth* danceFloor.mapWidthInTiles + tempXAdjustment - 20, 325);

		//int maxCardSlots = 7;
		//for(int i = 0; i < maxCardSlots; i++){
		//	String numberButton = "emojione-monotone_keycap-" + i;
		//	drawButton(numberButton, 150, i*150);
		//}

		font.draw(batch, "Change what dance move to consider", danceFloor.tileWidth* danceFloor.mapWidthInTiles + 100 , 50);

		int spacing = 195;
		int cardsBottomY = 80;
		int xAdjustment = 90;

		drawButton("emojione-monotone_keycap-1", 1*spacing + xAdjustment , 10);
		drawButton("emojione-monotone_keycap-2", 2*spacing + xAdjustment, 10);
		drawButton("emojione-monotone_keycap-3", 3*spacing + xAdjustment, 10);
		drawButton("emojione-monotone_keycap-4", 4*spacing + xAdjustment, 10);
		drawButton("emojione-monotone_keycap-5", 5*spacing + xAdjustment, 10);
		//drawButton("emojione-monotone_keycap-6", 6*spacing , cardsBottomY);
		//drawButton("emojione-monotone_keycap-7", 7*spacing , cardsBottomY);
		//TODO: have keys up to 7 but probably not needed now

		for(int i = 0; i < model.currentlyOpenCards().size(); i++){
			String card;
			if(i == model.currentPlayer().getCardDeck().selected){
				card = "id=" + model.currentlyOpenCards().get(i).getId() + ", selected=True";
			} else{
				card = "id=" + model.currentlyOpenCards().get(i).getId() + ", selected=False";
			}
			drawCard(card, i* spacing + 220, cardsBottomY);
		}

		String currentPlayerDeckImageName;
		if (model.currentPlayer().playerTurnSlot == PlayerTurnSlot.ONE)
			currentPlayerDeckImageName = "deck_red";
		else
			currentPlayerDeckImageName = "deck_green";

		int currentPlayerNumber;
		if (model.currentPlayer().playerTurnSlot == PlayerTurnSlot.ONE)
			currentPlayerNumber = 1;
		else
			currentPlayerNumber = 2;


		drawButton(currentPlayerDeckImageName, 10 , cardsBottomY);
		font.draw(batch, "Player " + currentPlayerNumber + "'s turn.", 50 , cardsBottomY - 30);

		batch.end();

	}


	public void dispose () {
		batch.dispose();
		sprites.clear();
		textureAtlas.dispose();
	}
}


