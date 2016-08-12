package ucalled911.DOTA2TimerAndReminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.wefika.horizontalpicker.HorizontalPicker;

public class SettingsActivity extends AppCompatActivity {

    private String rune_time = "";
    private String spawn_time = "";
    private String stack_time = "";
    private String day_night_time = "";

    private String[] ten_seconds_array;
    private String[] twenty_seconds_array;
    private String[] thirty_seconds_array;

    private SharedPreferences shared_preferences;
    private Intent main_activity;
    private CoordinatorLayout coordinator_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setup();
    }

    public void setup(){
        Toolbar tool_bar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(tool_bar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shared_preferences = getSharedPreferences("user_settings", Context.MODE_PRIVATE);

        rune_time = shared_preferences.getString("rune_time", "20");
        stack_time = shared_preferences.getString("stack_time", "20");
        spawn_time = shared_preferences.getString("spawn_time", "15");
        day_night_time = shared_preferences.getString("day_night_time", "30");

        ten_seconds_array = getResources().getStringArray(R.array.r_ten_seconds_array);
        twenty_seconds_array = getResources().getStringArray(R.array.r_twenty_seconds_array);
        thirty_seconds_array = getResources().getStringArray(R.array.r_thirty_seconds_array);

        rune_picker_listener();
        stack_picker_listener();
        spawn_picker_listener();
        day_night_picker_listener();
        main_activity = new Intent(SettingsActivity.this, MainActivity.class);
        coordinator_layout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            // for API 11 +
            onBackPressed();
            // API 16 +
            // http://stackoverflow.com/questions/10108774/how-to-implement-the-android-actionbar-back-button
            //NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void rune_picker_listener(){
        HorizontalPicker rune_picker = (HorizontalPicker) findViewById(R.id.rune_picker);
        for (int c = 0; c < twenty_seconds_array.length; c++){
            if (twenty_seconds_array[c].equals(rune_time)){
                rune_picker.setSelectedItem(c);
                break;
            }
        }
        final String rune = getResources().getString(R.string.r_rune);
        rune_picker.setOnItemClickedListener(
                new HorizontalPicker.OnItemClicked() {
                    @Override
                    public void onItemClicked(int index) {
                        snackbar_logic(twenty_seconds_array, index, rune);
                    }
                }
        );
        rune_picker.setOnItemSelectedListener(
                new HorizontalPicker.OnItemSelected() {
                    @Override
                    public void onItemSelected(int index) {
                        snackbar_logic(twenty_seconds_array, index, rune);
                    }
                }
        );
    }

    public void stack_picker_listener(){
        HorizontalPicker stack_picker = (HorizontalPicker) findViewById(R.id.stack_picker);
        for (int c = 0; c < twenty_seconds_array.length; c++){
            if (twenty_seconds_array[c].equals(stack_time)){
                stack_picker.setSelectedItem(c);
                break;
            }
        }
        final String stack = getResources().getString(R.string.r_stack);

        stack_picker.setOnItemClickedListener(
                new HorizontalPicker.OnItemClicked() {
                    @Override
                    public void onItemClicked(int index) {
                        snackbar_logic(twenty_seconds_array, index, stack);
                    }
                }
        );
        stack_picker.setOnItemSelectedListener(
                new HorizontalPicker.OnItemSelected() {
                    @Override
                    public void onItemSelected(int index) {
                        snackbar_logic(twenty_seconds_array, index, stack);
                    }
                }
        );
    }

    public void spawn_picker_listener(){
        HorizontalPicker spawn_picker = (HorizontalPicker) findViewById(R.id.spawn_picker);
        for (int c = 0; c < ten_seconds_array.length; c++){
            if (ten_seconds_array[c].equals(spawn_time)){
                spawn_picker.setSelectedItem(c);
                break;
            }
        }
        final String spawn = getResources().getString(R.string.r_spawn);

        spawn_picker.setOnItemClickedListener(
                new HorizontalPicker.OnItemClicked() {
                    @Override
                    public void onItemClicked(int index) {
                        snackbar_logic(ten_seconds_array, index, spawn);
                    }
                }
        );
        spawn_picker.setOnItemSelectedListener(
                new HorizontalPicker.OnItemSelected() {
                    @Override
                    public void onItemSelected(int index) {
                        snackbar_logic(ten_seconds_array, index, spawn);
                    }
                }
        );
    }

    public void day_night_picker_listener(){
        HorizontalPicker day_night_picker = (HorizontalPicker) findViewById(R.id.day_night_picker);
        for (int c = 0; c < thirty_seconds_array.length; c++){
            if (thirty_seconds_array[c].equals(day_night_time)){
                day_night_picker.setSelectedItem(c);
                break;
            }
        }
        final String day_night = getResources().getString(R.string.r_day_night);

        day_night_picker.setOnItemClickedListener(
                new HorizontalPicker.OnItemClicked() {
                    @Override
                    public void onItemClicked(int index) {
                        snackbar_logic(thirty_seconds_array, index, day_night);
                    }
                }
        );
        day_night_picker.setOnItemSelectedListener(
                new HorizontalPicker.OnItemSelected() {
                    @Override
                    public void onItemSelected(int index) {
                        snackbar_logic(thirty_seconds_array, index, day_night);
                    }
                }
        );
    }

    public void snackbar_logic(String [] array_type, int index, String reminder_type){
        final String off = getResources().getString(R.string.r_off);
        final String reminder = getResources().getString(R.string.r_reminder);
        final String disabled = getResources().getString(R.string.r_disabled);
        final String set_to = getResources().getString(R.string.r_set_to);
        final String seconds_before_event = getResources().getString(R.string.r_seconds_before_event);
        final String rune = getResources().getString(R.string.r_rune);
        final String stack = getResources().getString(R.string.r_stack);
        final String spawn = getResources().getString(R.string.r_spawn);
        // final String day_night = getResources().getString(R.string.r_day_night);

        String reminder_time = "";

        if (array_type[index].equals(off)) {
            reminder_time = off;
            Snackbar.make(coordinator_layout, reminder_type + " " + reminder + " " + disabled, Snackbar.LENGTH_SHORT).show();
        } else {
            reminder_time = array_type[index];
            Snackbar.make(coordinator_layout, reminder_type + " " + reminder + " " + set_to + " " + reminder_time + " " + seconds_before_event, Snackbar.LENGTH_SHORT).show();
        }
        SharedPreferences.Editor editor = shared_preferences.edit();
        if (reminder_type.equals(rune)) {
            rune_time = reminder_time;
            editor.putString("rune_time", rune_time);
        }else if (reminder_type.equals(stack)){
            stack_time = reminder_time;
            editor.putString("stack_time", stack_time);
        }else if (reminder_type.equals(spawn)){
            spawn_time = reminder_time;
            editor.putString("spawn_time", spawn_time);
        }else{ //reminder_time.equals(day_night)
            day_night_time = reminder_time;
            editor.putString("day_night_time", day_night_time);
        }
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(main_activity);
    }

}
