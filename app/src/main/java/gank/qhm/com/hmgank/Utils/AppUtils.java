package gank.qhm.com.hmgank.Utils;

import android.content.Context;

/**
 * Created by qhm on 2017/3/22
 */

public class AppUtils {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }
}
