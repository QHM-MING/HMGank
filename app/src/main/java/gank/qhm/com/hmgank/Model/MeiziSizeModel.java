package gank.qhm.com.hmgank.Model;

/**
 * Created by qhm on 2017/4/27
 * 记录item ImageView 尺寸
 */

public class MeiziSizeModel {

    public int width;
    public int height;
    public String url;

    public MeiziSizeModel(String url) {
        this.url = url;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isNull() {
        return width == 0 || height == 0;
    }
}
