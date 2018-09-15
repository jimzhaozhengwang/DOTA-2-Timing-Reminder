package ucalled911.DOTA2TimerAndReminder.app;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ucalled911.DOTA2TimerAndReminder.R;


public class MainActivity extends AppCompatActivity {
    private static int second = 0;
    private static int minute = 0;
    private static String plus_minus_zero = "0";
    private RadioButton before_after_button;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setUpDrawer();
        second = 0;
        minute = 0;
        plus_minus_zero = "0";
        second_picker_listener();
        minute_picker_listener();
        start_button_listener();
    }

    private void setUpDrawer() {
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drwr_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment.setUpDrawer(R.id.nav_drwr_fragment, drawerLayout, toolbar);
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.r_timing_reminder);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // return super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean go_to_settings() {
        Intent settings_activity = new Intent(MainActivity.this, SettingsActivity.class);
        finish();
        startActivity(settings_activity);
        return true;
    }

    public boolean go_to_information() {
        Intent information_activity = new Intent(MainActivity.this, InformationActivity.class);
        finish();
        startActivity(information_activity);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.information) {
            go_to_information();
        } else if (id == R.id.settings) {
            go_to_settings();
        }

        return super.onOptionsItemSelected(item);
    }

    public void second_picker_listener() {
        NumberPicker second_picker = (NumberPicker) findViewById(R.id.second_picker);
        second_picker.setMaxValue(59);
        second_picker.setMinValue(0);
        second_picker.setWrapSelectorWheel(true);
        second_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        second_picker.setOnValueChangedListener(
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        second = picker.getValue();
                    }
                }
        );
    }

    public void minute_picker_listener() {
        NumberPicker minute_picker = (NumberPicker) findViewById(R.id.minute_picker);
        minute_picker.setMaxValue(99);
        minute_picker.setMinValue(0);
        minute_picker.setWrapSelectorWheel(true);
        minute_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        minute_picker.setOnValueChangedListener(
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        minute = picker.getValue();
                    }
                }
        );
    }

    public void start_button_listener() {
        final RadioButton before_button = (RadioButton) findViewById(R.id.before_button);
        // RadioButton after_button = (RadioButton) findViewById(R.id.after_button);
        final RadioGroup before_after_group = (RadioGroup) findViewById(R.id.before_after_group);

        FloatingActionButton fab_start = (FloatingActionButton) findViewById(R.id.fab);

        fab_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int before_after_selected_id = before_after_group.getCheckedRadioButtonId();
                before_after_button = (RadioButton) findViewById(before_after_selected_id);
                if (before_after_button.getId() == before_button.getId()) {
                    plus_minus_zero = "-";
                } else { // before_after_button.getId() == after_button.getId()
                    plus_minus_zero = "+";
                }
                Intent reminder_activity = new Intent(MainActivity.this, ReminderActivity.class);
                finish();
                startActivity(reminder_activity);
            }
        });
    }

    public int getSecond() {
        return second;
    }

    public int getMinute() {
        return minute;
    }

    public String getPlus_minus_zero() {
        return plus_minus_zero;
    }
}
