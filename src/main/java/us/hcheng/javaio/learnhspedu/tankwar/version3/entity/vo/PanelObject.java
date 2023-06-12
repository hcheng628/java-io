package us.hcheng.javaio.learnhspedu.tankwar.version3.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.hcheng.javaio.learnhspedu.tankwar.version3.entity.Direction;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanelObject {
	private int x;
	private int y;
	private int speed;
	private boolean alive;
	private Direction dir;

	public void goUp() {
		this.dir = Direction.UP;
		this.setY(y - speed);
	}

	public void goRight() {
		this.dir = Direction.RIGHT;
		this.setX(x + speed);
	}

	public void goDown() {
		this.dir = Direction.DOWN;
		this.setY(y + speed);
	}

	public void goLeft() {
		this.dir = Direction.LEFT;
		this.setX(x - speed);
	}

}
