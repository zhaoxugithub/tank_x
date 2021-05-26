package com.tank.game.control;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.cor.ColliderChain;
import com.tank.game.entity.GameObject;
import com.tank.game.entity.Tank;
import com.tank.game.entity.Wall;
import com.tank.util.ConfigUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加门面模式,内部管理这个实体，tank、bullet、explode，然后通过gameModel 与 TankFrame 打交道
 */
public class GameModel {

    //将坦克、子弹、爆炸 放在同一个集合中
    private List<GameObject> objects = new ArrayList<>();
    //责任链模式
    ColliderChain colliderChain = new ColliderChain();

    private Tank tank;

    private static final GameModel INSTANCE = new GameModel();

    static {
        INSTANCE.init();
    }

    private GameModel() {
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public void init() {
        tank = new Tank(200, 200, Dir.DOWN, Group.GOOD);
        int tankNum = ConfigUtil.getInteger("tankNum");

        for (int i = 0; i < tankNum; i++) {
            objects.add(new Tank(100 + i * 80, 400, Dir.DOWN, Group.BAD));
        }
        addObject(new Wall(150, 150, 200, 50));
        addObject(new Wall(550, 150, 200, 50));
        addObject(new Wall(300, 300, 50, 200));
        addObject(new Wall(550, 300, 50, 200));
    }

    public void addObject(GameObject object) {
        this.objects.add(object);
    }

    public void remove(GameObject object) {
        this.objects.remove(object);
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.RED);
//        g.drawString("打出子弹个数：" + bulletList.size(), 10, 60);
//        g.drawString("剩余敌方坦克：" + tanks.size(), 200, 60);
        g.setColor(color);
        //把画自己的逻辑放在tank里面，更加方便，提现了面向对象的封装性
        //生产己方坦克
        tank.paint(g);
        //生成子弹数量
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                colliderChain.collide(objects.get(i), objects.get(j));
            }
        }
    }

    public Tank getMainTank() {
        return tank;
    }
}
