package us.hcheng.javaio.learnhspedu.tankwar.version3.view;

import static us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Constants.NUM_BOT_TANK;
import static us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Constants.PANEL_HEIGHT;
import static us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Constants.PANEL_WIDTH;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.stream.IntStream;
import javax.swing.*;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Stats;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo.Bomb;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo.BotTank;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Direction;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo.PlayerTank;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo.Missile;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo.Tank;
import us.hcheng.javaio.utils.SleepUtil;

public class GamePanel extends JPanel implements KeyListener, Runnable {
    private Stats stats;
    private PlayerTank player;
    private Vector<BotTank> tanks;
    private Vector<Bomb> bombs;


    public GamePanel() {
        stats = new Stats(0);
        player = new PlayerTank(this, 500, 500, 5, Direction.UP, Color.yellow);
        tanks = new Vector<>();
        bombs = new Vector<>();

        IntStream.range(0, NUM_BOT_TANK).forEach(i ->
                tanks.add(new BotTank(this, (100 * (i + 1)), (i + 1) * 10, 5, Direction.DOWN, Color.cyan)));

        tanks.forEach(t -> new Thread(t).start());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        drawTank(g, player);
        tanks.forEach(t -> {
            drawTank(g, t);
            drawMissiles(g, t.getMissiles());
        });

        drawMissiles(g, player.getMissiles());
        drawBombs(g);
        drawStats(g);
    }

    private void drawMissiles(Graphics g, Vector<Missile> missiles) {
        g.setColor(Color.yellow);
        missiles.forEach(m -> g.fillOval(m.getX(), m.getY(), Missile.SIZE, Missile.SIZE));
    }

    private void drawStats(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体", Font.BOLD, 25));
        g.drawString("您累积击毁敌方坦克", 1020, 30);
        drawTank(g, new BotTank(this, 1020, 60, 0, Direction.UP, Color.cyan));
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(stats.getKills()), 1080, 100);
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
            case UP -> {
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
            }
            case RIGHT -> {
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
            }
            case DOWN -> {
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
            }
            case LEFT -> {
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
        return x > -1 && y > -1 && x < PANEL_WIDTH && y < PANEL_HEIGHT;
    }

    public static boolean notOnPanel(int x, int y) {
        return !onPanel(x, y);
    }

    public Collection<Tank> panelTanks() {
        Collection<Tank> ret = new ArrayList<>(tanks);
        ret.add(player);
        return Collections.unmodifiableCollection(ret);
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
        if (bomb != null) {
            bombs.add(bomb);
            if (t != player)
                stats.increaseKills();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}
