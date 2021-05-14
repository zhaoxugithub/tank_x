package com.tank.game.factory;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.entity.*;

public class DefaultFactory extends GameFactory {

    @Override
    public BaseTank createTank(int x, int y, Dir dir, TankFrame tf, Group group) {
        return new Tank(x, y, dir, tf, group);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, TankFrame tf, Group group) {
        return new Bullet(x, y, dir, tf, group);
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tankFrame) {
        return new Explode(x, y, tankFrame);
    }
}
