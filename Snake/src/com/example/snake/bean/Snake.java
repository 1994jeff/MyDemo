package com.example.snake.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.SoundPool;
import android.util.Log;

import com.example.snake.utils.IClearCache;
import com.example.snake.utils.LogUtils;
import com.example.snake.utils.SoundPoolUtils;
import com.example.snake.view.SnakeView;

/**
 * 蛇
 * 
 * @author dengjifu
 * 
 */
public class Snake implements IClearCache, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 总长度
	private int length;
	// 上次长度
	private int lastLength;
	public static final int levelNum = 3;

	public static final int TOP = 1;
	public static final int BOTTOM = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public int DIRECTION = TOP;

	private List<Point> position;
	private Food food;
	private Obstacle obstacle;

	private transient Bitmap snakeBitmap;
	private transient Bitmap snakeHeadBitmap;
	public static boolean isDirectionPressed = false;

	private transient SoundPool sp;
	private transient Context context;

	public Obstacle getObstacle() {
		return obstacle;
	}

	public void setObstacle(Obstacle obstacle) {
		this.obstacle = obstacle;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public int getDIRECTION() {
		return DIRECTION;
	}

	public void setDIRECTION(int dIRECTION) {
		DIRECTION = dIRECTION;
	}

	public Snake(Bitmap snakeBitmap, Bitmap snakeHeadBitmap, Context context) {
		super();
		this.snakeBitmap = snakeBitmap;
		this.snakeHeadBitmap = snakeHeadBitmap;
		this.context = context;
		this.sp = SoundPoolUtils.getInstance(context);
		initSnake();
	}

	public void initSnake() {
		length = 0;
		position = new ArrayList<Point>();
		DIRECTION = TOP;
		Point point = null;
		for (int i = 0; i < 4; i++) {
			point = new Point((BackGround.xNum / 2), (BackGround.yNum / 2) + i
					+ 1);
			position.add(point);
		}
	}

	public List<Point> getPosition() {
		return position;
	}

	public void setPosition(List<Point> position) {
		this.position = position;
	}

	public int getLastLength() {
		return lastLength;
	}

	public void setLastLength(int lastLength) {
		this.lastLength = lastLength;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Bitmap getSnakeBitmap() {
		return snakeBitmap;
	}

	public void setSnakeBitmap(Bitmap snakeBitmap) {
		this.snakeBitmap = snakeBitmap;
	}

	public static int getTop() {
		return TOP;
	}

	public static int getBottom() {
		return BOTTOM;
	}

	public static int getLeft() {
		return LEFT;
	}

	public static int getRight() {
		return RIGHT;
	}

	public void draw(Canvas canvas) {
		Rect rectBitmapRect = new Rect(0, 0, snakeBitmap.getWidth(),
				snakeBitmap.getHeight());
		Rect rectShowRect;
		for (int i = 0; i < position.size(); i++) {
			int x = position.get(i).getX() * BackGround.SIZE
					+ BackGround.xOffSet;
			int y = position.get(i).getY() * BackGround.SIZE
					+ BackGround.yOffSet;
			rectShowRect = new Rect(x, y, x + BackGround.SIZE, y
					+ BackGround.SIZE);
			if (i == 0) {
				canvas.drawBitmap(snakeHeadBitmap, rectBitmapRect,
						rectShowRect, null);
			} else {
				canvas.drawBitmap(snakeBitmap, rectBitmapRect, rectShowRect,
						null);
			}
		}
	}

	public void goTop() {
		if (canMove(TOP)) {
			Point headPoint = position.get(0);
			Point newPoint = new Point(headPoint.getX(), headPoint.getY() - 1);
			position.add(0, newPoint);
			boolean isEat = false;
			int arg = 0;
			List<Point> list = food.getListFood();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getX() == newPoint.getX()
						&& list.get(i).getY() == newPoint.getY()) {
					isEat = true;
					arg = i;
				}
			}
			if (!isEat) {
				position.remove(position.size() - 1);
			} else {
				toNextLevel(arg);
			}

		} else {
			snakeDead();
		}
	}

	private void snakeDead() {
		SoundPoolUtils.playMusic(sp, 3, context);
		SnakeView.gameState = SnakeView.GAME_OVER;
	}

	private void toNextLevel(int arg) {
		length++;
		SoundPoolUtils.playMusic(sp, 1, context);
		// if level up,food was init by thread
		if (length == levelNum && lastLength < 9 * levelNum) {
			lastLength += levelNum;
			SnakeView.isLevelUp = true;
			LogUtils.d("dengjifu", "isLevelUp= " + true);
			SoundPoolUtils.playMusic(sp, 2, context);
		} else {
			food.createNew(arg);
		}
	}

	public void goBottom() {
		if (canMove(BOTTOM)) {
			Point headPoint = position.get(0);
			Point newPoint = new Point(headPoint.getX(), headPoint.getY() + 1);
			position.add(0, newPoint);
			boolean isEat = false;
			int arg = 0;
			List<Point> list = food.getListFood();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getX() == newPoint.getX()
						&& list.get(i).getY() == newPoint.getY()) {
					isEat = true;
					arg = i;
				}
			}
			if (!isEat) {
				position.remove(position.size() - 1);
			} else {
				toNextLevel(arg);
			}

		} else {
			LogUtils.d("dead", "--goBottom--");
			snakeDead();
		}
	}

	public void goLeft() {
		if (canMove(LEFT)) {
			Point headPoint = position.get(0);
			Point newPoint = new Point(headPoint.getX() - 1, headPoint.getY());
			position.add(0, newPoint);
			boolean isEat = false;
			int arg = 0;
			List<Point> list = food.getListFood();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getX() == newPoint.getX()
						&& list.get(i).getY() == newPoint.getY()) {
					isEat = true;
					arg = i;
				}
			}
			if (!isEat) {
				position.remove(position.size() - 1);
			} else {
				toNextLevel(arg);
			}

		} else {
			LogUtils.d("dead", "--goLeft--");
			snakeDead();
		}
	}

	public void goRight() {
		if (canMove(RIGHT)) {
			Point headPoint = position.get(0);
			Point newPoint = new Point(headPoint.getX() + 1, headPoint.getY());
			position.add(0, newPoint);
			boolean isEat = false;
			int arg = 0;
			List<Point> list = food.getListFood();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getX() == newPoint.getX()
						&& list.get(i).getY() == newPoint.getY()) {
					isEat = true;
					arg = i;
				}
			}
			if (!isEat) {
				position.remove(position.size() - 1);
			} else {
				toNextLevel(arg);
			}

		} else {
			LogUtils.d("dead", "--goRight--");
			snakeDead();
		}
	}

	private boolean canMove(int direction) {
		boolean flag = true;
		Point headPoint = position.get(0);
		Point[] points = null;
		List<Point[]> list = obstacle.getList();
		switch (direction) {
		case TOP:
			if (position.get(0).getY() == 0) {
				flag = false;
			}
			for (Point point : position) {
				if (point.getY() == (headPoint.getY() - 1)
						&& point.getX() == headPoint.getX())
					flag = false;
			}
			for (int i = 0; i < list.size(); i++) {
				points = list.get(i);
				for (int j = 0; j < points.length; j++) {
					if (points[j].getX() == headPoint.getX()
							&& points[j].getY() == (headPoint.getY() - 1)) {
						flag = false;
					}
				}
			}
			break;
		case BOTTOM:
			if (position.get(0).getY() == BackGround.yNum - 1) {
				flag = false;
			}
			for (Point point : position) {
				if (point.getY() == (headPoint.getY() + 1)
						&& point.getX() == headPoint.getX())
					flag = false;
			}
			for (int i = 0; i < list.size(); i++) {
				points = list.get(i);
				for (int j = 0; j < points.length; j++) {
					if (points[j].getX() == headPoint.getX()
							&& points[j].getY() == (headPoint.getY() + 1)) {
						flag = false;
					}
				}
			}
			break;
		case LEFT:
			if (position.get(0).getX() == 0) {
				flag = false;
			}
			for (Point point : position) {
				if (point.getX() == (headPoint.getX() - 1)
						&& point.getY() == headPoint.getY())
					flag = false;
			}
			for (int i = 0; i < list.size(); i++) {
				points = list.get(i);
				for (int j = 0; j < points.length; j++) {
					if (points[j].getX() == (headPoint.getX() - 1)
							&& points[j].getY() == (headPoint.getY())) {
						flag = false;
					}
				}
			}
			break;
		case RIGHT:
			if (position.get(0).getX() == BackGround.xNum - 1) {
				flag = false;
			}
			for (Point point : position) {
				if (point.getX() == (headPoint.getX() + 1)
						&& point.getY() == headPoint.getY())
					flag = false;
			}
			for (int i = 0; i < list.size(); i++) {
				points = list.get(i);
				for (int j = 0; j < points.length; j++) {
					if (points[j].getX() == (headPoint.getX() + 1)
							&& points[j].getY() == (headPoint.getY())) {
						flag = false;
					}
				}
			}
			break;
		default:
			break;
		}
		return flag;
	}

	public void move() {
		switch (DIRECTION) {
		case TOP:
			goTop();
			break;
		case BOTTOM:
			goBottom();
			break;
		case LEFT:
			goLeft();
			break;
		case RIGHT:
			goRight();
			break;
		default:
			break;
		}
		isDirectionPressed = false;
	}

	@Override
	public void clearCache() {
		position.clear();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setData(Snake objSnake) {
		DIRECTION = objSnake.DIRECTION;
		position = objSnake.getPosition();
		length = objSnake.getLength();
		lastLength = objSnake.getLastLength();
	}
}
