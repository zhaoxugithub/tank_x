package com.tank.game.entity;

import com.tank.util.ResourceManager;

import java.awt.*;

/**
 * 爆炸类
 */
public class Explode {

    private int x, y;
    public static int WIDTH = ResourceManager.explodes[0].getWidth();
    public static int HEIGHT = ResourceManager.explodes[0].getHeight();

    TankFrame tankFrame = null;

    private boolean living = true;

    private int step = 0;

    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g) {
        if (!living) return;
        g.drawImage(ResourceManager.explodes[step++], this.x, this.y, null);
        if (step >= ResourceManager.explodes.length) {
            living = false;
        }
    }
}
