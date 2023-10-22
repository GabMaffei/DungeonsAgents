package com.mygdx.dungeonsagents;

public class Entities {
    private float healthPoints;
    private boolean alignment; // 0 = Hero/Ally - 1 = Villain/Enemy
    private int mapPos;
    private int fightingClass;

    public Entities(boolean alignment, int mapPos, int fightingClass){
        this.alignment = alignment;
        this.mapPos = mapPos;

    }

    public float getHealthPoints() {
        return healthPoints;
    }

    public boolean isAlignment() {
        return alignment;
    }

    public int getMapPos() {
        return mapPos;
    }

    public int getFightingClass() {
        return fightingClass;
    }
}
