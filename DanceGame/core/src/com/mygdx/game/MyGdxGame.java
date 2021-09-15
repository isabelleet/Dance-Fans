package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import com.badlogic.gdx.Input;
import com.mygdx.game.model.DanceFloorTile;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture selectedTile;

	TextureAtlas textureAtlas;
	//private Texture greenDancerImage;
	//private Texture redDancerImage;
	//private Texture greenPlayerImage;
	//private Texture redPlayerImage;
	//private Texture selectedTile;
	Sprite greenDancer;
	Sprite redDancer;
	Sprite greenMainDancer;
	Sprite redMainDancer;
	Sprite selectedTile_sprite;


	final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

	// Map
	private TiledMap map;
	private AssetManager manager;

	// Map properties
	private int tileWidth, tileHeight,
			mapWidthInTiles, mapHeightInTiles,
			mapWidthInPixels, mapHeightInPixels;

	// Camera and render
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;

	int tileSideLength = 128;
	int dancefloorHeight = 6;
	int dancefloorWidth = 9;
  	DanceFloorTile currentDanceFloorState[] = new DanceFloorTile[ dancefloorWidth * dancefloorHeight ];
    //TODO:
//	DanceFloor currentDanceFloorState = new DanceFloor();



//	public class DanceFloor  {
//		int dancefloorHeight = 6;
//		int dancefloorWidth = 9;
//		DanceFloorTile danceFloor[] = new DanceFloorTile[ dancefloorWidth * dancefloorHeight ];
//
//		void setDanceFloor(DanceFloor updatedDanceFloor) {
//			danceFloor[] = updatedDanceFloor;
//		}
//
//	}

	private DanceFloorTile[] initializeDanceFloor(DanceFloorTile[] danceFloor) {
		int i;
		for (i = 0; i < danceFloor.length; i++) {

			if (i == 11)
				danceFloor[i] = new DanceFloorTile("redMainDancer");
			else if (i == ((dancefloorWidth*dancefloorHeight) - dancefloorWidth - 2) )
				danceFloor[i] = new DanceFloorTile("greenMainDancer");

			else
				danceFloor[i] = new DanceFloorTile("transparent_tile");

		}
		return danceFloor;
	}

	private DanceFloorTile[] initializeFullDanceFloor(DanceFloorTile[] danceFloor) {
		int i;
		for (i = 0; i < danceFloor.length; i++) {

			if (i == 11)
				danceFloor[i] = new DanceFloorTile("redMainDancer");
			else if (i == ((dancefloorWidth*dancefloorHeight) - dancefloorWidth - 2) )
				danceFloor[i] = new DanceFloorTile("greenMainDancer");
			else if (i % 2 == 0)
				danceFloor[i] = new DanceFloorTile("redDancer");
			else
				danceFloor[i] = new DanceFloorTile("greenDancer");

		}
		return danceFloor;
	}

	@Override
	public void create () {

		batch = new SpriteBatch();
		// load images for dancers and main characters
		//https://www.codeandweb.com/texturepacker/start-download?os=mac&bits=64&download=true
		//tiled
		// Guide: https://www.codeandweb.com/texturepacker/tutorials/libgdx-physics
		textureAtlas = new TextureAtlas("sprites.txt");
		//greenDancerImage = new Texture(Gdx.files.internal("greenDancer.png"));
		//redDancerImage = new Texture(Gdx.files.internal("redDancer.png"));
		//greenPlayerImage = new Texture(Gdx.files.internal("greenMainDancer.png"));
		//redPlayerImage = new Texture(Gdx.files.internal("redMainDancer.png"));

		greenDancer = textureAtlas.createSprite("greenDancer");
		redDancer = textureAtlas.createSprite("redDancer");
		greenMainDancer = textureAtlas.createSprite("greenMainDancer");
		redMainDancer = textureAtlas.createSprite("redMainDancer");

		//selectedTile = new Texture(Gdx.files.internal("selectionBorder.png"));
		selectedTile_sprite = textureAtlas.createSprite("selectionBorder");
		selectedTile_sprite.setPosition(0, 0);

		addSprites();


		// Setup dance floor

		initializeDanceFloor(currentDanceFloorState);
		//TODO: Use this to test end of game conditions e.g.
		//initializeFullDanceFloor(currentDanceFloorState);

 		// Used this guide: http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
		// Code: https://github.com/angelnavarro/Gdx-MyExamples/blob/master/gdx-tiled-draw-map/core/src/com/pixnbgames/tiled/draw_map/MyGdxTiledGame.java
		manager = new AssetManager();
		manager.setLoader(TiledMap.class, new TmxMapLoader());
		manager.load("maps/BasicDanceFloor.tmx", TiledMap.class);
		manager.finishLoading();

		map = manager.get("maps/BasicDanceFloor.tmx", TiledMap.class);

		// Read properties
		MapProperties properties = map.getProperties();
		tileWidth = properties.get("tilewidth", Integer.class);
		tileHeight        = properties.get("tileheight", Integer.class);
		mapWidthInTiles   = properties.get("width", Integer.class);
		mapHeightInTiles  = properties.get("height", Integer.class);
		mapWidthInPixels  = mapWidthInTiles  * tileWidth;
		mapHeightInPixels = mapHeightInTiles * tileHeight;


		// Set up the camera
		//camera = new OrthographicCamera(1152.f, 768.f);
		camera = new OrthographicCamera(1600.f, 900.f);
		//TODO: fix bug with camera, where you click isn't the same as where things are rendered.
		//bug is caused by the input method working in a different coordinate system from the render thing,
		//possible solution: find a way to convert between these systems.
		camera.position.x = mapWidthInPixels * .5f;
		camera.position.y = mapHeightInPixels * .5f;

		// Instantiation of the render for the map object
		renderer = new OrthogonalTiledMapRenderer(map);

	}

	private void addSprites() {
		Array<AtlasRegion> regions = textureAtlas.getRegions();

		for (AtlasRegion region : regions) {
			Sprite sprite = textureAtlas.createSprite(region.name);

			sprites.put(region.name, sprite);
		}
	}

	private void drawSprite(String name, float x, float y) {
		Sprite sprite = sprites.get(name);

		sprite.setPosition(x, y);

		sprite.draw(batch);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		batch.begin();

		//TODO: Draw all dancers from state
		//batch.draw(greenDancerImage, 10, 10);

		//drawSprite("redDancer", 0, 0);
		//drawSprite("greenDancer", tileSideLength, tileSideLength);

		//int rowIndex, columnIndex;
		for (int rowIndex = 0; rowIndex < dancefloorWidth; rowIndex++){
			for (int columnIndex = 0; columnIndex < dancefloorHeight; columnIndex++){
				int currentIndexInDanceFloorArray = rowIndex + (columnIndex * dancefloorWidth);
				String spriteName = currentDanceFloorState[currentIndexInDanceFloorArray].getOccupantName();
				drawSprite(spriteName, tileSideLength * rowIndex, tileSideLength * columnIndex );
				//drawSprite("redDancer", tileSideLength * columnIndex, tileSideLength * rowIndex);

			}

		}

		detectInput();

		//TODO: Draw UI that help player play
		batch.draw(selectedTile_sprite, selectedTile_sprite.getX(), selectedTile_sprite.getY());

		//Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
		Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setView(camera);
		renderer.render();

		batch.end();
	}

	public void detectInput(){
		int xOffset = (128 + Gdx.graphics.getWidth() - mapWidthInPixels)/2;
		int yOffset = (128 + Gdx.graphics.getHeight() - mapHeightInPixels)/2;
		// TODO: seems weird to include inputs inside of render function. I guess render is the main loop. Perhaps get this out somehow?


		// Inputs from mouse buttons
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			//TODO: check which tile was clicked
			//TODO: find that square's coordinates
			//TODO: selectedTile_sprite.setPosition(X, Y)
			selectedTile_sprite.setPosition(Gdx.input.getX() - xOffset, Gdx.graphics.getHeight() - Gdx.input.getY() - yOffset);
			//TODO: mapWidth/height are not dynamic with screensize, implement a fix for this and the camera/input/problem should be fixed
			System.out.println(mapWidthInPixels);
		}

		if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			//TODO: Undo on right click maybe?
			selectedTile_sprite.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		}
	}



	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		manager.dispose();
		sprites.clear();
		textureAtlas.dispose();
	}
}


