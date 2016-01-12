package com.jy.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.jy.framework.R;

public class OvalProgressView extends View {

	private static final String PERCENT_SYMBOL = "%";

	/**
	 * 画笔对象的引用
	 */
	private Paint mPaint;

	/**
	 * 圆环的颜色
	 */
	private int strokenColor;
	/**
	 * 圆环线宽
	 */
	private float strokenWidth;
	/**
	 * 圆环半径
	 */
	private float radius;

	/**
	 * 圆环进度的颜色
	 */
	private int progressColor;
	/**
	 * 最大进度
	 */
	private float max;
	/**
	 * 当前进度
	 */
	private float progress;
	/**
	 * 中间进度百分比的字符串的颜色
	 */
	private int textColor;
	/**
	 * 中间进度百分比的字符串的字体大小
	 */
	private float textSize;

	/**
	 * 是否显示中间的进度
	 */
	private boolean textIsDisplayable;

	/**
	 * 进度的风格，实心或者空心
	 */
	private int style;

	public static final int STROKE = 0;
	public static final int FILL = 1;

	private Point center;

	public OvalProgressView(Context context) {
		this(context, null);
	}

	public OvalProgressView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public OvalProgressView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.OvalProgressView);
		// 获取自定义属性和默认值
		strokenColor = ta.getColor(R.styleable.OvalProgressView_strokenColor, Color.RED);
		strokenWidth = ta.getDimension(R.styleable.OvalProgressView_strokenWidth, 10f);
		radius = ta.getDimension(R.styleable.OvalProgressView_radius, 0f);
		progressColor = ta.getColor(R.styleable.OvalProgressView_progressColor, Color.GREEN);
		textColor = ta.getColor(R.styleable.OvalProgressView_textColor, Color.BLUE);
		textSize = ta.getDimension(R.styleable.OvalProgressView_textSize, 30f);
		max = ta.getFloat(R.styleable.OvalProgressView_max, 100f);
		textIsDisplayable = ta.getBoolean(R.styleable.OvalProgressView_textIsDisplayable, true);
		style = ta.getInt(R.styleable.OvalProgressView_style, STROKE);

		ta.recycle();
		init();
	}

	private String hint;
	private float hint_length;

	private void init() {
		mPaint = new Paint();
		mPaint.setTextSize(textSize);
		mPaint.setAntiAlias(true);
		// 设置默认字样
		// hint = getResources().getString(R.string.hint);
		hint_length = mPaint.measureText(hint);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (radius == 0) {
			radius = (getWidth() - strokenWidth) / 2;
		}

		if (center == null) {
			center = new Point();
			center.x = getWidth() / 2;
			center.y = getHeight() / 2;
		}
		/**
		 * 画最外层的大圆环
		 */
		mPaint.setColor(strokenColor); // 设置圆环的颜色
		mPaint.setStyle(Paint.Style.STROKE); // 设置空心
		mPaint.setStrokeWidth(strokenWidth); // 设置圆环的宽度
		mPaint.setAntiAlias(true); // 消除锯齿
		canvas.drawCircle(center.x, center.y, radius, mPaint); // 画出圆环

		/**
		 * 画圆弧 ，画圆环的进度
		 */
		mPaint.setColor(progressColor); // 设置进度的颜色
		RectF oval = new RectF(center.x - radius, center.y - radius, center.x + radius, center.y + radius); // 用于定义的圆弧的形状和大小的界限

		switch (style) {

		case STROKE:
			mPaint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval, -90, 360 * progress / max, false, mPaint); // 根据进度画圆弧
			break;

		case FILL:
			mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			if (progress >= 0) canvas.drawArc(oval, -90, 360 * progress / max, true, mPaint); // 根据进度画圆弧
			break;

		}

		/**
		 * 画出文字
		 */
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(textColor);
		StringBuffer percent = new StringBuffer().append(progress).append(PERCENT_SYMBOL);
		float percent_length = mPaint.measureText(percent.toString());
		canvas.drawText(percent.toString(), center.x - percent_length / 2, center.y, mPaint);
		canvas.drawText(hint, center.x - hint_length / 2, center.y + 0.5f * radius, mPaint);
	}

	public int getStrokenColor() {
		return strokenColor;
	}

	public void setStrokenColor(int strokenColor) {
		this.strokenColor = strokenColor;
	}

	public int getProgressColor() {
		return progressColor;
	}

	public void setProgressColor(int progressColor) {
		this.progressColor = progressColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getStrokenWidth() {
		return strokenWidth;
	}

	public void setStrokenWidth(float strokenWidth) {
		this.strokenWidth = strokenWidth;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public boolean isTextIsDisplayable() {
		return textIsDisplayable;
	}

	public void setTextIsDisplayable(boolean textIsDisplayable) {
		this.textIsDisplayable = textIsDisplayable;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
		// postInvalidate();
	}

}
