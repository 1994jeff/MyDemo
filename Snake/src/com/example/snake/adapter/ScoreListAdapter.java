package com.example.snake.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.snake.R;
import com.example.snake.bean.Score;
import com.example.snake.database.GameDataBase;
/**
 * @author dengjifu
 * @date 20171027
 */
public class ScoreListAdapter extends BaseAdapter {

	private Context context;
	private List<Score> scores;
	private LayoutInflater inflater;
	private GameDataBase dbHelper;
	private SQLiteDatabase db;
	
	public ScoreListAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		scores = new ArrayList<Score>();
		this.context = context;
		getData();
	}

	private void getData() {
		dbHelper = new GameDataBase(context, "game.db", null, 1);
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("score", null, null, null, null, null, "score desc");
		if(cursor!=null){
			Score scoreBean = null;
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				int score = cursor.getInt(cursor.getColumnIndex("score"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				scoreBean = new Score(id,score,name);
				scores.add(scoreBean);			
			}
			cursor.close();
		}
		db.close();
		dbHelper.close();
	}
	
	@Override
	public int getCount() {
		return scores.size();
	}

	@Override
	public Object getItem(int position) {
		return scores.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHoler holer;
		if(convertView==null){
			holer = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_score_rank_listview,parent,false);
			holer.orderData = (TextView) convertView.findViewById(R.id.orderData);
			holer.nameData = (TextView) convertView.findViewById(R.id.nameData);
			holer.scoreData = (TextView) convertView.findViewById(R.id.socreData);
			convertView.setTag(holer);
		}else{
			holer = (ViewHoler) convertView.getTag();
		}
		holer.orderData.setText((position+1)+"");
		holer.nameData.setText(scores.get(position).getName());
		holer.scoreData.setText(scores.get(position).getScore()+"");
		
		return convertView;
	}
	
	private class ViewHoler{
		TextView orderData,scoreData,nameData;
	}
	
}
