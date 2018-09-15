package ucalled911.DOTA2TimerAndReminder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class InformationTabsAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragment_list;
    private ArrayList<String> title_list;

    public InformationTabsAdapter(FragmentManager fm, ArrayList<Fragment> fragment_list, ArrayList<String> title_list) {
        super(fm);
        this.fragment_list = fragment_list;
        this.title_list = title_list;


    }

    @Override
    public Fragment getItem(int position) {
        return fragment_list.get(position);
    }

    @Override
    public int getCount() {
        return fragment_list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title_list.get(position);
    }
}
