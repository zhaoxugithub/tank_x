package com.tank;


import com.tank.enums.Dir;
import com.tank.enums.Group;
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
    private boolean moving = false;

    //判断坦克是否存活
    private boolean living = true;

    //获取坦克的宽度和高度
    public static int WIDTH = ResourceManager.goodTankL.getWidth();
    public static int HEIGHT = ResourceManager.goodTankL.getHeight();

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
                g.drawImage(ResourceManager.goodTankL, this.x, this.y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceManager.goodTankR, this.x, this.y, null);
                break;
            case UP:
                g.drawImage(ResourceManager.goodTankU, this.x, this.y, null);
                break;
            case DOWN:
                g.drawImage(ResourceManager.goodTankD, this.x, this.y, null);
                break;
            default:
                break;
        }
        move();
    }

    private void move() {

        if (random.nextInt(10) > 8 && this.group == Group.BAD) this.fire();
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

    }

    public void fire() {

        int bx = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Bullet bullet = new Bullet(bx, by, this.dir, tf, this.group);
        tf.bulletList.add(bullet);
    }

    public void die() {
        this.setLiving(false);
    }
}
