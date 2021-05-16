package com.tank.game.entity;

public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {

        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet bullet = (Bullet) o1;
            Tank tank = (Tank) o2;
            //如果坦克和子弹撞上了
            return !bullet.collideWith(tank);

        } else if (o2 instanceof Bullet && o1 instanceof Tank) {
            collide(o2, o1);
        }
        return true;
    }
}
