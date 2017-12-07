package gank.qhm.com.hmgank.ViewModel.View;

/**
 * Created by qhm on 2017/4/25
 * <p>
 * 主页View
 */

public interface MainView {
    void setFabDynamicState();

    void initSearchClick();

    void initMeizi(String url);

    void initMeiziFail();

    void initMeiziClick();

    void initMenuClick();
}
