package us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;
    private int kills;

    public synchronized void increaseKills() {
        kills++;
    }

    public synchronized int getKills() {
        return kills;
    }

}
