package com.example.snake.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.snake.R;
import com.example.snake.bean.BackGround;
import com.example.snake.bean.Food;
import com.example.snake.bean.LevelUp;
import com.example.snake.bean.Obstacle;
import com.example.snake.bean.Snake;
import com.example.snake.utils.LogUtils;
import com.example.snake.utils.ObjectOutputUtils;
import com.example.snake.utils.OnGameOverListener;

/**
 * @author dengjifu
 * @date 20171027
 */
public class SnakeView extends View implements Runnable {

	/*******************************************/
	private Context context;
	private BackGround backGround;
	private Snake snake;
	private Obstacle obstacle;
	private Food food;
	private LevelUp levelUp;

	private Bitmap bgBitmap, snakeBitmap, obstacleBitmap, foodBitmap,
			snakeHeadBitmap, levelUpBitmap;

	private int width, height;
	// game state
	public static final int GAMEING = 1;
	public static final int GAME_OVER = 2;
	public static final int GAME_PAUSE = 3;
	public static int gameState = GAMEING;
	// game is a new game or continue game
	public static final int NEW_GAME = 1;
	public static final int OLD_GAME = 2;
	public static int gameStore = NEW_GAME;

	private Thread gameThread;
	// when game over then let the Thread run end
	public static boolean endGame = false;
	// when level up then Thread sleep 2000ms
	public volatile static boolean isLevelUp = false;
	// after level up first ondraw, stils to wait 2000ms
	public volatile static boolean isLevelUpOk = false;

	// when the application was in the background and the MainActivity is go
	// into onStop() method
	// then let the Thread sleep, until it back to front;
	public static volatile boolean sleepThread = false;
	// when the game is over,to execute the interface method and pass the length
	// of snake to calculate score
	private OnGameOverListener onGameOverListener;
	// game speed array
	public static long[] speedsArray = new long[] { 350, 300, 250, 220, 200,
			180, 150 };
	// to set the game speed
	public static long speed = speedsArray[0];

	/*******************************************/

	public void setOnGameOverListener(OnGameOverListener onGameOverListener) {
		this.onGameOverListener = onGameOverListener;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		SnakeView.speed = speed;
	}

	public SnakeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SnakeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SnakeView(Context context, int width, int height) {
		super(context);
		this.context = context;
		this.width = width;
		this.height = height;
		initGame();
		gameThread = new Thread(this);
		gameThread.start();
	}

	private void initGame() {
		initBitMap();
		initBean();
	}

	private void initBean() {
		backGround = new BackGround(bgBitmap, width, height);
		levelUp = new LevelUp(levelUpBitmap);
		obstacle = new Obstacle(obstacleBitmap);
		snake = new Snake(snakeBitmap, snakeHeadBitmap, getContext());
		food = new Food(foodBitmap, snake, obstacle);
		// if continue game then load the data
		if (gameStore == OLD_GAME) {
			getGameData();
		}
		snake.setObstacle(obstacle);
		snake.setFood(food);
	}

	private void initBitMap() {
		bgBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.block00);
		snakeBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.block4);
		snakeHeadBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.block5);
		foodBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.block6);
		obstacleBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.block0);
		levelUpBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.level_up);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		backGround.draw(canvas);
		obstacle.draw(canvas);
		food.draw(canvas);
		snake.draw(canvas);
		if (isLevelUp) {
			levelUp.draw(canvas);
		}
	}

	// change the game logic and invalidate the game view(snakeview)
	@Override
	public void run() {
		while (true && !endGame) {
			try {
				// to sleep by run while loop when the application was in
				// background
				while (sleepThread) {
					Thread.sleep(2000);
				}
				if (isLevelUpOk) {
					isLevelUpOk = false;
				}
				// to sleep 2000ms before level up view change
				if (isLevelUp) {
					LogUtils.d("dengjifu", "init level up data");
					Thread.sleep(2000);
					isLevelUpOk = true;
					snake.initSnake();
					obstacle.initObstacle(snake.getLastLength()
							/ Snake.levelNum);
					// after level up food should be create by here,init after
					// obstacle and snake
					food.getRandomXY();
					isLevelUp = false;
				}
				long start = System.currentTimeMillis();
				if (gameState == GAMEING) {
					if (!isLevelUpOk) {
						snake.move();
					}
					postInvalidate();
				} else if (gameState == GAME_OVER) {
					backGround.initPoint();
					if (onGameOverListener != null) {
						onGameOverListener.afterGameOver(GAME_OVER,
								calculScore());
					}
					clearCache();
					clearGameData();
					saveToshareprefernce("levelint", "gameState",
							SnakeView.gameState);
					endGame = true;
				}
				long end = System.currentTimeMillis();
				long time = end - start;
				// thread speed time
				if (time < speed) {
					Thread.sleep(speed - time+50);
				} else {
					// postinvalidate isn't use,beacause thread not to sleep,then the second postinvalidate is come
					Thread.sleep(50);
				}
			} catch (InterruptedException e) {
				LogUtils.d("thread",
						"exception=" + Thread.currentThread().getName()
								+ "----" + e.getMessage());
			}
		}
	}

	public void saveToshareprefernce(String preferName, String key, int value) {
		SharedPreferences.Editor edit = context.getSharedPreferences(
				preferName, Context.MODE_PRIVATE).edit();
		edit.putInt(key, value);
		edit.apply();
	}

	// calculate score
	private int calculScore() {
		int eatLength = snake.getLength() + snake.getLastLength();
		int level = obstacle.getLevel();
		int Score = (int) (level * 100 + eatLength * 20 * ((400 - speed) / 100));
		return Score;
	}

	private void clearCache() {
		food.clearCache();
		snake.clearCache();
		obstacle.clearCache();
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
	}

	// save the game data
	public void saveGameData() {
//		saveSharePre();
		LogUtils.d("saveObj", "--------saveGameData----");
		ObjectOutputUtils objectOutputUtils = new ObjectOutputUtils(context);
		try {
			objectOutputUtils.outPutObj(food, "food");
			objectOutputUtils.outPutObj(snake, "snake");
			objectOutputUtils.outPutObj(obstacle, "obstacle");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LogUtils.d("saveObj", "--saveGameData-e---" + e.getMessage());
		} catch (NotFoundException e) {
			e.printStackTrace();
			LogUtils.d("saveObj", "--saveGameData-e---" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			LogUtils.d("saveObj", "--saveGameData-e---" + e.getMessage());
		}
	}

	// read game data
	public void getGameData() {
		LogUtils.d("dengjifu", "--------getGameData----");
		ObjectOutputUtils objectOutputUtils = new ObjectOutputUtils(context);
		try {
			Food objFood = (Food) objectOutputUtils.inPutObj("food");
			Snake objSnake = (Snake) objectOutputUtils.inPutObj("snake");
			Obstacle objobstacle = (Obstacle) objectOutputUtils
					.inPutObj("obstacle");
			if (objFood != null && objFood.getListFood() != null
					&& objFood.getListFood().size() > 0) {
				food.setData(objFood);
			}
			if (objSnake != null && objSnake.getPosition() != null
					&& objSnake.getPosition().size() > 0) {
				snake.setData(objSnake);
			}
			if (objobstacle != null && objobstacle.getList() != null
					&& objobstacle.getList().size() > 0) {
				obstacle.setData(objobstacle);
			}
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
			LogUtils.d("saveObj", "--getGameData-e---" + e.getMessage());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LogUtils.d("saveObj", "--getGameData-e---" + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			LogUtils.d("saveObj", "--getGameData-e---" + e.getMessage());
		} catch (NotFoundException e) {
			e.printStackTrace();
			LogUtils.d("saveObj", "--getGameData-e---" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			LogUtils.d("saveObj", "--getGameData-e---" + e.getMessage());
		}
	}

	// clear data
	public void clearGameData() {
		LogUtils.d("saveObj", "--------clearGameData----");
		ObjectOutputUtils objectOutputUtils = new ObjectOutputUtils(context);
		try {
			objectOutputUtils.outPutObj(null, "food");
			objectOutputUtils.outPutObj(null, "snake");
			objectOutputUtils.outPutObj(null, "obstacle");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LogUtils.d("saveObj", "--clearGameData-e---" + e.getMessage());
		} catch (NotFoundException e) {
			LogUtils.d("saveObj", "--clearGameData-e---" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LogUtils.d("saveObj", "--clearGameData-e---" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void setDirection(int direction) {
		snake.setDIRECTION(direction);
	}

	public int getDirection() {
		return snake.getDIRECTION();
	}

	public void saveSharePre() {
		SharedPreferences.Editor edit = context.getSharedPreferences("levelup",
				Context.MODE_PRIVATE).edit();
		edit.putBoolean("isLevelUp", isLevelUp);
		edit.putBoolean("isLevelUpOk", isLevelUpOk);
		edit.apply();
	}

}
