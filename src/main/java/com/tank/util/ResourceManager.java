package com.tank.util;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 用来加载图片
 */
public class ResourceManager {

    // 四张坦克图片
    public static BufferedImage goodTankL, goodTankR, goodTankU, goodTankD;
    public static BufferedImage badTankU, badTankL, badTankR, badTankD;
    public static BufferedImage badBulletL, badBulletR, badBulletU, badBulletD;
    public static BufferedImage goodBulletL, goodBulletR, goodBulletU, goodBulletD;

    public static BufferedImage[] explodes = new BufferedImage[16];

    public static BufferedImage wall;

    static {
        try {
            //好坦克图片加载
            goodTankU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            goodTankL = ImageUtil.rotateImage(goodTankU, -90);
            goodTankR = ImageUtil.rotateImage(goodTankU, 90);
            goodTankD = ImageUtil.rotateImage(goodTankU, 180);
            //坏坦克图片加载
            badTankU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            badTankL = ImageUtil.rotateImage(badTankU, -90);
            badTankR = ImageUtil.rotateImage(badTankU, 90);
            badTankD = ImageUtil.rotateImage(badTankU, 180);
            //子弹图片加载
            goodBulletU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            goodBulletL = ImageUtil.rotateImage(goodBulletU, -90);
            goodBulletR = ImageUtil.rotateImage(goodBulletU, 90);
            goodBulletD = ImageUtil.rotateImage(goodBulletU, 180);

            badBulletU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            badBulletL = ImageUtil.rotateImage(badBulletU, -90);
            badBulletR = ImageUtil.rotateImage(badBulletU, 90);
            badBulletD = ImageUtil.rotateImage(badBulletU, 180);


            for (int i = 0; i < 16; i++) {
                explodes[i] = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif"));
            }

            //墙图片加载
            wall = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/square0.jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
