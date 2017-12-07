package gank.qhm.com.hmgank.Widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import gank.qhm.com.hmgank.R;

/**
 * Created by qhm on 2017/9/5
 */

public class ProgressView extends View {

    public static final int ANIMATION_DURING_TIME = 600;

    /**
     * 中心颜色
     */
    private int centerColor;

    /**
     * 边缘颜色
     */
    private int edgeColor;

    private Paint paint;
    private Rect progressRect;
    private int totalWidth, totalHeight;
    private int minProgress, maxProgress;
    private int progress;
    private float progressScale;
    private AnimatorSet animatorSet;
    private boolean isStarted;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        progressRect = new Rect();
        animatorSet = new AnimatorSet();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        centerColor = array.getColor(R.styleable.ProgressView_centerColor, Color.RED);
        edgeColor = array.getColor(R.styleable.ProgressView_edgeColor,
                getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        totalHeight = MeasureSpec.getSize(heightMeasureSpec);
        minProgress = totalWidth / 20;
        maxProgress = totalWidth / 2;
        progressScale = maxProgress - minProgress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgress(canvas);
    }

    private void drawProgress(Canvas canvas) {
        progressRect.left = totalWidth / 2 - progress;
        progressRect.top = 0;
        progressRect.right = totalWidth / 2 + progress;
        progressRect.bottom = totalHeight;
        LinearGradient mLinearGradient = new LinearGradient(totalWidth / 2, 0, progressRect.right, 0,
                new int[]{centerColor, edgeColor}, new float[]{0, 1.0f}, Shader.TileMode.MIRROR);
        paint.setShader(mLinearGradient);
        canvas.drawRect(progressRect, paint);
    }

    public void startAnimation() {
        Log.i("sss", "startAnimation: " + progress);
        if (isStarted) {
            return;
        }
        isStarted = true;
        ValueAnimator animatorAppear = ValueAnimator.ofInt(minProgress, maxProgress);
        animatorAppear.setDuration(ANIMATION_DURING_TIME * 2 / 3);
        animatorAppear.addUpdateListener((ValueAnimator animation) -> {
            progress = (int) animation.getAnimatedValue();
            float alpha;
            alpha = (progress - minProgress) / progressScale / 2;
            if (alpha > 1) {
                alpha = (2 - alpha) / 2 + 0.5f;
            }
            setAlpha(alpha);
            invalidate();
        });
        ValueAnimator animatorDisappear = ValueAnimator.ofFloat(0.5f, 0);
        animatorDisappear.setDuration(ANIMATION_DURING_TIME / 3);
        animatorDisappear.addUpdateListener((ValueAnimator animation) -> {
            setAlpha((float) animation.getAnimatedValue());
            invalidate();
        });
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progress = 0;
                invalidate();
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                progress = 0;
                postInvalidate();
            }
        });
        animatorSet.setDuration(ANIMATION_DURING_TIME);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playSequentially(animatorAppear, animatorDisappear);
        animatorSet.start();
    }

    public void stopAnimation() {
        progress = 0;
        invalidate();
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        isStarted = false;
        Log.i("sss", "stopAnimation: " + progress);
    }
}
