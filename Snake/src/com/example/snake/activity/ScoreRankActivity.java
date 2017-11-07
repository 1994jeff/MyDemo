package com.example.snake.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.snake.R;
import com.example.snake.adapter.ScoreListAdapter;
/**
 * @author dengjifu
 * @date 20171027
 */
public class ScoreRankActivity extends BaseActivity {

	private ScoreListAdapter adapter;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_rank);
	
		listView = (ListView) findViewById(R.id.scoreList);
		adapter = new ScoreListAdapter(this);
		listView.setAdapter(adapter);
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	
}
