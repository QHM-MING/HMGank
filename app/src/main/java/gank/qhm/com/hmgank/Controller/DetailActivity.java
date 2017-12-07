package gank.qhm.com.hmgank.Controller;

import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import gank.qhm.com.hmgank.Model.CategoryModel;
import gank.qhm.com.hmgank.R;
import gank.qhm.com.hmgank.Utils.DisplayUtils;
import gank.qhm.com.hmgank.Widget.ProgressView;

/**
 * Created by qhm on 2017/5/11
 * 展示干货详情
 */

public class DetailActivity extends BaseActivity {
    public static final String EXTRA_BEAN = "title";

    private AppBarLayout mAppbar;
    private Toolbar toolbar;
    private TextView tv_title;
    private WebView web_content;
    private ProgressView pv;

    private CategoryModel.ResultsBean mBean;

    @Override
    protected int initLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initValue() {
        mBean = (CategoryModel.ResultsBean) getIntent().getSerializableExtra(EXTRA_BEAN);
    }

    @Override
    protected void initView() {
        mAppbar = fv(R.id.mAppbar);
        toolbar = fv(R.id.toolbar);
        tv_title = fv(R.id.tv_title);
        pv = fv(R.id.pv);
        web_content = fv(R.id.web_content);
        DisplayUtils.setViewTransparentPadding(this, mAppbar);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        web_content.setWebViewClient(new HmWebViewClient());
        WebSettings webSettings = web_content.getSettings();
        webSettings.setJavaScriptEnabled(true);         //支持Javascript
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(false);       //设置不支持缩放
    }

    @Override
    protected void initData() {
        if (mBean != null) {
            tv_title.setText(mBean.desc);
            web_content.loadUrl(mBean.url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //其中webView.canGoBack()在webView含有一个可后退的浏览记录时返回true
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web_content.canGoBack()) {
            web_content.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    class HmWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pv.stopAnimation();
            pv.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pv.startAnimation();
        }
    }
}
