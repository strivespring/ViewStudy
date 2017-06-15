package com.zgjzd.pieview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */

public class LineView extends View {


    private Paint mPaint;
    private float defualtPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
    private float defualtTextsize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;
    private List<Float> datas;
    private int mXDeviation;
    private int mYCount = 6;
    private Paint mTextPaint;
    private List<String> descriptions;
    private float borderstrokeWidth = 10;
    private float textHeight;
    private float mTextScale;
    private float mYAverage;
    private int mYOffset;
    private float mXOffset;
    private List<Point> points = new ArrayList<>();
    private Paint mLinePaint;
    private Path mLinePath;
    private int selectIndex = -1;
    private Paint mCirclePaint;

    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();

    }

    /**
     * 初始化
     *
     * @param measureWidth
     * @param measureHeight
     */
    private void initCoordinatePoint(int measureWidth, int measureHeight) {
        mLeft = (int) (getPaddingLeft() + defualtPadding + mTextScale);
        mTop = (int) (getPaddingTop() + defualtPadding);
        mRight = measureWidth - getPaddingRight();
        mBottom = (int) (measureHeight - getPaddingBottom() - defualtPadding);
        int borderWidth = mRight - mLeft;
        int borderHeight = mBottom - mTop;
        mYOffset = borderHeight / mYCount;
        mXOffset = borderWidth / descriptions.size();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //边框画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(borderstrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        //文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(defualtTextsize);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextScale = mTextPaint.getTextSize() * 2;
        //折线画笔
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.RED);
        //增加线条圆角效果
//        mLinePaint.setPathEffect(new CornerPathEffect(50));
        mLinePath = new Path();

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(Color.RED);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        initCoordinatePoint(measureWidth, measureHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorderLine(canvas);
        drawBorderScale(canvas);
        drawData(canvas);
        drawPoints(canvas);
    }

    private void drawPoints(Canvas canvas) {
        mCirclePaint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            canvas.save();
            canvas.translate(point.x, point.y);
            if (i == selectIndex) {
                mCirclePaint.setColor(Color.GREEN);
            } else {
                mCirclePaint.setColor(Color.RED);
            }
            canvas.drawCircle(0, 0, 10, mCirclePaint);

            canvas.restore();
        }
    }

    /**
     * 画折线
     *
     * @param canvas
     */
    private void drawData(Canvas canvas) {

        float yScale;
        float xScale;
        points.clear();
        for (int i = 0; i < datas.size(); i++) {
            yScale = (datas.get(i) / mYAverage * mYOffset);
            xScale = mXOffset * (i + 1);
            if (i == 0)
                mLinePath.moveTo(xScale + mLeft, yScale);
            mLinePath.lineTo(xScale + mLeft, yScale);
            points.add(new Point((int) xScale + mLeft, (int) yScale));

        }
        canvas.drawPath(mLinePath, mLinePaint);

    }

    /**
     * 画边框刻度文字
     *
     * @param canvas
     */
    private void drawBorderScale(Canvas canvas) {

        for (int i = 0; i < mYCount; i++) {
            canvas.save(); //
            canvas.translate(mLeft, mBottom - (mYOffset * i));
            canvas.drawText(String.format("%.0f", mYAverage * i), -mTextPaint.getTextSize(), 0, mTextPaint);
            canvas.restore();

        }

        for (int i = 0; i < descriptions.size(); i++) {
            canvas.save(); //
            canvas.translate(mLeft + mXOffset * (i + 1), mBottom + mTextScale);
            canvas.drawText(descriptions.get(i), 0, textHeight, mTextPaint);
            canvas.restore();
            canvas.save();
        }

    }


    /**
     * 画边框
     *
     * @param canvas
     */
    private void drawBorderLine(Canvas canvas) {
        Path path = new Path();
        path.moveTo(mLeft, mTop);
        mXDeviation = (mRight - mLeft) / datas.size();
        path.lineTo(mLeft, mBottom);
        for (int i = 0; i <= datas.size(); i++) {
            path.lineTo(mXOffset * i + mLeft, mBottom);

        }
        canvas.drawPath(path, mPaint);
    }

    public void setData(List<Float> datas, List<String> descriptions) {
        this.datas = datas;
        this.descriptions = descriptions;
        float bigNumber = Collections.max(datas);
        mYAverage = (bigNumber / mYCount + 0.5F);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                touchIsAvailable(event);
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_MOVE:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;

        }
        return true;

    }

    private void touchIsAvailable(MotionEvent event) {
        float upX = event.getX();
        float upY = event.getY();
        float border = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        RectF rectF = new RectF(upX - border, upY - border, upX + border, upY + border);
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            if (rectF.contains(point.x, point.y)) {
                selectIndex = i;
                invalidate();
            }
        }

    }
}
