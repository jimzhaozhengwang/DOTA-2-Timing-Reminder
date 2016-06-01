package ucalled911.DOTA2TimerAndReminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setup();
    }

    public void setup(){
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
        back_button_listener();
        this.setTitle("DOTA 2 Timer & Reminder");
        main_activity = new Intent(SettingsActivity.this, MainActivity.class);
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
                        toast_logic(twenty_seconds_array, index, rune);
                    }
                }
        );
        rune_picker.setOnItemSelectedListener(
                new HorizontalPicker.OnItemSelected() {
                    @Override
                    public void onItemSelected(int index) {
                        toast_logic(twenty_seconds_array, index, rune);
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
                        toast_logic(twenty_seconds_array, index, stack);
                    }
                }
        );
        stack_picker.setOnItemSelectedListener(
                new HorizontalPicker.OnItemSelected() {
                    @Override
                    public void onItemSelected(int index) {
                        toast_logic(twenty_seconds_array, index, stack);
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
                        toast_logic(ten_seconds_array, index, spawn);
                    }
                }
        );
        spawn_picker.setOnItemSelectedListener(
                new HorizontalPicker.OnItemSelected() {
                    @Override
                    public void onItemSelected(int index) {
                        toast_logic(ten_seconds_array, index, spawn);
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
                        toast_logic(thirty_seconds_array, index, day_night);
                    }
                }
        );
        day_night_picker.setOnItemSelectedListener(
                new HorizontalPicker.OnItemSelected() {
                    @Override
                    public void onItemSelected(int index) {
                        toast_logic(thirty_seconds_array, index, day_night);
                    }
                }
        );
    }

    public void toast_logic(String [] array_type, int index, String reminder_type){
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
            Toast.makeText(SettingsActivity.this, reminder_type + " " + reminder + " " + disabled, Toast.LENGTH_SHORT).show();
        } else {
            reminder_time = array_type[index];
            Toast.makeText(SettingsActivity.this, reminder_type + " " + reminder + " " + set_to + " " + reminder_time + " " + seconds_before_event, Toast.LENGTH_SHORT).show();
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

    public void back_button_listener(){
        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        finish();
                        startActivity(main_activity);
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(main_activity);
    }

}
