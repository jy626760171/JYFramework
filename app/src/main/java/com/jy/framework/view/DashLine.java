package com.jy.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.jy.framework.R;

/**
 * Created by jiangy on 2015/12/1.
 */
public class DashLine extends View {
	private Paint mPaint;
	// 虚线实线部分宽度
	private float dashWidth;
	// 虚线水平时的高度，竖直时的宽度
	private float dashStrokenWidth;
	// 虚线空白部分宽度
	private float dashGap;
	// 虚线颜色
	private int dashColor;
	// 虚线方向
	private int dashOrientation;

	private Path linePath;

	private PathEffect linePathEffect;

	public static final int vertical = 0;
	public static final int horizontal = 1;

	public DashLine(Context context) {
		this(context, null);
	}

	public DashLine(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DashLine(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DashLine);
		dashWidth = ta.getDimension(R.styleable.DashLine_dashWidth, 20f);
		dashGap = ta.getDimension(R.styleable.DashLine_dashGap, 15f);
		dashColor = ta.getColor(R.styleable.DashLine_dashColor, Color.BLACK);
		dashStrokenWidth = ta.getDimension(R.styleable.DashLine_dashStrokenWidth, 5f);
		dashOrientation = ta.getInt(R.styleable.DashLine_dashOrientation, horizontal);
		ta.recycle();

		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(dashColor);
		mPaint.setStrokeWidth(dashStrokenWidth);
		linePath = new Path();
		float[] arrayOfFloat = {dashWidth, dashGap, dashWidth, dashGap};
		linePathEffect = new DashPathEffect(arrayOfFloat, 1f);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		switch (dashOrientation) {
			case horizontal:
				linePath.moveTo(0.0f, 0.0f);
				linePath.lineTo(getWidth(), 0.0f);
				break;
			case vertical:
				linePath.moveTo(0.0f, 0.0f);
				linePath.lineTo(0.0f, getHeight());
				break;
		}

		mPaint.setPathEffect(linePathEffect);
		canvas.drawPath(linePath, mPaint);
	}
}
