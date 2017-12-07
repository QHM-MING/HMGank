package gank.qhm.com.hmgank.Controller;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gank.qhm.com.hmgank.Config;
import gank.qhm.com.hmgank.Controller.adapter.CategoryFragmentAdapter;
import gank.qhm.com.hmgank.Navigation;
import gank.qhm.com.hmgank.R;
import gank.qhm.com.hmgank.Utils.DisplayUtils;
import gank.qhm.com.hmgank.Utils.ImageLoader;
import gank.qhm.com.hmgank.Utils.ToastUtils;
import gank.qhm.com.hmgank.ViewModel.Presenter.MainPresenter;
import gank.qhm.com.hmgank.ViewModel.View.MainView;

/**
 * Created by qhm on 2017/4/7
 * 主界面
 */

public class MainActivity extends BaseActivity implements MainView {

    private DrawerLayout dl_base;
    private ImageView iv;
    private TextView tv_search;
    private AppBarLayout mAppBarLayout;
    private TabLayout tl_tab;
    private Toolbar mToolbar;
    private ViewPager vp_category;
    private AppCompatImageView iv_setting;
    private FrameLayout fl_navigation;

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

        dl_base = fv(R.id.dl_base);
        iv = fv(R.id.iv);
        tv_search = fv(R.id.tv_search);
        mAppBarLayout = fv(R.id.mAppBarLayout);
        tl_tab = fv(R.id.tl_tab);
        vp_category = fv(R.id.vp_category);
        mToolbar = fv(R.id.mToolbar);
        iv_setting = fv(R.id.iv_setting);
        fl_navigation = fv(R.id.fl_navigation);


//        // 设置 Toolbar 高度为 80dp，适配状态栏
        ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
        layoutParams.height = DisplayUtils.dp2px(80, this);
        mToolbar.setLayoutParams(layoutParams);
        DisplayUtils.setViewTransparentPadding(this, fl_navigation);

        initFragments();
        initViewPager();
        initTabLayout();
        setFabDynamicState();
        initSearchClick();
        initMeiziClick();
        initMenuClick();
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
            Navigation.showMeizi(this);
        });
    }

    @Override
    public void initMenuClick() {
        dl_base.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        iv_setting.setOnClickListener(v -> {
            dl_base.openDrawer(GravityCompat.START);
        });
    }
}
