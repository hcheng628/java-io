package us.hcheng.javaio.learnhspedu.tankwar.version3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo.BotTank;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo.PlayerTank;
import us.hcheng.javaio.learnhspedu.tankwar.version3.view.GamePanel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Constants.SAVED_GAME;
import javax.swing.*;

@Data
@AllArgsConstructor
public class Recorder {
    private static Stats stats;
    private static PlayerTank player;
    private static Vector<BotTank> bots;

    public static void init(Stats stats, PlayerTank player, Vector<BotTank> bots) {
        Recorder.stats = stats;
        Recorder.player = player;
        Recorder.bots = bots;
    }

    public static void saveGame() {
        if (!hasSavedGameFile() && !createSavedGameFile())
            return;

        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(Recorder.class.getResource("/" + SAVED_GAME).getFile()))) {
            os.writeObject(stats);
            os.writeObject(new TankNode(player.getX(), player.getY(), player.getDir()));
            List<TankNode> saveBots = new ArrayList<>();
            bots.forEach(b -> saveBots.add(new TankNode(b.getX(), b.getY(), b.getDir())));
            os.writeObject(saveBots);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void loadGame(GamePanel panel) {
        try (ObjectInputStream os = new ObjectInputStream(new FileInputStream(Recorder.class.getResource("/" + SAVED_GAME).getFile()))) {
            Object o = os.readObject();
            if (o instanceof Stats) {
                Stats s = (Stats) o;
                stats.setKills(s.getKills());
            }

            o = os.readObject();
            if (o instanceof TankNode) {
                TankNode p = (TankNode) o;
                player.setX(p.getX());
                player.setY(p.getY());
                player.setDir(p.getDir());
            }

            o = os.readObject();
            if (o instanceof List<?>) {
                List<Object> list = (List<Object>) o;
                for (Object t : list)
                    if (t instanceof TankNode) {
                        TankNode bot = (TankNode) t;
                        bots.add(new BotTank(panel, bot.getX(), bot.getY(), bot.getDir()));
                    }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean loadGameUI(final JFrame f) {
        return hasSavedGameFile() &&
                JOptionPane.showConfirmDialog(f,"Load Previous Game?", "", YES_NO_OPTION, QUESTION_MESSAGE) == YES_OPTION;
    }

    private static boolean hasSavedGameFile() {
        return new File(Recorder.class.getResource("/").getFile() + SAVED_GAME).exists();
    }

    private static boolean createSavedGameFile() {
        try {
            return new File(Recorder.class.getResource("/").getFile() + SAVED_GAME).createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Data
    @AllArgsConstructor
    public static class TankNode implements Serializable {
        private static final long serialVersionUID = 1L;
        private int x;
        private int y;
        private Direction dir;
    }

}
