package us.hcheng.javaio.learnhspedu.tankwar.version1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

@Data
@AllArgsConstructor
public class Tank {
    private int x;
    private int y;
    private int speed;
    private Direction dir;
    private Color color;

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
