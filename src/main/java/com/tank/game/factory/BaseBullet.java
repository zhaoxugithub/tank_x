package com.tank.game.factory;

import com.tank.game.entity.Tank;

import java.awt.*;

public abstract class BaseBullet {

    public abstract void paint(Graphics g);

    public abstract void collideWith(Tank tank);
}
