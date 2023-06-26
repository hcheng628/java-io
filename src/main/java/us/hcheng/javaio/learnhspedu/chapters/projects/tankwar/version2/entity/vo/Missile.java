package us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.vo;

import java.util.Arrays;

import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.entity.Direction;
import us.hcheng.javaio.learnhspedu.chapters.projects.tankwar.version2.view.GamePanel;
import us.hcheng.javaio.utils.SleepUtil;

public class Missile extends PanelObject implements Runnable {

	public static final int SIZE = 5;
	private final Tank t;

	public Missile(int x, int y, Tank t) {
		super(x, y, 2, true, t.getDir());
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
				case Direction.UP -> this.goUp();
				case Direction.LEFT -> this.goLeft();
				case Direction.DOWN -> this.goDown();
				case Direction.RIGHT -> this.goRight();
			}

			if (GamePanel.notOnPanel(getX(), getY()))
				setAlive(false);
			SleepUtil.sleep(30);
		}
	}

}
