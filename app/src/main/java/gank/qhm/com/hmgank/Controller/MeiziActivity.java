package gank.qhm.com.hmgank.Controller;


import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import gank.qhm.com.hmgank.Config;
import gank.qhm.com.hmgank.Controller.adapter.CommonRecyclerAdapter;
import gank.qhm.com.hmgank.Controller.adapter.CommonRecyclerHolder;
import gank.qhm.com.hmgank.Model.CategoryModel;
import gank.qhm.com.hmgank.R;
import gank.qhm.com.hmgank.Utils.DisplayUtils;
import gank.qhm.com.hmgank.Utils.ImageLoader;
import gank.qhm.com.hmgank.Utils.ToastUtils;
import gank.qhm.com.hmgank.ViewModel.Presenter.MeiziPresenter;
import gank.qhm.com.hmgank.ViewModel.View.MeiziView;
import gank.qhm.com.hmgank.Widget.SquareImageView;
import gank.qhm.com.hmgank.Widget.recyclerviewwithfooter.OnLoadMoreListener;
import gank.qhm.com.hmgank.Widget.recyclerviewwithfooter.RecyclerViewWithFooter;

/**
 * Created by qhm on 2017/4/25
 * 妹子展示页面
 */

public class MeiziActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        OnLoadMoreListener, MeiziView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewWithFooter rv_content;

    private CommonRecyclerAdapter mAdapter;
    private List<CategoryModel.ResultsBean> mData;

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
        rv_content = fv(R.id.rv_content);
        swipeRefreshLayout = fv(R.id.swipeRefreshLayout);
        rv_content = fv(R.id.rv_content);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4,
                R.color.colorSwipeRefresh5,
                R.color.colorSwipeRefresh6);
        swipeRefreshLayout.setOnRefreshListener(this);
        rv_content.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv_content.setItemAnimator(null);
        rv_content.setAdapter(mAdapter =
                new CommonRecyclerAdapter<CategoryModel.ResultsBean>(R.layout.item_meizi, MeiziActivity.this, mData) {
                    @Override
                    public void convert(CommonRecyclerHolder holder, CategoryModel.ResultsBean item) {
                        ImageView iv_meizi = holder.getView(R.id.iv_meizi);
                        ImageLoader.loadImg(MeiziActivity.this, item.url, iv_meizi);
                        Glide.with(MeiziActivity.this).load(item.url).into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                int intrinsicHeight = resource.getIntrinsicHeight();
                                int intrinsicWidth = resource.getIntrinsicWidth();
                                ViewGroup.LayoutParams params = iv_meizi.getLayoutParams();
                                params.height = iv_meizi.getWidth() * (intrinsicHeight / intrinsicWidth);
                                Log.d("qhm", "pw === " + params.width);
                                Log.d("qhm", "ph === " + params.height);
                                Log.d("qhm", "dw === " + intrinsicWidth);
                                Log.d("qhm", "dh === " + intrinsicHeight);
                                iv_meizi.setImageDrawable(resource);

                             }
                        });
                        iv_meizi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("qhm", "url ---- " + item.url);
                                Log.d("qhm", "WIDTH ---- " + iv_meizi.getWidth());
                                Log.d("qhm", "height ---- " + iv_meizi.getHeight());
                                Drawable drawable = iv_meizi.getDrawable();

                                Log.d("qhm", "dWIDTH ---- " + drawable.getMinimumWidth());
                                Log.d("qhm", "dheight ---- " + drawable.getMinimumHeight());
                            }
                        });
                    }
                });
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
        }
        mData.addAll(model.results);
        mAdapter.notifyDataSetChanged();
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
}
