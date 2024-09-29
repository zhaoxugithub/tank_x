package com.tank.game.entity;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import com.tank.enums.Dir;
import com.tank.enums.Group;
import com.tank.game.DefaultFireStrategy;
import com.tank.game.adapter.MyKeyAdapter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.tank.util.TankWarConfig.*;

//集成frame 之后才能有构造里面的方法
@EqualsAndHashCode(callSuper = true)
@Data
public class TankFrame extends Frame {
    private static final Logger log = LoggerFactory.getLogger(TankFrame.class);
    // 玩家坦克
    private Tank playerTank;
    // 电脑坦克
    private List<Tank> computerTanks;
    //子弹容器
    private List<Bullet> bulletList = Lists.newArrayList();
    //爆炸容器
    private List<Explode> explodes = Lists.newArrayList();
    // 双缓冲解决闪烁问题
    private Image offScreenImage = null;

    public TankFrame() {
        //开启windows窗口
        setVisible(Boolean.TRUE);
        setResizable(Boolean.FALSE);
        setTitle(TITLE);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        // 初始化玩家坦克
        buildPlayerTanks();
        // 初始化电脑坦克
        buildComputerTanks();
        //添加键盘监听器
        addKeyListener(new MyKeyAdapter(playerTank));
        //添加窗口监听器
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        // 通过线程池来rePaint
        repaintScheduleTask();
    }

    private void buildPlayerTanks() {
        playerTank = buildTank(200, 200, Boolean.FALSE, Group.GOOD);
    }

    private void buildComputerTanks() {
        ArrayList<Tank> list = Lists.newArrayList();
        for (int i = 0; i < COMPUTERTANKS; i++) {
            list.add(buildTank(100 + i * 80, 400, Boolean.TRUE, Group.BAD));
        }
        computerTanks = list;
    }

    private Tank buildTank(int x, int y, Boolean moving, Group group) {
        Tank tank = new Tank();
        tank.setX(x);
        tank.setY(y);
        tank.setDir(Dir.DOWN);
        tank.setMoving(moving);
        tank.setGroup(group);
        tank.setRectangle(new Rectangle(x, y, BADTANKWIDTH, BADTANKHEIGHT));
        tank.setFs(group == Group.GOOD ? ReflectUtil.newInstance(FIRETYPE) : new DefaultFireStrategy());
        return tank;
    }

    @Override
    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
//        graphics.drawString("剩余子弹个数：" + computerGoodBullets(), 10, 60);
//        graphics.drawString("剩余敌方坦克：" + computerTanks.size(), 200, 60);
        graphics.setColor(color);
        // 生产己方坦克
        playerTank.paint(graphics);

        // 生成子弹数量,生成敌方坦克个数,生成爆炸类
        bulletList.removeIf(bullet -> !bullet.isLive());
        bulletList.forEach(bullet -> bullet.paint(graphics));

        // 生成敌方坦克个数
        computerTanks.removeIf(tank -> !tank.isLiving());
        computerTanks.forEach(tank -> tank.paint(graphics));

        // 生成爆炸类
        explodes.removeIf(explode -> !explode.isLiving());
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

    /**
     * 创建定时任务线程池，用来 repaint
     */
    private void repaintScheduleTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // 定义一个任务
        Runnable task = this::repaint;
        // 每秒钟执行一次任务，初始延迟为0秒
        scheduler.scheduleAtFixedRate(task, 0, 20, TimeUnit.MILLISECONDS);
        // 添加一个钩子来关闭调度器，以便在应用程序退出时清理资源
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down scheduler...");
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.out.println("Scheduler did not terminate in the specified time.");
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                System.out.println("Scheduler interrupted during shutdown.");
                scheduler.shutdownNow();
            }
            System.out.println("Scheduler shut down.");
        }));
    }
}