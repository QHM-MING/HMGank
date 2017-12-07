package gank.qhm.com.hmgank.ViewModel.View;

import gank.qhm.com.hmgank.Model.CategoryModel;

/**
 * Created by qhm on 2017/4/25
 * <p>
 * 妹子View
 */

public interface MeiziView extends BaseView {

    void showRefresh();

    void stopRefresh();

    void refreshData(CategoryModel model, boolean isRefresh);

    String getCategory();

    void showLoadFail(String s);

    int randomColor(int position);
}
