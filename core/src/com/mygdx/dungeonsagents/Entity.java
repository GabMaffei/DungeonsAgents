package com.mygdx.dungeonsagents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
    private float healthPoints;
    private final boolean alignment; // 0 = Hero/Ally - 1 = Villain/Enemy
    private final int placement;
    private final int fightingClass;
    private Rectangle position;
    private int animationState;
    public Animation<TextureRegion> currentAnimation;
    public TextureRegion currentFrame;

    public Entity(boolean alignment, int placement, int fightingClass, int viewportWidth, int viewportHeight){
        this.alignment = alignment;
        this.placement = placement;
        this.fightingClass = fightingClass;
        this.animationState = 0;
        this.createPosition(viewportWidth, viewportHeight);
        this.loadAnimations();
    }

    private void loadAnimations(){
        String internal_file_name;
        int frame_cols, frame_rows;
        switch (this.fightingClass){
            case 0:
                internal_file_name = "Heroes/Archer/Idle.png";
                frame_cols = 6;
                frame_rows = 1;
                break;
            case 1:
                internal_file_name = "Heroes/Swordsman/Idle.png";
                frame_cols = 8;
                frame_rows = 1;
                break;
            default:
                internal_file_name = "Heroes/Archer/Idle.png";
                frame_cols = 6;
                frame_rows = 1;
                break;
        }

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
        currentAnimation = new Animation<TextureRegion>(0.25f, walkFrames);
    }

    private void createPosition(int viewportWidth, int viewportHeight){
        if (!(this.alignment)) {
            switch (this.placement) {
                case 0:
                    this.position = new Rectangle();
                    this.position.x = (int) viewportWidth / 4;
                    this.position.y = (int) ((int) viewportHeight / 4 * 1);
                    this.position.width = 128;
                    this.position.height = 128;
                    break;
                case 1:
                    this.position.x = (int) viewportWidth / 4;
                    this.position.y = (int) ((int) viewportHeight / 4 * 1.8);
                    this.position.width = 128;
                    this.position.height = 128;
                    break;
                case 2:
                    this.position.x = (int) viewportWidth / 4;
                    this.position.y = (int) ((int) viewportHeight / 4 * 2.5);
                    this.position.width = 128;
                    this.position.height = 128;
                    break;
            }
        } else {
            switch (this.placement) {
                case 0:
                    this.position = new Rectangle();
                    this.position.x = ((int) viewportWidth / 4 * 3);
                    this.position.y = (int) ((int) viewportHeight / 4 * 1);
                    this.position.width = 128;
                    this.position.height = 128;
                    break;
                case 1:
                    this.position.x = ((int) viewportWidth / 4 * 3);
                    this.position.y = (int) ((int) viewportHeight / 4 * 1.8);
                    this.position.width = 128;
                    this.position.height = 128;
                    break;
                case 2:
                    this.position.x = ((int) viewportWidth / 4 * 3);
                    this.position.y = (int) ((int) viewportHeight / 4 * 2.5);
                    this.position.width = 128;
                    this.position.height = 128;
                    break;
            }
        }
    }

    public float getHealthPoints() {
        return healthPoints;
    }

    public boolean isAlignment() {
        return alignment;
    }

    public int getPlacement() {
        return placement;
    }

    public int getFightingClass() {
        return fightingClass;
    }

    public Rectangle getPosition() {
        return position;
    }
}
