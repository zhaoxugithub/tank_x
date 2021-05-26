package com.tank.game.cor;

import com.tank.game.entity.GameObject;
import com.tank.game.entity.Tank;
import com.tank.game.entity.Wall;

public class TankWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Wall) {
            Tank tank = (Tank) o1;
            Wall wall = (Wall) o2;
            if (tank.rectangle.intersects(wall.rectangle)) {
                tank.backMoving();
            }
        } else if (o1 instanceof Wall && o2 instanceof Tank) {
            collide(o2, o1);
        }
        return false;
    }
}
