package gank.qhm.com.hmgank.Controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gank.qhm.com.hmgank.Model.CategoryModel;
import gank.qhm.com.hmgank.Model.MeiziSizeModel;
import gank.qhm.com.hmgank.R;
import gank.qhm.com.hmgank.Utils.DisplayUtils;
import gank.qhm.com.hmgank.Utils.ImageLoader;
import gank.qhm.com.hmgank.Utils.TimeUtils;

/**
 * Created by qhm on 2017/4/27
 */

public class MeiziRecylerViewAdapter extends RecyclerView.Adapter<MeiziRecylerViewAdapter.MeiziRecylerViewHodel> {

    private Context mContext;
    private List<CategoryModel.ResultsBean> mData;
    private List<MeiziSizeModel> sizeModels;

    public MeiziRecylerViewAdapter(Context mContext, List<CategoryModel.ResultsBean> mData, List<MeiziSizeModel> sizeModels) {
        this.mContext = mContext;
        this.mData = mData;
        this.sizeModels = sizeModels;
    }

    @Override
    public MeiziRecylerViewHodel onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meizi, parent, false);
        return new MeiziRecylerViewHodel(view);
    }

    @Override
    public void onBindViewHolder(MeiziRecylerViewHodel holder, int position) {
        holder.setData(mData.get(position), sizeModels.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MeiziRecylerViewHodel extends RecyclerView.ViewHolder {

        public ImageView iv_meizi;
        public TextView tv_name;
        public TextView tv_time;

        public MeiziRecylerViewHodel(View itemView) {
            super(itemView);
            iv_meizi = (ImageView) itemView.findViewById(R.id.iv_meizi);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }

        public void setData(CategoryModel.ResultsBean resultsBean, MeiziSizeModel meiziSizeModel) {
            if (!meiziSizeModel.isNull()) {
                setImageLayoutParams(meiziSizeModel.width, meiziSizeModel.height);
            }
            ImageLoader.loadImg(mContext, resultsBean.url, resource -> {
               int width =  ((Activity)mContext).getWindow().getDecorView().getWidth() / 2;
                int viewWidth = width - DisplayUtils.dp2px(5 + 5, mContext);
//                int viewWidth = iv_meizi.getWidth();
                int viewHeight = (int) (resource.getIntrinsicHeight() * (viewWidth * 1.0) / resource.getMinimumWidth());
                Log.d("qhm", "vw == " + viewWidth);
                Log.d("qhm", "vh == " + viewHeight);
                setImageLayoutParams(viewWidth, viewHeight);
                meiziSizeModel.setSize(viewWidth, viewHeight);
                iv_meizi.setImageDrawable(resource);
            });

//            tv_name.setText(resultsBean.who);
//            tv_time.setText(TimeUtils.formatTo(resultsBean.publishedAt, "yyyy-MM-dd"));
        }

        private void setImageLayoutParams(int width, int height) {
            ViewGroup.LayoutParams layoutParams = iv_meizi.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            iv_meizi.setLayoutParams(layoutParams);
        }
    }
}
