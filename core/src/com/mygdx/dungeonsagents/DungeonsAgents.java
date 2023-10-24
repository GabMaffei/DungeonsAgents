package com.mygdx.dungeonsagents;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.core.Runtime;
import jade.wrapper.StaleProxyException;
//import jade.tools.rma.rma;
//import jade.wrapper.AgentController;
//import jade.wrapper.ContainerController;
//import jade.wrapper.StaleProxyException;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class DungeonsAgents extends ApplicationAdapter {
//	Cameras and batches/SpriteBatches
	private OrthographicCamera camera;
	private SpriteBatch batch;
//	Textures and sprites
	private Texture background;
	private Sprite spriteHeroOne;
//  Animations:
	private Animation<TextureRegion> archerAnimation;
	private Animation<TextureRegion> swordsmanAnimation;
	private TextureRegion archerCurrentFrame;
	private TextureRegion swordsmanCurrentFrame;
	private float stateTime;
//	Rectangles, collision and entities
	private Rectangle archer;
	private int archerState;
	private Rectangle swordsman;
	private int swordsmanState;
//  Music and sound effects
	private Music battlemusic;
	private float masterVolume;
	private Entity hero1, hero2, hero3;
	private Entity enemy1, enemy2, enemy3;
	private BitmapFont font;

//  Jade
	private final HashMap<String, ContainerController> containerMap = new HashMap<>();

	public void volumeControl(boolean volumeKnob){
		if(volumeKnob) {
			if(masterVolume - 0.1f * Gdx.graphics.getDeltaTime() <= 1.0f) masterVolume += 0.1f * Gdx.graphics.getDeltaTime();
		} else {
			if(masterVolume - 0.1f * Gdx.graphics.getDeltaTime() >= 0.0f) masterVolume -= 0.1f * Gdx.graphics.getDeltaTime();
		}
		this.battlemusic.setVolume(masterVolume);
	}

	public Animation<TextureRegion> loadAnimationSpriteSheet(String internal_file_name, int frame_cols, int frame_rows, float frame_duration){
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

		// sprites
//		sprite_hero_one = new Sprite(tex_hero_one, 0, 0, 128, 128);
//		sprite_hero_one.setPosition(10, 10);

		// animations
		stateTime = 0f;

		// entities

		float[] iniciativas = Utils.calcularIniciativa();

		hero1 = new Entity(true, 0, 0, camera.viewportWidth, camera.viewportHeight, 50, 90, 25,0, iniciativas[0]);
		hero2 = new Entity(true, 1, 1, camera.viewportWidth, camera.viewportHeight, 70, 80, 30,0, iniciativas[1]);
		hero3 = new Entity(true, 2, 2, camera.viewportWidth, camera.viewportHeight, 40, 100, 20,0, iniciativas[2]);
		enemy1 = new Entity(false, 0, 0, camera.viewportWidth, camera.viewportHeight, 50, 90, 25,0, iniciativas[3]);
		enemy2 = new Entity(false, 1, 1, camera.viewportWidth, camera.viewportHeight, 70, 80, 30,0, iniciativas[4]);
		enemy3 = new Entity(false, 2, 2, camera.viewportWidth, camera.viewportHeight, 40, 100, 20,0, iniciativas[5]);

		font = new BitmapFont();

		// load the drop sound effect and the rain background "music"
//		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		battlemusic = Gdx.audio.newMusic(Gdx.files.internal("battlemusic-yeah-18130.mp3"));

		// start the playback of the background music immediately
		masterVolume = 0.05f;
		battlemusic.setLooping(true);
		battlemusic.play();
		battlemusic.setVolume(masterVolume);

		// Jade initialize

		Runtime rt = Runtime.instance();
		Profile p = new ProfileImpl();
		p.setParameter(Profile.MAIN_HOST, "localhost");
		p.setParameter(Profile.MAIN_PORT, "1099");
		p.setParameter(Profile.CONTAINER_NAME, "Main-Container");
		p.setParameter(Profile.GUI, "true");

		this.containerMap.put("Main-Container", rt.createMainContainer(p));

		CreateAgents();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		// Update current camera and time deltas
		camera.update();
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Animations: Get current frame of animation for the current stateTime
		if (hero1.isLooping()){
			hero1.currentFrame = hero1.currentAnimation.getKeyFrame(stateTime, hero1.isLooping());
		}
		else {
			hero1.currentFrame = hero1.currentAnimation.getKeyFrame(stateTime);
		}
		hero2.currentFrame = hero2.currentAnimation.getKeyFrame(stateTime, hero2.isLooping());
		hero3.currentFrame = hero3.currentAnimation.getKeyFrame(stateTime, hero3.isLooping());
		enemy1.currentFrame = enemy1.currentAnimation.getKeyFrame(stateTime, enemy1.isLooping());
		enemy2.currentFrame = enemy2.currentAnimation.getKeyFrame(stateTime, enemy2.isLooping());
		enemy3.currentFrame = enemy3.currentAnimation.getKeyFrame(stateTime, enemy3.isLooping());

		// Start rendering
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(hero1.currentFrame, hero1.getPosition().x, hero1.getPosition().y);
		batch.draw(hero2.currentFrame, hero2.getPosition().x, hero2.getPosition().y);
		batch.draw(hero3.currentFrame, hero3.getPosition().x, hero3.getPosition().y);
		batch.draw(enemy1.currentFrame, enemy1.getPosition().x, enemy1.getPosition().y);
		batch.draw(enemy2.currentFrame, enemy2.getPosition().x, enemy2.getPosition().y);
		batch.draw(enemy3.currentFrame, enemy3.getPosition().x, enemy3.getPosition().y);
//		sprite_hero_one.draw(batch);

		font.draw(batch, hero1.getHealthPointsText(), hero1.getPosition().x, hero1.getPosition().y);
		font.draw(batch, hero2.getHealthPointsText(), hero2.getPosition().x, hero2.getPosition().y);
		font.draw(batch, hero3.getHealthPointsText(), hero3.getPosition().x, hero3.getPosition().y);
		font.draw(batch, enemy1.getHealthPointsText(), enemy1.getPosition().x, enemy1.getPosition().y);
		font.draw(batch, enemy2.getHealthPointsText(), enemy2.getPosition().x, enemy2.getPosition().y);
		font.draw(batch, enemy3.getHealthPointsText(), enemy3.getPosition().x, enemy3.getPosition().y);

		font.draw(batch, hero1.getCharacterName(), hero1.getPosition().x, hero1.getPosition().y + 90);
		font.draw(batch, hero2.getCharacterName(), hero2.getPosition().x, hero2.getPosition().y + 90);
		font.draw(batch, hero3.getCharacterName(), hero3.getPosition().x, hero3.getPosition().y + 90);
		font.draw(batch, enemy1.getCharacterName(), enemy1.getPosition().x, enemy1.getPosition().y + 90);
		font.draw(batch, enemy2.getCharacterName(), enemy2.getPosition().x, enemy2.getPosition().y + 90);
		font.draw(batch, enemy3.getCharacterName(), enemy3.getPosition().x, enemy3.getPosition().y + 90);

		// End rendering
		batch.end();

		// Game logic
		if(Gdx.input.isKeyPressed(Input.Keys.MINUS)) volumeControl(false);
		if(Gdx.input.isKeyPressed(Input.Keys.PLUS) || Gdx.input.isKeyPressed(Input.Keys.EQUALS)) volumeControl(true);
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) hero1.setAnimationState(2);

		if(hero1.currentAnimation.isAnimationFinished(stateTime) && hero1.getAnimationState() != 0){  //condition false
			hero1.setAnimationState(0);
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		battlemusic.dispose();

	}
	private void CreateAgents()
	{
		ContainerController cc = this.containerMap.get("Main-Container");

		try
		{
			cc.createNewAgent("Mago", "com.mygdx.dungeonsagents.ai.Mago", new Object[]{}).start();
			cc.createNewAgent("Guerreiro", "com.mygdx.dungeonsagents.ai.Guerreiro", new Object[]{}).start();
			cc.createNewAgent("Arqueiro", "com.mygdx.dungeonsagents.ai.Arqueiro", new Object[]{}).start();
			cc.createNewAgent("Orc_Berserker", "com.mygdx.dungeonsagents.ai.Orc_Berserker", new Object[]{}).start();
			cc.createNewAgent("Orc_Warrior", "com.mygdx.dungeonsagents.ai.Orc_Warrior", new Object[]{}).start();
			cc.createNewAgent("Orc_Shaman", "com.mygdx.dungeonsagents.ai.Orc_Shaman", new Object[]{}).start();
		}
		catch (StaleProxyException ex)
		{
			System.out.println("Problema ao criar Agente.");
		}
	}
}
