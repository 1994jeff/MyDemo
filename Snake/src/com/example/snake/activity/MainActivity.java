package com.example.snake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.example.snake.bean.Snake;
import com.example.snake.utils.LogUtils;
import com.example.snake.utils.OnGameOverListener;
import com.example.snake.view.SnakeView;
/**
 * @author dengjifu
 * @date 20171027
 */
public class MainActivity extends BaseActivity implements OnGameOverListener{

	SnakeView snakeView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; //width px
		int height = metric.heightPixels; // height px

		snakeView = new SnakeView(this, width, height);
		setContentView(snakeView);
		snakeView.setClickable(false);
		snakeView.setOnGameOverListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(!SnakeView.isLevelUpOk){
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if(snakeView.getDirection()!=Snake.RIGHT && !Snake.isDirectionPressed){
					snakeView.setDirection(Snake.LEFT);
					Snake.isDirectionPressed = true;
				}
				return true;
			case KeyEvent.KEYCODE_DPAD_UP:
				if(snakeView.getDirection()!=Snake.BOTTOM && !Snake.isDirectionPressed){
					snakeView.setDirection(Snake.TOP);
					Snake.isDirectionPressed = true;
				}
				return true;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if(snakeView.getDirection()!=Snake.LEFT && !Snake.isDirectionPressed){
					snakeView.setDirection(Snake.RIGHT);
					Snake.isDirectionPressed = true;
				}
				return true;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				if(snakeView.getDirection()!=Snake.TOP && !Snake.isDirectionPressed){
					snakeView.setDirection(Snake.BOTTOM);
					Snake.isDirectionPressed = true;
				}
				return true;
			case KeyEvent.KEYCODE_BACK:
				onBackPressed();
				return true;
			}
		}else{
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onBackPressed() {
		if(snakeView.isLevelUp || SnakeView.isLevelUpOk){
			try {
				LogUtils.d("dengjifu", "-----Thread.sleep(2000)----------"+System.currentTimeMillis());
				Thread.sleep(2000);
				LogUtils.d("dengjifu", "-----Thread.sleep(2000)----------"+System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		snakeView.saveSharePre();
		snakeView.saveGameData();
		SnakeView.endGame = true;
		LogUtils.d("dengjifu", "-----onBackPressed----------");
		MainActivity.this.finish();
	}

	@Override
	public void afterGameOver(int game,int score) {
		Intent intent = new Intent(this, DialogActivity.class);
		intent.putExtra("game", game);
		intent.putExtra("score", score);
		startActivity(intent);
		MainActivity.this.finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onStop() {
		if(snakeView.isLevelUp || SnakeView.isLevelUpOk){
			try {
				LogUtils.d("dengjifu", "-----Thread.sleep(2000)----------"+System.currentTimeMillis());
				Thread.sleep(2000);
				LogUtils.d("dengjifu", "-----Thread.sleep(2000)----------"+System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		SnakeView.sleepThread = true;
		snakeView.saveGameData();
		LogUtils.d("dengjifu", "-------onStop--------");
		super.onStop();
	}
	
	@Override
	protected void onRestart() {
		LogUtils.d("saveObj", "-------onRestart--------");
		SnakeView.sleepThread = false;
		super.onRestart();
	}

}
