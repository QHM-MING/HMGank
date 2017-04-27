package gank.qhm.com.hmgank.Controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by qhm on 2017/4/24
 * 通用RecyclerView Adapter
 */

public class CommonRecyclerHolder extends RecyclerView.ViewHolder {

    public View ContentView;

    public CommonRecyclerHolder(View itemView) {
        super(itemView);
        this.ContentView = itemView;
    }

    public <T extends View> T getView(int id) {
        return (T) ContentView.findViewById(id);
    }
}
