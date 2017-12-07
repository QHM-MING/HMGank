package gank.qhm.com.hmgank.Utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by qhm on 2017/5/5
 * 图片操作utils
 */

public class ImageUtils {

    /**
     * 保存图片到本地
     *
     * @param bmp 图片
     * @param url url
     * @return 保存的路径
     */
    public static String saveImageToGallery(Bitmap bmp, String url) {
        if (bmp == null || TextUtils.isEmpty(url)) {
            ToastUtils.showToast("保存失败了");
            return "";
        }
        String path;
        // 首先保存图片
        File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "photo");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File file = new File(appDir, fileName);
        path = file.getAbsolutePath();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ToastUtils.showToast("保存失败了");
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtils.showToast("保存失败了");
        }
        return path;
    }

}
