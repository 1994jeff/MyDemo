package com.example.snake.utils;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

import com.example.snake.R;

public class SoundPoolUtils {

	private static HashMap<Integer, Integer> map;
	private static boolean isLoaded = false;
	public static boolean isShowMusic = true;

	private SoundPoolUtils() {
	}

	private static class SoundPoolInstance {
		private static SoundPool spPool;

		@SuppressWarnings("deprecation")
		private SoundPool newInstance(Context context) {
			if (spPool == null) {
				spPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
				map = new HashMap<>();
				map.put(1, spPool.load(context, R.raw.eat, 1));
				map.put(2, spPool.load(context, R.raw.level, 1));
				map.put(3, spPool.load(context, R.raw.dead, 1));
				spPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
					@Override
					public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
						isLoaded = true;
					}
				});
			}
			return spPool;
		}
	}

	public static SoundPool getInstance(Context context) {
		return new SoundPoolInstance().newInstance(context);
	}

	public static void playMusic(SoundPool sp, int number,Context context) {
		if (isLoaded && isShowMusic) {
			AudioManager am = (AudioManager) context
	                .getSystemService(Context.AUDIO_SERVICE);
	        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	        float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
	        float volumnRatio = volumnCurrent / audioMaxVolumn;
	        sp.play(map.get(number),   
	                volumnRatio,
	                volumnRatio,
	                1, //play level
	                0,// loop nums  
	                1);// speed when loop  
		}
	}

}
