package com.tank;


import java.awt.*;

public class Tank {
    //坦克位置
    private int x, y;
    //坦克方向
    private Dir dir = Dir.DOWN;
    //坦克速度，步进
    private static final int SPEED = 5;
    //判断坦克 是否移动，默认是静止
    private boolean moving = false;

    TankFrame tf = null;

    public Tank(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
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

        int bx = this.x + ResourceManager.goodTankL.getWidth() / 2 - ResourceManager.bulletL.getWidth() / 2;
        int by = this.y + ResourceManager.goodTankL.getHeight() / 2 - ResourceManager.bulletL.getHeight() / 2;

        Bullet bullet = new Bullet(bx, by, this.dir, tf);
        tf.bulletList.add(bullet);
    }
}
