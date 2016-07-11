package com.framgia.hourpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class FancyHourPicker extends View {
    private static final int STARTING_POINT = 270;
    private int mFillColor;
    private int mBackgroundColor;
    private int mTextColor;
    private int mStrokeSize;
    private int mChoosenPoint = 15;
    private String mChoosenHour = "01:00";
    private int mRawHour = 1;
    private float mTextSize;
    private Paint mBackgroundPaint;
    private Paint mFillPaint;
    private Paint mTextPaint;
    private RectF mOvalRectF;


    public FancyHourPicker(Context context) {
        super(context);
        setup();
    }

    public FancyHourPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            readInitValues(context, attrs);
            setup();
        }
    }

    public FancyHourPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            readInitValues(context, attrs);
            setup();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        int radius = width > height ? height / 2 : width / 2;
        mOvalRectF.set(width - radius, height - radius, width + radius, height + radius);
        canvas.drawCircle(width, height, radius, mBackgroundPaint);
        canvas.drawArc(mOvalRectF, STARTING_POINT, mChoosenPoint, false, mFillPaint);
        canvas.drawText(mChoosenHour, canvas.getWidth() / 2, ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2)), mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mChoosenPoint = (int) (Math.toDegrees(Math.atan2(getWidth() - pointX, getHeight() - pointY)));
                Log.i("TAG", mChoosenPoint + " ");
                invalidate();
        }
        return super.onTouchEvent(event);

    }

    private void readInitValues(Context context, AttributeSet attrs) {
        TypedArray typedArray =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.FancyHourPicker, 0, 0);
        try {
            mFillColor = typedArray.getInteger(R.styleable.FancyHourPicker_fill_color, R.color.fillColor);
            mBackgroundColor =
                    typedArray.getInteger(R.styleable.FancyHourPicker_background_color, R.color.backgroundColor);
            mTextColor =
                    typedArray.getInteger(R.styleable.FancyHourPicker_text_color, R.color.backgroundColor);
            mTextSize = typedArray.getDimension(R.styleable.FancyHourPicker_text_size, 100);
            mStrokeSize = typedArray.getInteger(R.styleable.FancyHourPicker_stroke_size, 10);
        } finally {
            typedArray.recycle();
        }
    }

    private void setup() {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(mBackgroundColor);
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStrokeWidth(mStrokeSize);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeJoin(Paint.Join.ROUND);
        mBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        mFillPaint = new Paint();
        mFillPaint.setColor(mFillColor);
        mFillPaint.setAntiAlias(true);
        mFillPaint.setStrokeWidth(mStrokeSize);
        mFillPaint.setStyle(Paint.Style.STROKE);
        mFillPaint.setStrokeJoin(Paint.Join.ROUND);
        mFillPaint.setStrokeCap(Paint.Cap.SQUARE);

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mOvalRectF = new RectF();
    }

    public void setHour(int hour) {
        if (hour >= 0 && hour < 24) {
            mChoosenPoint = convertHourToPoint(hour);
            mRawHour = hour;
            if (hour < 10) mChoosenHour = "0" + hour + ":00";
            else mChoosenHour = hour + ":00";
            invalidate();
        }
    }

    public int getHour() {
        return mRawHour;
    }

    private int convertHourToPoint(int hour) {
        return hour * 15;
    }
}
