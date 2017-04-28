package gank.qhm.com.hmgank.Controller;


import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import gank.qhm.com.hmgank.Config;
import gank.qhm.com.hmgank.Controller.adapter.CommonRecyclerAdapter;
import gank.qhm.com.hmgank.Controller.adapter.CommonRecyclerHolder;
import gank.qhm.com.hmgank.Controller.adapter.MeiziRecylerViewAdapter;
import gank.qhm.com.hmgank.Model.CategoryModel;
import gank.qhm.com.hmgank.Model.MeiziSizeModel;
import gank.qhm.com.hmgank.R;
import gank.qhm.com.hmgank.Utils.DisplayUtils;
import gank.qhm.com.hmgank.Utils.ImageLoader;
import gank.qhm.com.hmgank.Utils.TimeUtils;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewWithFooter rv_content;

//    private CommonRecyclerAdapter mAdapter;
    private MeiziRecylerViewAdapter mAdapter;
    private List<CategoryModel.ResultsBean> mData;
    private List<MeiziSizeModel> mSizemodel;

    private MeiziPresenter mMeiziPresenter;
    private String mCategory = Config.CATEGORY_MEIZI;
    private int width;

    @Override
    protected int initLayout() {
        return R.layout.activity_meizi;
    }

    @Override
    protected void initValue() {
        mData = new ArrayList<>();
        mSizemodel = new ArrayList<>();
        mMeiziPresenter = new MeiziPresenter(this);
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = getWindow().getDecorView().getWidth() / 2;
            }
        });
    }

    @Override
    protected void initView() {
        mAppbar = fv(R.id.mAppbar);
        rv_content = fv(R.id.rv_content);
        swipeRefreshLayout = fv(R.id.swipeRefreshLayout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAppbar.setPadding(
                    mAppbar.getPaddingLeft(),
                    mAppbar.getPaddingTop() + DisplayUtils.getStatusBarHeight(this),
                    mAppbar.getPaddingRight(),
                    mAppbar.getPaddingBottom());
        }

        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4,
                R.color.colorSwipeRefresh5,
                R.color.colorSwipeRefresh6);
        swipeRefreshLayout.setOnRefreshListener(this);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv_content.setLayoutManager(layoutManager);
        rv_content.setAdapter(mAdapter = new MeiziRecylerViewAdapter(MeiziActivity.this, mData, mSizemodel));
//        rv_content.setAdapter(mAdapter =
//                new CommonRecyclerAdapter<CategoryModel.ResultsBean>(R.layout.item_meizi, MeiziActivity.this, mData) {
//                    @Override
//                    public void convert(CommonRecyclerHolder holder, CategoryModel.ResultsBean item, int position) {
//                        ImageView iv_meizi = holder.getView(R.id.iv_meizi);
//                        TextView tv_name = holder.getView(R.id.tv_name);
//                        TextView tv_time = holder.getView(R.id.tv_time);
////                        iv_meizi.setBackgroundColor(getResources().getColor(randomColor(position)));
////                        iv_meizi.setImageDrawable(null);
//                        ImageLoader.loadImg(MeiziActivity.this, item.url, resource -> {
//                            int viewWidth = width - DisplayUtils.dp2px(5 + 5, MeiziActivity.this);
//                            int viewHeight = (int) (resource.getIntrinsicHeight() * (viewWidth * 1.0) / resource.getMinimumWidth());
//                            ViewGroup.LayoutParams layoutParams = iv_meizi.getLayoutParams();
//                            layoutParams.width = viewWidth;
//                            layoutParams.height = viewHeight;
//                            iv_meizi.setImageDrawable(resource);
//                        });
//                        tv_name.setText(item.who);
//                        tv_time.setText(TimeUtils.formatTo(item.publishedAt, "yyyy-MM-dd"));
//                    }
//                });
//        rv_content.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                layoutManager.invalidateSpanAssignments();
//            }
//        });
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
        mAdapter.notifyItemRangeRemoved(0, mData.size());
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
            mAdapter.notifyItemRangeRemoved(0, mData.size());
            mData.clear();
            mSizemodel.clear();
        }
        for (int i = 0; i < model.results.size(); i++) {
            mData.add(model.results.get(i));
            MeiziSizeModel sizeModel = new MeiziSizeModel(model.results.get(i).url);
            mSizemodel.add(sizeModel);
            mAdapter.notifyItemInserted(mData.size());
        }
//        mData.addAll(model.results);
//        for (int i = 0; i < model.results.size(); i++) {
//            MeiziSizeModel sizeModel = new MeiziSizeModel(model.results.get(i).url);
//            mSizemodel.add(sizeModel);
//        }
//        mAdapter.notifyItemInserted(mData.size());
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
