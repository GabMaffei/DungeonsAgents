package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;

public class Guerreiro extends Entity {
    public Guerreiro()
    {
        super(true, 1, 1, 1280, 720, 70, 80, 300, 0, 0);
    }
    public Guerreiro(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight, float healthPoints, float energy, float defense, int turno, float iniciativa) {
        super(ally, placement, fightingClass, viewportWidth, viewportHeight, healthPoints, energy, defense, turno, iniciativa);
    }
}
