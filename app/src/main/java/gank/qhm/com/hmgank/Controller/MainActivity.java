package gank.qhm.com.hmgank.Controller;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gank.qhm.com.hmgank.Config;
import gank.qhm.com.hmgank.Controller.adapter.CategoryFragmentAdapter;
import gank.qhm.com.hmgank.Navegation;
import gank.qhm.com.hmgank.R;
import gank.qhm.com.hmgank.Utils.ImageLoader;
import gank.qhm.com.hmgank.Utils.ToastUtils;
import gank.qhm.com.hmgank.ViewModel.Presenter.MainPresenter;
import gank.qhm.com.hmgank.ViewModel.View.MainView;

/**
 * Created by qhm on 2017/4/7
 * 主界面
 */

public class MainActivity extends BaseActivity implements MainView {

    private ImageView iv;
    private TextView tv_search;
    private AppBarLayout mAppBarLayout;
    private TabLayout tl_tab;
    private Toolbar mToolbar;
    private ViewPager vp_category;

    private CategoryFragmentAdapter adapter;
    private List<CategoryFragment> fragments;
    private String[] titles = Config.TITLES;
    private String catrgotyMeizi = Config.CATEGORY_MEIZI;

    private MainPresenter mMainPresenter;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initValue() {
        mMainPresenter = new MainPresenter(this);
    }

    @Override
    protected void initView() {

        iv = fv(R.id.iv);
        tv_search = fv(R.id.tv_search);
        mAppBarLayout = fv(R.id.mAppBarLayout);
        tl_tab = fv(R.id.tl_tab);
        vp_category = fv(R.id.vp_category);
        mToolbar = fv(R.id.mToolbar);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4 以上版本
            // 设置 Toolbar 高度为 80dp，适配状态栏
//            ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
//            layoutParams.height = DisplayUtils.dp2px(80, this);
//            mToolbar.setLayoutParams(layoutParams);
        } else { // 4.4 一下版本
            // 设置 设置图标距离顶部（状态栏最底）为
//            mIvSetting.setPadding(mIvSetting.getPaddingLeft(),
//                    DisplayUtils.dp2px(15, this),
//                    mIvSetting.getPaddingRight(),
//                    mIvSetting.getPaddingBottom());
        }


        initFragments();
        initViewPager();
        initTabLayout();
        setFabDynamicState();
        initSearchClick();
        initMeiziClick();
    }

    @Override
    protected void initData() {
        mMainPresenter.initMeizi(catrgotyMeizi, 1, 1, (url) -> {
            initMeizi(url);
        });
    }

    private void initFragments() {
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.clear();
        for (int i = 0; i < titles.length; i++) {
            CategoryFragment fragment = CategoryFragment.NewInstance(titles[i]);
            fragments.add(fragment);
        }
    }

    private void initViewPager() {
        adapter = new CategoryFragmentAdapter(getSupportFragmentManager(), fragments, Arrays.asList(titles));
        vp_category.setAdapter(adapter);
        vp_category.setCurrentItem(0);
        vp_category.setOffscreenPageLimit(titles.length);
    }

    private void initTabLayout() {
        tl_tab.setTabMode(TabLayout.MODE_SCROLLABLE);   //可以滚动
        for (int i = 0; i < titles.length; i++) {
            tl_tab.addTab(tl_tab.newTab().setText(titles[i]));
        }
        tl_tab.setupWithViewPager(vp_category);
    }

    /**
     * 根据 CollapsingToolbarLayout 的折叠状态，设置 FloatingActionButton 的隐藏和显示
     */
    @Override
    public void setFabDynamicState() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    //完全展开
                    tv_search.setAlpha(1);
                    tv_search.setVisibility(View.VISIBLE);
                    tl_tab.setAlpha(0);
                    tl_tab.setVisibility(View.GONE);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //完全折叠
                    tv_search.setAlpha(0);
                    tv_search.setVisibility(View.GONE);
                    tl_tab.setAlpha(1);
                    tl_tab.setVisibility(View.VISIBLE);
                } else {
                    if (tv_search.getVisibility() != View.VISIBLE) {
                        tv_search.setVisibility(View.VISIBLE);
                    }
                    if (tl_tab.getVisibility() != View.VISIBLE) {
                        tl_tab.setVisibility(View.VISIBLE);
                    }
                    //中间状态
                    tv_search.setAlpha(1 - (float) (1.0 * Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange()));
                    tl_tab.setAlpha((float) (1.0 * Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange()));
                }
            }
        });
    }

    @Override
    public void initSearchClick() {
        tv_search.setOnClickListener(v -> {
            ToastUtils.showToast("搜索点什么呢");
        });
    }

    @Override
    public void initMeizi(String url) {
        if (TextUtils.isEmpty(url)) {
            initMeiziFail();
            return;
        }
        ImageLoader.loadImg(this, url, iv);
    }

    @Override
    public void initMeiziFail() {
        ToastUtils.showToast("加载妹子失败了");
    }

    @Override
    public void initMeiziClick() {
        iv.setOnClickListener(v -> {
            Navegation.showMeizi(this);
        });
    }
}
