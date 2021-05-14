package com.tank.game.factory;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.entity.TankFrame;

public class RectFactory extends GameFactory {
    @Override
    public BaseTank createTank(int x, int y, Dir dir, TankFrame tf, Group group) {
        return null;
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, TankFrame tf, Group group) {
        return new RectBullet(x, y, dir, tf, group);
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tankFrame) {
        return new RectExplode(x, y, tankFrame);
    }
}
