package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;

public class Orc_Warrior extends Entity {

    public Orc_Warrior()
    {
        super(false, 1, 1, 1280, 720, 70, 80, 30, 0, 0);
    }

    public Orc_Warrior(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight, float healthPoints, float energy, float defense, int turno, float iniciativa) {
        super(ally, placement, fightingClass, viewportWidth, viewportHeight, healthPoints, energy, defense, turno, iniciativa);
    }
}
