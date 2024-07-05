package com.tank;

import com.google.common.collect.Lists;
import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.entity.Tank;
import com.tank.game.entity.TankFrame;
import com.tank.util.ConfigUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();

        int tankNum = ConfigUtil.getInteger("tankNum", 10);
        ArrayList<Tank> list = Lists.newArrayList();
        for (int i = 0; i < tankNum; i++) {
            Tank tank = new Tank(100 + i * 80, 400, Dir.DOWN, true, tankFrame, Group.BAD);
            list.add(tank);
        }
        tankFrame.tanks = list;
        while (true) {
            Thread.sleep(25);
            tankFrame.repaint();
        }
    }
}
