package com.jorflekel.mathtutor;

import android.content.res.Resources;

public class Crate extends DrawnItem {

	
	public Crate(int width, int left, int top, Resources resources) {
		super(left, top, left + width, top + width, R.drawable.crate, resources);
	}
	
	public Crate(boolean center, int width, int left, int top, Resources resources) {
		super(left - width / 2, top - width / 2, left + width / 2, top + width / 2, R.drawable.crate, resources);
	}

}
