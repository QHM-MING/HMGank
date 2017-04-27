package gank.qhm.com.hmgank.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by qhm on 2017/4/7
 */

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        initValue();
        initView();
        initData();
    }

    protected abstract int initLayout();

    protected abstract void initValue();

    protected abstract void initView();

    protected abstract void initData();

    public <T> T fv(int viewId) {
        return (T) findViewById(viewId);
    }
}
