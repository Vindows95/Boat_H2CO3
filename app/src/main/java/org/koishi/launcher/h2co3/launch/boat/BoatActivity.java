package org.koishi.launcher.h2co3.launch.boat;

import static cosine.boat.CHTools.LAUNCHER_FILE_DIR;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.koishi.h2co3.mclauncher.customcontrol.H2CO3CrossingKeyboard;
import org.koishi.h2co3.mclauncher.customcontrol.H2CO3CustomManager;
import org.koishi.h2co3.mclauncher.customcontrol.MioCustomButton;
import org.lwjgl.glfw.CallbackBridge;

import cosine.boat.BoatInput;
import cosine.boat.BoatKeycodes;
import cosine.boat.LauncherConfig;
import cosine.boat.LoadMe;

//import com.koishi.launcher.h2o2.MainActivity;


public class BoatActivity extends cosine.boat.BoatActivity implements OnClickListener, View.OnTouchListener, TextWatcher, TextView.OnEditorActionListener, TextureView.SurfaceTextureListener, SurfaceHolder.Callback {

    public FrameLayout mControlLayout;
    public ImageView mouseCursor;
    public H2CO3CustomManager h2co3CustomManager;
    public H2CO3CrossingKeyboard h2CO3CrossingKeyboard;
    public SharedPreferences msh;
    public SharedPreferences.Editor mshe;
    public int cursorMode = BoatInput.CursorEnabled;
    MyHandler mHandler;
    Button touchPad2, touchPad;
    private DrawerLayout drawerLayout;
    private NavigationView navDrawer;
    private NavigationView.OnNavigationItemSelectedListener gameActionListener;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        //mainSurfaceView = new SurfaceView(this);
        //mainSurfaceView.getHolder().addCallback(this);

        setContentView(cosine.boat.R.layout.overlay);

        msh = getSharedPreferences("Boat_H2CO3", MODE_PRIVATE);
        mshe = msh.edit();

        base = findViewById(cosine.boat.R.id.r_base);
        mainTextureView = base.findViewById(cosine.boat.R.id.client_surface);
        mainTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
                //initCustomButtom();
                // TODO: Implement this method
                System.out.println("SurfaceTexture is available!");
                cosine.boat.BoatActivity.setBoatNativeWindow(new Surface(texture));
                initCustomButtom();
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
            public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
            }
        });
        mouseCursor = base.findViewById(cosine.boat.R.id.mouse_cursor);
        mControlLayout = findViewById(cosine.boat.R.id.content_frame);
        touchPad = this.findButton(cosine.boat.R.id.touch_pad);
        drawerLayout = findViewById(cosine.boat.R.id.overlay_drawer_options);

        navDrawer = findViewById(cosine.boat.R.id.main_navigation_view);
        gameActionListener = menuItem -> {
            if (menuItem.getItemId() == cosine.boat.R.id.menu_ctrl_custom) {
                openH2CO3CustomControls();
            }/* else if (menuItem.getItemId() == cosine.boat.R.id.menu_ctrl_vi) {
                //h2co3CustomManager.按键显示隐藏(h2CO3CrossingKeyboard);
                openH2CO3CustomControls();
            }*/
            drawerLayout.closeDrawers();
            return true;
        };
        navDrawer.setNavigationItemSelectedListener(gameActionListener);

        //cv1=(CardView)base.findViewById(R.id.cv1);

        //Control the 2/3 screen

        touchPad2 = this.findButton(cosine.boat.R.id.touch_pad2);
        touchPad2.setOnTouchListener(new View.OnTouchListener() {
            private long currentMS;
            private float itialY, itialX;
            private int moveX, moveY;

            @SuppressLint("SetTextI18n")
            @Override
            public boolean onTouch(View p1, MotionEvent p2) {
                if (cursorMode == BoatInput.CursorDisabled) {
                    if (getResources().getString(cosine.boat.R.string.touch_mode_2).equals(getResources().getString(cosine.boat.R.string.touch_mode_2))) {
                        switch (p2.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
                                moveX = 0;
                                moveY = 0;
                                initialX = (int) p2.getX();
                                initialY = (int) p2.getY();
                                itialX = p2.getX();
                                itialY = p2.getY();
                                currentMS = System.currentTimeMillis();
                            case MotionEvent.ACTION_MOVE:
                                moveX += Math.abs(p2.getX() - itialX);
                                moveY += Math.abs(p2.getY() - itialY);
                                long movesTime = System.currentTimeMillis() - currentMS;//移动时间
                                if (movesTime > 400 && moveX < 3 && moveY < 3) {
                                    BoatInput.setMouseButton(BoatInput.Button1, true);
                                    BoatInput.setPointer(baseX + (int) p2.getX() - initialX, baseY + (int) p2.getY() - initialY);
                                    return false;
                                }
                                BoatInput.setPointer(baseX + (int) p2.getX() - initialX, baseY + (int) p2.getY() - initialY);
                                break;
                            case MotionEvent.ACTION_UP:
                                baseX += ((int) p2.getX() - initialX);
                                baseY += ((int) p2.getY() - initialY);
                                BoatInput.setMouseButton(BoatInput.Button1, false);
                                BoatInput.setMouseButton(BoatInput.Button3, false);
                                BoatInput.setPointer(baseX, baseY);
                                long moveTime = System.currentTimeMillis() - currentMS;
                                if (moveTime < 200 && (moveX < 2 || moveY < 2)) {
                                    BoatInput.setMouseButton(BoatInput.Button3, true);
                                    BoatInput.setMouseButton(BoatInput.Button3, false);
                                    return false;
                                }
                                break;
                            default:
                                break;
                        }
                    } else {
                        switch (p2.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
                                moveX = 0;
                                moveY = 0;
                                initialX = (int) p2.getX();
                                initialY = (int) p2.getY();
                                itialX = p2.getX();
                                itialY = p2.getY();
                                currentMS = System.currentTimeMillis();
                            case MotionEvent.ACTION_MOVE:
                                moveX += Math.abs(p2.getX() - itialX);
                                moveY += Math.abs(p2.getY() - itialY);
                                long movesTime = System.currentTimeMillis() - currentMS;//移动时间
                                if (movesTime > 400 && moveX < 3 && moveY < 3) {
                                    BoatInput.setMouseButton(BoatInput.Button3, true);
                                    BoatInput.setPointer(baseX + (int) p2.getX() - initialX, baseY + (int) p2.getY() - initialY);
                                    return false;
                                }
                                BoatInput.setPointer(baseX + (int) p2.getX() - initialX, baseY + (int) p2.getY() - initialY);
                                break;
                            case MotionEvent.ACTION_UP:
                                baseX += ((int) p2.getX() - initialX);
                                baseY += ((int) p2.getY() - initialY);
                                BoatInput.setMouseButton(BoatInput.Button3, false);
                                BoatInput.setMouseButton(BoatInput.Button1, false);
                                BoatInput.setPointer(baseX, baseY);
                                long moveTime = System.currentTimeMillis() - currentMS;
                                if (moveTime < 200 && (moveX < 2 || moveY < 2)) {
                                    BoatInput.setMouseButton(BoatInput.Button1, true);
                                    BoatInput.setMouseButton(BoatInput.Button1, false);
                                    return false;
                                }

                                break;
                            default:
                                break;
                        }
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
        });

        int height = getWindowManager().getDefaultDisplay().getHeight();
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int scale = 1;
        while (width / (scale + 1) >= 320 && height / (scale + 1) >= 240) {
            scale++;
        }
        //popupWindow.setContentView(base);

        mHandler = new MyHandler();

        drawerLayout.closeDrawers();
    }

    private void initCustomButtom() {
        h2co3CustomManager = new H2CO3CustomManager();
        h2co3CustomManager.初始化(this, (this.mControlLayout), LAUNCHER_FILE_DIR);
        h2co3CustomManager.设置自定义按键回调(new H2CO3CustomManager.自定义按键回调() {
            @Override
            public void 命令接收事件(String command) {

            }

            @Override
            public void 键值接收事件(short 键值, boolean 按下) {
                sendKeyPress(键值, 0, 按下);
            }

            @Override
            public void 控制鼠标指针移动事件(int x, int y) {

            }

            @Override
            public void 鼠标回调(short 键值, boolean 按下) {
                sendMouseButton(键值, 按下);
            }

            @Override
            public void 按下() {

            }

            @Override
            public void 抬起() {

            }
        });
        h2CO3CrossingKeyboard = findViewById(cosine.boat.R.id.mio_keyboard);
        float xxx = msh.getFloat("方向键x", (float) 0.09 * (CallbackBridge.windowWidth));
        float yyy = msh.getFloat("方向键y", (float) 0.49 * (CallbackBridge.windowHeight));
        if (xxx != 0 && yyy != 0) {
            h2CO3CrossingKeyboard.setX(xxx);
            h2CO3CrossingKeyboard.setY(yyy);
        }
        h2CO3CrossingKeyboard.setScale(msh.getFloat("方向键缩放", 1.0f));
        h2CO3CrossingKeyboard.setListener(new H2CO3CrossingKeyboard.MioListener() {
            private boolean upFromCenter = false;
            private boolean upToCenter = false;
            private boolean isShift = false;

            @Override
            public void onLeftUp() {
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_W, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_A, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_S, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_D, 0, false);
            }

            @Override
            public void onUp() {
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_W, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_A, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_S, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_D, 0, false);
            }

            @Override
            public void onRightUp() {
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_W, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_D, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_A, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_S, 0, false);
            }

            @Override
            public void onLeft() {
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_A, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_W, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_S, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_D, 0, false);
            }

            @Override
            public void onCenter(boolean direct) {
                if (direct) {
                    isShift = !isShift;
                    BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_Shift_L, 0, true);
                    BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_W, 0, false);
                    BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_A, 0, false);
                    BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_S, 0, false);
                    BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_D, 0, false);
                } else {
                    upFromCenter = true;
                }
            }

            @Override
            public void onRight() {
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_D, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_W, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_A, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_S, 0, false);
            }

            @Override
            public void onLeftDown() {
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_A, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_S, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_W, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_D, 0, false);
            }

            @Override
            public void onDown() {
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_S, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_W, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_A, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_D, 0, false);
            }

            @Override
            public void onRightDown() {
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_S, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_D, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_W, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_A, 0, false);
            }

            @Override
            public void onSlipLeftUp() {
                System.out.println("左上");
            }

            @Override
            public void onSlipUp() {
                System.out.println("上");
            }

            @Override
            public void onSlipRightUp() {
                System.out.println("右上");
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_E, 0, true);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_E, 0, false);
            }

            @Override
            public void onSlipLeft() {
                System.out.println("左");
            }

            @Override
            public void onSlipRight() {
                System.out.println("右");
            }

            @Override
            public void onSlipLeftDown() {
                System.out.println("左下");
                showKeyboard();
            }

            @Override
            public void onSlipDown() {
                System.out.println("下");
            }

            @Override
            public void onSlipRightDown() {
                System.out.println("右下");
            }

            @Override
            public void onUpToCenter() {
//            if (!禁用手势){
//
//            }
                upToCenter = true;
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_space, 0, true);
            }

            @Override
            public void onFingerUp() {
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_W, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_A, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_S, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_D, 0, false);
                BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_Shift_L, 0, false);
                if (upToCenter) {
                    BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_space, 0, false);
                    upToCenter = false;
                }

            }
        });
    }

    public void showKeyboard() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        mainTextureView.requestFocusFromTouch();
        mainTextureView.requestFocus();
    }

    private void openH2CO3CustomControls() {
        h2co3CustomManager.自定义开关();
        h2CO3CrossingKeyboard.自定义();
        navDrawer.getMenu().clear();
        navDrawer.inflateMenu(cosine.boat.R.menu.menu_mio);
        navDrawer.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == cosine.boat.R.id.menu_mio_add) {
                if (h2co3CustomManager.获取按键选择模式()) {
                    h2co3CustomManager.设置按键选择模式(false);
                    if (h2co3CustomManager.获取按键修改模式()) {
                        h2co3CustomManager.自定义按键对话框(false);
                        if (h2co3CustomManager.获取当前按键().get按键类型().equals("显隐控制按键")) {
                            for (String 标识 : h2co3CustomManager.获取当前按键().get目标按键标识()) {
                                MioCustomButton b = h2co3CustomManager.获取按键(标识);
                                b.setTextColor(Color.parseColor(b.get文本颜色()));
                                b.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        h2co3CustomManager.自定义按键对话框(true);
                    }
                } else {
                    h2co3CustomManager.自定义按键对话框(true);
                }
            } else if (item.getItemId() == cosine.boat.R.id.menu_mio_save) {
                h2co3CustomManager.自定义开关();
                h2CO3CrossingKeyboard.自定义();
                mshe.putFloat("方向键x", h2CO3CrossingKeyboard.getX());
                mshe.putFloat("方向键y", h2CO3CrossingKeyboard.getY());
                mshe.putFloat("方向键缩放", h2CO3CrossingKeyboard.mScale);
                mshe.commit();
                navDrawer.getMenu().clear();
                navDrawer.inflateMenu(cosine.boat.R.menu.menu_boat_overlay);
                navDrawer.setNavigationItemSelectedListener(gameActionListener);
            }
            return true;
        });
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

    @SuppressLint("ClickableViewAccessibility")
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

    @SuppressLint("HandlerLeak")
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
                    BoatActivity.this.finish();
                    break;
            }
        }
    }


}



