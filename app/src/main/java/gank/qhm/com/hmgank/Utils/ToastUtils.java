package gank.qhm.com.hmgank.Utils;

import android.widget.Toast;

/**
 * Created by qhm on 2017/3/22
 */

public class ToastUtils {

    public static void showToast(String msg) {
        Toast.makeText(AppUtils.getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(String msg, int duration) {
        Toast.makeText(AppUtils.getContext(), msg, duration).show();
    }
}
