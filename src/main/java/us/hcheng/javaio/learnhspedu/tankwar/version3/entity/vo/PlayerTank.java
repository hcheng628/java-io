package us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo;

import java.awt.*;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Direction;
import us.hcheng.javaio.learnhspedu.tankwar.version3.view.GamePanel;

public class PlayerTank extends Tank {
    public PlayerTank(GamePanel panel, int x, int y, int speed, Direction dir, Color color) {
        super(panel, x, y, speed, dir, color);
    }

}
