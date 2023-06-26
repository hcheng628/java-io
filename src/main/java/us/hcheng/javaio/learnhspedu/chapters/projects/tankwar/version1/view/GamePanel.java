package us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version1.view;

import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version1.entity.BotTank;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version1.entity.Direction;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version1.entity.PlayerTank;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version1.entity.Tank;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.stream.IntStream;

public class GamePanel extends JPanel {
    private static final int BOT_TANK_COUNT = 3;
    private PlayerTank player;
    private Vector<BotTank> tanks;

    public GamePanel() {
        player = new PlayerTank(100, 100, 1, Direction.UP, Color.yellow);
        tanks = new Vector<>();
        IntStream.range(0, BOT_TANK_COUNT).forEach(i ->
                tanks.add(new BotTank((100 * (i + 1)), 0, 1, Direction.DOWN, Color.cyan)));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);

        this.drawTank(g, player);
        tanks.forEach(t -> this.drawTank(g, t));
    }

    private void drawTank(Graphics g, Tank t) {
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

}
