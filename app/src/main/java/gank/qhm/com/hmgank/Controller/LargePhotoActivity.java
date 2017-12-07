package gank.qhm.com.hmgank.Controller;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import gank.qhm.com.hmgank.R;
import gank.qhm.com.hmgank.Utils.DisplayUtils;
import gank.qhm.com.hmgank.Utils.ImageLoader;
import gank.qhm.com.hmgank.ViewModel.Presenter.LargePhotoPresenter;
import gank.qhm.com.hmgank.ViewModel.View.LargePhotoView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by qhm on 2017/5/2
 * 展示大图功能
 */

public class LargePhotoActivity extends BaseActivity implements LargePhotoView, View.OnClickListener,
        PhotoViewAttacher.OnPhotoTapListener {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_BUNDLE = "bundle";

    private static final int DURATION = 300; //缩放动画时间

    private FrameLayout fl_root;
    private PhotoView view_photo;
    private ImageView iv_save;

    private String url;
    private LargePhotoPresenter mLargePhotoPresenter;

    private ColorDrawable colorDrawable;

    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;

    @Override
    protected int initLayout() {
        return R.layout.activity_large_photo;
    }

    @Override
    protected void initValue() {
        url = getIntent().getExtras().getString(EXTRA_URL);
        mLargePhotoPresenter = new LargePhotoPresenter(this, this);
        colorDrawable = new ColorDrawable(Color.BLACK);
    }

    @Override
    protected void initView() {
        view_photo = fv(R.id.view_photo);
        iv_save = fv(R.id.iv_save);
        fl_root = fv(R.id.fl_root);

        view_photo.setOnPhotoTapListener(this);
        iv_save.setOnClickListener(this);
        fl_root.setBackgroundDrawable(colorDrawable);
    }

    @Override
    protected void initData() {
        ImageLoader.loadImg(this, url, resource -> {
            onUiReady();
            view_photo.setImageDrawable(resource);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_save:
                //保存图片
                mLargePhotoPresenter.savePhoto(url);
                break;
        }
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        onBackPressed();
    }

    @Override
    public void onUiReady() {
        view_photo.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view_photo.getViewTreeObserver().removeOnPreDrawListener(this);
                enterAnimation();
                return true;
            }
        });
    }

    @Override
    public void enterAnimation() {
        Bundle bundle = getIntent().getBundleExtra(EXTRA_BUNDLE);
        if (bundle == null) {
            return;
        }

        int[] screenLocation = new int[2];
        view_photo.getLocationOnScreen(screenLocation);
        mLeftDelta = bundle.getInt(DisplayUtils.KEY_LEFT);
        mTopDelta = bundle.getInt(DisplayUtils.KEY_TOP);

        mWidthScale = (float) bundle.getInt(DisplayUtils.KEY_WIDTH) / view_photo.getWidth();
        mHeightScale = (float) bundle.getInt(DisplayUtils.KEY_HEIGHT) / view_photo.getHeight();
        //pivotX和pivotY：这两个属性控制着View对象的支点位置，围绕这个支点进行旋转和缩放变换处理。默认情况下，该支点的位置就是View对象的中心点
        view_photo.setPivotX(0);
        view_photo.setPivotY(0);
        //scaleX和scaleY：这两个属性控制着View对象围绕他的支点进行2D缩放。
        view_photo.setScaleX(mWidthScale);
        view_photo.setScaleY(mHeightScale);
        //translationX和translationY：这两个属性作为一种增量来控制这View对象从它布局容器的左上角坐标开始的位置。
        view_photo.setTranslationX(mLeftDelta);
        view_photo.setTranslationY(mTopDelta);
        //设置动画
        TimeInterpolator sDecelerator = new DecelerateInterpolator();
        //设置imageview缩放动画，以及缩放开始位置
        view_photo.animate().setDuration(DURATION).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator);

        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0, 255);
        bgAnim.setDuration(DURATION);
        bgAnim.start();
    }

    @Override
    public void exitAnimation(Runnable endAction) {
        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        //设置imageview缩放动画，以及缩放结束位置
        view_photo.animate().setDuration(DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta)
                .setInterpolator(sInterpolator).withEndAction(endAction);

        // 设置activity主布局背景颜色DURATION毫秒内透明度从不透明到透明
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0);
        bgAnim.setDuration(DURATION);
        bgAnim.start();
    }

    @Override
    public void onBackPressed() {
        exitAnimation(new Runnable() {
            public void run() {
                finish();
                //取消activity动画
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public String getUrl() {
        return url;
    }
}
