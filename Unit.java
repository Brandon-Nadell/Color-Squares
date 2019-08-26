package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Unit {
	
	private int x;
	private int y;
	private float a;
	private double u;
	private double v;
	private Type type;
	private MoveAnimation moveAnimation;
	private TeleportAnimation teleportAnimation;
	
	public Unit(int x, int y, Type type) {
		this.type = type;
		setX(x, false);
		setY(y, false);
		a = 255f;
	}
	
	public Unit(Type type) {
		this(-1, -1, type);
	}
	
	public enum Type {
		PLAYER(new Texture("player_outline.png"), new Texture("player.png")),
		STAR(new Texture("star.png"), null),
		;
		
		public Texture texture;
		public Texture overlay;
		
		private Type(Texture texture, Texture overlay) {
			this.texture = texture;
			this.overlay = overlay;
		}
	}
	
	public void render() {
		draw();
		if (moveAnimation != null && moveAnimation.update()) {
			moveAnimation = null;
		}
		if (teleportAnimation != null && teleportAnimation.update()) {
			teleportAnimation = null;
		}
	}
	
	public void draw(double u, double v, int offsetX, int offsetY, float a) {
//		Graphics.setColor(1f, 1f, 1f, a/255f);
//		Graphics.draw(type.texture, (int)u, (int)v, true);
//		if (type.overlay != null) {
//			Graphics.setColor(Colorsquares.getIterationColor().color.r, Colorsquares.getIterationColor().color.g, Colorsquares.getIterationColor().color.b, a/255f);
//			Graphics.draw(type.overlay, (int)u, (int)v, true);
//			Graphics.setColor(Color.WHITE);
//		}
		
		Graphics.setColor(1f, 1f, 1f, a/255f);
		Graphics.draw(type.texture, type == Type.PLAYER ? (int) u : Square.toU(offsetX + x), type == Type.PLAYER ? (int) v : Square.toV(offsetY + y), true);
		if (type.overlay != null) {
			Graphics.setColor(Colorsquares.getIterationColor().color.r, Colorsquares.getIterationColor().color.g, Colorsquares.getIterationColor().color.b, a/255f);
			Graphics.draw(type.overlay, type == Type.PLAYER ? (int) u : Square.toU(offsetX + x), type == Type.PLAYER ? (int) v : Square.toV(offsetY + y), true);
			Graphics.setColor(Color.WHITE);
		}
	}
	
	public void draw(int offsetX, int offsetY) {
		draw(u, v, offsetX, offsetY, a);
		a = Math.min(a + 255f/Colorsquares.MOVE_TIME, 255f);
	}
	
	public void draw() {
		draw(0, 0);
	}
	
	public void move(int dx, int dy, Square[][] board, boolean teleport) {
		try {
			if (moveAnimation == null && teleportAnimation == null && (board[x + dx][y + dy].getColor() == Colorsquares.getIterationColor() || Colorsquares.hasStars())) {
				float pitch = 1;
				if (board[x + dx][y + dy].getColor() == Colorsquares.getIterationColor()) {
					Colorsquares.iterate();
				} else {
					pitch = 0.75f;
					Colorsquares.takeStar();
				}
				if (board[x + dx][y + dy].getUnit() != null && board[x + dx][y + dy].getUnit().getType() == Type.STAR) {
					board[x + dx][y + dy].setUnit(null);
					Colorsquares.addStar();
				}
				if (board[x + dx][y + dy].getType() == Square.Type.BUCKET) {
					board[x + dx][y + dy].bucket(board);
				}
				if (board[x + dx][y + dy].isShift()) {
					Colorsquares.board.shift = Colorsquares.board.toShift((board[x + dx][y + dy].getType()));
				}
				set(x + dx, y + dy, board, true);
				board[x][y].setTime(2);
				board[x - dx][y - dy].setUnit(null);
				if (!teleport) {
					moveAnimation = new MoveAnimation(x - dx, y - dy, x, y, this);
				} else {
					teleportAnimation = new TeleportAnimation(x - dx, y - dy, x, y, this);
				}
				Colorsquares.round();
				if (!teleport) {
					Colorsquares.move.play(Colorsquares.volume/3, pitch, 0);
				} else {
					Colorsquares.teleport.play(Colorsquares.volume/3, pitch, 0);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) { }
	}
	
	public void set(int x2, int y2, Square[][] board, boolean move) {
		board[x2][y2].setUnit(this);;
		setX(x2, !move);
		setY(y2, !move);
	}
	
	public Unit clone() {
		return new Unit(x, y, type);
	}
	
	
	public int getX() {
		return x;
	}
	
	public void setX(int x, boolean initialize) {
		this.x = x;
		if (initialize) {
			u = Square.toU(x);
		}
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y, boolean initialize) {
		this.y = y;
		if (initialize) {
			v = Square.toV(y);
		}
	}
	
	public void addU(double u) {
		this.u += u;
	}
	
	public void addV(double v) {
		this.v += v;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setA(float a) {
		this.a = a;
	}
	
}
