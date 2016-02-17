package ucalled911.DOTA2TimerAndReminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ReminderActivity extends AppCompatActivity {

    MainActivity access = new MainActivity();
    public int second = access.getSecond();
    public int minute = access.getMinute();
    public String plus_minus_zero = access.getPlus_minus_zero();
    public String plus = "+";
    public String minus = "-";
    long startTime = 0;
    MediaPlayer the_battle_begins_sound;
    MediaPlayer stack_sound;
    MediaPlayer rune_sound;
    MediaPlayer day_sound;
    MediaPlayer night_sound;
    MediaPlayer spawn_sound;

    String rune_time;
    String stack_time;
    String spawn_time;
    String day_night_time;
    SharedPreferences shared_preferences;
    String off;

    Intent main_activity;
    PowerManager power_manager;
    PowerManager.WakeLock wake_lock;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millisecond = System.currentTimeMillis() - startTime;
            int one_second = (int) (millisecond / 1000);
            if (one_second == 1){
                startTime = System.currentTimeMillis();
                forward_second_logic();
            }
            timerHandler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        setup();
    }

    public void setup(){

        shared_preferences = getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        rune_time = shared_preferences.getString("rune_time", "20");
        stack_time = shared_preferences.getString("stack_time", "20");
        spawn_time = shared_preferences.getString("spawn_time", "15");
        day_night_time = shared_preferences.getString("day_night_time", "30");
        off = getResources().getString(R.string.r_off);

        the_battle_begins_sound = MediaPlayer.create(this, R.raw.the_battle_begins);
        stack_sound = MediaPlayer.create(this, R.raw.stack);
        rune_sound = MediaPlayer.create(this, R.raw.rune);
        day_sound = MediaPlayer.create(this, R.raw.day);
        night_sound = MediaPlayer.create(this, R.raw.night);
        spawn_sound = MediaPlayer.create(this, R.raw.spawn);

        display_time();
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        minus_button_listener();
        plus_button_listener();
        play_pause_button_listener();
        reset_button_listener();

        power_manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wake_lock = power_manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Wake Lock Tag");
        wake_lock.acquire();

        main_activity = new Intent(ReminderActivity.this, MainActivity.class);
    }

    public void minus_button_listener(){
        Button minus_button = (Button) findViewById(R.id.minus_button);
        minus_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        backward_second_logic();
                    }
                }
        );
    }

    public void plus_button_listener(){
        Button plus_button = (Button) findViewById(R.id.plus_button);
        plus_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        forward_second_logic();
                    }
                }
        );
    }

    public void play_pause_button_listener(){
        final ToggleButton play_pause_button = (ToggleButton) findViewById(R.id.play_pause_button);
        final String pause = getResources().getString(R.string.r_pause);
        final String play = getResources().getString(R.string.r_play);
        play_pause_button.setText(pause);
        play_pause_button.setTextOn(play);
        play_pause_button.setTextOff(pause);

        play_pause_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View V) {
                        if (play_pause_button.getText().equals(play)) {
                            timerHandler.removeCallbacks(timerRunnable);
                        } else {
                            startTime = System.currentTimeMillis();
                            timerHandler.postDelayed(timerRunnable, 0);
                        }
                    }
                }
        );
    }

    public void backward_second_logic(){
        if (plus_minus_zero.equals(plus)){
            if (minute == 0 && second == 0) {
                plus_minus_zero = minus;
                second = 1;
            }else if (second == 0){
                second = 59;
                minute--;
            }else{
                second--;
            }
        }else{ // plus_minus_zero.equals(minus)
            if (second == 59){
                second = 0;
                minute++;
            }
            else{
                second++;
            }
        }
        display_time();
    }

    public void forward_second_logic(){
        if (plus_minus_zero.equals(plus)) {
            if (second == 59) {
                second = 0;
                minute++;
            } else {
                second++;
            }
        }else { // plus_minus_zero.equals(minus)
            if (minute != 0 && second == 0) {
                second = 59;
                minute--;
            } else {
                second--;
            }
        }
        display_time();
    }

    public void display_time(){
        String time;
        if (minute == 0 && second == 0){
            time = String.format("%02d : %02d", minute, second);
            the_battle_begins_sound.start();
            plus_minus_zero = plus;
        }else{
            time = String.format(plus_minus_zero + " %02d : %02d", minute, second);
            if (plus_minus_zero.equals(minus)){
                if (rune_time.length() < 3 && minute == 0 && second == Integer.parseInt(rune_time)){
                    rune_sound.start();
                }
            }else{ // plus_minus_zero.equals(plus)
                if (rune_time.length() < 3 && (minute % 2) != 0 && second == 60 - Integer.parseInt(rune_time)){
                    rune_sound.start();
                }
                if (stack_time.length() < 3 && second == 53 - Integer.parseInt(stack_time)){
                    stack_sound.start();
                }
                if (spawn_time.length() < 3 && (second == 30 - Integer.parseInt(spawn_time) || second == 60 - Integer.parseInt(spawn_time))){
                    spawn_sound.start();
                    //fix spawn timing array
                }
                if (day_night_time.length() < 3 && minute != 0 && minute % 7 == 0 && second == 60 - Integer.parseInt(day_night_time)){
                    day_sound.start();
                }else if (day_night_time.length() < 3 && minute != 0 && minute % 3 == 0 && second == 60 - Integer.parseInt(day_night_time)){
                    night_sound.start();
                }
            }
        }

        TextView time_text = (TextView) findViewById(R.id.time_text);
        time_text.setText(time);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(main_activity);
    }

    public void reset_button_listener(){
        Button reset_button = (Button) findViewById(R.id.reset_button);
        reset_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                        startActivity(main_activity);
                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        the_battle_begins_sound.release();
        stack_sound.release();
        rune_sound.release();
        day_sound.release();
        night_sound.release();
        spawn_sound.release();
        timerHandler.removeCallbacks(timerRunnable);
        wake_lock.release();
    }
}