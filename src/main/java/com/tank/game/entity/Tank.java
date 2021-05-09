package com.tank.game.entity;


import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.DefaultFireStrategy;
import com.tank.game.FireStrategy;
import com.tank.util.ConfigUtil;
import com.tank.util.ResourceManager;

import java.awt.*;
import java.util.Random;

public class Tank extends BaseTank {
    //坦克位置
    private int x, y;
    //坦克方向
    public Dir dir = Dir.DOWN;
    //坦克速度，步进
    private static final int SPEED = ConfigUtil.getInteger("tankSpeed");
    //判断坦克 是否移动，默认是静止
    private boolean moving = true;

    //判断坦克是否存活
    private boolean living = true;

    //获取坦克的宽度和高度
    public static int GOODWIDTH = ResourceManager.goodTankL.getWidth();
    public static int GOODHEIGHT = ResourceManager.goodTankL.getHeight();

    public static int BADWIDTH = ResourceManager.badTankL.getWidth();
    public static int BADHEIGHT = ResourceManager.badTankL.getHeight();

    public Rectangle rectangle = new Rectangle();

    //生产随机数
    private Random random = new Random();

    TankFrame tf = null;

    private Group group = Group.BAD;

    private FireStrategy fs = null;

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

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = BADWIDTH;
        rectangle.height = BADHEIGHT;

        if (this.getGroup() == Group.GOOD) {
            String goodFs = ConfigUtil.getString("goodFS");
            try {
                fs = (FireStrategy)Class.forName(goodFs).newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            fs = new DefaultFireStrategy();
        }
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

        rectangle.x = this.x;
        rectangle.y = this.y;
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
        fs.fire(this);
    }

    public void die() {
        this.setLiving(false);
    }
}
