package com.tank.game.entity;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.util.Audio;
import com.tank.util.ConfigUtil;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

//集成frame 之后才能有构造里面的方法
public class TankFrame extends Frame {

    //添加一个门面成员对象，后面专门和门面成员对象打交道
    public GameModel gameModel = new GameModel();

    public static final int GAME_WIDTH = ConfigUtil.getInteger("gameWidth"), GAME_HEIGHT = ConfigUtil.getInteger("gameHeight");


    public TankFrame() {
        setVisible(true);
        setResizable(false);
        setTitle("Tank war");
        setSize(GAME_WIDTH, GAME_HEIGHT);

        //添加键盘监听器
        addKeyListener(new MyKeyAdapter());
        //添加窗口监听器
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        gameModel.paint(g);
    }

    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {

        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics goffScreen = offScreenImage.getGraphics();
        Color color = goffScreen.getColor();
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        goffScreen.setColor(color);
        paint(goffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    class MyKeyAdapter extends KeyAdapter {

        boolean bl = false;
        boolean br = false;
        boolean bu = false;
        boolean bd = false;


        @Override
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();

            switch (keyCode) {

                case KeyEvent.VK_LEFT:
                    bl = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = true;
                    break;
                case KeyEvent.VK_UP:
                    bu = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = true;
                    break;
                default:
                    break;
            }
            setMainTankDir();
            new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {

                case KeyEvent.VK_LEFT:
                    bl = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = false;
                    break;
                case KeyEvent.VK_UP:
                    bu = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = false;
                    break;
                case KeyEvent.VK_Z:
                    gameModel.getMainTank().fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {

            Tank mainTank = gameModel.getMainTank();
            //如果没有按下方向键，就静止
            if (!br && !bl && !bu && !bd) {
                mainTank.setMoving(false);
            } else {
                mainTank.setMoving(true);
                if (bl) mainTank.setDir(Dir.LEFT);
                if (br) mainTank.setDir(Dir.RIGHT);
                if (bu) mainTank.setDir(Dir.UP);
                if (bd) mainTank.setDir(Dir.DOWN);
            }
        }
    }
}
