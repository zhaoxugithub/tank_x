package com.tank.game.entity;

import cn.hutool.core.util.ReflectUtil;
import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.DefaultFireStrategy;
import com.tank.game.FireStrategy;
import com.tank.util.ConfigUtil;
import com.tank.util.ResourceManager;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Random;

import static com.tank.util.TankWarConfig.*;

/**
 * 测试
 */
@Slf4j
@ToString
@Data
public class Tank {
    //坦克位置
    private int x, y;
    //坦克方向
    private Dir dir;
    //判断坦克 是否移动，默认是静止
    private boolean moving = false;
    //判断坦克是否存活
    private boolean living = true;
    private Rectangle rectangle;
    //生产随机数
    private Random random = new Random();
    private TankFrame tf;
    private Group group;
    private FireStrategy fs = null;

    public Tank(int x, int y, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
        rectangle = new Rectangle(this.x, this.y, BADTANKWIDTH, BADTANKHEIGHT);
        confirmFireMethod();
    }

    /**
     * 指定坦克开火方式
     */
    private void confirmFireMethod() {
        fs = this.getGroup() == Group.GOOD ?
                ReflectUtil.newInstance(
                        ConfigUtil.getString("goodFS", "com.tank.game.DefaultFireStrategy")) :
                new DefaultFireStrategy();
    }

    public Tank(int x, int y, Dir dir, boolean move, TankFrame tf, Group group) {
        this(x, y, dir, tf, group);
        this.moving = move;
    }

    //生成或者画出一个坦克
    public void paint(Graphics g) {
//        if (!isLiving()) {
//            tf.getComputerTanks().remove(this);
//        }
        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankL : ResourceManager.badTankL, this.x,
                        this.y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankR : ResourceManager.badTankR, this.x,
                        this.y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankU : ResourceManager.badTankU, this.x,
                        this.y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankD : ResourceManager.badTankD, this.x,
                        this.y, null);
                break;
            default:
                break;
        }
        move();
    }

    private void move() {

        if (!moving) return;
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }
        if (random.nextInt(10) > 8 && this.group == Group.BAD) this.fire();
        //敌方坦克的随机方向
        if (this.group == Group.BAD && random.nextInt(10) > 8) randomDir();

        //坦克开除边界检测
        boundsCheck();

        rectangle.x = this.x;
        rectangle.y = this.y;
    }

    private void boundsCheck() {
        if (this.x < 0) this.x = 0;
        if (this.x > GAME_WIDTH - GOODTANKWIDTH) this.x = GAME_WIDTH - GOODTANKWIDTH;
        if (this.y < 30) this.y = 30;
        if (this.y > GAME_HEIGHT - GOODTANKHEIGHT) this.y = GAME_HEIGHT - GOODTANKHEIGHT;
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    /**
     * 为了方便调试工厂模式，暂时停用策略模式，注释掉fs.fire()方法，将默认的fire方法拷贝到下面的fire方法中
     */
    public void fire() {
        fs.fire(this);
    }

    public void die() {
        this.setLiving(false);
    }
}
