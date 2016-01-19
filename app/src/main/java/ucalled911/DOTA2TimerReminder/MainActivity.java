package ucalled911.DOTA2TimerReminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    static int second = 0;
    static int minute = 0;
    static String plus_minus_zero = "0";
    static RadioButton before_after_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
    }

    public void setup(){
        second = 0;
        minute = 0;
        plus_minus_zero = "0";

        second_picker_listener();
        minute_picker_listener();
        start_button_listener();
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
                        if (before_after_button.getId() == before_button.getId()){
                            plus_minus_zero = "-";
                        }
                        else { // before_after_button.getId() == after_button.getId()
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
