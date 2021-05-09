package com.tank.game.entity;

import java.awt.*;

public abstract class BaseBullet {

    public abstract void paint(Graphics g);

    public abstract void collideWith(Tank tank);
}
