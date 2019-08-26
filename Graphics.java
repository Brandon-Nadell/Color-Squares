package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Graphics {
	
	public static int windowWidth;
	public static int windowHeight;
	private static int actualWidth;
	private static int actualHeight;
	private static SpriteBatch batch = new SpriteBatch();
	
	
	public static void setup() {
		actualWidth = 480;
		actualHeight = 852;
		windowWidth = Gdx.graphics.getWidth(); //coords specified in config (modified if fullscreen in config)
		windowHeight = Gdx.graphics.getHeight();
	}
	
	public static int getU() {
		return Gdx.input.getX();
	}
	
	public static int getV() {
		return windowHeight - Gdx.input.getY();
	}
	
	public static int getX() {
		return (int)(getU()/(60*ratioX()));
	}
	
	public static int getY() {
		return (int)((getV() - 30)/(60*ratioY()));
	}
	
	public static float ratioX() {
		return (float)windowWidth/actualWidth;
	}
	
	public static float ratioY() {
		return (float)windowHeight/actualHeight;
	}
	
	
	public static void begin() {
		batch.begin();
	}
	
	public static void end() {
		batch.end();
	}
	
	public static void setColor(Color color) {
		batch.setColor(color);
	}
	
	public static void setColor(float r, float g, float b, float a) {
		batch.setColor(r, g, b, a);
	}

	public static void draw(Texture texture, float x, float y, boolean shift) {
		batch.draw(texture, (x + (shift ? Colorsquares.board.shift.computeX() : 0))*ratioX(), y*ratioY(), 0, 0, texture.getWidth(), texture.getHeight(), ratioX(), ratioY(), 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
	}

	public static void draw(TextureRegion region, int x, int y, int originX, int originY, int width, int height, int scaleX, int scaleY, int rotation) {
		batch.draw(region, x*ratioX(), y*ratioY(), originX, originY, width, height, ratioX(), ratioY(), rotation);
	}

	
}
