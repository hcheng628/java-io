package us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.vo;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.Direction;
import us.hcheng.javaio.utils.SleepUtil;

public class BotTank extends Tank implements Runnable {

    private ThreadLocalRandom rand;

    public BotTank(int x, int y, int speed, Direction dir, Color color) {
        super(x, y, speed, dir, color);
    }

    @Override
    public void run() {
        rand = ThreadLocalRandom.current();
        while (isAlive()) {
            switch (rand.nextInt(4)) {
                case 0 -> randomRepeater(v -> goUp());
                case 1 -> randomRepeater(v -> goRight());
                case 2 -> randomRepeater(v -> goDown());
                default -> randomRepeater(v -> goLeft());
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
