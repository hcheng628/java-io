package us.hcheng.javaio.learnhspedu.tankwar.version2;

import static us.hcheng.javaio.learnhspedu.tankwar.version2.entity.Constants.PANEL_HEIGHT;
import static us.hcheng.javaio.learnhspedu.tankwar.version2.entity.Constants.PANEL_WIDTH;
import javax.swing.*;
import us.hcheng.javaio.learnhspedu.tankwar.version2.view.GamePanel;

public class App extends JFrame {
    public static void main(String[] args) {
        new App();
    }

    public App() {
        GamePanel gp = new GamePanel();
        this.add(gp);
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.addKeyListener(gp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        new Thread(gp).start();
    }

}
