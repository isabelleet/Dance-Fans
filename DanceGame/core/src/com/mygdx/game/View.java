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
import com.mygdx.game.model.DanceFloor;
import com.mygdx.game.model.DanceFloorTile;
import com.mygdx.game.model.Model;
import com.mygdx.game.DanceFans;

//public class View extends ApplicationAdapter {
public class View {

	SpriteBatch batch;
	Texture img;
	Texture selectedTile;

	TextureAtlas textureAtlas;
	Sprite greenDancer;
	Sprite redDancer;
	Sprite greenMainDancer;
	Sprite redMainDancer;
	public Sprite selectedTile_sprite;

	final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();


	// Camera and render
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;


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

    public void initCamera(int mapWidthInTiles, int mapHeightInTiles){
        // Set up the camera
        camera = new OrthographicCamera(1600.f, 900.f);
        camera.position.x = mapWidthInTiles * .5f;
        camera.position.y = mapHeightInTiles * .5f;

    }

    public void initRenderer(TiledMap map){
        renderer = new OrthogonalTiledMapRenderer(map);
    }



	public void create() {

		//# Things to draw
		batch = new SpriteBatch();

		// load images for dancers and main characters
		//https://www.codeandweb.com/texturepacker/start-download?os=mac&bits=64&download=true
		//tiled
		// Guide: https://www.codeandweb.com/texturepacker/tutorials/libgdx-physics
		textureAtlas = new TextureAtlas("sprites.txt");

		greenDancer = textureAtlas.createSprite("greenDancer");
		redDancer = textureAtlas.createSprite("redDancer");
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
	}


	private void drawSprite(String name, float x, float y) {
		Sprite sprite = sprites.get(name);

		sprite.setPosition(x, y);

		sprite.draw(batch);
	}

	public void render (DanceFloor danceFloor) {
		ScreenUtils.clear(1, 0, 0, 1);

		batch.begin();

		// Draw Dance Floor and things on it
		for (int rowIndex = 0; rowIndex < danceFloor.mapWidthInTiles; rowIndex++){
			for (int columnIndex = 0; columnIndex < danceFloor.mapHeightInTiles; columnIndex++){
				int currentIndexInDanceFloorArray = rowIndex + (columnIndex * danceFloor.mapWidthInTiles);
				String spriteName = danceFloor.danceFloorTiles[currentIndexInDanceFloorArray].getOccupantName();
			    drawSprite(spriteName, danceFloor.tileSideLength * rowIndex, danceFloor.tileSideLength * columnIndex );
			}

		}

		//TODO: Draw UI that help player play
		batch.draw(selectedTile_sprite, selectedTile_sprite.getX(), selectedTile_sprite.getY());

		Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setView(camera);
		renderer.render();

		batch.end();
	}

	public void moveSelectorSprite(){
    	selectedTile_sprite.translateX(3f);
	}

	public void dispose () {
		batch.dispose();
		img.dispose();
		sprites.clear();
		textureAtlas.dispose();
	}
}

