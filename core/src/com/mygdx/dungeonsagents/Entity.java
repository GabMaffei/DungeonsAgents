package com.mygdx.dungeonsagents;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import jade.core.Agent;

import static com.badlogic.gdx.math.MathUtils.round;

public class Entity extends Agent {
    protected float healthPoints;
    protected float energy;
    protected float defense;
    protected int turno;
    protected float iniciativa;
    private final boolean ally; // true = Hero/Ally - false = Villain/Enemy
    private final int placement;
    private final int fightingClass;
    private Rectangle position;
    private int animationState;
    private boolean looping;
    public Animation<TextureRegion> currentAnimation;
    public TextureRegion currentFrame;

    public Entity(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight, float healthPoints, float energy, float defense, int turno, float iniciativa){
        this.ally = ally;
        this.placement = placement;
        this.fightingClass = fightingClass;
        this.animationState = 0;
        this.looping = true;
        this.healthPoints = healthPoints;
        this.energy = energy;
        this.defense = defense;
        this.turno = turno;
        this.iniciativa = iniciativa;
        this.createPosition(viewportWidth, viewportHeight);
        this.loadAnimations();
    }

    public void setAnimationState(int animationState) {
        this.animationState = animationState;
        this.loadAnimations();
//        this.looping = animationState == 0;
    }

    private String getFightingClassName(){
        if (this.ally) {
            switch (this.fightingClass){
                case 0:
                    return "Archer";
                case 1:
                    return "Swordsman";
                case 2:
                    return "Wizard";
            }
        } else {
            switch (this.fightingClass){
                case 0:
                    return "Orc_Berserk";
                case 1:
                    return "Orc_Warrior";
                case 2:
                    return "Orc_Shaman";
                case 3:
                    return  "Red_Slime";
                case 4:
                    return "Green_Slime";
                case 5:
                    return "Blue_Slime";
            }
        }
        return "Archer";
    }

    private String getAlignmentName(){
        if(this.ally){
            return "Heroes";
        }
        return "Enemies";
    }

    private String getAnimationStateName(){
        switch (this.animationState){
            case 0:
                return "Idle";
            case 1:
                return "Idle_2";
            case 2:
                return "Attack_1";
            case 3:
                return "Attack_2";
            case 4:
                return "Attack_3";
            case 5:
                return "Shot_1";
            case 6:
                return "Shot_2";
            case 7:
                return "Hurt";
            case 8:
                return "Dead";
            default:
                return "Idle";
        }
    }

    private String getAnimationInternalFileName(){
        return this.getAlignmentName() + "/" + this.getFightingClassName() + "/" + this.getAnimationStateName() + ".png";
    }

    private List<Integer> getFrameColsRows() {
        List<Integer> listColsRows = new LinkedList<Integer>();
        if (ally) {
            switch (fightingClass) {
                case 0: //Archer
                    switch (this.animationState) {
                        case 0: //Idle
                            listColsRows.add(6);
                            listColsRows.add(1);
                        case 2: //Attack_1
                            listColsRows.add(4);
                            listColsRows.add(1);
                        default:
                            listColsRows.add(6);
                            listColsRows.add(1);
                            break;
                    }
                case 1: //Swordsman
                    switch (this.animationState) {
                        case 0: //Idle
                            listColsRows.add(8);
                            listColsRows.add(1);
                        default:
                            listColsRows.add(6);
                            listColsRows.add(1);
                            break;
                    }
                case 2: //Wizard
                    switch (this.animationState) {
                        case 0: //Idle
                            listColsRows.add(6);
                            listColsRows.add(1);
                        default:
                            listColsRows.add(6);
                            listColsRows.add(1);
                            break;
                    }
            }
        } else {
            switch (fightingClass) {
                case 0: //Orc_Berserk
                    switch (this.animationState) {
                        case 0: //Idle
                            listColsRows.add(5);
                            listColsRows.add(1);
                        default:
                            listColsRows.add(5);
                            listColsRows.add(1);
                            break;
                    }
                case 1: //Orc_Warrior
                    switch (this.animationState) {
                        case 0: //Idle
                            listColsRows.add(5);
                            listColsRows.add(1);
                        default:
                            listColsRows.add(5);
                            listColsRows.add(1);
                            break;
                    }
                case 2: //Orc_Shaman
                    switch (this.animationState) {
                        case 0: //Idle
                            listColsRows.add(5);
                            listColsRows.add(1);
                        default:
                            listColsRows.add(5);
                            listColsRows.add(1);
                            break;
                    }
            }
        }
        return listColsRows;
    }

    private void loadAnimations(){
        String internal_file_name = this.getAnimationInternalFileName();
        List<Integer> frameColsRows= this.getFrameColsRows();
        int frame_cols = frameColsRows.get(0);
        int frame_rows = frameColsRows.get(1);

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

    private void createPosition(float viewportWidth, float viewportHeight){
        this.position = new Rectangle();
        if (this.ally) {
            switch (this.placement) {
                case 0:
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

    public String getHealthPointsText(){
        return "HP: " + round(this.healthPoints);
    }

    public String getCharacterName(){
        return (this.placement + 1) + " " + this.getFightingClassName();
    }

    public Rectangle getPosition() {
        return position;
    }

    public boolean isLooping() {
        return looping;
    }

    public int getAnimationState() {
        return animationState;
    }
}
