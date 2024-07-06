package com.tank.game;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.entity.Bullet;
import com.tank.game.entity.Tank;
import com.tank.util.Audio;

import static com.tank.util.TankWarConfig.*;
import static com.tank.util.TankWarConfig.BADBULLETHEIGHT;

public class FourFireStrategy implements FireStrategy {
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
        Dir[] values = Dir.values();
        for (Dir dir : values) {
            Bullet bullet = new Bullet(bx, by, dir, tank.getTf(), tank.getGroup());
        }
        if (tank.getGroup() == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
