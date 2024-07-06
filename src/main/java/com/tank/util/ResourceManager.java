package com.tank.util;


import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 用来加载图片
 */
@Slf4j
public class ResourceManager {
    // 四张坦克图片
    public static BufferedImage goodTankL, goodTankR, goodTankU, goodTankD;
    public static BufferedImage badTankU, badTankL, badTankR, badTankD;
    public static BufferedImage badBulletL, badBulletR, badBulletU, badBulletD;
    public static BufferedImage goodBulletL, goodBulletR, goodBulletU, goodBulletD;
    public static BufferedImage[] explodes = new BufferedImage[16];

    static {
        try {
            //好坦克图片加载
            goodTankU = ImageIO.read(ResourceUtil.getStream("images/GoodTank1.png"));
            goodTankL = ImageUtil.rotateImage(goodTankU, -90);
            goodTankR = ImageUtil.rotateImage(goodTankU, 90);
            goodTankD = ImageUtil.rotateImage(goodTankU, 180);
            //坏坦克图片加载
            badTankU = ImageIO.read(ResourceUtil.getStream("images/BadTank1.png"));
            badTankL = ImageUtil.rotateImage(badTankU, -90);
            badTankR = ImageUtil.rotateImage(badTankU, 90);
            badTankD = ImageUtil.rotateImage(badTankU, 180);
            //子弹图片加载
            goodBulletU = ImageIO.read(ResourceUtil.getStream("images/bulletU.png"));
            goodBulletL = ImageUtil.rotateImage(goodBulletU, -90);
            goodBulletR = ImageUtil.rotateImage(goodBulletU, 90);
            goodBulletD = ImageUtil.rotateImage(goodBulletU, 180);

            badBulletU = ImageIO.read(ResourceUtil.getStream("images/bulletU.gif"));
            badBulletL = ImageUtil.rotateImage(badBulletU, -90);
            badBulletR = ImageUtil.rotateImage(badBulletU, 90);
            badBulletD = ImageUtil.rotateImage(badBulletU, 180);
            for (int i = 0; i < 16; i++) {
                explodes[i] = ImageIO.read(ResourceUtil.getStream("images/e" + (i + 1) + ".gif"));
            }
        } catch (IOException e) {
            log.warn("图片加载失败 :{}", e.getCause());
        }
    }
}
