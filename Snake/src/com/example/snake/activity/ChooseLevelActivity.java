package com.example.snake.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snake.R;
import com.example.snake.view.SnakeView;
/**
 * @author dengjifu
 * @date 20171027
 */
public class ChooseLevelActivity extends BaseActivity implements
		OnClickListener {

	private ImageView leftImage, rightImage;
	private TextView levelText;
	private RelativeLayout backRelative;
	
	String[] level = new String[] { "A", "B", "C", "D", "E", "F", "G" };

	private int currentItem = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_level);

		backRelative = (RelativeLayout) findViewById(R.id.backRelative);
		leftImage = (ImageView) findViewById(R.id.leftImage);
		rightImage = (ImageView) findViewById(R.id.rightImage);
		levelText = (TextView) findViewById(R.id.levelText);

		SharedPreferences preferences = getSharedPreferences("levelint",MODE_PRIVATE);
		currentItem = preferences.getInt("levelint", 0);
		SnakeView.speed = SnakeView.speedsArray[currentItem];
		levelText.setText(level[currentItem]);

		leftImage.setClickable(true);
		rightImage.setClickable(true);
		leftImage.setOnClickListener(this);
		rightImage.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leftImage:
			setLevelText(1);
			break;
		case R.id.rightImage:
			setLevelText(2);
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i("dengjifu", keyCode+"");
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
			setLevelText(1);
			return true;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			setLevelText(2);
			return true;
		default:
			onBackPressed();
			return true;
		}
	}

	@Override
	public void onBackPressed() {
		backRelative.setVisibility(View.GONE);
		ChooseLevelActivity.this.finish();
	}
	
	private void setLevelText(int i) {
		if(i==1){
			if(currentItem>0){
				currentItem--;
			}
		}else if(i==2){
			if(currentItem<level.length-1){
				currentItem++;
			}
		}
		levelText.setText(level[currentItem]);
		SharedPreferences.Editor edit = getSharedPreferences("levelint", MODE_PRIVATE).edit();
		edit.putInt("levelint", currentItem);
		edit.apply();
		SnakeView.speed = SnakeView.speedsArray[currentItem];
	}
}
