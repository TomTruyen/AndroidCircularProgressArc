package com.tomtruyen.circularprogressarc


import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.tomtruyen.android.material.loadingbutton.R

class CircularProgressArc(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int
): View(context, attrs, defStyleAttr) {
    private var mViewWidth = 0
    private var mViewHeight = 0
    private var mStartAngle = 90f // Always start from bottom (default is: "3 o'clock on a watch.")
    private var mSweepAngle = 360f // How long to sweep from mStartAngle
    private var mMaxSweepAngle = 360f // How long to sweep from mStartAngle
    private var mMaxProgress = 100 // Max progress to use
    private var mAnimationDuration = 1000 // Animation duration for progress change
    private var mRoundedCorners = true // Set to true if rounded corners should be applied to outline ends
    private var mProgressColor: Int = Color.RED // Outline color
    private var mBackgroundColor: Int = Color.GRAY
    private var mGradientColors = intArrayOf()
    private var mStrokeWidth = 8f // Outline stroke width
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)// Allocate paint outside onDraw to avoid unnecessary object creation

    constructor(context: Context?) : this(context, null) {}
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    init {
        // Load attributes
        val a = context?.obtainStyledAttributes(
            attrs,
            R.styleable.CircularProgressArc, defStyleAttr, 0
        )

        mStartAngle = a?.getFloat(R.styleable.CircularProgressArc_angle_start, mStartAngle) ?: mStartAngle
        mSweepAngle = a?.getFloat(R.styleable.CircularProgressArc_angle_sweep, mSweepAngle) ?: mSweepAngle
        mMaxSweepAngle = mSweepAngle
        mMaxProgress = a?.getInt(R.styleable.CircularProgressArc_max_progress, mMaxProgress) ?: mMaxProgress
        mAnimationDuration = a?.getInt(R.styleable.CircularProgressArc_animation_duration, mAnimationDuration) ?: mAnimationDuration
        mProgressColor = a?.getColor(R.styleable.CircularProgressArc_progress_color, mProgressColor) ?: mProgressColor
        mBackgroundColor = a?.getColor(R.styleable.CircularProgressArc_background_color, mBackgroundColor) ?: mBackgroundColor
        mStrokeWidth = a?.getDimension(R.styleable.CircularProgressArc_stroke_width, mStrokeWidth) ?: mStrokeWidth
        mRoundedCorners = a?.getBoolean(R.styleable.CircularProgressArc_rounded_corners, mRoundedCorners) ?: mRoundedCorners

        a?.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initMeasurments()
        drawOutlineArc(canvas)
    }

    private fun initMeasurments() {
        mViewWidth = width
        mViewHeight = height
    }

    private fun drawOutlineArc(canvas: Canvas) {
        val outerOval = RectF(
            mStrokeWidth / 2,
            mStrokeWidth /2,
            measuredWidth - (mStrokeWidth / 2),
            measuredHeight - (mStrokeWidth / 2)
        )

        canvas.drawArc(outerOval, mStartAngle, mMaxSweepAngle, false, Paint().apply {
            color = mBackgroundColor
            style = Paint.Style.STROKE
            strokeWidth = mStrokeWidth
            isAntiAlias = true
        })

        // ProgressColor
        if(mGradientColors.isNotEmpty()) {
            mPaint.shader = SweepGradient(0f, 0f, mGradientColors, null)
        } else {
            mPaint.color = mProgressColor
        }

        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mStrokeWidth
        mPaint.isAntiAlias = true
        mPaint.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT

        canvas.drawArc(outerOval, mStartAngle, mSweepAngle, false, mPaint)
    }

    private fun calcSweepAngleFromProgress(progress: Int): Float {
        return mMaxSweepAngle / mMaxProgress * progress
    }

    /**
     * Set progress of the circular progress bar.
     * @param progress progress between 0 and 100.
     */
    fun setProgress(progress: Int) {
        val animator = ValueAnimator.ofFloat(mSweepAngle, calcSweepAngleFromProgress(progress))
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDuration.toLong()
        animator.addUpdateListener { valueAnimator ->
            mSweepAngle = valueAnimator.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    fun setMax(max: Int) {
        mMaxProgress = max
        invalidate()
    }

    /**
     * Set start angle.
     * Default is 90 degrees (bottom).
     * @param angle
     */
    fun setStartAngle(angle: Float) {
        mStartAngle = angle
        invalidate()
    }

    /**
     * Set total angle that can progressed (0-360).
     * Default is 360.
     * @param angle
     */
    fun setSweepAngle(angle: Float) {
        mSweepAngle = angle
        mMaxSweepAngle = angle
        invalidate()
    }

    /**
     * Set progress color.
     * @param color
     */
    fun setProgressColor(color: Int) {
        mProgressColor = color
        invalidate()
    }

    fun setArcBackgroundColor(color: Int) {
        mBackgroundColor = color
        invalidate()
    }

    /**
     * Set progress color as gradient (overrides the progress color property).
     * @param colors
     */
    fun setGradientColors(colors: IntArray) {
        mGradientColors = colors
        invalidate()
    }

    /**
     * Set animation duration in milliseconds.
     * Default is 1000.
     * @param duration
     */
    fun setAnimationDuration(duration: Int) {
        mAnimationDuration = duration
    }

    /**
     * Set the width of arc.
     * Default is 8f.
     * @param width
     */
    fun setStrokeWidth(width: Float) {
        mStrokeWidth = width
        invalidate()
    }

    /**
     * Toggle this if you don't want rounded corners (stroke caps) on progress bar.
     * Default is true.
     * @param roundedCorners true if you want rounded corners of false otherwise.
     */
    fun useRoundedCorners(roundedCorners: Boolean) {
        mRoundedCorners = roundedCorners
        invalidate()
    }
}