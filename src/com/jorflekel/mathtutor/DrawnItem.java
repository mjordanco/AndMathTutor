package com.jorflekel.mathtutor;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class DrawnItem {

	protected final Rect drawRect;
	private final Drawable drawable;
	
	public DrawnItem(int left, int top, int right, int bottom, int resId, Resources resources) {
		drawRect = new Rect(left, top, right, bottom);
		drawable = resources.getDrawable(resId);
	}
	
	public void draw(Canvas c) {
		drawable.setBounds(drawRect);
		drawable.draw(c);
	}
	
	public int height() {
		return drawRect.height();
	}
	
	public void offset(int dx, int dy) {
		drawRect.offset(dx, dy);
	}
	
	public boolean contains(int x, int y) {
		return drawRect.contains(x, y);
	}

	public int width() {
		return drawRect.width();
	}

	public int bottom() {
		return drawRect.bottom;
	}

	public int left() {
		return drawRect.left;
	}

	public float top() {
		return drawRect.top;
	}

}
