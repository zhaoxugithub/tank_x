package com.tank.util;


public class TankWarConfig {
    /**
     * 游戏窗口的宽度和高度
     */
    public static final int GAME_WIDTH = ConfigUtil.getInteger("gameWidth", 800);
    public static final int GAME_HEIGHT = ConfigUtil.getInteger("gameHeight", 600);
    public static final int BULLET_NUM = ConfigUtil.getInteger("bulletNum", 10);
    public static final String TITLE = ConfigUtil.getString("title", "Tank war");


    /**
     * 子弹的宽度和高度
     */
    public static final int GOODBULLETWIDTH = ResourceManager.goodBulletD.getWidth();
    public static final int GOODBULLETHEIGHT = ResourceManager.goodBulletD.getHeight();
    public static final int BADBULLETWIDTH = ResourceManager.badBulletD.getWidth();
    public static final int BADBULLETHEIGHT = ResourceManager.badBulletD.getHeight();


    /**
     * 坦克的宽度和高度
     */
    public static int GOODTANKWIDTH = ResourceManager.goodTankL.getWidth();
    public static int GOODTANKHEIGHT = ResourceManager.goodTankL.getHeight();
    public static int BADTANKWIDTH = ResourceManager.badTankL.getWidth();
    public static int BADTANKHEIGHT = ResourceManager.badTankL.getHeight();

    /**
     * 开火方式
     */
    public static String FIRETYPE = ConfigUtil.getString("goodFS", "com.tank.game.DefaultFireStrategy");

    //坦克速度，步进
    public static final int SPEED = ConfigUtil.getInteger("tankSpeed", 10);

    /**
     * 电脑坦克
     */
    public static int COMPUTERTANKS = ConfigUtil.getInteger("tankNum", 10);
}
