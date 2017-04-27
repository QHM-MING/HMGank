package gank.qhm.com.hmgank.ViewModel.View;

import gank.qhm.com.hmgank.Model.CategoryModel;

/**
 * Created by qhm on 2017/4/24
 */

public interface CategoryView extends BaseView {
    void showRefresh();

    void hideRefresh();

    String getCategory();

    void refreshDate(CategoryModel value, boolean isRefresh);

    void showLoadFail(String fail);
}
