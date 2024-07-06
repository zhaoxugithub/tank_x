package com.tank.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TankWarConfig {
    public static final int GAME_WIDTH = ConfigUtil.getInteger("gameWidth", 800);
    public static final int GAME_HEIGHT = ConfigUtil.getInteger("gameHeight", 600);
    public static final int BULLET_NUM = ConfigUtil.getInteger("bulletNum", 10);
    public static final String TITLE = ConfigUtil.getString("title", "Tank war");


    public static final int GOODBULLETWIDTH = ResourceManager.goodBulletD.getWidth();
    public static final int GOODBULLETHEIGHT = ResourceManager.goodBulletD.getHeight();
    public static final int BADBULLETWIDTH = ResourceManager.badBulletD.getWidth();
    public static final int BADBULLETHEIGHT = ResourceManager.badBulletD.getHeight();


    //获取坦克的宽度和高度
    public static int GOODTANKWIDTH = ResourceManager.goodTankL.getWidth();
    public static int GOODTANKHEIGHT = ResourceManager.goodTankL.getHeight();
    public static int BADTANKWIDTH = ResourceManager.badTankL.getWidth();
    public static int BADTANKHEIGHT = ResourceManager.badTankL.getHeight();


    //坦克速度，步进
    public static final int SPEED = ConfigUtil.getInteger("tankSpeed", 10);


    private static final ExecutorService audioTankMoveExecutorService;

    static {
        audioTankMoveExecutorService = Executors.newCachedThreadPool();
    }

    public static void playAudio() {
        audioTankMoveExecutorService.execute(() -> new Audio("audio/explode.wav").play());
    }
}
