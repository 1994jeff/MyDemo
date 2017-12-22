/*
 * Copyright 2011 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package android_serialport_api.sample;

import java.io.IOException;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Sending01010101Activity extends SerialPortActivity {

	TextView mReception;
	
	SendingThread mSendingThread;
	byte[] mBuffer = new byte[1024];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sending01010101);
		
		mReception = (TextView) findViewById(R.id.textView2);
		
		String str = "0x55";
		mBuffer = str.getBytes();
		
		if (mSerialPort != null) {
			mSendingThread = new SendingThread();
			mSendingThread.start();
		}
	}
	
	@Override
	protected void onDestroy() {
		
		if (mSendingThread != null) {
			mSendingThread.interrupt();
		}
		super.onDestroy();		
	}

	@Override
	protected void onDataReceived(final byte[] buffer, final int size) {
		
//		runOnUiThread(new Runnable() {
//			
//			@Override
//			public void run() {
//				String result;
//				Log.d("liuhanling", "buffer.size=" + size);
//				if (mReception != null) {
//					mReception.append(new String(buffer, 0, size));
////					mReception.append(HexUtils.byte2HexStr(buffer));
//				}
//			}
//		});
	}

	private class SendingThread extends Thread {
		
		@Override
		public void run() {
			
			Log.d("liuhanling", "SendingThread/isInterrupted()" + isInterrupted());
			
			while (!isInterrupted()) {
				
				try {
					if (mOutputStream != null) {
						mOutputStream.write(mBuffer);
						Log.d("liuhanling", "SendingThread/write ...");
					} else {
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
}
