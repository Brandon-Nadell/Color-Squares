package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Square {
	
	private int x;
	private int y;
	private Colors color;
	private Type type;
	private float a;
	private float a2;
	private Unit unit;
	private int time;
	public static Texture texture = new Texture("square.png");
	
	public Square(int x, int y, Colors color) {
		this.x = x;
		this.y = y;
		this.color = color;
		type = Type.random();
		a = 0f;
		time = 1;
	}
	
	public enum Colors {
		
		RED(new Color(1f, 50/255f, 50/255f, 1f)),
		BLUE(new Color(50/255f, 50/255f, 1f, 1f)),
		DARK_GREEN(Color.FOREST),
		YELLOW(Color.GOLD),
		PURPLE(Color.PURPLE),
		LIGHT_BLUE(new Color(50/255f, 220/255f, 220/255f, 1f)),
		ORANGE(new Color(1f, 110/255f, 0f, 1f)),
		GREEN(new Color(0f, 240/255f, 0f, 1f)),
		PINK(Color.PINK),
		DARK_RED(new Color(150/255f, 0f, 0f, 1f)),
		;
		
		public Color color;
		public static int colors;
		
		private Colors(Color color) {
			this.color = color;
		}
		
		public static Colors random() {
			return values()[(int)(Math.random()*colors)];
		}
		
		public static Colors random(int colors) {
			return values()[(int)(Math.random()*colors)];
		}
		
		public static void add() {
			colors++;
		}
		
	}
	
	public enum Type {
		NORMAL(null),
		TELEPORT(new Texture("teleport.png")),
		BUCKET(new Texture("bucket.png")),
		SHIFT_LEFT(new Texture("shift_left.png")),
		SHIFT_RIGHT(new Texture("shift_right.png")),
		;
		
		public Texture overlay;
		
		private Type(Texture overlay) {
			this.overlay = overlay;
		}
		
		public static Type random() {
			double num = Math.random();
			if (num < .01) {
				return Type.SHIFT_LEFT;
			} else if (num < .02) {
				return Type.SHIFT_RIGHT;
			} else if (num < .05) {
				return Type.BUCKET;
			} else if (num < .1) {
				return Type.TELEPORT;
			} else {
				return Type.NORMAL;
			}
		}
		
	}
	
	public void draw(int offsetX, int offsetY) {
		Graphics.setColor(Color.GRAY);
		Graphics.draw(texture, toU(x + offsetX), toV(y + offsetY), true);
		Graphics.setColor(color.color.r, color.color.g, color.color.b, a/255f);
		Graphics.draw(texture, toU(x + offsetX), toV(y + offsetY), true);
		if (type.overlay != null) {
			Graphics.setColor(1f, 1f, 1f, a2/255f);
			Graphics.draw(type.overlay, toU(x + offsetX), toV(y + offsetY), true);
		}
		Graphics.setColor(Color.WHITE);
		if (time == 0) {
			a = Math.min(a + 255f/Colorsquares.MOVE_TIME, 255f);
			a2 = Math.min(a2 + 255f/Colorsquares.MOVE_TIME, 255f);
		} else {
			a = Math.max(a - 255f/Colorsquares.MOVE_TIME, 0f);
		}
	}
	
	public void draw() {
		draw(0, 0);
	}
	
	public void renderUnit() {
		if (unit != null) {
			unit.render();
		}
	}
	
	public void updateTime() {
		if (time > 0) {
			time--;
			if (time == 0) {
				type = Type.random();
				color = Colors.random();
			}
		}
	}
	
	public void bucket(Square[][] board) {
		for (int x = this.x - 2; x <= this.x + 2; x++) {
			for (int y = this.y - 2; y <= this.y + 2; y++) {
				if (!(this.x == x && this.y == y)) {
					try {
						board[x][y] = new Square(x, y, Colors.random());
						board[x][y].setTime(2);
						Colorsquares.board.visible -= 1;
					} catch (ArrayIndexOutOfBoundsException e) { }
				}
			}
		}
		Colorsquares.board.fixShifts();
	}
	
	public Square clone() {
		Square square = new Square(x, y, color);
		square.time = 0;
		square.type = type;
		if (unit != null && unit.getType() != Unit.Type.PLAYER) {
			square.setUnit(unit.clone());
		}
		return square;
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public static int toU(int x) {
		return x*texture.getWidth();
	}
	
	public static int toV(int y) {
		return y*texture.getHeight() + 30;
	}
	
	public Colors getColor() {
		return color;
	}
	
	public void setColor(Colors color) {
		this.color = color;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public boolean isShift() {
		return type == Type.SHIFT_LEFT || type == Type.SHIFT_RIGHT ;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
}
