package code;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Scoreboard extends JLabel implements Runnable {
	BufferedImage background;
	Color color;
	int score = 0;
	boolean isWorking = false;

	Scoreboard(String background, Color color) {
		try {
			this.background = ImageIO.read(getClass().getResourceAsStream(
					"/image/" + background));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		this.color = color;

		setVerticalAlignment(SwingConstants.BOTTOM);
		setPreferredSize(new Dimension(this.background.getWidth(),
				this.background.getHeight()));
		setFont(new Font("Times New Romen", Font.BOLD, 24));
	}

	void setScore(int score) {
		this.score = score;
		if (!isWorking) {
			Thread counter = new Thread(this);
			counter.start();
		}
	}

	public void run() {
		isWorking = true;
		int scoreNow = Integer.parseInt(getText());
		while (true) {
			if (scoreNow < score) {
				scoreNow++;
				setText(String.valueOf(scoreNow));
				repaint();
				try {
					Thread.sleep(4);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
		isWorking = false;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(background, 0, 0, this);

		g.setColor(color);
		String text = getText();
		FontMetrics fm = g.getFontMetrics();
		int stringWidth = fm.stringWidth(text);
		int x = 50 - stringWidth / 2;
		g.drawString(text, x, 70);
	}
}
