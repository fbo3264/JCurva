package model;

public class PathSegment {

	public PathSegment(float _lastX, float _lastY, float _currX, float _currY) {
		super();
		this._fromX = _lastX;
		this._fromY = _lastY;
		this._toX = _currX;
		this._toY = _currY;
	}

	private float _fromX;
	private float _fromY;

	private float _toX;
	private float _toY;

}
