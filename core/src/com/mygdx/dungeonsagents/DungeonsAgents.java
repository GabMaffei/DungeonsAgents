package com.mygdx.dungeonsagents;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class DungeonsAgents extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture background;
	private Sprite sprite_hero_one;
	private Music battlemusic;
	private float master_volume;

	public void volume_control(boolean volume_knob){
		if(volume_knob) {
			if(master_volume - 0.1f <= 1.0f) master_volume += 0.1f;
		} else {
			if(master_volume - 0.1f >= 0.0f) master_volume -= 0.1f;
		}
		this.battlemusic.setVolume(master_volume);
	}
	
	@Override
	public void create () {
		// camera and sprite batch setup
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		batch = new SpriteBatch();

		// textures
		background = new Texture(Gdx.files.internal("background.png"));
		Texture tex_hero_one = new Texture(Gdx.files.internal("Heroes/Archer/Idle.png"));

		// sprites
		sprite_hero_one = new Sprite(tex_hero_one, 0, 0, 128, 128);
		sprite_hero_one.setPosition(10, 10);

		// load the drop sound effect and the rain background "music"
//		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		battlemusic = Gdx.audio.newMusic(Gdx.files.internal("battlemusic-yeah-18130.mp3"));

		// start the playback of the background music immediately
		master_volume = 1.0f;
		battlemusic.setLooping(true);
		battlemusic.play();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0);
		sprite_hero_one.draw(batch);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.MINUS)) volume_control(false);
		if(Gdx.input.isKeyPressed(Input.Keys.PLUS) || Gdx.input.isKeyPressed(Input.Keys.EQUALS)) volume_control(true);

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		battlemusic.dispose();
	}
}
