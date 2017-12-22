package com.example.snake.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * ’œ∞≠ŒÔ
 * 
 * @author dengjifu
 * 
 */
public class Obstacle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient Bitmap obsBitmap;
	private int x, y;
	private List<Point[]> list = new ArrayList<Point[]>();
	private Point[] points1, points2, points3, points4, points5;

	private int level;

	public Obstacle(Bitmap obsBitmap) {
		super();
		this.obsBitmap = obsBitmap;
		this.x = (BackGround.xNum - 8) / 2;
		this.y = (BackGround.yNum - 8) / 2;
		initObstacle(0);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public Bitmap getObsBitmap() {
		return obsBitmap;
	}

	public List<Point[]> getList() {
		return list;
	}

	public void setList(List<Point[]> list) {
		this.list = list;
	}

	public void setObsBitmap(Bitmap obsBitmap) {
		this.obsBitmap = obsBitmap;
	}

	public void initObstacle(int l) {
		level = l;
		switch (l) {
		case 0:
			obsOne();
			break;
		case 1:
			obsTwo();
			break;
		case 2:
			obsThree();
			break;
		case 3:
			obsFour();
			break;
		case 4:
			obsFive();
			break;
		case 5:
			obsSix();
			break;
		case 6:
			obsSeven();
			break;
		case 7:
			obsEgiht();
			break;
		case 8:
			obsNine();
			break;
		case 9:
			obsTen();
			break;
		default:
			obsOne();
			break;
		}
	}

	private void obsTen() {
		list.clear();
		points1 = new Point[] { new Point(x, y), new Point(x + 1, y),
				new Point(x + 2, y), new Point(x, y + 1), new Point(x, y + 2) };
		points2 = new Point[] { new Point(x + 8, y), new Point(x + 7, y),
				new Point(x + 6, y), new Point(x + 8, y + 1),
				new Point(x + 8, y + 2) };
		points3 = new Point[] { new Point(x, y + 8), new Point(x, y + 7),
				new Point(x, y + 6), new Point(x + 1, y + 8),
				new Point(x + 2, y + 8) };
		points4 = new Point[] { new Point(x + 8, y + 8),
				new Point(x + 7, y + 8), new Point(x + 6, y + 8),
				new Point(x + 8, y + 7), new Point(x + 8, y + 6) };
		points5 = new Point[] { new Point(x + 3, y + 3),
				new Point(x + 3, y + 5), new Point(x + 5, y + 3),
				new Point(x + 5, y + 5), new Point(x + 2, y + 2),
				new Point(x + 2, y + 6), new Point(x + 6, y + 2),
				new Point(x + 6, y + 6), new Point(x, y + 4),
				new Point(x + 8, y + 4), new Point(x + 4, y),
				new Point(x + 4, y + 8) };
		list.add(points1);
		list.add(points2);
		list.add(points3);
		list.add(points4);
		list.add(points5);
	}

	private void obsNine() {
		list.clear();
		points1 = new Point[] { new Point(x + 2, y + 2), new Point(x, y),
				new Point(x + 2, y), new Point(x, y + 2) };
		points2 = new Point[] { new Point(x + 8, y), new Point(x + 6, y + 2),
				new Point(x + 8, y + 2), new Point(x + 6, y) };
		points3 = new Point[] { new Point(x, y + 8), new Point(x + 2, y + 6),
				new Point(x, y + 6), new Point(x + 2, y + 8) };
		points4 = new Point[] { new Point(x + 8, y + 8),
				new Point(x + 6, y + 6), new Point(x + 8, y + 6),
				new Point(x + 6, y + 8) };
		points5 = new Point[] { new Point(x + 3, y + 3),
				new Point(x + 3, y + 5), new Point(x + 5, y + 3),
				new Point(x + 5, y + 5) };
		list.add(points1);
		list.add(points2);
		list.add(points3);
		list.add(points4);
		list.add(points5);
	}

	private void obsEgiht() {
		list.clear();
		points1 = new Point[] { new Point(x + 2, y + 2),
				new Point(x + 1, y + 2), new Point(x + 2, y + 1),
				new Point(x + 2, y), new Point(x, y + 2), new Point(x, y),
				new Point(x + 3, y + 3) };
		points2 = new Point[] { new Point(x + 8, y + 2),
				new Point(x + 7, y + 2), new Point(x + 6, y + 2),
				new Point(x + 6, y + 1), new Point(x + 6, y),
				new Point(x + 8, y), new Point(x + 5, y + 3) };
		points3 = new Point[] { new Point(x, y + 6), new Point(x + 1, y + 6),
				new Point(x + 2, y + 6), new Point(x + 2, y + 7),
				new Point(x + 2, y + 8), new Point(x, y + 8),
				new Point(x + 3, y + 5) };
		points4 = new Point[] { new Point(x + 6, y + 6),
				new Point(x + 7, y + 6), new Point(x + 8, y + 6),
				new Point(x + 6, y + 7), new Point(x + 6, y + 8),
				new Point(x + 5, y + 5), new Point(x + 8, y + 8) };
		list.add(points1);
		list.add(points2);
		list.add(points3);
		list.add(points4);
	}

	private void obsSeven() {
		list.clear();
		points1 = new Point[] { new Point(x + 2, y + 2),
				new Point(x + 1, y + 2), new Point(x + 2, y + 1),
				new Point(x + 2, y), new Point(x, y + 2) };
		points2 = new Point[] { new Point(x + 8, y + 2),
				new Point(x + 7, y + 2), new Point(x + 6, y + 2),
				new Point(x + 6, y + 1), new Point(x + 6, y) };
		points3 = new Point[] { new Point(x, y + 6), new Point(x + 1, y + 6),
				new Point(x + 2, y + 6), new Point(x + 2, y + 7),
				new Point(x + 2, y + 8) };
		points4 = new Point[] { new Point(x + 6, y + 6),
				new Point(x + 7, y + 6), new Point(x + 8, y + 6),
				new Point(x + 6, y + 7), new Point(x + 6, y + 8) };
		list.add(points1);
		list.add(points2);
		list.add(points3);
		list.add(points4);
	}

	private void obsSix() {
		list.clear();
		points1 = new Point[] { new Point(x + 2, y + 2),
				new Point(x + 1, y + 1), new Point(x, y) };
		points2 = new Point[] { new Point(x + 8, y), new Point(x + 7, y + 1),
				new Point(x + 6, y + 2) };
		points3 = new Point[] { new Point(x, y + 8), new Point(x + 1, y + 7),
				new Point(x + 2, y + 6) };
		points4 = new Point[] { new Point(x + 8, y + 8),
				new Point(x + 7, y + 7), new Point(x + 6, y + 6) };
		points5 = new Point[] { new Point(x + 3, y + 3),
				new Point(x + 3, y + 5), new Point(x + 5, y + 3),
				new Point(x + 5, y + 5) };
		list.add(points1);
		list.add(points2);
		list.add(points3);
		list.add(points4);
		list.add(points5);
	}

	private void obsFive() {
		list.clear();
		points1 = new Point[] { new Point(x, y), new Point(x + 1, y),
				new Point(x + 2, y), new Point(x, y + 1), new Point(x, y + 2) };
		points2 = new Point[] { new Point(x + 8, y), new Point(x + 7, y),
				new Point(x + 6, y), new Point(x + 8, y + 1),
				new Point(x + 8, y + 2) };
		points3 = new Point[] { new Point(x, y + 8), new Point(x, y + 7),
				new Point(x, y + 6), new Point(x + 1, y + 8),
				new Point(x + 2, y + 8) };
		points4 = new Point[] { new Point(x + 8, y + 8),
				new Point(x + 7, y + 8), new Point(x + 6, y + 8),
				new Point(x + 8, y + 7), new Point(x + 8, y + 6) };
		points5 = new Point[] { new Point(x + 3, y + 3),
				new Point(x + 3, y + 5), new Point(x + 5, y + 3),
				new Point(x + 5, y + 5) };
		list.add(points1);
		list.add(points2);
		list.add(points3);
		list.add(points4);
		list.add(points5);
	}

	private void obsFour() {
		list.clear();
		points1 = new Point[] { new Point(x, y), new Point(x + 1, y),
				new Point(x + 2, y), new Point(x, y + 1), new Point(x, y + 2) };

		points2 = new Point[] { new Point(x + 8, y), new Point(x + 7, y),
				new Point(x + 6, y), new Point(x + 8, y + 1),
				new Point(x + 8, y + 2) };

		points3 = new Point[] { new Point(x, y + 8), new Point(x, y + 7),
				new Point(x, y + 6), new Point(x + 1, y + 8),
				new Point(x + 2, y + 8) };

		points4 = new Point[] { new Point(x + 8, y + 8),
				new Point(x + 7, y + 8), new Point(x + 6, y + 8),
				new Point(x + 8, y + 7), new Point(x + 8, y + 6) };
		list.add(points1);
		list.add(points2);
		list.add(points3);
		list.add(points4);
	}

	private void obsTwo() {
		list.clear();
		points1 = new Point[] { new Point(x, y), new Point(x + 1, y),
				new Point(x + 2, y), new Point(x, y + 1), new Point(x, y + 2) };
		points4 = new Point[] { new Point(x + 8, y + 8),
				new Point(x + 7, y + 8), new Point(x + 6, y + 8),
				new Point(x + 8, y + 7), new Point(x + 8, y + 6) };
		list.add(points1);
		list.add(points4);
	}

	private void obsThree() {
		list.clear();
		points1 = new Point[] { new Point(x, y), new Point(x + 1, y),
				new Point(x + 2, y) };

		points2 = new Point[] { new Point(x + 8, y), new Point(x + 7, y),
				new Point(x + 6, y) };

		points3 = new Point[] { new Point(x, y + 8), new Point(x + 1, y + 8),
				new Point(x + 2, y + 8) };

		points4 = new Point[] { new Point(x + 8, y + 8),
				new Point(x + 7, y + 8), new Point(x + 6, y + 8) };
		list.add(points1);
		list.add(points2);
		list.add(points3);
		list.add(points4);
	}

	private void obsOne() {
		list.clear();
		points2 = new Point[] { new Point(x + 8, y), new Point(x + 7, y),
				new Point(x + 6, y), new Point(x + 8, y + 1),
				new Point(x + 8, y + 2) };

		points3 = new Point[] { new Point(x, y + 8), new Point(x, y + 7),
				new Point(x, y + 6), new Point(x + 1, y + 8),
				new Point(x + 2, y + 8) };
		list.add(points2);
		list.add(points3);
	}

	public void draw(Canvas canvas) {
		Rect src = new Rect(0, 0, obsBitmap.getWidth(), obsBitmap.getHeight());
		Rect dst = null;
		Point[] points = null;
		for (int i = 0; i < list.size(); i++) {
			points = list.get(i);
			for (int j = 0; j < points.length; j++) {
				dst = new Rect(BackGround.SIZE * points[j].getX()
						+ BackGround.xOffSet, BackGround.SIZE
						* points[j].getY() + BackGround.yOffSet,
						BackGround.SIZE * points[j].getX() + BackGround.xOffSet
								+ BackGround.SIZE, BackGround.SIZE
								* points[j].getY() + BackGround.yOffSet
								+ BackGround.SIZE);
				canvas.drawBitmap(obsBitmap, src, dst, null);
			}
		}
	}

	public void setData(Obstacle objobstacle) {
		x = objobstacle.getX();
		y = objobstacle.getY();
		list = objobstacle.getList();
		level = objobstacle.getLevel();
	}

	public void clearCache() {
		initObstacle(0);
	}

}
