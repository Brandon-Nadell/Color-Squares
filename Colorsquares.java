package com.mygdx.game;

//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Scanner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Square.Colors;
import com.mygdx.game.Unit.Type;

public class Colorsquares extends ApplicationAdapter {
	
	private static State state;
	private static ArrayList<Colors> sequence;
	public static Board board;
	private Texture highlight;
	private Texture background;
	private Texture gameOver;
	private Texture warning;
	private float a;
	private static int iteration;
	private static int loops;
	private static int stars;
	private static int score;
	private static int highscore;
//	private static BufferedWriter w;
//	private static Scanner sc;
	public static float volume;
	public static Sound start;
	public static Sound move;
	public static Sound teleport;
	public static Sound warn;
	public static final int MOVE_TIME = 30;
	private static ArrayList<Integer> iterationsColor = new ArrayList<Integer>(Arrays.asList(2, 14, 26, 38, 50, 62, 74, 86, 98));
	private static ArrayList<Integer> iterationsSequencePre = new ArrayList<Integer>(Arrays.asList(4, 16, 32, 50, 70, 92));
	private static ArrayList<Integer> iterationsSequence = new ArrayList<Integer>(Arrays.asList(10, 38, 76));
	private static ArrayList<Integer> iterationsSequencePlus = new ArrayList<Integer>(Arrays.asList(24, 58, 100));
	
	public enum State {
		GAME,
		OVER
	}
	
	@Override
	public void create () {
		Graphics.setup();
		start = Gdx.audio.newSound(Gdx.files.internal("start.mp3"));
		move = Gdx.audio.newSound(Gdx.files.internal("move.mp3"));
		teleport = Gdx.audio.newSound(Gdx.files.internal("teleport.mp3"));
		warn = Gdx.audio.newSound(Gdx.files.internal("warning.mp3"));
		highlight = new Texture("highlight.png");
		background = new Texture("background.png");
		gameOver = new Texture("game_over.png");
		warning = new Texture("warning.png");
		a = 0f;
		iteration = 0;
		stars = 10;
		loops = 0;
		score = 0;
		volume = 1f;
		Colors.colors = 2;
		board = new Board(true, 8, 10);
//		for (int i = 0; i < 40; i++) {
//			board.spawnItem();
//		}
		state = State.GAME;
//		try {
//			sc = new Scanner(new File("options.txt"));
//			highscore = Integer.parseInt(sc.next());
//		} catch (FileNotFoundException e) { }
		highscore = 0;
		
		
		newSequence(3, Colors.colors);
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Graphics.begin();
		Graphics.draw(background, 0, 0, false);
		
		board.render();
		
		for (int i = 0; i < sequence.size(); i++) {
			Graphics.setColor(sequence.get(i).color);
			Graphics.draw(Square.texture, 60*i, 660, false);
			Graphics.setColor(Color.WHITE);
			if (i == iteration) {
				Graphics.draw(highlight, 60*i, 660, false);
			}
		}
		if (warning()) {
			Graphics.draw(warning, 60*(sequence.size()), 660, false);
		}
		Word score = new Word(Integer.toString(Colorsquares.score), 48);
		score.render(240 - score.getWidth()/2, 752);
		Word highscore = new Word(Integer.toString(Colorsquares.highscore), 24);
		highscore.render(12, 768);
		Word stars = new Word(Integer.toString(Colorsquares.stars), 48);
		stars.render(468 - stars.getWidth(), 658);
		Graphics.draw(Type.STAR.texture, 413 - stars.getWidth(), 662, false);
		
		if (state == State.GAME && board.visible == 79 && board.shift.magnitude == 0) {
			if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
				board.player.move(-1, 0, board.board, false);
			}
			if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
				board.player.move(1, 0, board.board, false);
			}
			if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
				board.player.move(0, -1, board.board, false);
			}
			if (Gdx.input.isKeyJustPressed(Keys.UP)) {
				board.player.move(0, 1, board.board, false);
			}
			try {
				if (Gdx.input.isButtonPressed(Buttons.LEFT) && board.board[board.player.getX()][board.player.getY()].getType() == Square.Type.TELEPORT && 
						board.board[Graphics.getX()][Graphics.getY()].getType() == Square.Type.TELEPORT && board.board[board.player.getX()][board.player.getY()] != board.board[Graphics.getX()][Graphics.getY()]) {
					board.player.move(Graphics.getX() - board.player.getX(), Graphics.getY() - board.player.getY(), board.board, true);
				}
			} catch (ArrayIndexOutOfBoundsException e) { }
		}
		if (state == State.OVER) {
			Graphics.setColor(1f, 1f, 1f, a/255f);
			Graphics.draw(gameOver, 0, 0, false);
			a = Math.min(a + 255f/Colorsquares.MOVE_TIME/2, 255f);
			score = new Word(Integer.toString(Colorsquares.score), 72);
			score.render(240 - score.getWidth()/2, 504);
			Word text = new Word("High score: ", 30);
			text.render(240 - text.getWidth()/2, 432);
			highscore = new Word(Integer.toString(Colorsquares.highscore), 30);
			highscore.render(240 - highscore.getWidth()/2, 384);
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				create();
			}
		}
		dev();
		
		Graphics.end();
	}
	
	public void dev() {
		if (Gdx.input.isKeyJustPressed(Keys.P)) {
			score++;
		}
		if (Gdx.input.isKeyJustPressed(Keys.I)) {
			iterate();
		}
		if (Gdx.input.isKeyJustPressed(Keys.E)) {
			state = State.OVER;
		}
		if (Gdx.input.isKeyJustPressed(Keys.R)) {
			board.randomize();
		}
		if (Gdx.input.isKeyJustPressed(Keys.S)) {
			newSequence(sequence.size(), Math.max(2, Colors.colors - 1));
		}
		if (Gdx.input.isKeyJustPressed(Keys.T)) {
			stars ++;
		}
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}
	
	public static void round() {
		board.update();
		score++;
		if (highscore < score) {
			highscore = score;
		}
		if (stars == 0) {
			int count = 0;
			try {
				if (board.board[board.player.getX() + 1][board.player.getY()].getColor() != getIterationColor()) {
					count++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				count++;
			}
			try {
				if (board.board[board.player.getX() - 1][board.player.getY()].getColor() != getIterationColor()) {
					count++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				count++;
			}
			try {
				if (board.board[board.player.getX()][board.player.getY() + 1].getColor() != getIterationColor()) {
					count++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				count++;
			}
			try {
				if (board.board[board.player.getX()][board.player.getY() - 1].getColor() != getIterationColor()) {
					count++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				count++;
			}
			if (board.board[board.player.getX()][board.player.getY()].getType() == Square.Type.TELEPORT) {
				for (Square[] squares : board.board) {
					for (Square square : squares) {
						if (square.getType() == Square.Type.TELEPORT && square.getColor() == getIterationColor()) {
							count = 0;
						}
					}
				}
			}
			if (count == 4) {
				state = State.OVER;
//				try {
//					w = new BufferedWriter(new FileWriter("options.txt"));
//					w.write(Integer.toString(Colorsquares.highscore));
//					w.close();
//				} catch (IOException e) { }
			}
		}
	}
	
	public static void iterate() {
		iteration++;
		if (iteration == sequence.size()) {
			iteration = 0;
			loops++;
			if (iterationsSequencePre.contains(loops)) {
				newSequence(sequence.size(), Colors.colors - 1);
				stars += (sequence.size() + 1)/2;
			}
			if (iterationsSequence.contains(loops)) {
				newSequence(sequence.size(), Colors.colors);
				stars += (sequence.size() + 1)/2;
			}
			if (iterationsSequencePlus.contains(loops)) {
				newSequence(sequence.size() + 1, Colors.colors);
				stars += (sequence.size() + 1)/2;
			}
			if (iterationsColor.contains(loops)) {
				Colors.add();
			}
			if (warning()) {
				warn.play(volume/3);
			}
		}
	}
	
	/*
	 * sequence (-1): +12, +16, ...
	 * sequence: 6 after every other sequence (-1) (start)
	 * sequence+: 8 after every other sequence (-1) (no start)
	 * 
	 * 2 - color
	 * 4 - sequence (-1)
	 * 10 - sequence
	 * 14 - color
	 * 16 - sequence (-1)
	 * 24 - sequence+
	 * 
	 * 26 - color
	 * 32 - sequence (-1)
	 * 38 - sequence
	 * 38 - color
	 * 50 - sequence (-1)
	 * 50 - color
	 * 58 - sequence+
	 * 
	 * 62 - color
	 * 70 - sequence (-1)
	 * 74 - color
	 * 76 - sequence
	 * 86 - color
	 * 92 - sequence (-1)
	 * 98 - color
	 * 100 - sequence+
	 * 
	 */
	
	public static void newSequence(int size, int colors) {
		sequence = new ArrayList<Colors>();
		for (int i = 0; i < size; i++) {
			Colors newColor = Colors.random(colors);
			int count = 0;
			for (Colors color : sequence) {
				if (color == newColor) {
					count++;
				}
			}
			if (count <= 1) {
				sequence.add(newColor);
			} else {
				i--;
			}
		}
	}
	
	public static boolean warning() {
		return iterationsSequence.contains(loops + 1) || iterationsSequencePre.contains(loops + 1) || iterationsSequencePlus.contains(loops + 1);
	}
	
	public static Colors getIterationColor() {
		return sequence.get(iteration);
	}
	
	public static boolean hasStars() {
		return stars > 0;
	}
	
	public static void takeStar() {
		stars--;
	}
	
	public static void addStar() {
		stars++;
	}
	
}
