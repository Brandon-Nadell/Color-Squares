package com.mygdx.game;

public class MoveAnimation {
	
	private double du;
	private double dv;
	private Unit unit;
	private int duration;
	
	public MoveAnimation(int x1, int y1, int x2, int y2, Unit unit) {
		du = (Square.toU(x2) - Square.toU(x1))/Colorsquares.MOVE_TIME;
		dv = (Square.toV(y2) - Square.toV(y1))/Colorsquares.MOVE_TIME;
		this.unit = unit;
		duration = Colorsquares.MOVE_TIME;
	}
	
	public boolean update() {
		unit.addU(du);
		unit.addV(dv);
		duration--;
		return duration == 0;
	}
	
}
