package gank.qhm.com.hmgank.ViewModel.Presenter;

import gank.qhm.com.hmgank.Config;
import gank.qhm.com.hmgank.Dao.HMGankServer;
import gank.qhm.com.hmgank.Model.CategoryModel;
import gank.qhm.com.hmgank.ViewModel.View.CategoryView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qhm on 2017/4/24
 */

public class CategoryPresenter extends BasePresenter {

    private CategoryView categoryView;

    private int mPage = 1;

    public CategoryPresenter(CategoryView categoryView) {
        this.categoryView = categoryView;
    }

    /**
     * 根据类型获取干货
     */
    public void getGankByCatrgory(final boolean isRefresh) {
        if (isRefresh) {
            categoryView.showRefresh();
            mPage = 1;
        } else {
            mPage++;
        }

        HMGankServer.getApi().getCategoryDate(categoryView.getCategory(), Config.LIMIT, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryModel>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CategoryModel value) {
                        categoryView.hideRefresh();
                        categoryView.refreshDate(value, isRefresh);
                    }

                    @Override
                    public void onError(Throwable e) {
                        categoryView.hideRefresh();
                        categoryView.showLoadFail(categoryView.getCategory() + " 列表数据获取失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
