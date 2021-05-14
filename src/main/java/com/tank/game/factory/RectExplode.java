package com.tank.game.factory;

import com.tank.game.entity.TankFrame;
import com.tank.util.ResourceManager;

import java.awt.*;

public class RectExplode extends BaseExplode {

    private int x, y;
    public static int WIDTH = ResourceManager.explodes[0].getWidth();
    public static int HEIGHT = ResourceManager.explodes[0].getHeight();

    TankFrame tankFrame = null;

    private boolean living = true;

    private int step = 0;

    public RectExplode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
    }


    @Override
    public void paint(Graphics g) {
        if (!living) return;
//        g.drawImage(ResourceManager.explodes[step++], this.x, this.y, null);
        Color color = g.getColor();
        g.setColor(Color.red);
        g.fillRect(x, y, 10 * step, 10 * step);
        step++;
        if (step >= 5) {
            living = false;
        }
        g.setColor(color);
    }
}
