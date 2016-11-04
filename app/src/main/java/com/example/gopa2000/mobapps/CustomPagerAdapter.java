package com.example.gopa2000.mobapps;

/**
 * Created by gopa2000 on 11/4/16.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public CustomPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ProfileTabFragment tab1 = new ProfileTabFragment();
                return tab1;
            case 1:
                MainViewFragment tab2 = new MainViewFragment();
                return tab2;
            case 2:
                MessagingFragment tab3 = new MessagingFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
