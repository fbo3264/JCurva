package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import view.MenuPanel.PlayerKeyMapping;
import model.Player;
import model.Player.ROTATION_DIRECTION;
import controller.GameController;
import controller.GameController.StartingPosition;

public class GamePanel extends JPanel implements KeyListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 700;

	private boolean[] _pressedKeys = new boolean[256];

	// gameloop
	private Thread _gameThread;
	private boolean _running = false;
	private static int FPS = 20;
	private static long TARGET_TIME = 1000 / FPS;

	private GameController _controller;

	private PlayerView[] _playerViews;

	// the image we draw on
	private BufferedImage _screenImage;

	public GamePanel( PlayerKeyMapping[] keyMapping) {		
	
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		_screenImage = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		this.setFocusable(true);
		this.requestFocus();
		this.setVisible(true);
		this.addKeyListener(this);

		init(keyMapping);

	}

	private void init(PlayerKeyMapping[] keyMapping) {
		_controller = new GameController();
		for (PlayerKeyMapping playerKeyMapping : keyMapping) {
			if(playerKeyMapping.IN_USE){
				_controller.addPlayer(new Player(playerKeyMapping.NAME, Character.toUpperCase(playerKeyMapping.CONTROL_LEFT),
						Character.toUpperCase(playerKeyMapping.CONTROL_RIGHT),playerKeyMapping.COLOR));
			}
		}

		_playerViews = new PlayerView[_controller.getNrOfPlayers()];
		for (int i = 0; i < _controller.getNrOfPlayers(); i++) {
			Player currPlayerModel = _controller.getAlivePlayers().get(i);
			StartingPosition currStartPos = GameController
					.getRandomStartPosition();
			currPlayerModel.setStartPos(currStartPos.PosX, currStartPos.PosY,
					currStartPos.Angle);

			_playerViews[i] = new PlayerView(currPlayerModel);

		}
	}

	public void start() {
		_running = true;
		_gameThread = new Thread(this);
		_gameThread.start();
	}

	private void update() {
		for (Player p : _controller.getAlivePlayers()) {
			Float nextPos = p.getNextPosition();
			if (!_controller.checkCollision(nextPos, p, _screenImage)) {
				p.update(nextPos.x, nextPos.y);
			} else {
				if (_controller.playerGameover(p)) {
					// only one player left
					_running = false;
					drawGameoverScreen();
				}

			}
		}
	}

	private void drawGameoverScreen() {
		Graphics g = _screenImage.getGraphics();
		g.setFont(new Font("Verdana", Font.PLAIN, 30));
		g.setColor(_controller.getAlivePlayers().get(0).getColor());
		g.drawString(_controller.getAlivePlayers().get(0).getName() + " wins!",
				100, 100);

	}

	private void draw(Graphics g) {
		for (PlayerView pv : _playerViews) {
			pv.draw(g);
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		_pressedKeys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		_pressedKeys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void run() {

		long start;
		long elapsed;
		long wait;

		while (_running) {
			start = System.nanoTime();
			checkInput();
			update();
			draw(_screenImage.getGraphics());
			elapsed = System.nanoTime() - start;
			wait = TARGET_TIME - elapsed / 1000000;
			try {
				if (wait < 0)
					wait = 5;
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void checkInput() {
		// check controls for each player

		for (Player p : _controller.getPlayers()) {
			if (_pressedKeys[p.getLeftControl()]) {
				p.rotate(ROTATION_DIRECTION.LEFT);
			} else if (_pressedKeys[p.getRightControl()]) {
				p.rotate(ROTATION_DIRECTION.RIGHT);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(_screenImage, 0, 0, this);
	}

}
