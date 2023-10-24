package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;
public class Orc_Berserker extends Entity {
    public Orc_Berserker()
    {
        super(false, 0, 0, 1280, 720, 50, 90, 25);
    }
    public Orc_Berserker(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight, float healthPoints, float energy, float defense) {
        super(ally, placement, fightingClass, viewportWidth, viewportHeight, healthPoints, energy, defense);
    }
}
