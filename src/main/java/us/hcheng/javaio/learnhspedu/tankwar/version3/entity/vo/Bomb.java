package us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo;

import java.awt.*;
import java.util.stream.IntStream;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Direction;
import us.hcheng.javaio.utils.SleepUtil;

public class Bomb extends PanelObject implements Runnable {
	private Image img;
	public static final int SIZE = 60;

	private static Image[] imgs = new Image[]{
			Toolkit.getDefaultToolkit().getImage(Bomb.class.getResource("/bomb_1.gif")),
			Toolkit.getDefaultToolkit().getImage(Bomb.class.getResource("/bomb_2.gif")),
			Toolkit.getDefaultToolkit().getImage(Bomb.class.getResource("/bomb_3.gif"))
	};

	public Bomb(int x, int y) {
		super(x, y, 0, true, Direction.UP);
		img = imgs[0];
	}

	public Image getImg() {
		return img;
	}

	@Override
	public void run() {
		IntStream.range(0, 3).forEach(i -> {
			SleepUtil.sleep(50);
			img = imgs[i];
		});
		setAlive(false);
	}

}
