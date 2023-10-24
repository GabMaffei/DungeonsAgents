package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;

public class Guerreiro extends Entity {
    public Guerreiro()
    {
        super(true, 1, 1, 1280, 720, 70, 80, 30);
    }
    public Guerreiro(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight, float healthPoints, float energy, float defense) {
        super(ally, placement, fightingClass, viewportWidth, viewportHeight, healthPoints, energy, defense);
    }
}
