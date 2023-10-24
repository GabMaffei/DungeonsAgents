package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;

public class Arqueiro extends Entity {
    public Arqueiro()
    {
        super(false, 0, 2, 1280, 720);
    }
    public Arqueiro(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight) {
        super(ally, placement, fightingClass, viewportWidth, viewportHeight);
    }
}
