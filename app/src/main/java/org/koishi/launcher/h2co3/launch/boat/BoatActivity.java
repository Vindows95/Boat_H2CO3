package org.koishi.launcher.h2co3.launch.boat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;

import cosine.boat.BoatInput;
import cosine.boat.BoatKeycodes;
import cosine.boat.LauncherConfig;
import cosine.boat.LoadMe;

//import com.koishi.launcher.h2o2.MainActivity;


public class BoatActivity extends cosine.boat.BoatActivity implements OnClickListener, View.OnTouchListener, TextWatcher, TextView.OnEditorActionListener, TextureView.SurfaceTextureListener, SurfaceHolder.Callback {

    MyHandler mHandler;
    public ImageView mouseCursor;
    Button touchPad2,touchPad;

    @Override
    public void surfaceCreated(SurfaceHolder p1) {
        // TODO: Implement this method
        System.out.println("Surface is available!");
        BoatActivity.setBoatNativeWindow(p1.getSurface());

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
    public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4) {
        // TODO: Implement this method
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder p1) {
        // TODO: Implement this method
        throw new RuntimeException("Surface is destroyed!");
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {

        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        //mainSurfaceView = new SurfaceView(this);
        //mainSurfaceView.getHolder().addCallback(this);

        setContentView(cosine.boat.R.layout.overlay_old);

        base = (RelativeLayout) findViewById(cosine.boat.R.id.base);
        mainTextureView = base.findViewById(cosine.boat.R.id.client_surface);
        mainTextureView.setSurfaceTextureListener(this);
        mouseCursor = (ImageView) base.findViewById(cosine.boat.R.id.mouse_cursor);
        touchPad = this.findButton(cosine.boat.R.id.touch_pad);

        //cv1=(CardView)base.findViewById(R.id.cv1);

        //Control the 2/3 screen

        touchPad2 = this.findButton(cosine.boat.R.id.touch_pad2);
        touchPad2.setOnTouchListener(new View.OnTouchListener() {
            private long currentMS;
            private float itialY, itY, itX, itialX;
            private int moveX, moveY, meX, meY;


            @SuppressLint("SetTextI18n")
            @Override
            public boolean onTouch(View p1, MotionEvent p2) {
                if (cursorMode == BoatInput.CursorDisabled) {
                    if (cursorMode == BoatInput.CursorEnabled) {
                        baseX = (int) p2.getX();
                        baseY = (int) p2.getY();
                        BoatInput.setPointer(baseX, baseY);
                        if (p2.getActionMasked() == MotionEvent.ACTION_DOWN) {
                            BoatInput.setMouseButton(BoatInput.Button1, true);

                        }
                        if (p2.getActionMasked() == MotionEvent.ACTION_UP) {
                            BoatInput.setMouseButton(BoatInput.Button1, false);
                        }
                    }
                }
                mouseCursor.setX(p2.getX());
                mouseCursor.setY(p2.getY());
                return true;
            }
        });

        int height = getWindowManager().getDefaultDisplay().getHeight();
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int scale = 1;
        while (width / (scale + 1) >= 320 && height / (scale + 1) >= 240) {
            scale++;
        }
        //popupWindow.setContentView(base);

        mHandler = new MyHandler();


    }

    @SuppressLint("ClickableViewAccessibility")
    public Button findButton(int id) {
        Button b = (Button) findViewById(id);
        b.setOnTouchListener(this);
        return b;
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

        Message msg = new Message();
        msg.what = BoatInput.CursorSetPos;
        msg.arg1 = x;
        msg.arg2 = y;
        mHandler.sendMessage(msg);
    }

    @Override
    public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
        // TODO: Implement this method
    }

    @Override
    public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
        // TODO: Implement this method
    }

    @Override
    public void afterTextChanged(Editable p1) {
        // TODO: Implement this method
        String newText = p1.toString();
        if (newText.length() < 1) {

            BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_BackSpace, 0, true);
            BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_BackSpace, 0, false);
        }
        if (newText.length() > 1) {
            for (int i = 1; i < newText.length(); i++) {
                BoatInput.setKey(0, newText.charAt(i), true);
                BoatInput.setKey(0, newText.charAt(i), false);
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView p1, int p2, KeyEvent p3) {
        // TODO: Implement this method

        BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_Return, '\n', true);
        BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_Return, '\n', false);
        return false;
    }

    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
    }

    @Override
    public boolean onTouch(View p1, MotionEvent p2) {
        if (p1 == touchPad) {
            if (cursorMode == BoatInput.CursorDisabled) {
                switch (p2.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = (int) p2.getX();
                        initialY = (int) p2.getY();
                    case MotionEvent.ACTION_MOVE:
                        BoatInput.setPointer(baseX + (int) p2.getX() - initialX, baseY + (int) p2.getY() - initialY);
                        break;
                    case MotionEvent.ACTION_UP:
                        baseX += ((int) p2.getX() - initialX);
                        baseY += ((int) p2.getY() - initialY);

                        BoatInput.setPointer(baseX, baseY);
                        break;
                    default:
                        break;
                }
            } else if (cursorMode == BoatInput.CursorEnabled) {
                baseX = (int) p2.getX();
                baseY = (int) p2.getY();
                BoatInput.setPointer(baseX, baseY);
                if (p2.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    BoatInput.setMouseButton(BoatInput.Button1, true);

                }
                if (p2.getActionMasked() == MotionEvent.ACTION_UP) {
                    BoatInput.setMouseButton(BoatInput.Button1, false);
                }
            }

            mouseCursor.setX(p2.getX());
            mouseCursor.setY(p2.getY());
            return true;
        }
        return false;

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case BoatInput.CursorDisabled:
                    BoatActivity.this.mouseCursor.setVisibility(View.INVISIBLE);
                    BoatActivity.this.cursorMode = BoatInput.CursorDisabled;
                    BoatActivity.this.touchPad.setVisibility(View.INVISIBLE);
                    BoatActivity.this.touchPad2.setVisibility(View.VISIBLE);
                    break;

                case BoatInput.CursorEnabled:
                    BoatActivity.this.mouseCursor.setVisibility(View.VISIBLE);
                    BoatActivity.this.cursorMode = BoatInput.CursorEnabled;
                    BoatActivity.this.touchPad.setVisibility(View.VISIBLE);
                    BoatActivity.this.touchPad2.setVisibility(View.INVISIBLE);
                    break;

                case BoatInput.CursorSetPos:
                    BoatActivity.this.mouseCursor.setX((float) msg.arg1);
                    BoatActivity.this.mouseCursor.setY((float) msg.arg2);
                    break;

                default:
                    //Intent intent1=new Intent(BoatActivity.this,MainActivity.class);
                    //startActivity(intent1);
                    BoatActivity.this.finish();
                    break;
            }
        }
    }


}



