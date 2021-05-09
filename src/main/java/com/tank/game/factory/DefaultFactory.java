package com.tank.game.factory;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.entity.*;

public class DefaultFactory extends BaseFactory {

    @Override
    BaseTank createTank(int x, int y, Dir dir, TankFrame tf, Group group) {
        return new Tank(x, y, dir, tf, group);
    }

    @Override
    BaseBullet createBullet(int x, int y, Dir dir, TankFrame tf, Group group) {
        return new Bullet(x, y, dir, tf, group);
    }

    @Override
    BaseExplode createExplode(int x, int y, TankFrame tankFrame) {
        return new Explode(x, y, tankFrame);
    }
}
