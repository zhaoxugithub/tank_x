package com.tank.game.entity;

import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.factory.*;
import com.tank.util.Audio;
import com.tank.util.ConfigUtil;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

//集成frame 之后才能有构造里面的方法
public class TankFrame extends Frame {

    public Tank tank = new Tank(200, 200, Dir.DOWN, this, Group.GOOD);
    //子弹容器
    public List<BaseBullet> bulletList = new ArrayList<BaseBullet>();
    //爆炸容器
    public List<BaseExplode> explodes = new ArrayList<BaseExplode>();
    //坦克容器
    public List<Tank> tanks = new ArrayList<Tank>();

    static final int GAME_WIDTH = ConfigUtil.getInteger("gameWidth"), GAME_HEIGHT = ConfigUtil.getInteger("gameHeight");

    //定义抽象工厂,采用默认工厂生产默认的
    public GameFactory gameFactory = new DefaultFactory();
    //如果想要换成其他的爆炸形式，可以采用以下的工厂
//    public GameFactory gameFactory = new RectFactory();


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
        g.setColor(Color.RED);
        g.drawString("打出子弹个数：" + bulletList.size(), 10, 60);
        g.drawString("剩余敌方坦克：" + tanks.size(), 200, 60);
        g.setColor(color);

        //把画自己的逻辑放在tank里面，更加方便，提现了面向对象的封装性

        //生产己方坦克
        tank.paint(g);

        //生成子弹数量
        for (int i = 0; i < bulletList.size(); i++) {
            bulletList.get(i).paint(g);
        }

        //生成敌方坦克个数
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }

        //生成爆炸类
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }


        //判断子弹和坦克是否发生碰撞
        for (int i = 0; i < bulletList.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bulletList.get(i).collideWith(tanks.get(j));
            }
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
