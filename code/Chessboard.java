package code;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Chessboard extends JPanel implements Runnable {
	BufferedImage[] powerOf2s = new BufferedImage[16];
	BufferedImage chessboardBackground;
	Block[][] blocks = new Block[4][4];
	BlockStorehouse extraBlocks = new BlockStorehouse();
	int amountOfBlocks = 0;
	int score = 0;
	boolean isWorking = false;

	final double ratioMax = 1.05;
	final double ratioMin = 1.0;
	double ratio = 1.0;

	Chessboard() {
		setPreferredSize(new Dimension(580, 580));

		try {
			chessboardBackground = ImageIO.read(getClass().getResourceAsStream(
					"/image/chessboard.png"));
			for (int i = 0; i < 16; i++) {
				powerOf2s[i] = ImageIO.read(getClass().getResourceAsStream(
						"/image/" + (i + 1) + ".png"));
				blocks[i / 4][i % 4] = new Block();
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		newBlock();
		newBlock();
	}

	void newBlock() {
		int x, y, power = 1;
		boolean flag = true;
		final double chance = 0.15;

		while (flag && amountOfBlocks < 16) {
			x = (int) (Math.random() * 4);
			y = (int) (Math.random() * 4);
			if (!blocks[x][y].checkExistence()) {
				if (Math.random() < chance) {
					power = 2;
				}
				blocks[x][y].setPower(power);
				blocks[x][y].setPosition(x, y);
				blocks[x][y].isNew = true;
				amountOfBlocks++;
				flag = false;
			}
		}
	}

	boolean canMove() {
		boolean canMove = false;
		if (amountOfBlocks < 16) {
			canMove = true;
		} else {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (blocks[i][j].getPower() == blocks[i + 1][j].getPower()
							|| blocks[i][j].getPower() == blocks[i][j + 1]
									.getPower()) {
						canMove = true;
					}
				}
			}
			for (int i = 0; i < 3; i++) {
				if (blocks[i][3].getPower() == blocks[i + 1][3].getPower()) {
					canMove = true;
				}
			}
			for (int j = 0; j < 3; j++) {
				if (blocks[3][j].getPower() == blocks[3][j + 1].getPower()) {
					canMove = true;
				}
			}
		}
		return canMove;
	}

	int getScore() {
		return score;
	}

	boolean moveLeft() {
		boolean move = false;
		for (int x = 1; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (blocks[x][y].checkExistence()) {
					for (int z = x; z > 0; z--) {
						if (!blocks[z - 1][y].checkExistence()) {
							blocks[z - 1][y] = blocks[z][y];
							blocks[z][y] = new Block();
							move = true;
						} else if (blocks[z][y].getPower() == blocks[z - 1][y]
								.getPower()) {
							blocks[z - 1][y] = blocks[z][y];
							blocks[z - 1][y].powerUp();
							blocks[z - 1][y].isNew = true;
							extraBlocks.push(blocks[z][y]);
							blocks[z][y] = new Block();
							amountOfBlocks--;
							score += pow(2, blocks[z - 1][y].getPower());
							move = true;
							break;
						} else {
							break;
						}
					}
				}
			}
		}
		return move;
	}

	boolean moveRight() {
		boolean move = false;
		for (int x = 2; x >= 0; x--) {
			for (int y = 0; y < 4; y++) {
				if (blocks[x][y].checkExistence()) {
					for (int z = x; z < 3; z++) {
						if (!blocks[z + 1][y].checkExistence()) {
							blocks[z + 1][y] = blocks[z][y];
							blocks[z][y] = new Block();
							move = true;
						} else if (blocks[z][y].getPower() == blocks[z + 1][y]
								.getPower()) {
							blocks[z + 1][y] = blocks[z][y];
							blocks[z + 1][y].powerUp();
							blocks[z + 1][y].isNew = true;
							extraBlocks.push(blocks[z][y]);
							blocks[z][y] = new Block();
							amountOfBlocks--;
							score += pow(2, blocks[z + 1][y].getPower());
							move = true;
							break;
						} else {
							break;
						}
					}
				}
			}
		}
		return move;
	}

	boolean moveUp() {
		boolean move = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 1; y < 4; y++) {
				if (blocks[x][y].checkExistence()) {
					for (int z = y; z > 0; z--) {
						if (!blocks[x][z - 1].checkExistence()) {
							blocks[x][z - 1] = blocks[x][z];
							blocks[x][z] = new Block();
							move = true;
						} else if (blocks[x][z].getPower() == blocks[x][z - 1]
								.getPower()) {
							blocks[x][z - 1] = blocks[x][z];
							blocks[x][z - 1].powerUp();
							blocks[x][z - 1].isNew = true;
							extraBlocks.push(blocks[x][z]);
							blocks[x][z] = new Block();
							amountOfBlocks--;
							score += pow(2, blocks[x][z - 1].getPower());
							move = true;
							break;
						} else {
							break;
						}
					}
				}
			}
		}
		return move;
	}

	boolean moveDown() {
		boolean move = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 2; y >= 0; y--) {
				if (blocks[x][y].checkExistence()) {
					for (int z = y; z < 3; z++) {
						if (!blocks[x][z + 1].checkExistence()) {
							blocks[x][z + 1] = blocks[x][z];
							blocks[x][z] = new Block();
							move = true;
						} else if (blocks[x][z].getPower() == blocks[x][z + 1]
								.getPower()) {
							blocks[x][z + 1] = blocks[x][z];
							blocks[x][z + 1].powerUp();
							blocks[x][z + 1].isNew = true;
							extraBlocks.push(blocks[x][z]);
							blocks[x][z] = new Block();
							amountOfBlocks--;
							score += pow(2, blocks[x][z + 1].getPower());
							move = true;
							break;
						} else {
							break;
						}
					}
				}
			}
		}
		return move;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(chessboardBackground, 0, 0, this);

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (blocks[i][j].checkExistence()) {
					if (blocks[i][j].isNew) {
						int newSize = (int) (120 * ratio);
						g.drawImage(powerOf2s[blocks[i][j].getPower() - 1],
								(i * 140 + 20) - (newSize - 120) / 2,
								(j * 140 + 20) - (newSize - 120) / 2, newSize,
								newSize, this);
					} else {
						g.drawImage(powerOf2s[blocks[i][j].getPower() - 1],
								blocks[i][j].x, blocks[i][j].y, 120, 120, this);
					}
				}
			}
		}

		for (Block tempBlock = extraBlocks.pop(); tempBlock != null; tempBlock = extraBlocks
				.pop()) {
			g.drawImage(powerOf2s[tempBlock.getPower() - 1], tempBlock.x,
					tempBlock.y, 120, 120, this);
		}
	}

	public void run() {
		isWorking = true;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				blocks[i][j].isNew = false;
			}
		}

		boolean flag = true;
		while (flag) {
			flag = false;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (blocks[i][j].checkExistence()) {
						if (blocks[i][j].x != (i * 140 + 20)) {
							if (blocks[i][j].x > (i * 140 + 20)) {
								blocks[i][j].x -= 20;
							}
							if (blocks[i][j].x < (i * 140 + 20)) {
								blocks[i][j].x += 20;
							}
							flag = true;
						}
						if (blocks[i][j].y != (j * 140 + 20)) {
							if (blocks[i][j].y > (j * 140 + 20)) {
								blocks[i][j].y -= 20;
							}
							if (blocks[i][j].y < (j * 140 + 20)) {
								blocks[i][j].y += 20;
							}
							flag = true;
						}
					}
				}
			}
			repaint();
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

		newBlock();
		extraBlocks.clear();
		System.gc();

		while (ratio <= ratioMax) {
			ratio += 0.01;
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		while (ratio >= ratioMin) {
			ratio -= 0.01;
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

		isWorking = false;
	}

	int pow(int base, int exponent) {
		int temp = 1;
		while (exponent > 0) {
			temp *= base;
			exponent--;
		}
		return temp;
	}

	void move() {
		Thread paint = new Thread(this);
		paint.start();
	}

	void reset() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				blocks[i][j] = new Block();
			}
		}
		amountOfBlocks = 0;
		score = 0;

		newBlock();
		newBlock();
	}

}
