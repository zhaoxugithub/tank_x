package com.tank.game.cor;

import com.tank.game.entity.GameObject;
import com.tank.util.ConfigUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 责任链模式
 */

//方便两个责任链进行add
public class ColliderChain implements Collider {


    private List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        List<Object> collide = ConfigUtil.getObjectList("collide");
        for (int i = 0; i < collide.size(); i++) {
            add((Collider) collide.get(i));
        }
    }

    public void add(Collider collider) {
        colliders.add(collider);
    }

    public boolean collide(GameObject o1, GameObject o2) {

        for (int i = 0; i < colliders.size(); i++) {
            if (!colliders.get(i).collide(o1, o2)) {
                return false;
            }
        }
        return true;
    }
}
