package com.jorflekel.mathtutor;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class OperationDrawnItem extends DrawnItem {

	Drawable operationDrawable;
	
	public OperationDrawnItem(int left, int top, int right, int bottom,
			int resId, Resources resources, int operationResId) {
		super(left, top, right, bottom, resId, resources);
		operationDrawable = resources.getDrawable(operationResId);
	}
	
	@Override
	public void draw(Canvas c) {
		super.draw(c);
		operationDrawable.setBounds(new Rect(drawRect.left + 10, drawRect.top + 10, drawRect.right - 10, drawRect.bottom - 10));
		operationDrawable.draw(c);
	}

}
