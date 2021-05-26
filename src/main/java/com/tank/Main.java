package com.tank;

import com.tank.game.view.TankFrame;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        TankFrame tankFrame = new TankFrame();

        while (true) {
            Thread.sleep(25);
            tankFrame.repaint();
        }
    }
}
