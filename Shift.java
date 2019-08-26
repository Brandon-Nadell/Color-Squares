package com.mygdx.game;

public class Shift {
	
	public Direction direction;
	public int magnitude;
	public Square[][] board;
	
	public Shift(Direction direction, Board board) {
		this.direction = direction;
		if (board != null) {
			this.board = new Square[8][10];
			for (int x = 0; x < board.board.length; x++) {
				for (int y = 0; y < board.board[0].length; y++) {
					this.board[x][y] = board.board[x][y].clone();
				}
			}
		}
	}
	
	public void render() {
		magnitude += Math.abs(direction.x);
		if (board != null) {
			for (Square[] squares : board) {
				for (Square square : squares) {
					square.draw(-direction.x*board.length, 0);
					if (square.getUnit() != null) {
						square.getUnit().draw(-direction.x*board.length, 0);
					}
				}
			}
		}
		
	}
	
	public enum Direction {
		LEFT(-1),
		RIGHT(1),
		NULL(0);
		
		public int x;
		
		private Direction(int x) {
			this.x = x;
		}
		
	}
	
	public int computeX() {
		return direction.x * magnitude;
	}
	
}
