package com.tank;

import com.sun.codemodel.internal.JCase;

import java.awt.*;

import static com.tank.Dir.*;

public class Bullet {

    //子弹速度
    private static final int SPEED = 4;
    private int x, y;
    private Dir dir;
    private static int WIDTH = 10, HEIGHT = 10;
    private boolean isLive = true;

    TankFrame tf = null;

    public Bullet(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public void paint(Graphics g) {

        if (!isLive) {
            this.tf.bulletList.remove(this);
        }

//        Color c = g.getColor();
//        g.setColor(Color.RED);
//        g.fillOval(x, y, WIDTH, HEIGHT);
//        g.setColor(c);
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

}
