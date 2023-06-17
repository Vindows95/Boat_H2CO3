package org.koishi.h2co3.mclauncher.customcontrol;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import cosine.boat.R;

public class MioSelectButton extends androidx.appcompat.widget.AppCompatButton {

    public MioSelectButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MioSelectButton);
        setKey(array.getString(R.styleable.MioSelectButton_key));
        array.recycle();
    }

    private String key;

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

  
}
