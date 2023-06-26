package us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.entity.vo;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.entity.Direction;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.view.GamePanel;
import us.hcheng.javaio.utils.SleepUtil;

public class BotTank extends Tank implements Runnable {
    private ThreadLocalRandom rand;
    public BotTank(GamePanel panel, int x, int y, Direction dir) {
        super(panel, x, y, dir);
    }

    @Override
    public void run() {
        rand = ThreadLocalRandom.current();
        //&& false
        while (isAlive()) {
            switch (rand.nextInt(4)) {
                case 0 -> randomRepeater(v -> {
                    if (!overlap(this, getPanel().panelTanks()))
                        goUp();
                });
                case 1 -> randomRepeater(v -> {
                    if (!overlap(this, getPanel().panelTanks()))
                        goRight();
                });
                case 2 -> randomRepeater(v -> {
                    if (!overlap(this, getPanel().panelTanks()))
                        goDown();
                });
                default -> randomRepeater(v -> {
                    if (!overlap(this, getPanel().panelTanks()))
                        goLeft();
                });
            }
            SleepUtil.sleep(50);
        }
    }

    public void randomRepeater(Consumer<Void> consumer) {
        if (rand == null)
            rand = ThreadLocalRandom.current();

        IntStream.range(0, rand.nextInt(30)).forEach(i -> {
            consumer.accept(null);
            SleepUtil.sleep(50);
            if (rand.nextInt(101) % 3 == 0) {
                SleepUtil.sleep(50);
                fire();
            }
        });
    }

}
