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
import android.util.Log;
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
    
    private int[] mColors;
    private float[] mChangeValues;

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

        //Check if user allocated changeValues and Colors
        if (mChangeValues != null && mColors != null) {
            if (mChangeValues.length != mColors.length) { //TODO: Proper error msg
                Log.d("KDGAUGEVIEW", "ChangeValues and Colors array must be the same length!");
            } else {
                //Convert values to angles
                float[] changeAngles = new float[mChangeValues.length];
                for (int i = 0; i < mChangeValues.length; i++) {
                    changeAngles[i] = mChangeValues[i] / mPerDegreeSpeed;
                }
                initMultiColor(canvas, mColors, changeAngles);
            }

        } else { //If user doesn't allocate colors and changeValues, proceed as normal
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
    
    public void initMultiColor(Canvas canvas, int[] colors, float[] changeAngles) {

        //Error check for OOB angles
        for (float angle : changeAngles) {
            if (angle > mMaxSpeed) {
                angle = mMaxSpeed;
            }
        }

        //Add starting angle to changeAngles
        float speedAngle = mCurrentSpeed / mPerDegreeSpeed;

        //Keep tally of angle left to be drawn
        float angleLeft = speedAngle;

        //Loop through the angles for each color, and draw aprropriate progress bar
        for (int i = 0; i < changeAngles.length; i++) {
            float changeAngle = changeAngles[i];
            int color = colors[i];

            //Temporarily change colors
            mAlertDotCirclePaint.setColor(color);
            mProgressBarPaint.setColor(color);

            //Draw alert circle
            canvas.drawCircle((float) (mCircleCenterX + mDotedCircleRadius * Math.cos(Math.toRadians(changeAngle))), (float) (mCircleCenterY + mDotedCircleRadius * Math.sin(Math.toRadians(changeAngle))), 6, mAlertDotCirclePaint);

            //The first angle, starts at minimum
            if ((i - 1) < 0) {
                if (speedAngle <= changeAngle) { //If the  current speed is less than the maximum for this color band, only go up till current speed
                    canvas.drawArc(progressBarRect, mMinSpeed / mPerDegreeSpeed, angleLeft, false, mProgressBarPaint);
                    break;
                } else { //If current speed is more than maximum, go until the end of color band
                    canvas.drawArc(progressBarRect, mMinSpeed / mPerDegreeSpeed, changeAngle, false, mProgressBarPaint);
                }
                angleLeft = speedAngle - changeAngle;
            } else { //Same logic applied here
                if (speedAngle <= changeAngle) {
                    canvas.drawArc(progressBarRect, changeAngles[i - 1], angleLeft, false, mProgressBarPaint);
                    break;
                } else {
                    canvas.drawArc(progressBarRect, changeAngles[i - 1], changeAngle - changeAngles[i - 1], false, mProgressBarPaint);
                }
                angleLeft = speedAngle - changeAngle;
            }
        }
        //Return to original paint color
        mAlertDotCirclePaint.setColor(dialSpeedColor);
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
    
    public int[] getColors() {
        return mColors;
    }

    public void setColors(int[] colors) {
        this.mColors = colors;
    }

    public float[] getChangeValues() {
        return mChangeValues;
    }

    public void setChangeValues(float[] changeValues) {
        this.mChangeValues = changeValues;
    }
    
     public void setmMinSpeed(float mMinSpeed) {
        this.mMinSpeed = mMinSpeed;
        mPerDegreeSpeed = (mMaxSpeed - mMinSpeed) / 270;

    }

    public void setmMaxSpeed(float mMaxSpeed) {
        this.mMaxSpeed = mMaxSpeed;
        mPerDegreeSpeed = (mMaxSpeed - mMinSpeed) / 270;

    }

    public void setmSafeSpeedLimit(float mSafeSpeedLimit) {
        this.mSafeSpeedLimit = mSafeSpeedLimit;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public void setmAnimationTime(int mAnimationTime) {
        this.mAnimationTime = mAnimationTime;
    }

    public void setmSpeedTextSize(float mSpeedTextSize) {
        this.mSpeedTextSize = mSpeedTextSize;
    }

    public void setmSpeedUnitTextSize(float mSpeedUnitTextSize) {
        this.mSpeedUnitTextSize = mSpeedUnitTextSize;
    }

    public void setmSpeedLimitTextSize(float mSpeedLimitTextSize) {
        this.mSpeedLimitTextSize = mSpeedLimitTextSize;
    }

    public void setSpeedDialRingWidth(float speedDialRingWidth) {
        this.speedDialRingWidth = speedDialRingWidth;
    }

    public void setSpeedDialRingInnerPadding(float speedDialRingInnerPadding) {
        this.speedDialRingInnerPadding = speedDialRingInnerPadding;
    }

    public void setDialActiveColor(int dialActiveColor) {
        this.dialActiveColor = dialActiveColor;
    }

    public void setDialInactiveColor(int dialInactiveColor) {
        this.dialInactiveColor = dialInactiveColor;
    }

    public void setDialSpeedColor(int dialSpeedColor) {
        this.dialSpeedColor = dialSpeedColor;
    }

    public void setDialSpeedAlertColor(int dialSpeedAlertColor) {
        this.dialSpeedAlertColor = dialSpeedAlertColor;
    }

    public void setSubDivisionCircleColor(int subDivisionCircleColor) {
        this.subDivisionCircleColor = subDivisionCircleColor;
    }

    public void setDivisionCircleColor(int divisionCircleColor) {
        this.divisionCircleColor = divisionCircleColor;
    }

    public void setSpeedTextColor(int speedTextColor) {
        this.speedTextColor = speedTextColor;
    }

    public void setSpeedUnitTextColor(int speedUnitTextColor) {
        this.speedUnitTextColor = speedUnitTextColor;
    }

    public void setSpeedLimitTexteColor(int speedLimitTexteColor) {
        this.speedLimitTexteColor = speedLimitTexteColor;
    }

}
