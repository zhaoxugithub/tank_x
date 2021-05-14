package com.tank.game.factory;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.entity.TankFrame;

public abstract class GameFactory {
    public abstract BaseTank createTank(int x, int y, Dir dir, TankFrame tf, Group group);
    public abstract BaseBullet createBullet(int x, int y, Dir dir, TankFrame tf, Group group);
    public abstract BaseExplode createExplode(int x, int y, TankFrame tankFrame);
}
