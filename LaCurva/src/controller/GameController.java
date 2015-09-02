package controller;

import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.naming.ldap.Rdn;

import view.GamePanel;
import view.PlayerView;
import model.*;

public class GameController {

	private List<Player> _players;
	private List<Player> _alivePlayers;

	public static StartingPosition[] STARTING_POSITIONS;
	static {
		STARTING_POSITIONS = new StartingPosition[6];
		STARTING_POSITIONS[0] = new StartingPosition(100, 100, 0);
		STARTING_POSITIONS[1] = new StartingPosition(GamePanel.WIDTH - 100,
				100, 90);
		STARTING_POSITIONS[2] = new StartingPosition(GamePanel.WIDTH - 100,
				GamePanel.HEIGHT - 100, 180);
		STARTING_POSITIONS[3] = new StartingPosition(100,
				GamePanel.HEIGHT - 100, 270);
		STARTING_POSITIONS[4] = new StartingPosition(GamePanel.WIDTH / 2,
				GamePanel.HEIGHT / 2, 180);
		STARTING_POSITIONS[5] = new StartingPosition(GamePanel.WIDTH / 2 + 10,
				GamePanel.HEIGHT / 2, 0);

	}

	private int _nrOfPlayers = 0;

	public GameController() {
		_players = new CopyOnWriteArrayList<>();
		_alivePlayers = new CopyOnWriteArrayList<>();
	}

	public void addPlayer(Player p) {
		this._players.add(p);
		_nrOfPlayers++;
		_alivePlayers.add(p);
	}

	public List<Player> getPlayers() {
		return _players;
	}

	public List<Player> getAlivePlayers() {
		return _alivePlayers;
	}

	public int getNrOfPlayers() {
		return _nrOfPlayers;
	}

	public boolean checkCollision(Float nextPos, Player currPlayer,
			BufferedImage img) {
		try {
			int[] pixels = null;
			int collisonCount = 0;
			if (nextPos.x >= GamePanel.WIDTH || nextPos.y >= GamePanel.HEIGHT
					|| nextPos.x < 0 || nextPos.y < 0)
				return true;
			pixels = img.getRaster().getPixels((int) nextPos.x,
					(int) nextPos.y, PlayerView.STROKE_WIDTH,
					PlayerView.STROKE_WIDTH, pixels);
			for (int i = 0; i < pixels.length; i = i + 3) {
				int red = pixels[i];
				int green = pixels[i + 1];
				int blue = pixels[i + 2];
				if (red + green + blue > 0) {
					collisonCount++;
				}
			}
			if (collisonCount >= PlayerView.STROKE_WIDTH*2+2)
				return true;
		} catch (Exception ex) {
			return true;
		}
		return false;
	}

	public boolean playerGameover(Player p) {
		_alivePlayers.remove(p);
		for (Player player : _alivePlayers) {
			player.updateScore(100);
		}
		return _alivePlayers.size() <= 1;
	}

	public static StartingPosition getRandomStartPosition() {
		while (true) {
			int rndIdx = (int) (Math.random() * 6);
			if (!STARTING_POSITIONS[rndIdx].InUse) {
				STARTING_POSITIONS[rndIdx].InUse = true;
				return STARTING_POSITIONS[rndIdx];
			}
		}
	}

	public static class StartingPosition {
		public int PosX;
		public int PosY;
		public int Angle;
		public boolean InUse = false;

		public StartingPosition(int x, int y, int angle) {
			this.PosX = x;
			this.PosY = y;
			this.Angle = angle;
		}
	}

}
