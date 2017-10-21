package android_serialport_api.sample;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android_serialport_api.SerialPort;
import android_serialport_api.sample.SerialPortActivity.ReadThread;
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

	private EditText rspEditText;

	protected Application mApplication;
	protected SerialPort mSerialPort;
	protected OutputStream mOutputStream;
	protected InputStream mInputStream;
	private ReadThread mReadThread;

	private String respTextString;

	public class ReadThread extends Thread {

		@Override
		public void run() {
			super.run();

			Log.i("dengjifu", Thread.currentThread().getName()
					+ "--ReadThread/isInterrupted():" + isInterrupted());
			try {
				byte[] buffer = new byte[1024];
				if (mInputStream == null) {
					Log.i("dengjifu", "mInputStream == null");
					return;
				}
				Log.i("dengjifu", "ReadThread-->read");
				int size = mInputStream.read(buffer);
				Log.i("dengjifu", "size:" + size);
				if (size > 0) {
					onDataReceived(buffer, size);
				}
				Log.i("dengjifu", Thread.currentThread().getName()
						+ "--ReadThread/isInterrupted():" + isInterrupted());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		rspEditText = (EditText) findViewById(R.id.rspEditText);
		rspEditText.setSaveEnabled(false);
		mApplication = (Application) getApplication();
		try {
			// 串口操作初始化
			mSerialPort = mApplication.getSerialPort();
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
			mReadThread = new ReadThread();
			mReadThread.start();
		} catch (InvalidParameterException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 上电GPIO
		pownOn(SamCard1);
		pownOn(SamCard2);

		// 发送AT指令
		try {
			sendATCmds();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void sendATCmds() throws SecurityException, IOException {
		Runnable sendCmdRunnable = new Runnable() {
			@Override
			public void run() {
				try {
					// 66 00 00 00 00 00 00 02 00 00 60
					String cmd = new String("6200000000000200000060");
					byte[] buffer = HexUtils.HexToByteArr(cmd);
					Log.i("dengjifu", mOutputStream + ":" + buffer);
					mOutputStream.write(buffer);
					mOutputStream.write('\r');
					mOutputStream.write('\n');
					Log.i("dengjifu",
							"Send AT command: " + HexUtils.byte2HexStr(buffer));
				} catch (Exception e) {
					e.printStackTrace();
				}
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
	protected void onDestroy() {
		super.onDestroy();
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
