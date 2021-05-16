package com.tank.game.entity;

public class TankTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {

        if (o1 instanceof Tank && o2 instanceof Tank) {
            Tank tank1 = (Tank) o1;
            Tank tank2 = (Tank) o2;
            //如果两个坦克相交碰撞
            if (tank1.getRectangle().intersects(tank2.getRectangle())) {
                tank1.backMoving();
                tank2.backMoving();
            }
        }
        return true;
    }
}
