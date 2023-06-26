package us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version1;

import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version1.view.GamePanel;
import javax.swing.*;

public class App extends JFrame {

    public static void main(String[] args) {
        new App();
    }

    public App() {
        GamePanel gp = new GamePanel();
        this.add(gp);
        this.setSize(1000, 750);
        //this.addKeyListener(gp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
