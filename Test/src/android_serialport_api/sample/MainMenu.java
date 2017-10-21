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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button buttonSetup = (Button)findViewById(R.id.ButtonSetup);
        buttonSetup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				startActivity(new Intent(MainMenu.this, SerialPortPreferences.class));
				Intent intent=new Intent(MainMenu.this, TestActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.setFlags(Intent.);
				startActivity(intent);
			}
		});

        
//        final Button buttonConsole = (Button)findViewById(R.id.ButtonConsole);
//        buttonConsole.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				startActivity(new Intent(MainMenu.this, ConsoleActivity.class));
//			}
//		});
//
//        final Button buttonLoopback = (Button)findViewById(R.id.ButtonLoopback);
//        buttonLoopback.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				startActivity(new Intent(MainMenu.this, LoopbackActivity.class));
//			}
//		});
//
//        final Button button01010101 = (Button)findViewById(R.id.Button01010101);
//        button01010101.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				startActivity(new Intent(MainMenu.this, Sending01010101Activity.class));
//			}
//		});
//
//        final Button buttonff00031d0c = (Button)findViewById(R.id.Buttonff00031d0c);
//        buttonff00031d0c.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				startActivity(new Intent(MainMenu.this, Sendingff00031d0cActivity.class));
//			}
//		});
//
//        final Button buttonQuit = (Button)findViewById(R.id.ButtonQuit);
//        buttonQuit.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				MainMenu.this.finish();
//			}
//		});
    }

	@Override
	public void onBackPressed() {
		super.onBackPressed();
//		System.exit(0);
	}
}
