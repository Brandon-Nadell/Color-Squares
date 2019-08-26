package com.mygdx.game;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Word {
	
	private String word;
	private ArrayList<TextureRegion> characters = new ArrayList<TextureRegion>();
	private int x;
	private int y;
	private int size;
	private static final int GAP = 4;
	
	private static Texture ascii = new Texture("ascii.png");
	private static TextureRegion[] chars = {
			new TextureRegion(ascii, 0, 500, 6, 20), new TextureRegion(ascii, 142, 80, 2, 20), new TextureRegion(ascii, 369, 80, 6, 20), new TextureRegion(ascii, 183, 80, 14, 20), new TextureRegion(ascii, 205, 80, 11, 20), new TextureRegion(ascii, 220, 80, 20, 20), new TextureRegion(ascii, 262, 80, 16, 20), new TextureRegion(ascii, 361, 80, 2, 20), 
			new TextureRegion(ascii, 62, 80, 7, 20), new TextureRegion(ascii, 72, 80, 7, 20), new TextureRegion(ascii, 285, 80, 11, 20), new TextureRegion(ascii, 303, 80, 14, 20), new TextureRegion(ascii, 404, 80, 5, 20), new TextureRegion(ascii, 9, 80, 11, 20), new TextureRegion(ascii, 413, 80, 3, 20), new TextureRegion(ascii, 40, 80, 10, 20), 
			
			new TextureRegion(ascii, 185, 40, 11, 20), new TextureRegion(ascii, 6, 40, 8, 20), new TextureRegion(ascii, 25, 40, 11, 20), new TextureRegion(ascii, 45, 40, 11, 20), new TextureRegion(ascii, 65, 40, 12, 20), 
			new TextureRegion(ascii, 85, 40, 11, 20), new TextureRegion(ascii, 105, 40, 11, 20), new TextureRegion(ascii, 125, 40, 11, 20), new TextureRegion(ascii, 145, 40, 11, 20), new TextureRegion(ascii, 165, 40, 11, 20), 
			
			new TextureRegion(ascii, 344, 80, 3, 20), new TextureRegion(ascii, 353, 80, 5, 20), new TextureRegion(ascii, 424, 80, 13, 20), new TextureRegion(ascii, 324, 80, 13, 20), new TextureRegion(ascii, 443, 80, 13, 20), new TextureRegion(ascii, 150, 80, 9, 20), new TextureRegion(ascii, 161, 80, 19, 20), 
			
			new TextureRegion(ascii, 2, 20, 16, 20), new TextureRegion(ascii, 24, 20, 13, 20), new TextureRegion(ascii, 43, 20, 14, 20), new TextureRegion(ascii, 63, 20, 15, 20), new TextureRegion(ascii, 84, 20, 12, 20), new TextureRegion(ascii, 106, 20, 11, 20),
			new TextureRegion(ascii, 123, 20, 15, 20), new TextureRegion(ascii, 144, 20, 13, 20), new TextureRegion(ascii, 167, 20, 6, 20), new TextureRegion(ascii, 186, 20, 8, 20), new TextureRegion(ascii, 205, 20, 14, 20), new TextureRegion(ascii, 225, 20, 11, 20), 
			new TextureRegion(ascii, 243, 20, 15, 20), new TextureRegion(ascii, 264, 20, 13, 20), new TextureRegion(ascii, 282, 20, 16, 20), new TextureRegion(ascii, 305, 20, 11, 20), new TextureRegion(ascii, 323, 20, 16, 20), new TextureRegion(ascii, 344, 20, 14, 20), 
			new TextureRegion(ascii, 364, 20, 13, 20), new TextureRegion(ascii, 383, 20, 14, 20), new TextureRegion(ascii, 404, 20, 13, 20), new TextureRegion(ascii, 422, 20, 16, 20), new TextureRegion(ascii, 441, 20, 19, 20), new TextureRegion(ascii, 463, 20, 14, 20), 
			new TextureRegion(ascii, 483, 20, 14, 20), new TextureRegion(ascii, 503, 20, 14, 20), 
			
			new TextureRegion(ascii, 82, 80, 6, 20), new TextureRegion(ascii, 50, 80, 10, 20), new TextureRegion(ascii, 92, 80, 6, 20), new TextureRegion(ascii, 243, 80, 15, 20), new TextureRegion(ascii, 23, 80, 15, 20), new TextureRegion(ascii, 501, 80, 5, 20), 
			
			new TextureRegion(ascii, 4, 0, 11, 20), new TextureRegion(ascii, 25, 0, 11, 20), new TextureRegion(ascii, 45, 0, 10, 20), new TextureRegion(ascii, 64, 0, 11, 20), new TextureRegion(ascii, 84, 0, 12, 20), new TextureRegion(ascii, 107, 0, 7, 20),
			new TextureRegion(ascii, 125, 0, 11, 20), new TextureRegion(ascii, 144, 0, 11, 20), new TextureRegion(ascii, 169, 0, 2, 20), new TextureRegion(ascii, 186, 0, 7, 20), new TextureRegion(ascii, 205, 0, 12, 20), new TextureRegion(ascii, 229, 0, 2, 20),
			new TextureRegion(ascii, 241, 0, 18, 20), new TextureRegion(ascii, 264, 0, 11, 20), new TextureRegion(ascii, 284, 0, 12, 20), new TextureRegion(ascii, 305, 0, 11, 20), new TextureRegion(ascii, 325, 0, 11, 20), new TextureRegion(ascii, 346, 0, 8, 20), 
			new TextureRegion(ascii, 365, 0, 10, 20), new TextureRegion(ascii, 386, 0, 8, 20), new TextureRegion(ascii, 404, 0, 11, 20), new TextureRegion(ascii, 424, 0, 12, 20), new TextureRegion(ascii, 442, 0, 17, 20), new TextureRegion(ascii, 464, 0, 12, 20), 
			new TextureRegion(ascii, 484, 0, 12, 20), new TextureRegion(ascii, 505, 0, 10, 20), 
			
			new TextureRegion(ascii, 100, 80, 10, 20), new TextureRegion(ascii, 507, 80, 2, 20), new TextureRegion(ascii, 110, 80, 10, 20), new TextureRegion(ascii, 123, 80, 15, 20), 
	};
	
	
	public Word(String word, int x, int y, int size) {
		this.word = word;
		this.x = x;
		this.y = y;
		this.size = size;
		construct();
	}
	
	public Word(String word, int x, int y) {
		this(word, x, y, 20);
	}
	
	public Word(String word) {
		this(word, 0, 0, 20);
	}
	
	public Word(String word, int size) {
		this(word, 0, 0, size);
	}

	
	public void construct() {
		for (int i = 0; i < word.length(); i++) {
			characters.add(chars[word.charAt(i) - 32]);
		}
	}
	
	public void render() {
		render(x, y);
	}
	
	public void render(int x, int y) {
//		Graphics.setColor(color);
		for (TextureRegion character : characters) {
			Graphics.draw(character, x, y, character.getRegionWidth()*size/40, character.getRegionHeight()*size/40, (character.getRegionWidth()*size)/20, (character.getRegionHeight()*size)/20, 1, 1, 0);
			x += character.getRegionWidth()*size/20 + GAP;
			y += 0;
		}
//		Graphics.setColor(Color.WHITE);
	}
	
	public int getWidth() {
		int width = -GAP;
		for (TextureRegion character : characters) {
			width += character.getRegionWidth()*size/20 + GAP;
		}
		return width;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public static int getGap() {
		return GAP;
	}
	
}
