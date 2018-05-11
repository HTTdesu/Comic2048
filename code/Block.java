package code;

public class Block {
	int x, y;
	boolean isExist, isNew;
	int power;

	Block() {
		x = 0;
		y = 0;
		setPower(0);
		isNew = false;
	}

	void setPosition(int x, int y) {
		this.x = 20 + x * 140;
		this.y = 20 + y * 140;
	}

	void setPower(int power) {
		this.power = power;
		if (power != 0) {
			isExist = true;
		} else {
			isExist = false;
		}
	}

	void powerUp() {
		power++;
	}

	int getPower() {
		return power;
	}

	boolean checkExistence() {
		return isExist;
	}
}
