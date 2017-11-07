package com.example.snake.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class LevelUp {

	private transient Bitmap levelUpBitmap;
	private int x;
	private int y;

	public Bitmap getLevelUpBitmap() {
		return levelUpBitmap;
	}

	public void setLevelUpBitmap(Bitmap levelUpBitmap) {
		this.levelUpBitmap = levelUpBitmap;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public LevelUp(Bitmap levelUpBitmap) {
		super();
		this.levelUpBitmap = levelUpBitmap;
		this.x = BackGround.xNum / 2 + 1;
		this.y = BackGround.yNum / 2 + 1;
	}

	public void draw(Canvas canvas) {
		Rect src = new Rect(0, 0, levelUpBitmap.getWidth(),
				levelUpBitmap.getHeight());
		Rect dst = new Rect(x * BackGround.SIZE - levelUpBitmap.getWidth() / 2,
				y * BackGround.SIZE - levelUpBitmap.getHeight() / 2, x
						* BackGround.SIZE + levelUpBitmap.getWidth() / 2, y
						* BackGround.SIZE + levelUpBitmap.getHeight() / 2);
		canvas.drawBitmap(levelUpBitmap, src, dst, null);
	}

}
