package gank.qhm.com.hmgank.Controller;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gank.qhm.com.hmgank.Config;
import gank.qhm.com.hmgank.Controller.adapter.CommonRecyclerAdapter;
import gank.qhm.com.hmgank.Controller.adapter.CommonRecyclerHolder;
import gank.qhm.com.hmgank.Model.CategoryModel;
import gank.qhm.com.hmgank.R;
import gank.qhm.com.hmgank.Utils.DisplayUtils;
import gank.qhm.com.hmgank.Utils.ImageLoader;
import gank.qhm.com.hmgank.Utils.TimeUtils;
import gank.qhm.com.hmgank.Utils.ToastUtils;
import gank.qhm.com.hmgank.ViewModel.Presenter.CategoryPresenter;
import gank.qhm.com.hmgank.ViewModel.View.CategoryView;
import gank.qhm.com.hmgank.Widget.recyclerviewwithfooter.OnLoadMoreListener;
import gank.qhm.com.hmgank.Widget.recyclerviewwithfooter.RecyclerViewWithFooter;

/**
 * Created by qhm on 2017/4/21
 * 干货分类fragment
 */

public class CategoryFragment extends BaseFragment implements CategoryView,
        SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final int THUMBNAIL_WIDTH = 60;          //缩略图长边限
    private static final int THUMBNAIL_HEIGHT = 60;         //缩略图短边限

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewWithFooter rv_content;

    private String category;
    private CategoryPresenter mPresenter;
    private List<CategoryModel.ResultsBean> mData;
    private CommonRecyclerAdapter mAdapter;


    public static CategoryFragment NewInstance(String c) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_NAME, c);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initValue() {
        category = getArguments().getString(CATEGORY_NAME);
        mData = new ArrayList<>();
        mPresenter = new CategoryPresenter(this);
    }

    @Override
    protected void initView() {
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
        rv_content.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_content.setAdapter(mAdapter =
                new CommonRecyclerAdapter<CategoryModel.ResultsBean>(R.layout.item_category, mContext, mData) {
                    @Override
                    public void convert(CommonRecyclerHolder holder, CategoryModel.ResultsBean item, int position) {
                        RelativeLayout rl_root = holder.getView(R.id.rl_root);
                        TextView tv_content = holder.getView(R.id.tv_content);
                        TextView tv_name = holder.getView(R.id.tv_name);
                        TextView tv_time = holder.getView(R.id.tv_time);
                        ImageView iv_thumbnail = holder.getView(R.id.iv_thumbnail);
                        tv_content.setText(item.desc);
                        tv_name.setText(item.who);
                        tv_time.setText(TimeUtils.formatTo(item.publishedAt, "yyyy-MM-dd"));
                        if (item.images == null || item.images.size() == 0) {
                            iv_thumbnail.setVisibility(View.GONE);
                        } else {
                            iv_thumbnail.setVisibility(View.VISIBLE);
                            ImageLoader.loadImg(mContext, item.images.get(0), iv_thumbnail,
                                    DisplayUtils.dp2px(THUMBNAIL_WIDTH, mContext),
                                    DisplayUtils.dp2px(THUMBNAIL_HEIGHT, mContext));
                        }
                        rl_root.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转浏览
                            }
                        });

                    }
                });
        rv_content.setOnLoadMoreListener(this);
        rv_content.setEmpty();

    }

    @Override
    protected void initData() {
        mPresenter.getGankByCatrgory(true);
    }

    @Override
    public void onRefresh() {
        //下拉刷新
        mPresenter.getGankByCatrgory(true);
    }

    @Override
    public void showRefresh() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void refreshDate(CategoryModel value, boolean isRefresh) {
        if (value == null || value.results == null) {
            return;
        }
        if (isRefresh) {
            mData.clear();
        }
        mData.addAll(value.results);
        mAdapter.notifyDataSetChanged();
        if (value.results.size() < Config.LIMIT) {
            rv_content.setEnd();
        } else {
            rv_content.setLoading();
        }

    }

    @Override
    public void showLoadFail(String fail) {
        ToastUtils.showToast(fail);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getGankByCatrgory(false);
    }

    public String printCategory() {
        return getArguments().getString(CATEGORY_NAME);
    }
}
