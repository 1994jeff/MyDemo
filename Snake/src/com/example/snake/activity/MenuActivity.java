package com.example.snake.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snake.R;
import com.example.snake.bean.Snake;
import com.example.snake.database.GameDataBase;
import com.example.snake.utils.LogUtils;
import com.example.snake.utils.SoundPoolUtils;
import com.example.snake.view.SnakeView;

/**
 * @author dengjifu
 * @date 20171027
 */
public class MenuActivity extends BaseActivity implements OnClickListener{

	private GameDataBase dbHelper;
	private SQLiteDatabase db;
	private Button startImageView, continueImageView, levelImageView;
	private TextView opereaTextView, backTextView;

	// sound is closed
	private static final int sound_close = 0;
	// sound is opening
	private static final int sound_open = 1;
	private int soundState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		dbHelper = new GameDataBase(this, "game.db", null, 1);
		initView();
		getStateFromSharedPreference();
		setOnClick();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (soundState == sound_open) {
			menu.getItem(1).setTitle(getString(R.string.closeSound));
		} else {
			menu.getItem(1).setTitle(getString(R.string.openSound));
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.openRank:
			startActivity(new Intent(MenuActivity.this, ScoreRankActivity.class));
			break;
		case R.id.closeSound:
			if (soundState == sound_close) {
				soundState = sound_open;
				SoundPoolUtils.isShowMusic = true;
			} else if (soundState == sound_open) {
				soundState = sound_close;
				SoundPoolUtils.isShowMusic = false;
			}
			saveGameStateToshareprefernce();
			break;
		case R.id.clearRankData:
			db = dbHelper.getWritableDatabase();
			db.delete("score", null, null);
			db.close();
			dbHelper.close();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void getStateFromSharedPreference() {
		SharedPreferences preferences = getSharedPreferences("levelint",
				MODE_PRIVATE);
		int i = preferences.getInt("levelint", 0);
		soundState = preferences.getInt("soundState", sound_open);
		SnakeView.speed = SnakeView.speedsArray[i];
		if (soundState == sound_close) {
			SoundPoolUtils.isShowMusic = false;
		} else {
			SoundPoolUtils.isShowMusic = true;
		}
	}

	private void setOnClick() {
		startImageView.setOnClickListener(this);
		continueImageView.setOnClickListener(this);
		levelImageView.setOnClickListener(this);
		opereaTextView.setOnClickListener(this);
		backTextView.setOnClickListener(this);
	}

	private void initView() {
		startImageView = (Button) findViewById(R.id.startGame);
		continueImageView = (Button) findViewById(R.id.continueGame);
		levelImageView = (Button) findViewById(R.id.level);
		opereaTextView = (TextView) findViewById(R.id.operation);
		backTextView = (TextView) findViewById(R.id.back);
		
	}

	@Override
	public void onBackPressed() {
		MenuActivity.this.finish();
		System.gc();
		System.exit(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.startGame:
			SnakeView.gameState = SnakeView.GAMEING;
			SnakeView.gameStore = SnakeView.NEW_GAME;
			SnakeView.endGame = false;
			SnakeView.sleepThread = false;
			saveGameStateToshareprefernce();
			startActivity(new Intent(MenuActivity.this, MainActivity.class));
			break;
		case R.id.continueGame:
			SnakeView.gameState = SnakeView.GAMEING;
			SnakeView.gameStore = SnakeView.OLD_GAME;
			SnakeView.sleepThread = false;
			SnakeView.endGame = false;
			saveGameStateToshareprefernce();
			startActivity(new Intent(MenuActivity.this, MainActivity.class));
			break;
		case R.id.level:
			toSettingLevel();
			break;
		case R.id.operation:
			break;
		case R.id.back:
			MenuActivity.this.finish();
			System.gc();
			System.exit(0);
			break;
		default:
			break;
		}
	}

	private void showPopMenu() {
	}

	private void toSettingLevel() {
		SharedPreferences preferences = getSharedPreferences("levelint",
				MODE_PRIVATE);
		int gameState = preferences.getInt("gameState", SnakeView.GAME_OVER);
		if (gameState != SnakeView.GAMEING) {
			startActivity(new Intent(MenuActivity.this,
					ChooseLevelActivity.class));
		} else {
			Toast.makeText(MenuActivity.this,
					getString(R.string.open_level_error), Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void saveGameStateToshareprefernce() {
		SharedPreferences.Editor edit = getSharedPreferences("levelint",
				MODE_PRIVATE).edit();
		edit.putInt("gameState", SnakeView.gameState);
		edit.putInt("soundState", soundState);
		edit.apply();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
			return true;
		case KeyEvent.KEYCODE_DPAD_UP:
			return false;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			return true;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
