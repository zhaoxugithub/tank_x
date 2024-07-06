package com.tank.game.entity;

import cn.hutool.core.collection.CollectionUtil;
import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.util.Audio;
import com.tank.util.ConfigUtil;
import com.tank.util.ResourceManager;
import lombok.Data;
import lombok.ToString;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import static com.tank.util.TankWarConfig.*;

@Data
@ToString
public class Bullet {

    //子弹速度
    private static final int SPEED = ConfigUtil.getInteger("bulletSpeed", 10);
    private int x, y;
    private Dir dir;
    private boolean isLive = true;

    public Rectangle rectangle = new Rectangle();
    TankFrame tf = null;

    private Group group = Group.BAD;

    public Bullet(int x, int y, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = BADBULLETWIDTH;
        rectangle.height = BADBULLETHEIGHT;

        List<Bullet> bulletList = tf.getBulletList();
        bulletList.add(this);
    }

    public Group getGroup() {
        return group;
    }

    public void paint(Graphics g) {

//        if (!isLive) {
//            this.tf.getBulletList().remove(this);
//        }

        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodBulletL : ResourceManager.badBulletL,
                        this.x, this.y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodBulletR : ResourceManager.badBulletR,
                        this.x, this.y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodBulletU : ResourceManager.badBulletU,
                        this.x, this.y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceManager.goodBulletD : ResourceManager.badBulletD,
                        this.x, this.y, null);
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

        if (x < 0 || y < 0 || x > GAME_WIDTH || y > GAME_HEIGHT) {
            this.isLive = false;
        }

        rectangle.x = this.x;
        rectangle.y = this.y;
    }

    public void die() {
        this.isLive = false;
    }

    //坦克和子弹的碰撞
    public void collideWith(Tank tank) {
        if (this.group == tank.getGroup()) return;
        //如果子弹和坦克繁盛碰撞
        if (rectangle.intersects(tank.getRectangle())) {
            new Thread(() -> new Audio("audio/explode.wav").play()).start();
            tank.die();
            this.die();
            int ex = tank.getX() + BADBULLETWIDTH / 2 - Explode.WIDTH / 2;
            int ey = tank.getY() + BADBULLETWIDTH / 2 - Explode.HEIGHT / 2;
//            BaseExplode explode = tf.gameFactory.createExplode(ex, ey, tf);
            tf.getExplodes().add(new Explode(ex, ey, tf));
        }
    }
}
