package cosine.boat;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceView;
import android.app.AlertDialog;
import android.view.View.OnClickListener;

import java.util.Timer;

import android.content.DialogInterface;

import java.util.TimerTask;

import android.os.Build;

import java.io.FileInputStream;

import org.json.JSONObject;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

//import com.koishi.launcher.h2o2.MainActivity;


public class BoatActivity extends Activity implements TextureView.SurfaceTextureListener{

    static {
        System.loadLibrary("boat");

    }

    public int cursorMode = BoatInput.CursorEnabled;
    public Timer timer, timer2;
    public RelativeLayout base;
    public TextureView mainTextureView;
    public MyHandler mHandler;
    public int initialX;
    public int initialY;
    public int baseX;
    public int baseY;

    public static native void setBoatNativeWindow(Surface surface);

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {

        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        //mainSurfaceView = new SurfaceView(this);
        //mainSurfaceView.getHolder().addCallback(this);

        setContentView(R.layout.overlay_old);

        base = (RelativeLayout) findViewById(R.id.base);
        mainTextureView = base.findViewById(R.id.client_surface);
        mainTextureView.setSurfaceTextureListener(this);

        timer = new Timer();
    }

    @Override
    protected void onPause() {
        // TODO: Implement this method
        super.onPause();

        if (cursorMode == BoatInput.CursorDisabled) {
        } else {
        }

        //popupWindow.dismiss();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO: Implement this method
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_Escape, 0, true);
        // TODO: Implement this method
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        // TODO: Implement this method
        System.out.println("SurfaceTexture is available!");
        BoatActivity.setBoatNativeWindow(new Surface(surface));

        new Thread() {
            @Override
            public void run() {

                LauncherConfig config = LauncherConfig.fromFile(getIntent().getExtras().getString("config"));
                LoadMe.exec(config);
                Message msg = new Message();
                msg.what = -1;
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture p1, int p2, int p3) {
        // TODO: Implement this method
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture p1) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture p1) {
        // TODO: Implement this method
    }

    public void setCursorMode(int mode) {

        Message msg = new Message();
        msg.what = mode;
        mHandler.sendMessage(msg);
    }


    public void setCursorPos(int x, int y) {
    }

    @SuppressLint("HandlerLeak")
    private static class MyHandler extends Handler {
    }


}



