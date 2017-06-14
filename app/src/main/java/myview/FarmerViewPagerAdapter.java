package myview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import util.FragmentManagement;

/**
 * Created by lenovo on 2017/6/4.
 */

public class FarmerViewPagerAdapter extends FragmentPagerAdapter {
    public FarmerViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentManagement.getFragmentList().get(position);
    }

    @Override
    public int getCount() {
        return FragmentManagement.getFragmentList().size();
    }
}
