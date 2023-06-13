package us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo;

import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Direction;
import us.hcheng.javaio.learnhspedu.tankwar.version3.view.GamePanel;

public class PlayerTank extends Tank {
    public PlayerTank(GamePanel panel, int x, int y, Direction dir) {
        super(panel, x, y, dir);
    }

}
