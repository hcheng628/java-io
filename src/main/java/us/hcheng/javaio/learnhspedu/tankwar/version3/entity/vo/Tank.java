package us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import lombok.Data;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Direction;
import us.hcheng.javaio.learnhspedu.tankwar.version3.view.GamePanel;

@Data
public class Tank extends PanelObject {
    private GamePanel panel;
    private Color color;
    private Vector<Missile> missiles;

    public Tank(GamePanel panel, int x, int y, int speed, Direction dir, Color color) {
        super(x, y, speed, true, dir);
        this.panel = panel;
        this.color = color;
        this.missiles = new Vector<>();
    }

    public void setX(int x) {
        if (GamePanel.onPanel(getDir() == Direction.RIGHT ? x + 50 : x, getY()))
            super.setX(x);
    }

    public void setY(int y) {
        if (GamePanel.onPanel(getX(), getDir() == Direction.DOWN ? y + 80 : y))
            super.setY(y);
    }

    public void fire() {
        if (getMissiles().size() > 5)
            return;

        Direction dir = getDir();
        Missile m = switch (dir) {
            case UP -> new Missile(getX() + 20, getY(), this);
            case RIGHT -> new Missile(getX() + 60, getY() + 20, this);
            case DOWN -> new Missile(getX() + 20, getY() + 60, this);
            case LEFT -> new Missile(getX(), getY() + 20, this);
        };

        getMissiles().add(m);
        new Thread(m).start();
    }

    protected boolean overlap(Tank a, Collection<Tank> tanks) {
        for (Tank t : tanks)
            if (overlap(this, t))
                return true;
        return false;
    }

    protected boolean overlap(Tank a, Tank b) {
        if (a == b)
            return false;

        int aX1 = a.getX(), aY1 = a.getY();
        int bX1 = b.getX(), bY1 = b.getY();
        int[] aX2Y2 = x2y2(aX1, aY1, a.getDir());
        int[] bX2Y2 = x2y2(bX1, bY1, b.getDir());
        return (intercept(aX1, aX2Y2[0], bX1, bX2Y2[0]) && intercept(aY1, aX2Y2[1], bY1, bX2Y2[1])) ||
                (intercept(bX1, bX2Y2[0], aX1, aX2Y2[0]) && intercept(bY1, bX2Y2[1], aY1, aX2Y2[1]));
    }

    private int[] x2y2(int x, int y, Direction d) {
        return d == Direction.UP || d == Direction.DOWN ? new int[]{x + 40, y + 60} : new int[]{x + 60, y + 40};
    }

    private boolean intercept(int x1, int x2, int xx1, int xx2) {
        return intercept(x1, x2, xx1) || intercept(x1, x2, xx2);
    }

    private boolean intercept(int from, int to, int val) {
        return val >= from && val <= to;
    }

    @Override
    public void goUp() {
        Direction prevDir = this.getDir();
        int prevY = getY();

        setDir(Direction.UP);
        this.setY(prevY - getSpeed());
        if (overlap(this, panel.panelTanks())) {
            setDir(prevDir);
            setY(prevY);
        }
    }

    @Override
    public void goRight() {
        Direction prevDir = this.getDir();
        int prevX = getX();

        setDir(Direction.RIGHT);
        this.setX(prevX + getSpeed());
        if (overlap(this, panel.panelTanks())) {
            setDir(prevDir);
            setX(prevX);
        }
    }

    @Override
    public void goDown() {
        Direction prevDir = this.getDir();
        int prevY = getY();

        setDir(Direction.DOWN);
        this.setY(prevY + getSpeed());
        if (overlap(this, panel.panelTanks())) {
            setDir(prevDir);
            setY(prevY);
        }
    }

    @Override
    public void goLeft() {
        Direction prevDir = this.getDir();
        int prevX = getX();

        setDir(Direction.LEFT);
        this.setX(prevX - getSpeed());
        if (overlap(this, panel.panelTanks())) {
            setDir(prevDir);
            setX(prevX);
        }
    }

    public static enum Type {
        PLAYER, BOT, UNKNOWN;
        public static Type getType(Tank t) {
            if (t instanceof PlayerTank)
                return PLAYER;
            else if (t instanceof BotTank)
                return BOT;

            return UNKNOWN;
        }
    }

}
