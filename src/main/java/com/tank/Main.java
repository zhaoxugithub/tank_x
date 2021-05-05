package com.tank;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.Tank;
import com.tank.game.TankFrame;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        TankFrame tankFrame = new TankFrame();

        for (int i = 0; i < 10; i++) {
            tankFrame.tanks.add(new Tank(100 + i * 80, 400, Dir.DOWN, tankFrame, Group.BAD));
        }

        while (true) {
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
