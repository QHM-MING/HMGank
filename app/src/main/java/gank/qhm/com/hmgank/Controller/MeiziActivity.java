package gank.qhm.com.hmgank.Controller;


import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import gank.qhm.com.hmgank.Config;
import gank.qhm.com.hmgank.Controller.adapter.MeiziRecylerViewAdapter;
import gank.qhm.com.hmgank.Model.CategoryModel;
import gank.qhm.com.hmgank.Model.MeiziSizeModel;
import gank.qhm.com.hmgank.R;
import gank.qhm.com.hmgank.Utils.DisplayUtils;
import gank.qhm.com.hmgank.Utils.ToastUtils;
import gank.qhm.com.hmgank.ViewModel.Presenter.MeiziPresenter;
import gank.qhm.com.hmgank.ViewModel.View.MeiziView;
import gank.qhm.com.hmgank.Widget.recyclerviewwithfooter.OnLoadMoreListener;
import gank.qhm.com.hmgank.Widget.recyclerviewwithfooter.RecyclerViewWithFooter;

/**
 * Created by qhm on 2017/4/25
 * 妹子展示页面
 */

public class MeiziActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        OnLoadMoreListener, MeiziView {

    private static final int[] COLOR = new int[]{
            R.color.colorSwipeRefresh2,
            R.color.colorSwipeRefresh3,
            R.color.colorSwipeRefresh4,
            R.color.colorSwipeRefresh5,
            R.color.colorSwipeRefresh6
    };

    private AppBarLayout mAppbar;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewWithFooter rv_content;

    private MeiziRecylerViewAdapter mAdapter;
    private List<CategoryModel.ResultsBean> mData;
    private List<MeiziSizeModel> mSizemodel;

    private MeiziPresenter mMeiziPresenter;
    private String mCategory = Config.CATEGORY_MEIZI;

    @Override
    protected int initLayout() {
        return R.layout.activity_meizi;
    }

    @Override
    protected void initValue() {
        mData = new ArrayList<>();
        mSizemodel = new ArrayList<>();
        mMeiziPresenter = new MeiziPresenter(this);

    }

    @Override
    protected void initView() {
        mAppbar = fv(R.id.mAppbar);
        toolbar = fv(R.id.toolbar);
        rv_content = fv(R.id.rv_content);
        swipeRefreshLayout = fv(R.id.swipeRefreshLayout);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAppbar.setPadding(
                    mAppbar.getPaddingLeft(),
                    mAppbar.getPaddingTop() + DisplayUtils.getStatusBarHeight(this),
                    mAppbar.getPaddingRight(),
                    mAppbar.getPaddingBottom());
        }

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4,
                R.color.colorSwipeRefresh5,
                R.color.colorSwipeRefresh6);
        swipeRefreshLayout.setOnRefreshListener(this);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_content.setLayoutManager(layoutManager);
        rv_content.setAdapter(mAdapter = new MeiziRecylerViewAdapter(MeiziActivity.this, mData, mSizemodel));
        rv_content.setHasFixedSize(true);
        rv_content.setOnLoadMoreListener(this);
        rv_content.setEmpty();
    }

    @Override
    protected void initData() {
        mMeiziPresenter.getMeizi(true);
    }

    @Override
    public void onRefresh() {
        //刷新数据
        mMeiziPresenter.getMeizi(true);
    }

    @Override
    public void onLoadMore() {
        //加载更多
        mMeiziPresenter.getMeizi(false);
    }

    @Override
    public void showRefresh() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshData(CategoryModel model, boolean isRefresh) {
        if (model == null || model.results == null) {
            return;
        }
        if (isRefresh) {
            mData.clear();
            mSizemodel.clear();
        }
        for (int i = 0; i < model.results.size(); i++) {
            mData.add(model.results.get(i));
            MeiziSizeModel sizeModel = new MeiziSizeModel(model.results.get(i).url);
            mSizemodel.add(sizeModel);
        }
        mAdapter.notifyItemInserted(mData.size());
        if (model.results.size() < Config.LIMIT) {
            rv_content.setEnd();
        } else {
            rv_content.setLoading();
        }
    }

    @Override
    public String getCategory() {
        return mCategory;
    }

    @Override
    public void showLoadFail(String s) {
        ToastUtils.showToast(getCategory() + " 获取资源失败");
    }

    @Override
    public int randomColor(int position) {
        return COLOR[position % COLOR.length];
    }
}
