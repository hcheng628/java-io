package us.hcheng.javaio.learnhspedu.tankwar.version3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Stats {
    private int kills;

    public synchronized void increaseKills() {
        kills++;
    }

    public synchronized int getKills() {
        return kills;
    }

}
