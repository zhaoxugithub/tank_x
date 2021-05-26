package com.tank.game.entity;

import java.awt.*;

public class Wall extends GameObject {

    private int x, y;
    private int w, h;

    public Rectangle rectangle = null;

    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        rectangle = new Rectangle(x, y, w, h);
    }


    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }
}
