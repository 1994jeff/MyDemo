package com.example.snake.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.snake.utils.IClearCache;
import com.example.snake.view.SnakeView;

/**
 * Ê³Îï
 * @author dengjifu
 *
 */
public class Food implements IClearCache,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  List<Point> listFood = new ArrayList<Point>();
	private transient Bitmap foodBitmap;
	private transient Rect src;
	private Snake snake;
	private Obstacle obstacle;
	public List<Point> getListFood() {
		return listFood;
	}
	public void setListFood(List<Point> listFood) {
		this.listFood = listFood;
	}
	public Food(Bitmap foodBitmap,Snake snake, Obstacle obstacle) {
		super();
		this.obstacle = obstacle;
		this.snake = snake;
		this.foodBitmap = foodBitmap;
		src = new Rect(0,0,foodBitmap.getWidth(),foodBitmap.getHeight());
		getRandomXY();
	}
	//get food(x,y)
	public void getRandomXY() {
		List<Point> list = snake.getPosition();
		Random random = new Random();
		boolean isRepeat = true;
		int x = 0 ;
		int y = 0 ;
		while(isRepeat){
			x = random.nextInt(BackGround.xNum);
			y = random.nextInt(BackGround.yNum);
			//is repeat with snake and obstacle
			boolean repeat = false;
			for(Point point:list){
				if(point.getX()==x && point.getY()==y){
					repeat = true;
				}
			}
			Point[] obspoints = null;
			List<Point[]> obslist = obstacle.getList();
			for (int i = 0; i < obslist.size(); i++) {
				obspoints = obslist.get(i);
				for (int j = 0; j < obspoints.length; j++) {
					if(obspoints[j].getX()==x 
						&& obspoints[j].getY()==y){
						repeat = true;
					}
				}
			}
			//stile to while loop
			if(repeat){
				isRepeat = true;
			}else {
				isRepeat = false;
			}
		}
		listFood.clear();
		Point object = new Point(x, y);
		listFood.add(object);
	}
	public Bitmap getFoodBitmap() {
		return foodBitmap;
	}
	public void setFoodBitmap(Bitmap foodBitmap) {
		this.foodBitmap = foodBitmap;
	}
	
	public void draw(Canvas canvas) {
		for(Point point:listFood){
			Rect dst = new Rect(BackGround.xOffSet + BackGround.SIZE*point.getX(),BackGround.yOffSet + BackGround.SIZE*point.getY(),
					BackGround.xOffSet + BackGround.SIZE*point.getX()+BackGround.SIZE,BackGround.yOffSet + BackGround.SIZE*point.getY()+BackGround.SIZE);
			canvas.drawBitmap(foodBitmap, src, dst, null);
		}
	}
	public void createNew(int arg) {
		listFood.clear();
		getRandomXY();
	}
	
	public Snake getSnake() {
		return snake;
	}
	public void setSnake(Snake snake) {
		this.snake = snake;
	}
	public Obstacle getObstacle() {
		return obstacle;
	}
	public void setObstacle(Obstacle obstacle) {
		this.obstacle = obstacle;
	}
	@Override
	public void clearCache() {
		if(listFood.size()>0)
		listFood.clear();
	}
	public void setData(Food objFood) {
		listFood = objFood.getListFood();
		snake = objFood.getSnake();
		obstacle = objFood.getObstacle();
	}
	
	
}
