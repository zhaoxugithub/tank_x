package com.tank.game.entity;

import java.awt.*;

/**
 * 坦克、子弹、爆炸的基类，都会继承这个类
 */
public abstract class GameObject {
    int x, y;
    public abstract void paint(Graphics g);
}
