package org.koishi.h2co3.mclauncher.customcontrol;
import android.content.*;
import android.util.*;
import android.view.*;

public class ControlLayout extends TextureView
{
	private boolean mModifiable;
	private boolean mControlVisible = false;
    
	public ControlLayout(Context ctx) {
		super(ctx);
	}

	public ControlLayout(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
	}


	public boolean getModifiable(){
		return mModifiable;
	}
}
