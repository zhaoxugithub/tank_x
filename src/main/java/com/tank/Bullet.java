package com.tank;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.util.Audio;
import com.tank.util.ResourceManager;

import java.awt.*;

public class Bullet {

    //子弹速度
    private static final int SPEED = 10;
    private int x, y;
    private Dir dir;
    private boolean isLive = true;

    public static int WIDTH = ResourceManager.bulletL.getWidth();
    public static int HEIGHT = ResourceManager.bulletL.getHeight();

    TankFrame tf = null;

    private Group group = Group.BAD;

    public Bullet(int x, int y, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
    }

    public void paint(Graphics g) {

        if (!isLive) {
            this.tf.bulletList.remove(this);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceManager.bulletL, this.x, this.y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceManager.bulletR, this.x, this.y, null);
                break;
            case UP:
                g.drawImage(ResourceManager.bulletU, this.x, this.y, null);
                break;
            case DOWN:
                g.drawImage(ResourceManager.bulletD, this.x, this.y, null);
                break;
            default:
                break;
        }
        move();
    }

    private void move() {
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

        if (x < 0 || y < 0 || x > tf.GAME_WIDTH || y > tf.GAME_HEIGHT) {
            this.isLive = false;
        }
    }

    public void die() {
        this.isLive = false;
    }

    //坦克和子弹的碰撞
    public void collideWith(Tank tank) {

        if (this.group == tank.getGroup()) return;
        Rectangle bulletRectangle = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle tankRectangle = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);

        //如果子弹和坦克繁盛碰撞
        if (bulletRectangle.intersects(tankRectangle)) {

            new Thread(() -> new Audio("audio/explode.wav").play()).start();
            tank.die();
            this.die();

            int ex = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
            int ey = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
            tf.explode = new Explode(ex, ey, tf);

        }
    }
}
