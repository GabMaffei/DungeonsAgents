package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;
public class Orc_Berserker extends Entity {
    public Orc_Berserker()
    {
        super(false, 0, 2, 1280, 720);
    }
    public Orc_Berserker(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight) {
        super(ally, placement, fightingClass, viewportWidth, viewportHeight);
    }
}
