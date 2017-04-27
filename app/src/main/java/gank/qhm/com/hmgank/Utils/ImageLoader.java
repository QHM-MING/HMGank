package gank.qhm.com.hmgank.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import gank.qhm.com.hmgank.R;

/**
 * Created by qhm on 2017/4/25
 * 图片加载工具类，图片处理走七牛
 * 图片压缩处理详见 https://developer.qiniu.com/dora/api/1279/basic-processing-images-imageview2
 */

public class ImageLoader {

    /**
     * 图片加载 原图不进行图片的大小缩放
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void loadImg(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }

    /**
     * 图片加载 原图不进行图片的大小缩放
     *
     * @param context
     * @param url
     * @param iv
     * @param errorDrawable 图片加载失败显示图片
     */
    public static void loadImg(Context context, String url, ImageView iv, Drawable errorDrawable) {
        Glide.with(context).load(url).into(iv).onLoadFailed(new Exception("图片下载失败 url" + url), errorDrawable);
    }

    /**
     * 模式 ?imageView2/0/w/<LongEdge>/h/<ShortEdge>
     * 限定缩略图的长边最多为<LongEdge>，短边最多为<ShortEdge>，进行等比缩放，不裁剪。
     * 如果只指定 w 参数则表示限定长边（短边自适应），只指定 h 参数则表示限定短边（长边自适应）
     *
     * @param context
     * @param url
     * @param iv
     * @param width
     * @param height
     */
    public static void loadImg(Context context, String url, ImageView iv, int width, int height) {
        String finalUrl = url + "?imageView2/0/w/" + width + "/h/" + height;
        Glide.with(context).load(finalUrl).into(iv);
    }

    public static void loadImg(Context context, String url, ImageView iv, int width) {
        String finalUrl = url + "?imageView2/2/w/" + width;
        Glide.with(context).load(finalUrl).into(iv);
    }



}
