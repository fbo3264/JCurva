package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import view.GamePanel;
import view.PlayerView;

public class Player {

	private float _lastX;
	private float _lastY;
	private float _currX;
	private float _currY;
	private int _currAngle;

	private Color _color;
	
	private int _score = 0;
	private String _name;
	
	private double _nrOfPoints = 0;
	private int _gapPixelCount = 0;
	private boolean _doGap = false;

	public Player(String name, char left, char right, Color c) {
		_name = name;
		CONTROL_LEFT = (int) left;
		CONTROL_RIGHT = (int) right;

		_color = c;
	}

	// controls
	private int CONTROL_LEFT;
	private int CONTROL_RIGHT;

	public int getLeftControl() {
		return CONTROL_LEFT;
	}

	public int getRightControl() {
		return CONTROL_RIGHT;
	}

	public void rotate(ROTATION_DIRECTION direction) {
		if (direction.equals(ROTATION_DIRECTION.LEFT)) {
			_currAngle -= 10;
			if (_currAngle < 0)
				_currAngle = 359;
		} else if (direction.equals(ROTATION_DIRECTION.RIGHT)) {
			_currAngle += 10;
			if (_currAngle > 360)
				_currAngle = 0;
		}
	}

	public float get_currX() {
		return _currX;
	}

	public float get_currY() {
		return _currY;
	}

	public void set_currX(float _currX) {
		this._currX = _currX;
	}

	public void set_currY(float _currY) {
		this._currY = _currY;
	}

	public enum ROTATION_DIRECTION {
		LEFT, RIGHT
	}

	public Point2D.Float getNextPosition() {
		float tmpX = (float) (_currX + Math.cos(Math.toRadians(_currAngle))
				* PlayerView.STROKE_WIDTH);
		float tmpY = (float) (_currY + Math.sin(Math.toRadians(_currAngle))
				* PlayerView.STROKE_WIDTH);
		Point2D.Float ret = new Point2D.Float(tmpX, tmpY);
		return ret;
	}

	public void update(float nextPosX, float nextPosY) {
		_currX = nextPosX;
		_currY = nextPosY;
	}

	public Color getColor() {
		return _color;
	}

	public void updateCoords() {
		_lastX = _currX;
		_lastY = _currY;
		_nrOfPoints++;
	}
	
	public void updateScore(int addToScore){
		_score+=addToScore;
	}

	public String getName() {
		return _name;
	}

	public void setStartPos(int posX, int posY, int angle) {
		this._currX = posX;
		this._currY = posY;
		this._currAngle = angle;
		updateCoords();
	}

	public boolean makeGap() {
		if(_nrOfPoints % 10 == 0){
			_doGap=true;
		}
		if(_doGap &&  _gapPixelCount < 4){
			_nrOfPoints--;
			_gapPixelCount++;
		}else{
			_doGap = false;
			_gapPixelCount = 0;
		}
		return _doGap;
		
	}
}
