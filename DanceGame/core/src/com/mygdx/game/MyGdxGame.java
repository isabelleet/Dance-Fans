package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private Texture greenDancerImage;
	private Texture redDancerImage;
	private Texture greenPlayerImage;
	private Texture redPlayerImage;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		// load images for dancers and main characters
		greenDancerImage = new Texture(Gdx.files.internal("greenDancer.png"));
		redDancerImage = new Texture(Gdx.files.internal("redDancer.png"));
		greenPlayerImage = new Texture(Gdx.files.internal("greenMainDancer.png"));
		redPlayerImage = new Texture(Gdx.files.internal("redMainDancer.png"));

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(greenDancerImage, 10, 10);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
