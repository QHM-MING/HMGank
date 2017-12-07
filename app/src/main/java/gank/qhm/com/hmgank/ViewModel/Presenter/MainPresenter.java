package gank.qhm.com.hmgank.ViewModel.Presenter;

import gank.qhm.com.hmgank.Dao.HMGankServer;
import gank.qhm.com.hmgank.Model.CategoryModel;
import gank.qhm.com.hmgank.Utils.Ext.Act1;
import gank.qhm.com.hmgank.ViewModel.View.MainView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qhm on 2017/4/25
 */

public class MainPresenter extends BasePresenter {
    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void initMeizi(String categoryMeizi, int limit, int page, Act1<String> callback) {
        HMGankServer.getApi().getCategoryDate(categoryMeizi, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryModel>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CategoryModel value) {
                        if (value != null && value.results != null && value.results.size() > 0) {
                            callback.run(value.results.get(0).url);
                        } else {
                            callback.run("");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.run("");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
