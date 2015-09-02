package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import view.GamePanel;
import view.MenuPanel;

public class CurvaGame extends JFrame {

	private MenuPanel _menuPanel;
	private GamePanel _gamePanel;

	public CurvaGame() {
		init();
	}

	public void startGame() {
		_gamePanel = new GamePanel(_menuPanel.get_playerKeyMapping());
		this.remove(_menuPanel);
		this.setContentPane(_gamePanel);
		this.pack();
		_gamePanel.requestFocus();
		_gamePanel.start();
	}

	private void init() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_menuPanel = new MenuPanel(this);
		this.setContentPane(_menuPanel);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CurvaGame g = new CurvaGame();
			}
		});

	}

}
