package code;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class Frame extends JFrame {
	Frame frame = this;
	Chessboard chessboard = new Chessboard();
	Scoreboard score = new Scoreboard("now.png", Color.WHITE);
	Scoreboard best = new Scoreboard("best.png", Color.BLACK);
	boolean gameover = false;
	Mask mask;

	File bestScoreFile = new File("BestScore.dat");

	Frame() {
		score.setText("0");
		try {
			if (!bestScoreFile.exists()) {
				bestScoreFile.createNewFile();
				saveBestScore();
				best.setText("0");
			} else {
				BufferedReader bestScoreReader = new BufferedReader(
						new FileReader(bestScoreFile));
				best.setText(bestScoreReader.readLine());
				bestScoreReader.close();
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		setTitle("2048");

		setIconImage(new ImageIcon(getClass().getResource("/image/logo.png"))
				.getImage());

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (gameover) {
					return;
				}

				if (chessboard.isWorking) {
					return;
				}

				if (!chessboard.canMove()) {
					gameover = true;

					if (chessboard.getScore() > Integer
							.parseInt(best.getText())) {
						best.setText(String.valueOf(chessboard.getScore()));
						saveBestScore();
					}

					mask = new Mask(frame);
					return;
				}

				boolean haveMove;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					haveMove = chessboard.moveUp();
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					haveMove = chessboard.moveLeft();
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					haveMove = chessboard.moveDown();
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					haveMove = chessboard.moveRight();
					break;
				default:
					return;
				}

				if (!haveMove) {
					return;
				}

				score.setScore(chessboard.getScore());

				chessboard.move();
			}
		});

		setLayout(null);

		JLabel title = new JLabel(new ImageIcon(getClass().getResource(
				"/image/title.png")));
		title.setLocation(0, 5);
		title.setSize(300, 100);
		score.setLocation(360, 5);
		score.setSize(100, 100);
		best.setLocation(480, 5);
		best.setSize(100, 100);
		chessboard.setLocation(0, 110);
		chessboard.setSize(580, 580);

		setSize(590, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		add(title);
		add(score);
		add(best);
		add(chessboard);
		setVisible(true);
	}

	void saveBestScore() {
		try {
			BufferedWriter bestScoreWriter = new BufferedWriter(new FileWriter(
					bestScoreFile));
			bestScoreWriter.write(String.valueOf(chessboard.getScore()));
			bestScoreWriter.flush();
			bestScoreWriter.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	void reset() {
		remove(mask);
		mask = null;
		chessboard.reset();
		score.setText("0");
		gameover = false;
		frame.requestFocus(true);
		repaint();
		System.gc();
	}
}
