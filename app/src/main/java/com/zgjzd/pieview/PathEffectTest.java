package com.zgjzd.pieview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2017/6/15.
 */

public class PathEffectTest extends View {

    // 实例化画笔
    private Paint mPaint = null;
    private Path mPath;// 路径对象
    private Context mContext;

    public PathEffectTest(Context context) {
        super(context);
    }

    /**
     * 当你要给view添加attribute的时候就需要用到这个构造
     *
     * @param context
     * @param attrs
     */
    public PathEffectTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        // 初始化画笔
        initPaint();
        initPath();
    }

    private void initPaint() {
        // 实例化画笔并打开抗锯齿
        // mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setPathEffect(new CornerPathEffect(100));
    }

    private void initPath() {
        // 实例化路径
        mPath = new Path();
        // 定义路径的起点
        mPath.moveTo(10, 50);

        // 定义路径的各个点
        for (int i = 0; i <= 30; i++) {
            mPath.lineTo(i * 35, (float) (Math.random() * 100));
        }
    }


    /*
     * 绘制view时调用的方法，可能会出现多次调用，所以不建议在这里面实例化对象，也就是不要出现new
     *
     * @param canvas 一个画布对象，我们可以用paint在上面画画
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        canvas.drawPath(mPath, mPaint);
    }
}
