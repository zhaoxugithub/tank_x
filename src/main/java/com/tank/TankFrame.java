package com.tank;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

//集成frame 之后才能有构造里面的方法
public class TankFrame extends Frame {

    Tank tank = new Tank(200, 200, Dir.DOWN, this);
    //子弹容器
    List<Bullet> bulletList = new ArrayList<Bullet>();
    static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;

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
        Color color = g.getColor();
        g.setColor(Color.white);
        g.drawString("打出子弹个数：" + bulletList.size(), 10, 60);
        g.setColor(color);

        //把画自己的逻辑放在tank里面，更加方便，提现了面向对象的封装性
        tank.paint(g);
        for (int i = 0; i < bulletList.size(); i++) {
            bulletList.get(i).paint(g);
        }
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
                    tank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            //如果没有按下方向键，就静止
            if (!br && !bl && !bu && !bd) {
                tank.setMoving(false);
            } else {
                tank.setMoving(true);
                if (bl) tank.setDir(Dir.LEFT);
                if (br) tank.setDir(Dir.RIGHT);
                if (bu) tank.setDir(Dir.UP);
                if (bd) tank.setDir(Dir.DOWN);
            }
        }
    }
}
