package com.tank.game.entity;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.util.ConfigUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加门面模式,内部管理这个实体，tank、bullet、explode，然后通过gameModel 与 TankFrame 打交道
 */
public class GameModel {


    private Tank tank = new Tank(200, 200, Dir.DOWN, this, Group.GOOD);
    //子弹容器
    List<Bullet> bulletList = new ArrayList<Bullet>();
    //爆炸容器
    List<Explode> explodes = new ArrayList<Explode>();
    //坦克容器
    List<Tank> tanks = new ArrayList<Tank>();


    public GameModel() {
        int tankNum = ConfigUtil.getInteger("tankNum");

        for (int i = 0; i < tankNum; i++) {
            tanks.add(new Tank(100 + i * 80, 400, Dir.DOWN, this, Group.BAD));
        }
    }


    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.RED);
        g.drawString("打出子弹个数：" + bulletList.size(), 10, 60);
        g.drawString("剩余敌方坦克：" + tanks.size(), 200, 60);
        g.setColor(color);

        //把画自己的逻辑放在tank里面，更加方便，提现了面向对象的封装性

        //生产己方坦克
        tank.paint(g);

        //生成子弹数量
        for (int i = 0; i < bulletList.size(); i++) {
            bulletList.get(i).paint(g);
        }

        //生成敌方坦克个数
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }

        //生成爆炸类
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }


        //判断子弹和坦克是否发生碰撞
        for (int i = 0; i < bulletList.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bulletList.get(i).collideWith(tanks.get(j));
            }
        }

    }


    public Tank getMainTank() {
        return tank;
    }
}
