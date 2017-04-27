package gank.qhm.com.hmgank.ViewModel.Presenter;

import gank.qhm.com.hmgank.Config;
import gank.qhm.com.hmgank.Dao.HMGankServer;
import gank.qhm.com.hmgank.Model.CategoryModel;
import gank.qhm.com.hmgank.ViewModel.View.MeiziView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qhm on 2017/4/25
 */

public class MeiziPresenter extends BasePresenter {

    private MeiziView mMeiziView;

    private int mPage = 1;

    public MeiziPresenter(MeiziView mMeiziView) {
        this.mMeiziView = mMeiziView;
    }

    public void getMeizi(boolean isRefresh) {
        if (isRefresh) {
            mMeiziView.showRefresh();
            mPage = 1;
        } else {
            mPage++;
        }


        HMGankServer.getApi().getCategoryDate(mMeiziView.getCategory(), Config.LIMIT, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryModel>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CategoryModel value) {
                        mMeiziView.stopRefresh();
                        mMeiziView.refreshData(value, isRefresh);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMeiziView.stopRefresh();
                        mMeiziView.showLoadFail(mMeiziView.getCategory() + " 列表数据获取失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
