package cn.piesat.medicaid.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 模拟实验tob
 */

public class ReactionPagerAdapter extends FragmentPagerAdapter {

    private String[] listTitle;
    private List<Fragment> listFragment;

    public ReactionPagerAdapter(FragmentManager fm, String[] listTitle, List<Fragment> listFragment) {
        super(fm);
        this.listTitle = listTitle;
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle[position];
    }
}
