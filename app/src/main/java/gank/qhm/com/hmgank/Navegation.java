package gank.qhm.com.hmgank;

import android.app.Activity;
import android.content.Intent;

import gank.qhm.com.hmgank.Controller.MeiziActivity;

/**
 * Created by qhm on 2017/4/7
 * <p>
 * 项目页面跳转管理
 */

public class Navegation {

    /**
     * 更多妹子
     */
    public static void showMeizi(Activity from) {
        Intent intent = new Intent(from, MeiziActivity.class);
        from.startActivity(intent);
    }

}
