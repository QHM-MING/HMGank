package gank.qhm.com.hmgank.Utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qhm on 2017/4/25
 * 时间格式处理类
 */

public class TimeUtils {


    /**
     * UTC time to format
     *
     * @param utcTime
     * @param format  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatTo(String utcTime, String format) {
        if (utcTime == null) {
            return "";
        }

        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS")).parse(utcTime.replaceAll("Z$", ""));
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return utcTime;
    }

}
