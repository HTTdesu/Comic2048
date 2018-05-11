package code;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Mask extends JPanel implements Runnable {
	Frame frame;
	Mask mask = this;
	final float finalAlpha = 0.5f;
	float alpha = 0.0f;
	boolean isReady = false;
	int choice = 0;

	JLabel gameover = new JLabel(new ImageIcon(getClass().getResource(
			"/image/gameover.png")));

	ImageIcon retrySelected = new ImageIcon(getClass().getResource(
			"/image/retrySelected.png"));
	ImageIcon retryNotSelected = new ImageIcon(getClass().getResource(
			"/image/retryNotSelected.png"));
	JLabel retry = new JLabel(retrySelected);

	ImageIcon exitSelected = new ImageIcon(getClass().getResource(
			"/image/exitSelected.png"));
	ImageIcon exitNotSelected = new ImageIcon(getClass().getResource(
			"/image/exitNotSelected.png"));
	JLabel exit = new JLabel(exitNotSelected);

	Mask(Frame frame) {
		this.frame = frame;

		setLocation(0, 0);
		setSize(frame.getWidth(), frame.getHeight());
		setLayout(null);
		setOpaque(false);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (isReady) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_W:
					case KeyEvent.VK_UP:
					case KeyEvent.VK_S:
					case KeyEvent.VK_DOWN:
						changeChoice();
						break;
					case KeyEvent.VK_ENTER:
						doChoice();
						break;
					default:
						return;
					}
				}
			}
		});

		gameover.setSize(150, 50);
		gameover.setLocation(-150, 410);
		retry.setSize(100, 30);
		retry.setLocation(-100, 470);
		exit.setSize(100, 30);
		exit.setLocation(-100, 510);

		mask.add(gameover);
		mask.add(retry);
		mask.add(exit);

		this.frame.add(mask, 0);

		Thread appearance = new Thread(this);
		appearance.start();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		requestFocus(true);
	}

	public void run() {
		try {
			while (alpha < finalAlpha) {
				repaint();
				alpha += 0.02f;

				Thread.sleep(15);
			}

			while (gameover.getLocation().x < 0) {
				gameover.setLocation(gameover.getLocation().x + 20,
						gameover.getLocation().y);
				Thread.sleep(10);
			}
			while (retry.getLocation().x < 60) {
				retry.setLocation(retry.getLocation().x + 20,
						retry.getLocation().y);
				Thread.sleep(10);
			}
			while (exit.getLocation().x < 60) {
				exit.setLocation(exit.getLocation().x + 20,
						exit.getLocation().y);
				Thread.sleep(10);
			}

			isReady = true;

		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
		Graphics2D g2d = (Graphics2D) g;
		Composite composite = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setComposite(composite);
		super.paintComponent(g2d);
	}

	void changeChoice() {
		switch (choice) {
		case 0:
			retry.setIcon(retryNotSelected);
			exit.setIcon(exitSelected);
			break;
		case 1:
			retry.setIcon(retrySelected);
			exit.setIcon(exitNotSelected);
			break;
		}

		choice = (choice + 1) % 2;
	}

	void doChoice() {
		switch (choice) {
		case 0:
			frame.reset();
			break;
		case 1:
			System.exit(0);
		default:
			return;
		}
	}
}
