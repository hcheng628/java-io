package us.hcheng.javaio.thread.part2.chapter5;

import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        Gate gate = new Gate();
        Stream.of(
            new User("CHENG", "CHENG", gate),
            new User("MOKA", "MOKA", gate),
            new User("MA", "MA", gate)).forEach(user -> new Thread(user).start());
    }

}
