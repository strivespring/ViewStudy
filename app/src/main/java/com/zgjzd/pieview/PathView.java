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

public class PathView extends View {

    private final Path mDst;
    private Paint mPaint;
    private float mPathLength;
    private float mPercent;
    private PathMeasure mPathMeasure;

    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.addCircle(400,400,100, Path.Direction.CW);
        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(path,true);
        mPathLength = mPathMeasure.getLength();
        mDst = new Path();
        startAnimation();
    }

    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
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
        mDst.reset();
        mDst.lineTo(0,0);
        float stop = mPathLength*mPercent;
        float start = (float) (stop - ((0.5 - Math.abs(mPercent - 0.5)) * mPathLength));
        mPathMeasure.getSegment(start,stop,mDst,true);
        canvas.drawPath(mDst,mPaint);

    }
}
