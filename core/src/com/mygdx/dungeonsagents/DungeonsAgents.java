package com.mygdx.dungeonsagents;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class DungeonsAgents extends ApplicationAdapter {
//	Cameras and batches/SpriteBatches
	private OrthographicCamera camera;
	private SpriteBatch batch;
//	Textures and sprites
	private Texture background;
	private Sprite sprite_hero_one;
//  Animations:
	private Animation<TextureRegion> archer_animation;
	private Animation<TextureRegion> swordsman_animation;
	private TextureRegion archer_current_frame;
	private TextureRegion swordsman_current_frame;
	private float stateTime;
//	Rectangles, collision and entities
	private Rectangle archer;
	private int archer_state;
	private Rectangle swordsman;
	private int swordsman_state;
//  Music and sound effects
	private Music battlemusic;
	private float master_volume;

	public void volume_control(boolean volume_knob){
		if(volume_knob) {
			if(master_volume - 0.1f * Gdx.graphics.getDeltaTime() <= 1.0f) master_volume += 0.1f * Gdx.graphics.getDeltaTime();
		} else {
			if(master_volume - 0.1f * Gdx.graphics.getDeltaTime() >= 0.0f) master_volume -= 0.1f * Gdx.graphics.getDeltaTime();
		}
		this.battlemusic.setVolume(master_volume);
	}

	public Animation<TextureRegion> load_animation_sprite_sheet(String internal_file_name, int frame_cols, int frame_rows, float frame_duration){
		// Load the sprite sheet as a Texture
		Texture walkSheet = new Texture(Gdx.files.internal(internal_file_name));

		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / frame_cols,
				walkSheet.getHeight() / frame_rows);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[frame_cols * frame_rows];
		int index = 0;
		for (int i = 0; i < frame_rows; i++) {
			for (int j = 0; j < frame_cols; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
        return new Animation<TextureRegion>(frame_duration, walkFrames);
	}
	
	@Override
	public void create () {
		// camera and sprite batch setup
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		batch = new SpriteBatch();

		// textures
		background = new Texture(Gdx.files.internal("background.png"));
//		Texture tex_hero_one = new Texture(Gdx.files.internal("Heroes/Archer/Idle.png"));

		// sprites
//		sprite_hero_one = new Sprite(tex_hero_one, 0, 0, 128, 128);
//		sprite_hero_one.setPosition(10, 10);

		// animations
		archer_animation = load_animation_sprite_sheet("Heroes/Archer/Idle.png", 6, 1, 0.25f);
		swordsman_animation = load_animation_sprite_sheet("Heroes/Swordsman/Idle.png", 8, 1, 0.25f);
		stateTime = 0f;

		// rectangles
		archer = new Rectangle();
		archer.x = (int) camera.viewportWidth / 4;
		archer.y = (int) camera.viewportHeight / 4;
		archer.width = 128;
		archer.height = 128;
		archer_state = 0;

		swordsman = new Rectangle();
		swordsman.x = (int) camera.viewportWidth / 4;
		swordsman.y = (int) ((camera.viewportHeight / 4) * 1.8);
		swordsman.width = 128;
		swordsman.height = 128;
		swordsman_state = 0;

		// load the drop sound effect and the rain background "music"
//		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		battlemusic = Gdx.audio.newMusic(Gdx.files.internal("battlemusic-yeah-18130.mp3"));

		// start the playback of the background music immediately
		master_volume = 0.1f;
		battlemusic.setLooping(true);
		battlemusic.play();
		battlemusic.setVolume(master_volume);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		// Update current camera and time deltas
		camera.update();
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Animations: Get current frame of animation for the current stateTime
		switch (archer_state){
			case 0: archer_current_frame = archer_animation.getKeyFrame(stateTime, true);
		}
		switch (swordsman_state){
			case 0: swordsman_current_frame = swordsman_animation.getKeyFrame(stateTime, true);
		}

		// Start rendering
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0);
		switch (archer_state){
			case 0: batch.draw(archer_current_frame, archer.x, archer.y);
			case 1: batch.draw(archer_current_frame, archer.x, archer.y);
		}
		switch (swordsman_state){
			case 0: batch.draw(swordsman_current_frame, swordsman.x, swordsman.y);
			case 1: batch.draw(swordsman_current_frame, swordsman.x, swordsman.y);
		}
//		sprite_hero_one.draw(batch);

		// End rendering
		batch.end();

		// Game logic
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
