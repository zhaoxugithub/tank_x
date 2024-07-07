package com.tank.game.entity;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.FireStrategy;
import com.tank.util.ResourceManager;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

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
    private Group group;
    private FireStrategy fs = null;

    private Map<Dir, Consumer<Dir>> map;

    public Tank() {
    }

    private void drawTank(Graphics graphics) {
        map.put(Dir.LEFT, dir -> {
            graphics.drawImage(this.group == Group.GOOD ?
                    ResourceManager.goodTankL : ResourceManager.badTankL, this.x, this.y, null);
            if (moving) x -= SPEED;
        });

        map.put(Dir.RIGHT, dir -> {
            graphics.drawImage(this.group == Group.GOOD ?
                    ResourceManager.goodTankR : ResourceManager.badTankR, this.x, this.y, null);
            if (moving) x += SPEED;
        });

        map.put(Dir.UP, dir -> {
            graphics.drawImage(this.group == Group.GOOD ?
                    ResourceManager.goodTankU : ResourceManager.badTankU, this.x, this.y, null);
            if (moving) y -= SPEED;
        });

        map.put(Dir.DOWN, dir -> {
            graphics.drawImage(this.group == Group.GOOD ?
                    ResourceManager.goodTankD : ResourceManager.badTankD, this.x, this.y, null);
            if (moving) y += SPEED;
        });

        map.get(dir).accept(dir);
    }

    //生成或者画出一个坦克
    public void paint(Graphics graphics) {
        drawTank(graphics);
        tankMove();
        fire();
    }

    private void tankMove() {
        computerTankMove();
        //敌方坦克的随机方向
        //坦克开除边界检测
        boundsCheck();
        rectangle.setLocation(x, y);
    }

    private void computerTankMove() {
        if (this.group == Group.BAD
                && random.nextInt(10) > 8) {
            this.setDir(Dir.values()[random.nextInt(4)]);
        }
    }

    private void boundsCheck() {
        if (this.x < 0) this.x = 0;
        if (this.x > GAME_WIDTH - GOODTANKWIDTH) this.x = GAME_WIDTH - GOODTANKWIDTH;
        if (this.y < 30) this.y = 30;
        if (this.y > GAME_HEIGHT - GOODTANKHEIGHT) this.y = GAME_HEIGHT - GOODTANKHEIGHT;
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
