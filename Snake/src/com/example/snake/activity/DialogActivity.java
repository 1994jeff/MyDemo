package com.example.snake.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snake.R;
import com.example.snake.database.GameDataBase;
import com.example.snake.view.SnakeView;
/**
 * @author dengjifu
 * @date 20171027
 */
public class DialogActivity extends BaseActivity implements OnClickListener {

	private TextView textView;
	private EditText editText;
	private Button okButton,cancelButton;
	private LinearLayout dialog;
	private int score;
	
	private SQLiteDatabase dbDatabase;
	private GameDataBase dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		
		dialog = (LinearLayout) findViewById(R.id.dialog);
		textView = (TextView) findViewById(R.id.info);
		editText = (EditText) findViewById(R.id.gameName);
		okButton = (Button) findViewById(R.id.ok);
		cancelButton = (Button) findViewById(R.id.cancel);
		
		int i = getIntent().getIntExtra("game", SnakeView.GAME_OVER);
		score = getIntent().getIntExtra("score", 0);
		
		dbHelper = new GameDataBase(this, "game.db", null, 1);
		
		if(i==SnakeView.GAME_OVER){
			textView.setText(getString(R.string.gameOver));
		}
		okButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
	}
	
	@Override
	public void onBackPressed() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok:
			String string = editText.getText().toString();
			if(string.equals("")){
				editText.requestFocus();
				return;
			}else {
				dbDatabase = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("score", score);
				values.put("name", string);
				dbDatabase.insert("score", null, values );
				dbDatabase.close();
				dbHelper.close();
			}
			break;
		case R.id.cancel:
			break;
		default:
			break;
		}
		dialog.setVisibility(View.GONE);
		DialogActivity.this.finish();
	}
}
