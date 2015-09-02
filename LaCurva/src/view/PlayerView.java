package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import model.Player;

public class PlayerView {
	private Player _playerModel;

	public static int STROKE_WIDTH = 5;

	public PlayerView(Player player) {
		this._playerModel = player;
	}

	public void draw(Graphics g) {

		g.setColor(_playerModel.getColor());
		Graphics2D g2d = (Graphics2D) g;
		if (!_playerModel.makeGap()) {
			// g.fillRect((int)_playerModel.get_currX(),
			// (int)_playerModel.get_currY(), STROKE_WIDTH, STROKE_WIDTH);
			g2d.fill(new Ellipse2D.Double(_playerModel.get_currX(),
					_playerModel.get_currY(), STROKE_WIDTH, STROKE_WIDTH));
		}
		_playerModel.updateCoords();
	}
}
