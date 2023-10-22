package com.mygdx.dungeonsagents;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class DungeonsAgents extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background;
	private Music battlemusic;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");

		// load the drop sound effect and the rain background "music"
//		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		battlemusic = Gdx.audio.newMusic(Gdx.files.internal("battlemusic-yeah-18130.mp3"));

		// start the playback of the background music immediately
		battlemusic.setLooping(true);
		battlemusic.play();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
