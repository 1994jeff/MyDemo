package android_serialport_api.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.widget.EditText;

/**
 * CmdUtil
 * <ul>
 * Set at command
 * <li>{@link #getConnStatus()}</li>
 * <li>{@link #setGroupParas(String parameters)}</li>
 * <li>{@link #setAutoPowContr(boolean autoPowContr)} </li>
 * <li>{@link #setVolume(String volume)} </li>
 * <li>{@link #getVersion()} </li>
 * </ul>
 * <ul>
 * Send or receive at command
 * <li>{@link #sendAtCommand(OutputStream outputStream, String atCommant)}</li>
 * <li>{@link #receiveAtCommandResult(InputStream inputStream)}</li>
 * </ul>
 * @author LiuHanling
 */
public class CmdUtil {

	public static final String CONN_STATUS = "AT+DMOCONNECT";
	public static final String CONN_STATUS_RECEIVE = "+DMOCONNECT:0";
	public static final String CONN_STATUS_TRANSMIT = "+DMOCONNECT:1";

	public static final String SET_GROUP = "AT+DMOSETGROUP";
	public static final String SET_GROUP_OK = "+DMOSETGROUP:0";
	public static final String SET_GROUP_FAIL = "+DMOSETGROUP:1";

	public static final String SET_AUTO_POW_CONTR = "AT+DMOAUTOPOWCONTR";
	public static final String SET_AUTO_POW_CONTR_ON = "+DMOAUTOPOWCONTR:0";
	public static final String SET_AUTO_POW_CONTR_OFF = "+DMOAUTOPOWCONTR:1";

	public static final String SET_VOLUME = "AT+DMOSETVOLUME";
	public static final String SET_VOLUME_OK = "+DMOSETVOLUME:0";
	public static final String SET_VOLUME_FAIL = "+DMOSETVOLUME:1";

	public static final String QUERY_VERSION = "AT+DMOVERQ";
	public static final String QUERY_VERSION_OK = "+DMOVERQ:";


	public static String getConnStatus() {
		return CONN_STATUS;
	}

	public static String setGroupParas(String parameters) {
		return (SET_GROUP + "=" + parameters);
	}

	public static String setAutoPowContr(boolean autoPowContr) {
		if (autoPowContr) {
			return (SET_AUTO_POW_CONTR + "=" + 1);
		} else {
			return (SET_AUTO_POW_CONTR + "=" + 0);
		}
	}

	public static String setVolume(String volume) {
		return (SET_VOLUME + "=" + volume);
	}
	
	public static String getVersion() {
		return QUERY_VERSION;
	}
EditText eText;
	public static void sendAtCommand(OutputStream outputStream, String atCommant) {
		if (atCommant == null || atCommant.length() == 0) {
			System.out.println("AT command is null!");
			return;
		}
		if (outputStream != null) {
			char[] msg = new char[atCommant.length()];		
			for (int i = 0; i < atCommant.length(); i++) {
				msg[i] = atCommant.charAt(i);
			}		
			try {
				outputStream.write(new String(msg).getBytes());
				outputStream.write('\n');
				System.out.println("Send AT command: " + atCommant);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String receiveAtCommandResult(InputStream inputStream) {	
		String result = null;
		if (inputStream != null) {
			try {
				byte[] buffer = new byte[64];
				int size = inputStream.read(buffer);
				if (size > 0) {
					result = new String(buffer, 0, size);
				}
				System.out.println("Receive AT command: " + result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
