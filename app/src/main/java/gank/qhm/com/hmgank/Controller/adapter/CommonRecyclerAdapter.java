package gank.qhm.com.hmgank.Controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qhm on 2017/4/24
 * 通用RecyclerViewHolder
 */

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter {

    private Context mContext;
    private List<T> mData;
    private int itemId;

    public CommonRecyclerAdapter(int layoutId, Context context, List<T> list) {
        this.itemId = layoutId;
        this.mContext = context;
        mData = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(itemId, parent, false);
        return new CommonRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommonRecyclerHolder commonRecyclerHolder = (CommonRecyclerHolder) holder;
        convert(commonRecyclerHolder, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public abstract void convert(CommonRecyclerHolder helper, T item);
}
