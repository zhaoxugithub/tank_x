import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class T {
    public static void main(String[] args) {

        Frame f = new Frame();
        f.setVisible(true);
        f.setSize(800, 600);
        //设置固定位置
        f.setResizable(false);
        //设置标题
        f.setTitle("tank war");

        //添加一个窗口监听器
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


    }
}
