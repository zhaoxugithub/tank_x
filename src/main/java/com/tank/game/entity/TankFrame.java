package com.tank.game.entity;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.util.Audio;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.tank.util.TankWarConfig.*;

//集成frame 之后才能有构造里面的方法
@EqualsAndHashCode(callSuper = true)
@Data
public class TankFrame extends Frame {
    private static final Logger log = LoggerFactory.getLogger(TankFrame.class);
    private Tank selfTank;
    //子弹容器
    private List<Bullet> bulletList = Lists.newArrayList();
    //爆炸容器
    private List<Explode> explodes = Lists.newArrayList();
    //坦克容器
    private List<Tank> computerTanks = Lists.newArrayList();
    // 双缓冲解决闪烁问题
    private Image offScreenImage = null;

    private void initObject() {
        selfTank = new Tank(200, 200, Dir.DOWN, false, this, Group.GOOD);
        bulletList = Lists.newArrayList();
        explodes = Lists.newArrayList();
        computerTanks = Lists.newArrayList();
    }

    // 定义抽象工厂,采用默认工厂生产默认的
    // public GameFactory gameFactory = new DefaultFactory();
    // 如果想要换成其他的爆炸形式，可以采用以下的工厂
    // public GameFactory gameFactory = new RectFactory();
    public TankFrame() {
        //开启windows窗口
        setVisible(Boolean.TRUE);
        setResizable(Boolean.FALSE);
        setTitle(TITLE);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        // 初始化tank, bullet, explode
        initObject();
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

    /**
     * 计算剩余子弹个数
     *
     * @return
     */
    public long computerGoodBullets() {
        long count =
                bulletList.stream().filter(bullet -> StrUtil.equals(bullet.getGroup().name(), Group.GOOD.name())).count();
        return BULLET_NUM - count;
    }

    @Override
    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.drawString("剩余子弹个数：" + computerGoodBullets(), 10, 60);
        graphics.drawString("剩余敌方坦克：" + computerTanks.size(), 200, 60);
        graphics.setColor(color);
        // 把画自己的逻辑放在tank里面，更加方便，提现了面向对象的封装性
        // 生产己方坦克
//        log.info("bulletList={}, computerTanks={}, explodes={}", bulletList, computerTanks, explodes);
        selfTank.paint(graphics);
        // 生成子弹数量,生成敌方坦克个数,生成爆炸类
        bulletList.forEach(bullet -> bullet.paint(graphics));
        // 生成敌方坦克个数
        computerTanks.forEach(tank -> tank.paint(graphics));
        // 生成爆炸类
        explodes.forEach(explode -> explode.paint(graphics));
        // 判断子弹和坦克是否发生碰撞
        bulletList.parallelStream().forEach(bullet -> computerTanks.forEach(bullet::collideWith));
    }

    @Override
    public void update(Graphics graphics) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics goffScreen = offScreenImage.getGraphics();
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        goffScreen.setColor(goffScreen.getColor());
        paint(goffScreen);
        graphics.drawImage(offScreenImage, 0, 0, null);
    }

    class MyKeyAdapter extends KeyAdapter {
        Boolean bl = Boolean.FALSE;
        Boolean br = Boolean.FALSE;
        Boolean bu = Boolean.FALSE;
        Boolean bd = Boolean.FALSE;
        private final Map<Integer, Consumer<Integer>> keyMap = Maps.newHashMap();

        public MyKeyAdapter() {
            keyMap.put(KeyEvent.VK_LEFT, type -> bl = type == KeyEvent.KEY_PRESSED ? Boolean.TRUE : Boolean.FALSE);
            keyMap.put(KeyEvent.VK_RIGHT, type -> br = type == KeyEvent.KEY_PRESSED ? Boolean.TRUE : Boolean.FALSE);
            keyMap.put(KeyEvent.VK_UP, type -> bu = type == KeyEvent.KEY_PRESSED ? Boolean.TRUE : Boolean.FALSE);
            keyMap.put(KeyEvent.VK_DOWN, type -> bd = type == KeyEvent.KEY_PRESSED ? Boolean.TRUE : Boolean.FALSE);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            keyMap.get(e.getKeyCode()).accept(KeyEvent.KEY_PRESSED);
            // 坦克摁下也要设置方向
            setMainTankDir();
            new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keyMap.get(e.getKeyCode()).accept(KeyEvent.KEY_RELEASED);
            // 案件放下要设置tank方向
            setMainTankDir();
        }

        /**
         * 设置坦克方向
         */
        private void setMainTankDir() {
            //如果没有按下方向键，就静止
            if (!br && !bl && !bu && !bd) {
                selfTank.setMoving(false);
            } else {
                selfTank.setMoving(true);
                if (bl) selfTank.setDir(Dir.LEFT);
                if (br) selfTank.setDir(Dir.RIGHT);
                if (bu) selfTank.setDir(Dir.UP);
                if (bd) selfTank.setDir(Dir.DOWN);
            }
        }
    }
}