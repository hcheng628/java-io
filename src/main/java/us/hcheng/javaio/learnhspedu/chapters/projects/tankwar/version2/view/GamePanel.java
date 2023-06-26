package us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import java.util.stream.IntStream;
import javax.swing.*;

import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.Constants;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.Direction;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.vo.Bomb;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.vo.BotTank;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.vo.Missile;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.vo.PlayerTank;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.vo.Tank;
import us.hcheng.javaio.utils.SleepUtil;

public class GamePanel extends JPanel implements KeyListener, Runnable {
    private PlayerTank player;
    private Vector<BotTank> tanks;
    private Vector<Bomb> bombs;

    public GamePanel() {
        player = new PlayerTank(100, 100, 5, Direction.UP, Color.yellow);
        tanks = new Vector<>();
        bombs = new Vector<>();

        IntStream.range(0, Constants.NUM_BOT_TANK).forEach(i ->
                tanks.add(new BotTank((100 * (i + 1)), (i + 1) * 10, 5, Direction.DOWN, Color.cyan)));

        tanks.forEach(t -> new Thread(t).start());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);

        this.drawTank(g, player);
        tanks.forEach(t -> {
            this.drawTank(g, t);
            this.drawMissiles(g, t.getMissiles());
        });

        this.drawMissiles(g, player.getMissiles());
        this.drawBombs(g);
    }

    private void drawMissiles(Graphics g, Vector<Missile> missiles) {
        g.setColor(Color.yellow);
        missiles.forEach(m -> g.fillOval(m.getX(), m.getY(), Missile.SIZE, Missile.SIZE));
    }

    private void drawBombs(Graphics g) {
        bombs.forEach(b -> {
            g.drawImage(b.getImg(), b.getX(), b.getY(), Bomb.SIZE, Bomb.SIZE, this);
            new Thread(b).start();
        });
    }

    private void drawTank(Graphics g, Tank t) {
        if (t == null || !t.isAlive())
            return;

        g.setColor(t.getColor());
        int x = t.getX();
        int y = t.getY();

        switch (t.getDir()) {
            case Direction.UP -> {
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
            }
            case Direction.RIGHT -> {
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
            }
            case Direction.DOWN -> {
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
            }
            case Direction.LEFT -> {
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
            }
            default -> System.out.println("暂时没有处理");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> player.goUp();
            case KeyEvent.VK_RIGHT -> player.goRight();
            case KeyEvent.VK_DOWN -> player.goDown();
            case KeyEvent.VK_LEFT -> player.goLeft();
            case KeyEvent.VK_SPACE -> player.fire();
            default -> {}
        }
    }

    @Override
    public void run() {
        while (player.isAlive()) {
            SleepUtil.sleep(50);
            processMissiles();
            this.repaint();
        }

        System.err.println("GAME OVER");
    }

    public static boolean onPanel(int x, int y) {
        return x > -1 && y > -1 && x < Constants.PANEL_WIDTH && y < Constants.PANEL_HEIGHT;
    }

    public static boolean notOnPanel(int x, int y) {
        return !onPanel(x, y);
    }

    public void processMissiles() {
        player.getMissiles().forEach(m -> tanks.forEach(t -> processMissile(m, t)));
        tanks.forEach(t -> t.getMissiles().forEach(m -> processMissile(m, player)));
        player.getMissiles().removeIf(s -> !s.isAlive());

        tanks.forEach(t -> t.getMissiles().removeIf(s -> !s.isAlive()));
        tanks.removeIf(t -> !t.isAlive());
        bombs.removeIf(t -> !t.isAlive());
    }

    public void processMissile(Missile m, Tank t) {
        Bomb bomb = m.hitTank(t);
        if (bomb != null)
            bombs.add(bomb);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}
