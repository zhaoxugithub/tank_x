package com.tank.game.factory;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.entity.BaseBullet;
import com.tank.game.entity.BaseExplode;
import com.tank.game.entity.BaseTank;
import com.tank.game.entity.TankFrame;

public abstract class BaseFactory {
    abstract BaseTank createTank(int x, int y, Dir dir, TankFrame tf, Group group);
    abstract BaseBullet createBullet(int x, int y, Dir dir, TankFrame tf, Group group);
    abstract BaseExplode createExplode(int x, int y, TankFrame tankFrame);
}
