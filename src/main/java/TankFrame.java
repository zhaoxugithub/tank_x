import java.awt.*;
import java.awt.event.*;

//集成frame 之后才能有构造里面的方法
public class TankFrame extends Frame {

    Tank tank = new Tank(800, 600, Dir.DOWN);

    public TankFrame() {
        setVisible(true);
        setResizable(false);
        setTitle("Tank war");
        setSize(800, 600);

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
        //把画自己的逻辑放在tank里面，更加方便，提现了面向对象的封装性
        tank.paint(g);
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
                default:
                    break;
            }
            setMainTankDir();
        }


        private void setMainTankDir() {
            if (bl) tank.setDir(Dir.LEFT);
            if (br) tank.setDir(Dir.RIGHT);
            if (bu) tank.setDir(Dir.UP);
            if (bd) tank.setDir(Dir.DOWN);

        }
    }
}
