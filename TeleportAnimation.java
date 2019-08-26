package com.mygdx.game;

public class TeleportAnimation {
	
	private int x1;
	private int y1;
	private Unit unit;
	private int duration;
	
	public TeleportAnimation(int x1, int y1, int x2, int y2, Unit unit) {
		this.x1 = x1;
		this.y1 = y1;
		this.unit = unit;
		unit.setX(x2, true);
		unit.setY(y2, true);
		unit.setA(0f);
		duration = Colorsquares.MOVE_TIME;
	}
	
	public boolean update() {
		unit.draw(Square.toU(x1), Square.toV(y1), 0, 0, duration*255f/Colorsquares.MOVE_TIME);
		duration--;
		return duration == 0;
	}
	
}
