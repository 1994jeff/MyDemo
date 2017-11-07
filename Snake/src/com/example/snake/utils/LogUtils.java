package com.example.snake.utils;

import android.util.Log;
/**
 * @author dengjifu
 * @date 20171027
 */
public class LogUtils{
	
	private final static int DEBUG_MODE = 1;
	private final static int INFO_MODE = 2;
	private static int MODE = DEBUG_MODE;
	
	public static void d(String tag,String msg){
		if(MODE==DEBUG_MODE){
			Log.i(tag, msg);
		}
	}
}
