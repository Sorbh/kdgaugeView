package in.unicodelabs.kdgaugeview;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


public class KdGaugeView extends View {

    private Paint mSmallDotCirclePaint, mBigDotCirclePaint, mAlertDotCirclePaint, mProgressBarPaint, mSpeedTextPaint, mSpeedLimitTextPaint, mSpeedUnitPaint;

    private float mCircleCenterX, mCircleCenterY;
    private float mRadius;


    private float mMinSpeed = 0;
    private float mMaxSpeed = 0;
    private float mSafeSpeedLimit = 0;
    private float mSpeed = 0;
    private String unitOfMeasurement = "";
    private int mAnimationTime = 0;

    private float mSpeedTextSize;
    private float mSpeedUnitTextSize;
    private float mSpeedLimitTextSize;

    private float speedDialRingWidth;
    private float speedDialRingInnerPadding;

    private int dialActiveColor = Color.parseColor("#D3D3D3");
    private int dialInactiveColor = Color.parseColor("#E0E0E0");
    private int dialSpeedColor = Color.GREEN;
    private int dialSpeedAlertColor = Color.RED;
    private int subDivisionCircleColor = Color.DKGRAY;
    private int divisionCircleColor = Color.BLUE;
    private int speedTextColor = Color.BLACK;
    private int speedUnitTextColor = Color.BLACK;
    private int speedLimitTexteColor = Color.BLACK;


    private RectF progressBarRect = new RectF();


    private float mProgressBarCircleRadius;
    private float mDotedCircleRadius;

    private float mCurrentSpeed = 0;

    private ValueAnimator valueAnimator;
    private float mPerDegreeSpeed = 0;

    public KdGaugeView(Context context) {
        super(context);
    }

    public KdGaugeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public KdGaugeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KdGaugeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs == null)
            return;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.kdgaugeview);

        mMaxSpeed = a.getFloat(R.styleable.kdgaugeview_maxSpeed, 180);
        mMinSpeed = a.getFloat(R.styleable.kdgaugeview_minSpeed, 0);
        mSpeed = a.getFloat(R.styleable.kdgaugeview_speed, 0);
        mSafeSpeedLimit = a.getFloat(R.styleable.kdgaugeview_speed_limit, 60);
        mAnimationTime = a.getInt(R.styleable.kdgaugeview_animationTime, 500);

        unitOfMeasurement = a.getString(R.styleable.kdgaugeview_unitOfMeasurement);
        if (TextUtils.isEmpty(unitOfMeasurement)) unitOfMeasurement = "Km/Hr";


        mSpeedTextSize = a.getDimension(R.styleable.kdgaugeview_speedTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getDisplayMetrics()));
        mSpeedUnitTextSize = a.getDimension(R.styleable.kdgaugeview_unitOfMeasurementTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getDisplayMetrics()));
        mSpeedLimitTextSize = a.getDimension(R.styleable.kdgaugeview_speedLimitTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getDisplayMetrics()));

        speedDialRingWidth = a.getDimension(R.styleable.kdgaugeview_speedDialRingWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getDisplayMetrics()));
        speedDialRingInnerPadding = a.getDimension(R.styleable.kdgaugeview_speedDialRingInnerPadding, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getDisplayMetrics()));


        dialActiveColor = a.getColor(R.styleable.kdgaugeview_dialActiveColor, dialActiveColor);
        dialInactiveColor = a.getColor(R.styleable.kdgaugeview_dialInactiveColor, dialInactiveColor);
        dialSpeedColor = a.getColor(R.styleable.kdgaugeview_dialSpeedColor, dialSpeedColor);
        dialSpeedAlertColor = a.getColor(R.styleable.kdgaugeview_dialSpeedAlertColor, dialSpeedAlertColor);
        subDivisionCircleColor = a.getColor(R.styleable.kdgaugeview_subDivisionCircleColor, subDivisionCircleColor);
        divisionCircleColor = a.getColor(R.styleable.kdgaugeview_divisionCircleColor, divisionCircleColor);
        speedTextColor = a.getColor(R.styleable.kdgaugeview_speedTextColor, speedTextColor);
        speedUnitTextColor = a.getColor(R.styleable.kdgaugeview_unitOfMeasurementTextColor, speedUnitTextColor);
        speedLimitTexteColor = a.getColor(R.styleable.kdgaugeview_speedLimitTextColor, speedLimitTexteColor);


        mSmallDotCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallDotCirclePaint.setColor(subDivisionCircleColor);
        mSmallDotCirclePaint.setStyle(Paint.Style.FILL);

        mBigDotCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBigDotCirclePaint.setColor(divisionCircleColor);
        mBigDotCirclePaint.setStyle(Paint.Style.FILL);

        mAlertDotCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAlertDotCirclePaint.setColor(dialSpeedAlertColor);
        mAlertDotCirclePaint.setStyle(Paint.Style.FILL);

        mProgressBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressBarPaint.setColor(dialSpeedColor);
        mProgressBarPaint.setStyle(Paint.Style.STROKE);
        mProgressBarPaint.setStrokeWidth(speedDialRingWidth);

        mSpeedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSpeedTextPaint.setColor(speedTextColor);
        mSpeedTextPaint.setStyle(Paint.Style.FILL);
        mSpeedTextPaint.setTextAlign(Paint.Align.CENTER);
        mSpeedTextPaint.setTextSize(mSpeedTextSize);
        mSpeedTextPaint.setTypeface(Typeface.DEFAULT_BOLD);

        mSpeedUnitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSpeedUnitPaint.setColor(speedUnitTextColor);
        mSpeedUnitPaint.setStyle(Paint.Style.FILL);
        mSpeedUnitPaint.setTextAlign(Paint.Align.CENTER);
        mSpeedUnitPaint.setTextSize(mSpeedUnitTextSize);
        mSpeedUnitPaint.setTypeface(Typeface.DEFAULT_BOLD);

        mSpeedLimitTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSpeedLimitTextPaint.setColor(speedLimitTexteColor);
        mSpeedLimitTextPaint.setStyle(Paint.Style.FILL);
        mSpeedLimitTextPaint.setTextAlign(Paint.Align.LEFT);
        mSpeedLimitTextPaint.setTextSize(mSpeedLimitTextSize);

        mPerDegreeSpeed = (mMaxSpeed - mMinSpeed) / 270;

        a.recycle();
    }

    private DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Defaults
        int defaultValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getDisplayMetrics());
        //The default width and height
        int defaultWidth = defaultValue + getPaddingLeft() + getPaddingRight();
        int defaultHeight = defaultValue + getPaddingTop() + getPaddingBottom();

        int width = measureHandler(widthMeasureSpec, defaultWidth);
        int height = measureHandler(heightMeasureSpec, defaultHeight);

        //Center coordinates
        mCircleCenterX = (width + getPaddingLeft() - getPaddingRight()) / 2.0f;
        mCircleCenterY = (height + getPaddingTop() - getPaddingBottom()) / 2.0f;

        //Cylindrical radius
        mRadius = (width - getPaddingLeft() - getPaddingRight()) / 2.0f;

        mProgressBarCircleRadius = mRadius - (speedDialRingWidth / 2);
        mDotedCircleRadius = mRadius - speedDialRingWidth - speedDialRingInnerPadding;

        //The radius of the inner courtyard is 1/3 of the radius of the foreign aid
//        mInsideRadius = mRadius / 3;

        setMeasuredDimension(width, height);

    }


    private int measureHandler(int measureSpec, int defaultSize) {

        int result = defaultSize;
        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);

        if (measureMode == MeasureSpec.UNSPECIFIED) {
            result = defaultSize;
        } else if (measureMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, measureSize);
        } else {
            result = measureSize;
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.rotate(-225, mCircleCenterX, mCircleCenterY);
//        drawRadar(canvas);
        drawSpeedometer(canvas);
    }

    public void drawSpeedometer(Canvas canvas) {

        for (int i = 0; 270 >= i; i = i + 5) {
            double radian = Math.toRadians(i);
            float x = (float) (mCircleCenterX + mDotedCircleRadius * Math.cos(radian));
            float y = (float) (mCircleCenterY + mDotedCircleRadius * Math.sin(radian));

            canvas.drawCircle(x, y, 2, mSmallDotCirclePaint);
        }

        for (int i = 0; 270 >= i; i = i + 45) {
            double radian = Math.toRadians(i);
            float x = (float) (mCircleCenterX + mDotedCircleRadius * Math.cos(radian));
            float y = (float) (mCircleCenterY + mDotedCircleRadius * Math.sin(radian));

            canvas.drawCircle(x, y, 4, mBigDotCirclePaint);
        }

        //Draw Alert circle
        float alertSpeedAngle = (mSafeSpeedLimit / mPerDegreeSpeed);

        canvas.drawCircle((float) (mCircleCenterX + mDotedCircleRadius * Math.cos(Math.toRadians(alertSpeedAngle))), (float) (mCircleCenterY + mDotedCircleRadius * Math.sin(Math.toRadians(alertSpeedAngle))), 6, mAlertDotCirclePaint);
//        canvas.drawCircle(mCircleCenterX, mCircleCenterY, 6, mAlertDotCirclePaint);

        //Draw progress bar
        progressBarRect.set(getWidth() / 2 - mProgressBarCircleRadius, getWidth() / 2 - mProgressBarCircleRadius, getWidth() / 2 + mProgressBarCircleRadius, getWidth() / 2 + mProgressBarCircleRadius);

        //Draw active area of progress bar
        mProgressBarPaint.setColor(dialActiveColor);
        canvas.drawArc(progressBarRect, 0, 270, false, mProgressBarPaint);

        //Draw inactive area of progress bar
        mProgressBarPaint.setColor(dialInactiveColor);
        canvas.drawArc(progressBarRect, 270, 90, false, mProgressBarPaint);


        //Draw Speed Progress
        float speedAngle = mCurrentSpeed / mPerDegreeSpeed;

        if (speedAngle <= alertSpeedAngle) {
            mProgressBarPaint.setColor(dialSpeedColor);
            canvas.drawArc(progressBarRect, 0, speedAngle, false, mProgressBarPaint);
        } else {
//            mSpeedProgressPaint.setColor(Color.GREEN);
//            canvas.drawArc(progressBarRect, 0, alertSpeedAngle, false, mSpeedProgressPaint);

            mProgressBarPaint.setColor(dialSpeedAlertColor);
            canvas.drawArc(progressBarRect, 0, speedAngle, false, mProgressBarPaint);
        }


        //Rotate canvas again to write the text in correct orientation
        canvas.rotate(+225, mCircleCenterX, mCircleCenterY);

        //Draw Min and Max Text
        mSpeedLimitTextPaint.setTextAlign(Paint.Align.LEFT);
        float x_min = (float) (mCircleCenterX + mDotedCircleRadius * Math.cos(Math.toRadians(-225)));
        float y_min = (float) (mCircleCenterY + mDotedCircleRadius * Math.sin(Math.toRadians(-225)));
        canvas.drawText("" + (int) mMinSpeed, x_min + 10, y_min - 10, mSpeedLimitTextPaint);

        mSpeedLimitTextPaint.setTextAlign(Paint.Align.RIGHT);
        float x_max = (float) (mCircleCenterX + mDotedCircleRadius * Math.cos(Math.toRadians(45)));
        float y_maz = (float) (mCircleCenterY + mDotedCircleRadius * Math.sin(Math.toRadians(45)));
        canvas.drawText("" + (int) mMaxSpeed, x_max - 10, y_maz - 10, mSpeedLimitTextPaint);


        //Text is always draw around the x coordinate and y coordinate is base line, so we have to shift the base line
        //in order to draw the text, consider the supplied point as center.
        canvas.drawText("" + (int) mCurrentSpeed, mCircleCenterX, mCircleCenterY - ((mSpeedTextPaint.descent() + mSpeedTextPaint.ascent()) / 2), mSpeedTextPaint);


        //Draw speed unit text
        float x = mCircleCenterX;
        float y = (float) (mCircleCenterY + mDotedCircleRadius * Math.sin(Math.toRadians(-225)));

        canvas.drawText(unitOfMeasurement, x, y - ((mSpeedUnitPaint.descent() + mSpeedUnitPaint.ascent()) / 2), mSpeedUnitPaint);


    }

    public void startProgressAnimation(float speed) {
//        if (valueAnimator == null) {
        valueAnimator = ValueAnimator.ofFloat(mCurrentSpeed, speed);
        valueAnimator.setDuration(mAnimationTime);
        valueAnimator.setEvaluator(new FloatEvaluator());
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentSpeed = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(0);
//        }
        valueAnimator.start();

    }

    public void setSpeed(float mSpeed) {

        //If supplied speed is more than max speed, use the max speed
        if (mSpeed > mMaxSpeed)
            mSpeed = mMaxSpeed;

        //if supplied speed is less than min speed, use the min speed
        if (mSpeed < mMinSpeed)
            mSpeed = mMinSpeed;

        this.mSpeed = mSpeed;

        startProgressAnimation(mSpeed);
    }

    public void setMaxSpeed(float mMaxSpeed) {

        //if supplied max speed is less than min speed, use the min speed
        if (mMaxSpeed < mMinSpeed)
            mMaxSpeed = mMinSpeed;

        this.mMaxSpeed = mMaxSpeed;
    }

    public void setMinSpeed(float mMinSpeed) {

        //if supplied min speed is more than max speed, use the max speed
        if (mMinSpeed > mMaxSpeed)
            mMinSpeed = mMaxSpeed;

        this.mMinSpeed = mMinSpeed;
    }

    public void setUnitOfMeasurement(string unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public void setAnimationTime(int mAnimationTime) {
        this.mAnimationTime = mAnimationTime;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            //onresume() called
            startProgressAnimation(mSpeed);
        } else {
            // onPause() called
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // onDestroy() called
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // onCreate() called
    }
}
