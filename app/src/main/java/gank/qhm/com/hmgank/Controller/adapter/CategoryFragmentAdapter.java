package gank.qhm.com.hmgank.Controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import gank.qhm.com.hmgank.Controller.BaseFragment;
import gank.qhm.com.hmgank.Controller.CategoryFragment;

/**
 * Created by qhm on 2017/4/24
 */

public class CategoryFragmentAdapter extends FragmentPagerAdapter {

    private List<CategoryFragment> fragments;
    private List<String> titles;

    public CategoryFragmentAdapter(FragmentManager fm, List<CategoryFragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
