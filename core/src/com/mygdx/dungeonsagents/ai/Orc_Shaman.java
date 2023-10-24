package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;

public class Orc_Shaman extends Entity {
    public Orc_Shaman() {
        super(false, 2, 2, 1280, 720, 40, 100, 20, 0, 0);
    }

    public Orc_Shaman(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight, float healthPoints, float energy, float defense, int turno, float iniciativa) {
        super(ally, placement, fightingClass, viewportWidth, viewportHeight, healthPoints, energy, defense, turno, iniciativa);
    }
}
