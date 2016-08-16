package ucalled911.DOTA2TimerAndReminder;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    private static int second = 0;
    private static int minute = 0;
    private static String plus_minus_zero = "0";
    private RadioButton before_after_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.ic_launcher);
        second = 0;
        minute = 0;
        plus_minus_zero = "0";
        second_picker_listener();
        minute_picker_listener();
        start_button_listener();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings){
            Intent settings_activity = new Intent(MainActivity.this, SettingsActivity.class);
            finish();
            startActivity(settings_activity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void second_picker_listener(){
        NumberPicker second_picker = (NumberPicker) findViewById(R.id.second_picker);
        second_picker.setMaxValue(59);
        second_picker.setMinValue(0);
        second_picker.setWrapSelectorWheel(true);

        second_picker.setOnValueChangedListener(
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        second = picker.getValue();
                    }
                }
        );
    }

    public void minute_picker_listener(){
        NumberPicker minute_picker = (NumberPicker) findViewById(R.id.minute_picker);
        minute_picker.setMaxValue(99);
        minute_picker.setMinValue(0);
        minute_picker.setWrapSelectorWheel(true);

        minute_picker.setOnValueChangedListener(
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        minute = picker.getValue();
                    }
                }
        );
    }

    public void start_button_listener(){
        final RadioButton before_button = (RadioButton) findViewById(R.id.before_button);
        // RadioButton after_button = (RadioButton) findViewById(R.id.after_button);

        Button start_button = (Button) findViewById(R.id.start_button);
        final RadioGroup before_after_group = (RadioGroup) findViewById(R.id.before_after_group);

        start_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View V) {
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
                }
        );
    }

    public int getSecond(){
        return second;
    }

    public int getMinute(){
        return minute;
    }

    public String getPlus_minus_zero(){
        return plus_minus_zero;
    }
}
