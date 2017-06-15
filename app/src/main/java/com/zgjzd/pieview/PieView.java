package com.zgjzd.pieview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;


public class PieView extends View {

    private int mWidth;
    private List<Integer> colors;
    private int dataSum;
    private PathMeasure mMeasure;
    private Paint mPaint;
    private float mAnimatorValue = 0;
    private List<Float> degrees = new ArrayList<>();
    private RectF mRectF;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    private void initPath() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);

        int circleX = mWidth / 2;
        mRectF = new RectF(-circleX, -circleX, circleX, circleX);


        mMeasure = new PathMeasure();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w - getPaddingLeft() - getPaddingRight();
        initPath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (degrees != null)
            drawValus(canvas);
    }


    private void drawValus(Canvas canvas) {
        canvas.translate(getWidth() / 2, getHeight() / 2);


        if (mMeasure != null) {
            for (int i = 0; i < degrees.size(); i++) {
                //如果不设置颜色则产生随机颜色
                mPaint.setColor(colors.get(i));
                canvas.drawArc(mRectF, getStart(i) * mAnimatorValue * 360, degrees.get(i) * mAnimatorValue * 360, true, mPaint);
            }

        }


    }

    private float getStart(int i) {
        float sum = 0;
        for (int j = 0; j < i; j++) {
            sum += degrees.get(j);
        }
        return sum;
    }


    public void setDatas(List<Float> datas, List<Integer> colors) {
        for (Float data : datas) {
            dataSum += data;
        }
        this.colors = colors;
        data2Degree(datas);


    }


    private void data2Degree(List<Float> datas) {
        for (Float data : datas) {
            float currentDegree = data / dataSum;
            degrees.add(currentDegree);
        }

    }


    public void startAnimation() {
        ValueAnimator anim = ValueAnimator.ofFloat(0.2F, 1);
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        anim.start();
    }
}
