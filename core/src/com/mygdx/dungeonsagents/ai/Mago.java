package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;
public class Mago extends Entity {
    public Mago()
    {
        super(false, 0, 2, 1280, 720);
    }
    public Mago(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight) {
        super(true, placement, 2, 1280, 720);
    }
}
