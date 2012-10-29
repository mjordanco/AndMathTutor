package com.jorflekel.mathtutor;

import android.content.res.Resources;

public class Ball extends DrawnItem {

	public Ball(int diameter, int left, int top,
			Resources resources) {
		super(left, top, left + diameter, top + diameter, R.drawable.ball, resources);
	}

}
