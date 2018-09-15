package ucalled911.DOTA2TimerAndReminder.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ucalled911.DOTA2TimerAndReminder.R;
import ucalled911.DOTA2TimerAndReminder.adapter.InformationTabsAdapter;
import ucalled911.DOTA2TimerAndReminder.fragments.FragmentFour;
import ucalled911.DOTA2TimerAndReminder.fragments.FragmentOne;
import ucalled911.DOTA2TimerAndReminder.fragments.FragmentThree;
import ucalled911.DOTA2TimerAndReminder.fragments.FragmentTwo;

public class InformationActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragment_list = new ArrayList<Fragment>();
    private ArrayList<String> title_list = new ArrayList<String>();

    private ViewPager view_pager;
    private TabLayout tab_layout;
    private Intent main_activity;

    private void addData(Fragment fragment, String text) {
        fragment_list.add(fragment);
        title_list.add(text);
    }

    private void prepareDataResource() {
        addData(new FragmentOne(), "Rune");
        addData(new FragmentTwo(), "Stack");
        addData(new FragmentThree(), "Spawn");
        addData(new FragmentFour(), "Day Night");
    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.r_information);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        setupToolbar();
        view_pager = (ViewPager) findViewById(R.id.viewPager);
        tab_layout = (TabLayout) findViewById(R.id.tabs);

        main_activity = new Intent(InformationActivity.this, MainActivity.class);

        prepareDataResource();
        InformationTabsAdapter adapter = new InformationTabsAdapter(getSupportFragmentManager(), fragment_list, title_list);
        view_pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(view_pager);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // for API 11 +
            onBackPressed();
            // API 16 +
            // http://stackoverflow.com/questions/10108774/how-to-implement-the-android-actionbar-back-button
            //NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(main_activity);
    }
}
