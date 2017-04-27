package gank.qhm.com.hmgank.Utils.Ext;



import java.util.Date;


/**
 * Created by yuety on 14-8-12.
 */
public class TimeSpan {
    private long mMilliseconds = 0;
    private boolean mIsSameDay = true;

    public TimeSpan() {
    }

    public TimeSpan(long d1, long d2) {
        mMilliseconds = d1 - d2;
    }

    public TimeSpan(Date d1, Date d2) {
        mMilliseconds = d1.getTime() - d2.getTime();
//        mIsSameDay = DateUtil.getDay(d1) == DateUtil.getDay(d2);//暂时
    }

    public long seconds() {
        return mMilliseconds / 1000;
    }

    public long minutes() {
        return mMilliseconds / 1000 / 60;
    }

    public long hours() {
        return mMilliseconds / 1000 / 60 / 60;
    }

    public long days() {
        return mMilliseconds / 1000 / 60 / 60 / 24;
    }

    public boolean isSameDay() {
        return mIsSameDay;
    }

    public long years() {
        return mMilliseconds / 1000 / 60 / 60 / 24 / 365;
    }

}
