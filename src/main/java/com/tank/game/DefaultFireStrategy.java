package com.tank.game;

import com.tank.enums.Group;
import com.tank.game.entity.Bullet;
import com.tank.game.entity.Tank;
import com.tank.util.Audio;

import static com.tank.util.TankWarConfig.*;

/**
 * 坦克的默认开火方式
 */
public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        int bx, by;
        if (tank.getGroup() == Group.GOOD) {
            bx = tank.getX() + GOODTANKWIDTH / 2 - GOODBULLETWIDTH / 2;
            by = tank.getY() + GOODTANKHEIGHT / 2 - GOODBULLETHEIGHT / 2;
        } else {
            bx = tank.getX() + BADTANKWIDTH / 2 - BADBULLETWIDTH / 2;
            by = tank.getY() + BADTANKWIDTH / 2 - BADBULLETHEIGHT / 2;
        }
        new Bullet(bx, by, tank.getDir(), tank.getGroup());
        if (tank.getGroup() == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
