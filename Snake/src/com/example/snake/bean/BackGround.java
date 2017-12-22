package com.example.snake.bean;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * 背景
 * 
 * @author dengjifu
 * 
 */
public class BackGround implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int SIZE;
	private transient Bitmap bgBitmap;
	public static int xNum;
	public static int yNum;
	private int x;
	private int y;
	//x方向偏移值
	public static int xOffSet;
	//y方向偏移值
	public static int yOffSet;
	private transient Rect src;
	public BackGround(Bitmap bgBitmap, int width, int height) {
		super();
		this.bgBitmap = bgBitmap;
		setSizeNum(width,height);
		initBgParam(width,height);
	}

	private void setSizeNum(int width, int height) {
		if(width>=960){
			SIZE=50;
		}else if(width>=768){
			SIZE=40;
		}else if(width>=600){
			SIZE=30;
		}else if(width>=480){
			SIZE=26;
		}else if(width>=320){
			SIZE=20;
		}else{
			SIZE=18;
		}
	}

	private void initBgParam(int width, int height) {
		int xnums = width/SIZE;
		if((xnums%2)==0){
			//是双数
			xNum=xnums-1;
			xOffSet = ((width%SIZE)+SIZE)/2;
		}else {
			//不是双数
			xNum = width/SIZE;
			xOffSet = (width%SIZE)/2;
		}
		yNum = height/SIZE;
		yOffSet = (height%SIZE)/2;
		initPoint();
		src = new Rect(0,0,bgBitmap.getWidth(),bgBitmap.getHeight());		
	}

	public Bitmap getBgBitmap() {
		return bgBitmap;
	}

	public void setBgBitmap(Bitmap bgBitmap) {
		this.bgBitmap = bgBitmap;
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

	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		//draw the background bitmap
		for(int i=0;i<xNum;i++)
		{
			this.x = xOffSet + SIZE*i;
			this.y = yOffSet;
			for(int j=0;j<yNum;j++)
			{
				this.y = yOffSet + SIZE * j;
				Rect dst = new Rect(x, y, x+BackGround.SIZE, y+BackGround.SIZE);
				canvas.drawBitmap(bgBitmap, src, dst, null);
			}
		}
	}

	public void initPoint() {
		this.x = xOffSet;
		this.y = yOffSet;
	}
	
}
