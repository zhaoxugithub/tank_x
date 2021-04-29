import java.awt.*;
import java.awt.event.*;

//集成frame 之后才能有构造里面的方法
public class TankFrame extends Frame {

    int x = 200;
    int y = 200;

    public TankFrame() {
        setVisible(true);
        setResizable(false);
        setTitle("Tank war");
        setSize(800, 600);


        //添加键盘监听器
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("key press");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("key release");
            }
        });

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
        System.out.println("print");
        g.fillRect(x, y, 50, 50);
        x += 10;
        y += 10;
    }


}
