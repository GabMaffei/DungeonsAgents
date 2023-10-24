package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;
public class Mago extends Entity {
    public Mago()
    {
        super(true, 2, 2, 1280, 720, 40, 100, 20);
    }
    public Mago(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight, float healthPoints, float energy, float defense) {
        super(ally, placement, fightingClass, viewportWidth, viewportHeight, healthPoints, energy, defense);
    }
}
