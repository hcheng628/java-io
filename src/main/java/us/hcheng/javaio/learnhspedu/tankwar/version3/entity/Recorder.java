package us.hcheng.javaio.learnhspedu.tankwar.version3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo.BotTank;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo.PlayerTank;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Constants.SAVED_GAME;

@Data
@AllArgsConstructor
public class Recorder {
    private Stats stats;
    private PlayerTank player;
    private Vector<BotTank> bots;

    public void saveGame() {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(SAVED_GAME))) {
            os.writeObject(stats);
            os.writeObject(new TankNode(player.getX(), player.getY()));
            List<TankNode> saveBots = new ArrayList<>();
            bots.forEach(b -> saveBots.add(new TankNode(b.getX(), b.getY())));
            os.writeObject(saveBots);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadGame() {
        System.out.println("loadGame... " + SAVED_GAME);
    }

    @Data
    @AllArgsConstructor
    public class TankNode implements Serializable {
        private static final long serialVersionUID = 1L;
        private int x;
        private int y;
    }

}
