package gank.qhm.com.hmgank.ViewModel.View;

/**
 * Created by qhm on 2017/5/5
 * 展示大图
 */

public interface LargePhotoView extends BaseView {

    void onUiReady();

    void enterAnimation();

    void exitAnimation(Runnable runnable);

    String getUrl();

}
