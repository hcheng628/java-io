package us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.entity.vo;

import static us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.entity.Constants.MISSILE_INIT_SPEED;
import java.util.Arrays;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.entity.Direction;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version3.view.GamePanel;
import us.hcheng.javaio.utils.SleepUtil;

public class Missile extends PanelObject implements Runnable {
	public static final int SIZE = 5;
	private final Tank t;

	public Missile(int x, int y, Tank t) {
		super(x, y, MISSILE_INIT_SPEED, true, t.getDir());
		this.t = t;
	}

	public Bomb hitTank(Tank hitTank) {
		int x = getX();
		int y = getY();
		int tX = hitTank.getX();
		int tY = hitTank.getY();
		boolean isV = Arrays.asList(Direction.UP, Direction.DOWN).contains(hitTank.getDir());

		return (isV && x > tX && x < tX + 40 && y > tY && y < tY + 60) ||
				(!isV && x >tX && x < tX + 60 && y > tY && y < tY + 40) ?
				hit(hitTank) : null;
	}

	private Bomb hit(Tank hitTank) {
		hitTank.setAlive(false);
		setAlive(false);
		return new Bomb(hitTank.getX(), hitTank.getY());
	}

	@Override
	public void run() {
		while (isAlive()) {
			switch (getDir()) {
				case UP -> this.goUp();
				case LEFT -> this.goLeft();
				case DOWN -> this.goDown();
				case RIGHT -> this.goRight();
			}

			if (GamePanel.notOnPanel(getX(), getY()))
				setAlive(false);
			SleepUtil.sleep(30);
		}
	}

}
