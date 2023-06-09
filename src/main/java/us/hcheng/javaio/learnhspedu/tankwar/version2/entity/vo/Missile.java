package us.hcheng.javaio.learnhspedu.tankwar.version2.entity;

import us.hcheng.javaio.learnhspedu.tankwar.version2.view.GamePanel;
import us.hcheng.javaio.utils.SleepUtil;

public class Missile extends PanelObject implements Runnable {

	private final Tank t;

	public Missile(int x, int y, Tank t) {
		super(x, y, 2, true, t.getDir());
		this.t = t;
	}

	public void hitTank(Tank t) {
		int x = getX();
		int y = getY();
		int tX = t.getX();
		int tY = t.getY();

		switch (t.getDir()) {
			case UP, DOWN -> {
				if (x > tX && x < tX + 40 && y > tY && y < tY + 60)
					setDeads();
			}
			case RIGHT, LEFT -> {
				if (x >tX && x < tX + 60 && y > tY && y < tY + 40)
					setDeads();
			}
		}
	}

	private void setDeads() {
		t.setAlive(false);
		setAlive(false);
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
