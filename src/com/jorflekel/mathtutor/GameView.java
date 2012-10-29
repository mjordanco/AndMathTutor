package com.jorflekel.mathtutor;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

	private Paint paint;

	private DrawnItem leftBoxDrawnItem, rightBoxDrawnItem, bottomBoxDrawnItem,
			leftPipeDrawnItem, rightPipeDrawnItem;

	private OperationDrawnItem operationDrawnItem;

	private List<DrawnItem> staticCrates, addedCrates, leftBalls, rightBalls;

	private Crate currentCrate;
	private int currentCrateIndex = -1;

	private int previousTouchX, previousTouchY;
	
	private int[] problem;

	public GameView(Context context) {
		super(context);

		staticCrates = new ArrayList<DrawnItem>();
		addedCrates = new ArrayList<DrawnItem>();
		leftBalls = new ArrayList<DrawnItem>();
		rightBalls = new ArrayList<DrawnItem>();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (paint == null)
			paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(getHeight() * .1f);
		leftBoxDrawnItem.draw(canvas);
		rightBoxDrawnItem.draw(canvas);
		bottomBoxDrawnItem.draw(canvas);
		operationDrawnItem.draw(canvas);
		leftPipeDrawnItem.draw(canvas);
		rightPipeDrawnItem.draw(canvas);

		for (DrawnItem item : addedCrates) {
			item.draw(canvas);
		}

		for (DrawnItem item : staticCrates) {
			item.draw(canvas);
		}

		if (currentCrate != null) {
			currentCrate.draw(canvas);
		}
		
		for(DrawnItem item : leftBalls) {
			item.draw(canvas);
		}
		
		for(DrawnItem item : rightBalls) {
			item.draw(canvas);
		}
		
		canvas.drawText("" + problem[0], leftBoxDrawnItem.left() + leftBoxDrawnItem.width() / 2, leftBoxDrawnItem.top(), paint);
		canvas.drawText("" + problem[1], rightBoxDrawnItem.left() + rightBoxDrawnItem.width() / 2, rightBoxDrawnItem.top(), paint);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (changed) {
			initialize();
		}
	}

	private void initialize() {
		int w = getWidth();
		int h = getHeight();
		leftBoxDrawnItem = new DrawnItem((int) (w * .15f), (int) (h * .15f),
				(int) (w * .4f), (int) (h * .4f), R.drawable.droploc,
				getResources());
		rightBoxDrawnItem = new DrawnItem((int) (w * .6f), (int) (h * .15f),
				(int) (w * .85f), (int) (h * .4f), R.drawable.droploc,
				getResources());
		bottomBoxDrawnItem = new DrawnItem((int) (w * .15f), (int) (h * .5f),
				(int) (w * .85f), (int) (h * .85f), R.drawable.droploc,
				getResources());
		operationDrawnItem = new OperationDrawnItem((int) (w * .45f),
				(int) (h * .275 - w * .05f), (int) (w * .55f),
				(int) (h * .275 + w * .05f), R.drawable.droploc,
				getResources(), R.drawable.plus);
		leftPipeDrawnItem = new DrawnItem((int) (w * 0), (int) (h * .15f),
				(int) (w * .1f), (int) (h * .85f), R.drawable.droploc,
				getResources());
		rightPipeDrawnItem = new DrawnItem((int) (w * .9f), (int) (h * .15f),
				(int) (w * 1), (int) (h * .85f), R.drawable.droploc,
				getResources());

		int crateSize = (int) (w * .1f);
		for (int i = (int) (h * .15f); i < (int) (h * .85f); i += crateSize) {
			staticCrates.add(new Crate(crateSize, 0, i, getResources()));
		}
		for (int i = (int) (h * .15f) + crateSize / 2; i < (int) (h * .85f)
				- crateSize / 2; i += crateSize) {
			staticCrates.add(new Crate(crateSize, (int) (-.33f * crateSize), i,
					getResources()));
		}

		for (int i = (int) (h * .15f); i < (int) (h * .85f); i += crateSize) {
			staticCrates.add(new Crate(crateSize, (int) (w * .9f), i,
					getResources()));
		}
		for (int i = (int) (h * .15f) + crateSize / 2; i < (int) (h * .85f)
				- crateSize / 2; i += crateSize) {
			staticCrates.add(new Crate(crateSize, (int) (w * .9f)
					+ (int) (.33f * crateSize), i, getResources()));
		}
		problem = generateAdditionProblem();
		
		for(int i = 0; i < problem[0]; i++) {
			int diameter = leftBoxDrawnItem.width() / 7;
			leftBalls.add(new Ball(diameter, leftBoxDrawnItem.left() + diameter + diameter * (i % 5), leftBoxDrawnItem.bottom() - diameter * (i / 5 + 1) - 10, getResources()));
		}
		
		for(int i = 0; i < problem[1]; i++) {
			int diameter = rightBoxDrawnItem.width() / 7;
			rightBalls.add(new Ball(diameter, rightBoxDrawnItem.left() + diameter + diameter * (i % 5), rightBoxDrawnItem.bottom() - diameter * (i / 5 + 1) - 10, getResources()));
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			if (leftPipeDrawnItem.contains(x, y)
					|| rightPipeDrawnItem.contains(x, y)) {
				int crateSize = (int) (getWidth() * .1f);
				currentCrate = new Crate(true, crateSize, x, y, getResources());
				previousTouchX = x;
				previousTouchY = y;
			} 
			if(bottomBoxDrawnItem.contains(x, y) && addedCrates.size() > 0) {
				int crateSize = (int) (getWidth() * .1f);
				currentCrate = new Crate(true, crateSize, x, y, getResources());
				addedCrates.remove(addedCrates.size() - 1);
				previousTouchX = x;
				previousTouchY = y;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (currentCrate != null) {
				currentCrate.offset(x - previousTouchX, y - previousTouchY);
				previousTouchX = x;
				previousTouchY = y;
			}
			break;
		case MotionEvent.ACTION_UP:
			if(currentCrate != null) {
				if (bottomBoxDrawnItem.contains(x, y)) {
					int crateSize = (int) (bottomBoxDrawnItem.width() / 8);
					if (addedCrates.size() < 10){
						addedCrates.add(new Crate(crateSize, (int)(bottomBoxDrawnItem.left() 
								+ (addedCrates.size() % 5 + 1.5) * crateSize), bottomBoxDrawnItem.bottom()
								- crateSize * (int) (addedCrates.size() / 5 + 1) - 10,
								getResources()));
					}
				}
			}
			currentCrate = null;
			return false;
		}
		invalidate();
		return true;
	}
	
	private static int[] generateAdditionProblem() {
		int first = (int)(Math.random() * 8) + 1;
		int second = (int)(Math.random() * (8)) + 1;
		while(first + second > 10) {
			second = (int)(Math.random() * (8)) + 1;
		}
		return new int[] {first, second};
	}
	
	private static int[] generateSubtractionProblem() {
		int first = (int)(Math.random() * 9) + 1;
		int second = (int)(Math.random() * (8 - first)) + 1;
		return new int[] {first, second};
	}

}
