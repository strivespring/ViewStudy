package com.zgjzd.pieview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2017/6/15.
 */

public class PathEffectView extends View {
    private   Paint mPaint;
    private float mLength;

    public PathEffectView(Context context) {
        this(context,null);
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(100,100);
        path.lineTo(300,400);
        path.lineTo(600,300);
        path.close();
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path,false);
        mLength = pathMeasure.getLength();
    }
    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               float  percent = (float) animation.getAnimatedValue();
//                Effect
                invalidate();
            }
        });
        animator.setDuration(10000);
        animator.setRepeatCount(-1);
        animator.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
