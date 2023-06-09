package us.hcheng.javaio.learnhspedu.tankwar.version2.entity;

import java.awt.*;
import java.util.Vector;
import lombok.Data;
import us.hcheng.javaio.learnhspedu.tankwar.version2.view.GamePanel;

@Data
public class Tank extends PanelObject {
    private Color color;
    private Vector<Missile> missiles;

    public Tank(int x, int y, int speed, Direction dir, Color color) {
        super(x, y, speed, true, dir);
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
