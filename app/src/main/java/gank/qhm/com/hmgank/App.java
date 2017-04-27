package gank.qhm.com.hmgank;

import android.app.Application;

import gank.qhm.com.hmgank.Utils.AppUtils;

/**
 * Created by qhm on 2017/4/7
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
    }
}
