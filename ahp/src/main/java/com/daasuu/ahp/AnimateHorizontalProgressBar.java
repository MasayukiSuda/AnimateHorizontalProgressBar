package com.daasuu.ahp;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.ProgressBar;

public class AnimateHorizontalProgressBar extends ProgressBar {

    private static final String TAG = AnimateHorizontalProgressBar.class.getName();

    private static final long DEFAULT_DURATION = 1000;
    private static final int DEFAULT_CORNER_RADIUS = -1;
    private static final int DEFAULT_PROGRESS_COLOR = Color.parseColor("#FF0000");
    private static final int DEFAULT_PROGRESS_BACKGROUND_COLOR = Color.parseColor("#FFFFFF");

    private ValueAnimator mProgressAnimator;
    private ValueAnimator mMaxAnimator;
    private boolean isAnimating = false;

    private AnimateProgressListener mAnimateProgressListener;


    public AnimateHorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimateHorizontalProgressBar(Context context) {
        this(context, null, 0);
    }

    public AnimateHorizontalProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, 0);
        setUpAnimator();

        // progress color set
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AnimateHorizontalProgressBar);

        int progressColor = ta.getColor(R.styleable.AnimateHorizontalProgressBar_ahp_progressColor, DEFAULT_PROGRESS_COLOR);
        int backgroundColor = ta.getColor(R.styleable.AnimateHorizontalProgressBar_ahp_backgroundColor, DEFAULT_PROGRESS_BACKGROUND_COLOR);
        int cornerRadius = ta.getDimensionPixelSize(R.styleable.AnimateHorizontalProgressBar_ahp_cornerRadius, DEFAULT_CORNER_RADIUS);

        ClipDrawable progressClipDrawable;
        Drawable[] progressDrawables;
        if (cornerRadius > 0) {
            progressClipDrawable = new ClipDrawable(
                    createGradientDrawable(progressColor, cornerRadius),
                    Gravity.LEFT,
                    ClipDrawable.HORIZONTAL);
            progressDrawables = new Drawable[]{
                    createGradientDrawable(backgroundColor, cornerRadius),
                    progressClipDrawable};
        } else {
            progressClipDrawable = new ClipDrawable(
                    new ColorDrawable(progressColor),
                    Gravity.LEFT,
                    ClipDrawable.HORIZONTAL);
            progressDrawables = new Drawable[]{
                    new ColorDrawable(backgroundColor),
                    progressClipDrawable};
        }
        LayerDrawable progressLayerDrawable = new LayerDrawable(progressDrawables);
        progressLayerDrawable.setId(0, android.R.id.background);
        progressLayerDrawable.setId(1, android.R.id.progress);

        super.setProgressDrawable(progressLayerDrawable);
        ta.recycle();
    }

    private GradientDrawable createGradientDrawable(int color, int radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    private void setUpAnimator() {
        mProgressAnimator = new ValueAnimator();
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                AnimateHorizontalProgressBar.super.setProgress((Integer) animation.getAnimatedValue());
            }
        });
        mProgressAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
                if (mAnimateProgressListener != null) {
                    mAnimateProgressListener.onAnimationStart(getProgress(), getMax());
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                if (mAnimateProgressListener != null) {
                    mAnimateProgressListener.onAnimationEnd(getProgress(), getMax());
                }
            }
        });
        mProgressAnimator.setDuration(DEFAULT_DURATION);


        mMaxAnimator = new ValueAnimator();
        mMaxAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                AnimateHorizontalProgressBar.super.setMax((Integer) animation.getAnimatedValue());
            }
        });
        mMaxAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
                if (mAnimateProgressListener != null) {
                    mAnimateProgressListener.onAnimationStart(getProgress(), getMax());
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                if (mAnimateProgressListener != null) {
                    mAnimateProgressListener.onAnimationEnd(getProgress(), getMax());
                }
            }
        });
        mMaxAnimator.setDuration(DEFAULT_DURATION);

    }

    /**
     * Animation Progress
     *
     * @param progress animationEnd progress point
     */
    public void setProgressWithAnim(int progress) {
        if (isAnimating) {
            Log.w(TAG, "now is animating. cant override animator");
            return;
        }
        if (mProgressAnimator == null) {
            setUpAnimator();
        }
        mProgressAnimator.setIntValues(getProgress(), progress);
        mProgressAnimator.start();
    }

    @Override
    public void setProgressDrawable(Drawable d) {
        // do Nothing
    }

    @Override
    public synchronized void setProgress(int progress) {
        if (!isAnimating) {
            super.setProgress(progress);
        }
    }

    @Deprecated
    @Override
    public synchronized void setSecondaryProgress(int secondaryProgress) {
        // do Nothing
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mProgressAnimator != null) {
            mProgressAnimator.cancel();
        }
        if (mMaxAnimator != null) {
            mMaxAnimator.cancel();
        }
    }


    /**
     * Animation Progress
     *
     * @param max animationEnd max point
     */
    public void setMaxWithAnim(int max) {
        if (isAnimating) {
            Log.w(TAG, "now is animating. cant override animator");
            return;
        }
        if (mMaxAnimator == null) {
            setUpAnimator();
        }
        mMaxAnimator.setIntValues(getMax(), max);
        mMaxAnimator.start();
    }

    /**
     * cancelAnimation
     */
    public void cancelAnimation() {
        if (!isAnimating) {
            Log.w(TAG, "now is no animating.");
            return;
        }
        if (mProgressAnimator != null) {
            mProgressAnimator.cancel();
        }
        if (mMaxAnimator != null) {
            mMaxAnimator.cancel();
        }
        isAnimating = false;
    }


    @Override
    public synchronized void setMax(int max) {
        if (!isAnimating) {
            super.setMax(max);
        }
    }

    public long getAnimDuration() {
        return mProgressAnimator.getDuration();
    }

    public void setAnimDuration(long duration) {
        mProgressAnimator.setDuration(duration);
        mMaxAnimator.setDuration(duration);
    }

    public void setStartDelay(long delay) {
        mProgressAnimator.setStartDelay(delay);
        mMaxAnimator.setStartDelay(delay);
    }

    public void setAnimInterpolator(@NonNull TimeInterpolator timeInterpolator) {
        mProgressAnimator.setInterpolator(timeInterpolator);
        mMaxAnimator.setInterpolator(timeInterpolator);
    }

    public void setAnimateProgressListener(AnimateProgressListener animateProgressListener) {
        this.mAnimateProgressListener = animateProgressListener;
    }

    // interface progress animationListener
    public interface AnimateProgressListener {
        void onAnimationStart(int progress, int max);

        void onAnimationEnd(int progress, int max);
    }

    private abstract class SimpleAnimatorListener implements Animator.AnimatorListener {
        @Override
        public abstract void onAnimationStart(Animator animation);

        @Override
        public abstract void onAnimationEnd(Animator animation);

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
