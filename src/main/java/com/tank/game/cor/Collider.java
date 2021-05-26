package com.tank.game.cor;

import com.tank.game.entity.GameObject;

/**
 * 碰撞器,返回boolean的作用是责任链模式
 */
public interface Collider {
    boolean collide(GameObject o1, GameObject o2);
}
