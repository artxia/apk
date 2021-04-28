package com.zmovie.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by owen on 2016/10/20.
 */

public class RoundFrameLayout extends FrameLayout {

    private float mTopLeftRadius;
    private float mTopRightRadius;
    private float mBottomLeftRadius;
    private float mBottomRightRadius;

    private boolean mIsDrawRound;
    private Path mRoundPath;
    private Paint mRoundPaint;
    private RectF mRoundRectF;

    public RoundFrameLayout(Context context) {
        this(context, null);
    }

    public RoundFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundFrameLayout);
            float radius = ta.getDimension(R.styleable.RoundFrameLayout_rfl_radius, 0);
            mTopLeftRadius = ta.getDimension(R.styleable.RoundFrameLayout_topLeftRadius, radius);
            mTopRightRadius = ta.getDimension(R.styleable.RoundFrameLayout_topRightRadius, radius);
            mBottomLeftRadius = ta.getDimension(R.styleable.RoundFrameLayout_bottomLeftRadius, radius);
            mBottomRightRadius = ta.getDimension(R.styleable.RoundFrameLayout_bottomRightRadius, radius);
            ta.recycle();
        }
        mIsDrawRound = mTopLeftRadius != 0 || mTopRightRadius != 0 || mBottomLeftRadius != 0 || mBottomRightRadius != 0;

        mRoundPaint = new Paint();
        mRoundPaint.setAntiAlias(true);
        // 取两层绘制交集。显示下层。
        mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if((w != oldw || h != oldh) && mIsDrawRound) {
            final Path path = new Path();
            mRoundRectF = new RectF(0, 0, w, h);
            final float[] radii = new float[]{
                    mTopLeftRadius, mTopLeftRadius,
                    mTopRightRadius, mTopRightRadius,
                    mBottomRightRadius, mBottomRightRadius,
                    mBottomLeftRadius, mBottomLeftRadius};
            path.addRoundRect(mRoundRectF, radii, Path.Direction.CW);
            mRoundPath = path;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(!mIsDrawRound) {
            super.draw(canvas);
        } else {
            final int count = canvas.save();
            final int count2 = canvas.saveLayer(mRoundRectF, null, Canvas.ALL_SAVE_FLAG);
            super.draw(canvas);
            canvas.drawPath(mRoundPath, mRoundPaint);
            if(count2 > 0)
                canvas.restoreToCount(count2);
            canvas.restoreToCount(count);
        }
    }

}
