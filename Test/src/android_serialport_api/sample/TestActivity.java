package android_serialport_api.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android_serialport_api.SerialPort;
import android_serialport_api.util.HexUtils;

/**
 * @author dengjifu
 */
public class TestActivity extends Activity implements Callback {

	private static final String TAG = "TestActivity";
	private static int SamCard1 = 12;
	private static int SamCard2 = 14;
	private static String path1 = "/dev/ttyMT0";
	private static String path2 = "/dev/ttyMT1";
	private static int baudrate = 38400;

	private EditText rspEditText,sendEditText;

	protected Application mApplication;
	protected SerialPort mSerialPort;
	protected OutputStream mOutputStream;
	protected InputStream mInputStream;
	private ReadThread mReadThread;

	private String respTextString;
	private Button btnSendButton;
	
	private boolean isRunning = true;
	public class ReadThread extends Thread {
		@Override
		public void run() {
			super.run();
//			while(!isInterrupted() && isRunning){
				try {
					byte[] buffer = new byte[1024];
					if (mInputStream == null) {
						Log.i("dengjifu", "mInputStream == null");
						return;
					}
					Log.i("dengjifu", Thread.currentThread().getName()+" wait read:");
					int size = mInputStream.read(buffer);
					Log.i("dengjifu", "size:" + size);
					if (size > 0) {
						onDataReceived(buffer, size);
					}else {
						Log.i("dengjifu", "size《0");
					}
					Log.i("dengjifu", Thread.currentThread().getName()+"---end--");

				} catch (IOException e) {
					Log.i("dengjifu", Thread.currentThread().getName()+"---IOException-->"+e.getMessage());
					return;
				} catch (Exception e) {
					Log.i("dengjifu", Thread.currentThread().getName()+"---Exception-->"+e.getMessage());
					return;
				}
			}
//		}
	}

	private void DisplayError(int resourceId) {
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Error");
		b.setMessage(resourceId);
		b.setPositiveButton("OK", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				TestActivity.this.finish();
			}
		});
		b.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_test);

		rspEditText = (EditText) findViewById(R.id.rspEditText);
		sendEditText = (EditText) findViewById(R.id.sendEditText);
		btnSendButton = (Button) findViewById(R.id.ok);
		
		// 上电GPIO
		pownOn(SamCard1);
		pownOn(SamCard2);
		
		//打开端口
		openSerialPort();

		//点击发送AT指令
		btnSendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String cmdString = sendEditText.getText().toString();
				if(!cmdString.equals("")){
					mReadThread = new ReadThread();
					mReadThread.start();
					// 发送AT指令
					sendATCmds(cmdString);
				}else{
					DisplayError(R.string.StringIsNull);
				}
			}
		});
	}

	private void openSerialPort() {
		mApplication = (Application) getApplication();
		try {
			// 串口操作初始化
			mSerialPort = mApplication.getSerialPort();
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
			
		} catch (SecurityException e) {
			DisplayError(R.string.error_security);
		} catch (IOException e) {
			DisplayError(R.string.error_unknown);
		} catch (InvalidParameterException e) {
			DisplayError(R.string.error_configuration);
		}
	}

	private void sendATCmds(final String cmdString){
		Runnable sendCmdRunnable = new Runnable() {
			@Override
			public void run() {
				try {
					//6600000000000002000060
					//6b08000000000000000040a1058f2001000029
					byte[] buffer = HexUtils.HexToByteArr("6200000000000200000060");
					Log.i("dengjifu", "mOutputStream is null:" + (mOutputStream==null));
					mOutputStream.write(buffer);
					mOutputStream.write('\n');
					Log.i("dengjifu",
							"Send AT command success: " + HexUtils.byte2HexStr(buffer));
				} catch (Exception e) {
					Log.i("dengjifu","Send AT command failed: " + e.getMessage());				}
			}
		};
		new Thread(sendCmdRunnable).start();
	}

	private void pownOn(int portid) {
		Intent i = new Intent("testsam.SAMCARD_POWER_UP");
		i.putExtra("gpio_port_id", portid);
		sendBroadcast(i);
		Log.i("dengjifu", "GPIO pownOn!");
	}

	private void pownOff(int portid) {
		Intent i = new Intent("testsam.SAMCARD_POWER_DOWN");
		i.putExtra("gpio_port_id", portid);
		sendBroadcast(i);
		Log.i("dengjifu", "GPIO pownOff!");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		isRunning = false;
		if(mReadThread!=null){
			mReadThread.interrupt();
		}
		if(mInputStream!=null){
			mInputStream=null;
		}
		if(mOutputStream!=null){
			mOutputStream=null;
		}
		if(mSerialPort!=null){
			mSerialPort=null;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		isRunning = false;
		if(mReadThread!=null){
			mReadThread.interrupt();
		}
		if(mInputStream!=null){
			mInputStream=null;
		}
		if(mOutputStream!=null){
			mOutputStream=null;
		}
		if(mSerialPort!=null){
			mSerialPort=null;
		}
	}

	protected void onDataReceived(byte[] buffer, int size) {
		respTextString = HexUtils.byte2HexStr(buffer);
		Log.i("dengjifu", "buffer:" + respTextString + ",size" + size);
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessage(msg);
	}

	Handler handler = new Handler(this);

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case 1:
			rspEditText.setText(respTextString);
			break;
		default:
			break;
		}
		return false;
	}
}
