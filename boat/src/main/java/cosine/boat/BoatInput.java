package cosine.boat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.view.Surface;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class BoatInput{
	
	public static final int KeyPress              = 2;
	public static final int KeyRelease            = 3;
	public static final int ButtonPress           = 4;
	public static final int ButtonRelease	      = 5;
	public static final int MotionNotify          = 6;

	public static final int Button1               = 1;
	public static final int Button2               = 2;
	public static final int Button3               = 3;
	public static final int Button4               = 4;
	public static final int Button5               = 5;
	public static final int Button6               = 6;
	public static final int Button7               = 7;

	public static final int CursorEnabled         = 1;
	public static final int CursorDisabled        = 0;
	public static final int CursorSetPos          = 2;
	public static final int ConfigureNotify       = 22;
	public static final int BoatMessage           = 37;

	public static final int ShiftMask             = 1 << 0;
	public static final int LockMask              = 1 << 1;
	public static final int ControlMask           = 1 << 2;
	public static final int Mod1Mask              = 1 << 3;
	public static final int Mod2Mask              = 1 << 4;
	public static final int Mod3Mask              = 1 << 5;
	public static final int Mod4Mask              = 1 << 6;
	public static final int Mod5Mask              = 1 << 7;
	public static final int CloseRequest          = 0;

	static {
        System.loadLibrary("boat");
    }

	public static void setMouseButton(int button, boolean press) {
        send(System.nanoTime(), press ? ButtonPress : ButtonRelease, button, 0);
    }
	public static void setPointer(int x, int y) {
        send(System.nanoTime(), MotionNotify, x, y);
    }

	public static void setKey(int keyCode, int keyChar, boolean press){
		send(System.nanoTime(), press ? KeyPress : KeyRelease, keyCode, keyChar);
	}

	public static void setInput(int keyCode, int keyChar, boolean press , boolean Difference){
		send(System.nanoTime(), Difference ? (press ? ButtonPress : ButtonRelease) : (press ? KeyPress : KeyRelease) , keyCode , Difference ? 0 : keyChar);
	}

	public static native void send(long time, int type, int p1, int p2);

	// To be called by lwjgl/glfw.
	@RequiresApi(api = Build.VERSION_CODES.O)
	public static void setCursorMode(int mode){
		AppCompatActivity activity = BoatApplication.getCurrentActivity();
		if (activity instanceof BoatActivity){
			BoatActivity boatActivity = (BoatActivity)activity;
			boatActivity.setCursorMode(mode);
		} else if (activity instanceof BoatActivityMk){
			BoatActivityMk boatActivityMk = (BoatActivityMk)activity;
			boatActivityMk.setCursorMode(mode);
		}

	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public static void setCursorPos(int x, int y){

		AppCompatActivity activity = BoatApplication.getCurrentActivity();
		if (activity instanceof BoatActivity){
			BoatActivity boatActivity = (BoatActivity)activity;
			boatActivity.setCursorPos(x, y);
		} else if (activity instanceof  BoatActivityMk){
			BoatActivityMk boatActivityMk = (BoatActivityMk)activity;
			boatActivityMk.setCursorPos(x, y);
		}

	}

	public static void setPrimaryClipString(String string){
		AppCompatActivity activity = BoatApplication.getCurrentActivity();
		if (activity instanceof BoatActivity){
			ClipboardManager clipboard = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText("Boat Clipboard", string);
			clipboard.setPrimaryClip(clip);
		}

	}

	public static String getPrimaryClipString(){
		AppCompatActivity activity = BoatApplication.getCurrentActivity();
		if (activity instanceof BoatActivity){

			ClipboardManager clipboard = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
			if (!clipboard.hasPrimaryClip()) {
				return null;
			}
			ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
			return item.getText().toString();
		}
		return null;

	}

	public static native void setBoatNativeWindow(Surface surface);
	public static native void setEventPipe();
	public static native void pushEvent(long time, int type, int p1, int p2);

	public static void pushEventMouseButton(int button, boolean press) {
		BoatInput.pushEvent(System.nanoTime(), press ? ButtonPress : ButtonRelease, button, 0);
	}
	public static void pushEventPointer(int x, int y) {
		BoatInput.pushEvent(System.nanoTime(), MotionNotify, x, y);
	}
	public static void pushEventKey(int keyCode, int keyChar, boolean press) {
		BoatInput.pushEvent(System.nanoTime(), press ? KeyPress : KeyRelease, keyCode, keyChar);
	}
	public static void pushEventWindow(int width, int height) {
		BoatInput.pushEvent(System.nanoTime(), ConfigureNotify, width, height);
	}
	public static void pushEventMessage(int msg) {
		BoatInput.pushEvent(System.nanoTime(), BoatMessage, msg, 0);
	}

}
