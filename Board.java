package com.mygdx.game;

import com.mygdx.game.Shift.Direction;
import com.mygdx.game.Unit.Type;

public class Board {
	
	public Square[][] board;
	public Unit player;
	public int visible;
	public Shift shift;

	public Board(boolean main, int x, int y) {
		board = new Square[x][y];
		shift = new Shift(Direction.NULL, null);
		if (main) {
			randomize();
			fixShifts();
		}
	}
	
	public void render() {
		shift.render();
		for (Square[] squares : board) {
			for (Square square : squares) {
				if (square != null) {
					square.draw();
				}
			}
		}
		
		for (Square[] squares : board) {
			for (Square square : squares) {
				if (square != null) {
					square.renderUnit();
				}
			}
		}
		if (Math.abs(shift.magnitude) == 60) {
			shift(shift.direction);
			shift = new Shift(Direction.NULL, this);
		}
		if (visible < 79) {
			spawnSquare();
		}
	}
	
	public void randomize() {
		visible = 0;
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				board[x][y] = new Square(x, y, Square.Colors.random());
			}
		}
		fixShifts();
		player = new Unit(Unit.Type.PLAYER);
		player.set(4, 5, board, false);
		board[4][5].setTime(1);
		Colorsquares.start.play(Colorsquares.volume);
	}
	
	public void update() {
		for (Square[] squares : board) {
			for (Square square : squares) {
				square.updateTime();
			}
		}
		fixShifts();
		if (Math.random() > 0.9) {
			spawnItem();
		}
	}
	
	public void fixShifts() {
		for (Square[] squares : board) {
			for (Square square : squares) {
				if (square.getType() == Square.Type.SHIFT_LEFT && square.getX() == 0) {
					square.setType(Square.Type.SHIFT_RIGHT);
				}
				if (square.getType() == Square.Type.SHIFT_RIGHT && square.getX() == board.length - 1) {
					square.setType(Square.Type.SHIFT_LEFT);
				}
			}
		}
	}
	
	public void position(Square square, int x, int y) {
		board[x][y] = square;
		square.setX(x);
		square.setY(y);
		if (square.getUnit() != null) {
			square.getUnit().setX(x, true);
			square.getUnit().setY(y, true);
		}
	}
	
	public void shift(Shift.Direction direction) {
		if (direction == Shift.Direction.RIGHT) {
			for (int x = board.length - 1; x > 0; x--) {
				for (int y = 0; y < board[0].length; y++) {
					try {
						position(board[x - 1][y], x, y);
					} catch (ArrayIndexOutOfBoundsException e) { }
				}
			}
			for (Square square : shift.board[board.length - 1]) {
				position(square, 0, square.getY());
			}
		}
		if (direction == Shift.Direction.LEFT) {
			for (int x = 0; x < board.length; x++) {
				for (int y = 0; y < board[0].length; y++) {
					try {
						position(board[x + 1][y], x, y);
					} catch (ArrayIndexOutOfBoundsException e) { }
				}
			}
			for (Square square : shift.board[0]) {
				position(square, board.length - 1, square.getY());
			}
		}
		fixShifts();
	}
	
	public Shift toShift(Square.Type type) {
		switch (type) {
			case SHIFT_LEFT:
				return new Shift(Shift.Direction.LEFT, this);
			case SHIFT_RIGHT:
				return new Shift(Shift.Direction.RIGHT, this);
			default:
				return new Shift(Shift.Direction.NULL, this);
		}
	}
	
	public void spawnSquare() {
		int x = (int)(Math.random() * board.length);
		int y = (int)(Math.random() * board[0].length);
		if (board[x][y].getTime() == 0 || (x == player.getX() && y == player.getY())) {
			spawnSquare();
		} else {
			board[x][y].setTime(0);
			visible++;
		}
	}
	
	public void spawnItem() {
		int x = (int)(Math.random() * board.length);
		int y = (int)(Math.random() * board[0].length);
		if (board[x][y].getUnit() != null) {
			spawnItem();
		} else {
			new Unit(Type.STAR).set(x, y, board, false);
		}
	}
	
}
