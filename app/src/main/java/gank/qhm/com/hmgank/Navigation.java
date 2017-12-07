package gank.qhm.com.hmgank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import gank.qhm.com.hmgank.Controller.LargePhotoActivity;
import gank.qhm.com.hmgank.Controller.MeiziActivity;
import gank.qhm.com.hmgank.Controller.DetailActivity;
import gank.qhm.com.hmgank.Model.CategoryModel;

/**
 * Created by qhm on 2017/4/7
 * <p>
 * 项目页面跳转管理
 */

public class Navigation {

    /**
     * 更多妹子
     */
    public static void showMeizi(Activity from) {
        Intent intent = new Intent(from, MeiziActivity.class);
        from.startActivity(intent);
    }

    /**
     * 展示图片大图
     */
    public static void showLargePhoto(Activity from, String url, Bundle bundle) {
        Intent intent = new Intent(from, LargePhotoActivity.class);
        intent.putExtra(LargePhotoActivity.EXTRA_URL, url);
        intent.putExtra(LargePhotoActivity.EXTRA_BUNDLE, bundle);
        from.startActivity(intent);
        from.overridePendingTransition(0, 0);
    }

    /**
     * 干货详情页面
     *
     * @param from
     */
    public static void showGankDetail(Activity from, CategoryModel.ResultsBean bean) {
        Intent intent = new Intent(from, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_BEAN, bean);
        from.startActivity(intent);
    }

}
