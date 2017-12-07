package gank.qhm.com.hmgank.ViewModel.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

import gank.qhm.com.hmgank.Config;
import gank.qhm.com.hmgank.Utils.ImageLoader;
import gank.qhm.com.hmgank.Utils.ImageUtils;
import gank.qhm.com.hmgank.Utils.ToastUtils;
import gank.qhm.com.hmgank.ViewModel.View.LargePhotoView;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by qhm on 2017/5/5
 */

public class LargePhotoPresenter extends BasePresenter {

    private static final int PREMISSION_IMG = 1;
    private static final int SAVE_PHOTO = 2;


    private Context mContext;
    private LargePhotoView largePhotoView;
    private Handler handler = null;
    private Bitmap bitmap = null;

    public LargePhotoPresenter(Context context, LargePhotoView largePhotoView) {
        this.largePhotoView = largePhotoView;
        this.mContext = context;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == SAVE_PHOTO) {
                    String filePath = ImageUtils.saveImageToGallery(bitmap, largePhotoView.getUrl());
                    File file = new File(filePath);
                    mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                    ToastUtils.showToast("保存成功");
                }
            }
        };
    }

    public void savePhoto(String url) {
        if (TextUtils.isEmpty(url)) {
            return; //图片URL为空
        }
        String[] per = {Config.Permission.REQUEST_WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(mContext, per)) {
            //有权限
            new Thread(() -> {
                try {
                    bitmap = ImageLoader.downloadImageReSize(mContext, url, Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    Message message = handler.obtainMessage();
                    message.what = SAVE_PHOTO;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            EasyPermissions.requestPermissions((Activity) mContext, "允许访问您的相册？", PREMISSION_IMG, per);
        }
    }
}
