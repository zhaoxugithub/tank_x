package com.tank.game;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.entity.Bullet;
import com.tank.game.entity.Tank;
import com.tank.util.Audio;

public class FourFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        int bx, by;
        if (tank.getGroup() == Group.GOOD) {
            bx = tank.getX() + Tank.GOODWIDTH / 2 - Bullet.GOODWIDTH / 2;
            by = tank.getY() + Tank.GOODHEIGHT / 2 - Bullet.GOODHEIGHT / 2;
        } else {
            bx = tank.getX() + Tank.BADWIDTH / 2 - Bullet.BADWIDTH / 2;
            by = tank.getY() + Tank.BADHEIGHT / 2 - Bullet.BADHEIGHT / 2;
        }
        Dir[] values = Dir.values();
        for (Dir dir : values) {
            Bullet bullet = new Bullet(bx, by, dir, tank.tf, tank.getGroup());
            tank.tf.bulletList.add(bullet);
        }
        if (tank.getGroup() == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
