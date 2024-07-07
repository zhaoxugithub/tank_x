package com.tank.game.adapter;

import com.google.common.collect.Maps;
import com.tank.enums.Dir;
import com.tank.game.entity.Tank;
import com.tank.util.Audio;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.function.Consumer;

public class MyKeyAdapter extends KeyAdapter {
    Boolean bl = Boolean.FALSE, br = Boolean.FALSE, bu = Boolean.FALSE, bd = Boolean.FALSE;
    private final Map<Integer, Consumer<Integer>> keyMap = Maps.newHashMap();

    private Tank playerTank;

    public MyKeyAdapter(Tank playerTank) {
        this.playerTank = playerTank;
        keyMap.put(KeyEvent.VK_LEFT, type -> bl = type == KeyEvent.KEY_PRESSED ? Boolean.TRUE : Boolean.FALSE);
        keyMap.put(KeyEvent.VK_RIGHT, type -> br = type == KeyEvent.KEY_PRESSED ? Boolean.TRUE : Boolean.FALSE);
        keyMap.put(KeyEvent.VK_UP, type -> bu = type == KeyEvent.KEY_PRESSED ? Boolean.TRUE : Boolean.FALSE);
        keyMap.put(KeyEvent.VK_DOWN, type -> bd = type == KeyEvent.KEY_PRESSED ? Boolean.TRUE : Boolean.FALSE);
        keyMap.put(KeyEvent.VK_Z, type -> {
            if (type == KeyEvent.KEY_PRESSED) playerTank.fire();
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyMap.get(e.getKeyCode()).accept(KeyEvent.KEY_PRESSED);
        setMainTankDir();
        new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyMap.get(e.getKeyCode()).accept(KeyEvent.KEY_RELEASED);
        setMainTankDir();
    }

    /**
     * 设置坦克方向
     */
    private void setMainTankDir() {
        boolean moving = bl || br || bu || bd;
        playerTank.setMoving(moving);
        if (!moving) {
            return;
        }
        playerTank.setDir(bl ? Dir.LEFT : br ? Dir.RIGHT : bu ? Dir.UP : Dir.DOWN);
    }
}
