package cosine.boat;


import static cosine.boat.BoatInput.setEventPipe;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.os.Bundle;
import android.app.Activity;
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
import com.mojang.minecraftpe.TextInputProxyEditTextbox;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import cosine.boat.logcat.BoatTask;


public abstract class BoatActivityBase extends Activity implements TextureView.SurfaceTextureListener,View.OnTouchListener{

	private static final String TAG = "BoatActivityBase";
	private RelativeLayout baseLayout;
	public TextureView textureView;
	public com.mojang.minecraftpe.TextInputProxyEditTextbox textInputWidget;

	public abstract void findViews();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		findViews();
		// Get and check for he required Boat argument
		setContentView(R.layout.overlay);
		baseLayout = findViewById(R.id.base_layout);
		// Set the SurfaceHolder callback to this class
		// to get the ANativeWindow instance
		textureView = findViewById(R.id.main_surface);
		textureView.setSurfaceTextureListener(this);
		
		int height = getWindowManager().getDefaultDisplay().getHeight();
		int width = getWindowManager().getDefaultDisplay().getWidth();	
		int scale = 1;	
		while(width / (scale + 1) >= 320 && height / (scale + 1) >= 240) {
			scale++;
		}

		this.textureView.setSurfaceTextureListener(this);
		//this.mainTextureView.setOpaque(false);
		this.textureView.setOnTouchListener(this);

		this.textInputWidget.setFocusable(true);
		this.textInputWidget.setFocusableInTouchMode(true);
		this.textInputWidget.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
		this.textInputWidget.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_DONE);

		this.textInputWidget.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				BoatInput.pushEventKey(BoatKeycodes.BOAT_KEYBOARD_KP_Enter, '\n', true);
				BoatInput.pushEventKey(BoatKeycodes.BOAT_KEYBOARD_KP_Enter, '\n', false);
				return false;

			}
		});
		this.textInputWidget.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void afterTextChanged(Editable s) {
				String newText = s.toString();
				if (newText.length() > 0){
					for(int i = 0; i < newText.length(); i++){
						BoatInput.pushEventKey(0, newText.charAt(i), true);
						BoatInput.pushEventKey(0, newText.charAt(i), false);
					}

					BoatActivityBase.this.textInputWidget.setText("");
				}
			}
		});
		this.textInputWidget.setOnMCPEKeyWatcher(new TextInputProxyEditTextbox.MCPEKeyWatcher() {
			public void onDeleteKeyPressed() {
				BoatActivityBase.this.runOnUiThread(new Runnable() {
					public void run() {
						BoatInput.pushEventKey(BoatKeycodes.BOAT_KEYBOARD_BackSpace, 0, true);
						BoatInput.pushEventKey(BoatKeycodes.BOAT_KEYBOARD_BackSpace, 0, false);
					}
				});
			}

			public void onBackKeyPressed() {
				BoatActivityBase.this.runOnUiThread(new Runnable() {
					public void run() {
						hideKeyboard();
					}
				});
			}
		});
		
		//popupWindow.setContentView(base);

		
	}



	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		if (cursorMode == BoatInput.CursorDisabled){
		BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_Escape, 0, true);
		}else{
		}
		//popupWindow.dismiss();
	}

	private native boolean nIsLoaded();
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		// TODO: Implement this method
		super.onWindowFocusChanged(hasFocus);
		/*
		if (hasFocus){
			popupWindow.showAtLocation(BoatActivityBase.this.getWindow().getDecorView(),Gravity.TOP|Gravity.LEFT,0,0);	

		}
		*/

	}

	public static native void setBoatNativeWindow(Surface surface);
	static {
		System.loadLibrary("boat");
		
	}
	public void hideKeyboard() {
		BoatActivityBase.this.textBoxShowing = false;
		BoatActivityBase.this.textInputWidget.setVisibility(View.GONE);
	}

	public int cursorMode = BoatInput.CursorEnabled;

	@Override
	public void onBackPressed() {
		BoatInput.pushEventMessage(BoatInput.CloseRequest);
	}

	private BoatTask task;

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
		setBoatNativeWindow(new Surface(surface));
		setEventPipe();

		Map<String, String> envvars = new HashMap<>();
		envvars.put(BoatScript.BOAT_ENV_WINDOW_WIDTH, Integer.toString(width));
		envvars.put(BoatScript.BOAT_ENV_WINDOW_HEIGHT, Integer.toString(height));
		envvars.put(BoatScript.BOAT_ENV_TMPDIR, this.getCacheDir().getAbsolutePath());
		this.task = new BoatTask(envvars, getIntent().getStringExtra(BoatTask.BOAT_TASK_SCRIPT_PATH)) {
			@Override
			public void afterExecute() {
				BoatActivityBase.this.finish();
			}
			@Override
			public void run(){
				LauncherConfig config = LauncherConfig.fromFile(getIntent().getExtras().getString("config"));
				LoadMe.exec(config);
				Message msg = new Message();
				msg.what = -1;
			}
		};
		this.task.startTask();
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
		BoatInput.pushEventWindow(width, height);
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		return false;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// TODO: Implement this method
	}

	public abstract void setCursorViewPos(float x, float y);
	public abstract void setCursorViewMode(int mode);

	private int initialX;
	private int initialY;
	private int baseX;
	private int baseY;
	public boolean textBoxShowing = false;

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if (view == textureView) {
			if (textBoxShowing != false) {
				hideKeyboard();
			}
			int currentX = (int)event.getX();
			int currentY = (int)event.getY();
			if (cursorMode == BoatInput.CursorDisabled) {
				switch(event.getActionMasked()) {
					case MotionEvent.ACTION_DOWN:
						initialX = currentX;
						initialY = currentY;
					case MotionEvent.ACTION_MOVE:
						BoatInput.pushEventPointer(baseX + currentX -initialX, baseY + currentY - initialY);
						break;
					case MotionEvent.ACTION_UP:
						baseX += (currentX - initialX);
						baseY += (currentY - initialY);
						BoatInput.pushEventPointer(baseX, baseY);
						break;
					default:
						break;
				}
			}
			else if (cursorMode == BoatInput.CursorEnabled) {
				baseX = currentX;
				baseY = currentY;
				BoatInput.pushEventPointer(baseX, baseY);
			}
			setCursorViewPos(event.getX(), event.getY());
			return true;
		}
		return false;
	}
	public void showKeyboard() {
		BoatActivityBase.this.textBoxShowing = true;
		BoatActivityBase.this.textInputWidget.setVisibility(View.VISIBLE);
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) new RelativeLayout.LayoutParams(BoatActivityBase.this.textInputWidget.getLayoutParams());
		lp.leftMargin = baseX;
		lp.topMargin = baseY;
		BoatActivityBase.this.textInputWidget.setLayoutParams(lp);

		BoatActivityBase.this.textInputWidget.postDelayed(new Runnable() {
			public void run() {
				MotionEvent event = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, 0.0f, 0.0f, 0);
				BoatActivityBase.this.textInputWidget.dispatchTouchEvent(event);
				event.recycle();
				MotionEvent event2 = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, 0.0f, 0.0f, 0);
				BoatActivityBase.this.textInputWidget.dispatchTouchEvent(event2);
				event2.recycle();
				BoatActivityBase.this.textInputWidget.setSelection(BoatActivityBase.this.textInputWidget.length());
			}
		}, 200);
	}
	
}



