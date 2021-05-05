package com.tank.game;


import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.util.Audio;
import com.tank.util.ResourceManager;

import java.awt.*;
import java.util.Random;

public class Tank {
    //坦克位置
    private int x, y;
    //坦克方向
    private Dir dir = Dir.DOWN;
    //坦克速度，步进
    private static final int SPEED = 5;
    //判断坦克 是否移动，默认是静止
    private boolean moving = true;

    //判断坦克是否存活
    private boolean living = true;

    //获取坦克的宽度和高度
    public static int GOODWIDTH = ResourceManager.goodTankL.getWidth();
    public static int GOODHEIGHT = ResourceManager.goodTankL.getHeight();

    public static int BADWIDTH = ResourceManager.badTankL.getWidth();
    public static int BADHEIGHT = ResourceManager.badTankL.getHeight();

    //生产随机数
    private Random random = new Random();

    TankFrame tf = null;

    private Group group = Group.BAD;

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tank(int x, int y, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    //生成或者画出一个坦克
    public void paint(Graphics g) {

        if (!isLiving()) {
            tf.tanks.remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankL : ResourceManager.badTankL, this.x, this.y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankR : ResourceManager.badTankR, this.x, this.y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankU : ResourceManager.badTankU, this.x, this.y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankD : ResourceManager.badTankD, this.x, this.y, null);
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
    }

    private void boundsCheck() {
        if (this.x < 0) this.x = 0;
        if (this.x > TankFrame.GAME_WIDTH - Tank.GOODWIDTH) this.x = TankFrame.GAME_WIDTH - Tank.GOODWIDTH;
        if (this.y < 30) this.y = 30;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.GOODHEIGHT) this.y = TankFrame.GAME_HEIGHT - Tank.GOODHEIGHT;
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        int bx, by;
        if (this.group == Group.GOOD) {
            bx = this.x + Tank.GOODWIDTH / 2 - Bullet.GOODWIDTH / 2;
            by = this.y + Tank.GOODHEIGHT / 2 - Bullet.GOODHEIGHT / 2;
        } else {
            bx = this.x + Tank.BADWIDTH / 2 - Bullet.BADWIDTH / 2;
            by = this.y + Tank.BADHEIGHT / 2 - Bullet.BADHEIGHT / 2;
        }

        Bullet bullet = new Bullet(bx, by, this.dir, tf, this.group);
        tf.bulletList.add(bullet);

        if (this.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }

    public void die() {
        this.setLiving(false);
    }
}
