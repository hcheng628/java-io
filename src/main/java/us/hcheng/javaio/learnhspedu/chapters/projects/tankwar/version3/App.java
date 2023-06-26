package us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3;

import javax.swing.*;

import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.entity.Constants;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.entity.Recorder;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.view.GamePanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App extends JFrame {

    public static void main(String[] args) {
        new App();
    }

    public App() {
        GamePanel gp = new GamePanel(Recorder.loadGameUI(this));
        this.add(gp);
        this.setSize(Constants.PANEL_WIDTH + 300, Constants.PANEL_HEIGHT);
        this.addKeyListener(gp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.saveGame();
            }
        });
        new Thread(gp).start();
    }

}
