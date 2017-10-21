/*
 * Copyright 2009 Cedric Priscal
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android_serialport_api.util.HexUtils;

public class ConsoleActivity extends SerialPortActivity implements OnClickListener {

	EditText mReception, mEmission;
	Button mReceptionClear, mEmissionClear, mString2Hex, mEmissionSend, mSending01010101;
	
	private boolean isHex = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.console);
		getSharedPreferences("com.android.ptt_preferences.xml", MODE_WORLD_WRITEABLE);

		mReception = (EditText) findViewById(R.id.et_reception);		
		mEmission = (EditText) findViewById(R.id.et_emission);
		
		mReceptionClear = (Button) findViewById(R.id.btn_reception_clear);
		mReceptionClear.setOnClickListener(this);
		
		mEmissionClear = (Button) findViewById(R.id.btn_emission_clear);
		mEmissionClear.setOnClickListener(this);
		
		mString2Hex = (Button) findViewById(R.id.btn_string2hex);
		mString2Hex.setOnClickListener(this);
		
		mEmissionSend = (Button) findViewById(R.id.btn_send_emission);
		mEmissionSend.setOnClickListener(this);
	}

	@Override
	protected void onDataReceived(final byte[] buffer, final int size) {
		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if (mReception != null) {
					mReception.append(new String(buffer, 0, size));
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {	
		case R.id.btn_reception_clear:
			mReception.setText(null);			
			break;
		case R.id.btn_emission_clear:
			mEmission.setText(null);
			break;
		case R.id.btn_string2hex:
			String str = mEmission.getText().toString();			
			if (mString2Hex.getText().equals("HEX")) {			
				mString2Hex.setText("TXT");
				mEmission.setText(HexUtils.str2HexStr(str));
				isHex = true;
			} else {
				mString2Hex.setText("HEX");
				mEmission.setText(HexUtils.hexStr2Str(str.replaceAll(" ", "")));
				isHex = false;
			}
			break;			
		case R.id.btn_send_emission:
			String text = mEmission.getText().toString();
			byte[] bytes = new byte[1024];
			
//			char[] msg = new char[text.length()];
//			for (int i = 0; i < text.length(); i++) {
//				msg[i] = text.charAt(i);
//			}
			if (isHex) {
				bytes = HexUtils.hexStr2Bytes(text.replaceAll(" ", ""));
			} else {
				bytes = text.getBytes();
			}
			
			try {
				mOutputStream.write(bytes);
				mOutputStream.write('\n');
				Log.d("liuhanling", "Write AT command!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
