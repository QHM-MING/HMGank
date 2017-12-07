package gank.qhm.com.hmgank;

import android.Manifest;

/**
 * Created by qhm on 2017/4/7
 */

public class Config {

    public static final String API = "http://gank.io/api/";

    //项目中列表请求个数
    public static final int LIMIT = 10;

    public static final String[] TITLES = new String[]{"App", "Android", "iOS", "前端", "瞎推荐"};

    public static final String CATEGORY_MEIZI = "福利";

    public static class Permission {
        public static final String REQUEST_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    }
}
