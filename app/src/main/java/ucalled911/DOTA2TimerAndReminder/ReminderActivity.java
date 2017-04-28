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

    private MainActivity access = new MainActivity();
    private int second = access.getSecond();
    private int minute = access.getMinute();
    private long startTime = 0;
    private MediaPlayer the_battle_begins_sound;
    private MediaPlayer stack_sound;
    private MediaPlayer rune_sound;
    private MediaPlayer day_sound;
    private MediaPlayer night_sound;
    private MediaPlayer spawn_sound;
    private int positive_negative;
    private String plus_minus;
    private int rune_time;
    private int stack_time;
    private int spawn_time;
    private int day_night_time;
    private SharedPreferences shared_preferences;

    private Intent main_activity;
    private PowerManager power_manager;
    private PowerManager.WakeLock wake_lock;

    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millisecond = System.currentTimeMillis() - startTime;
            int one_second = (int) (millisecond / 1000);
            if (one_second == 1) {
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

    private int convert(String string_value) {
        if (string_value.equals("OFF")) {
            return -1;
        } else {
            return Integer.parseInt(string_value);
        }
    }

    public void setup() {
        if (access.getPlus_minus_zero().equals("+")) {
            positive_negative = 1;
            plus_minus = "+";
        } else {
            positive_negative = 0;
            plus_minus = "-";
        }
        shared_preferences = getSharedPreferences(getPackageName() + ".user_settings", Context.MODE_PRIVATE);
        rune_time = convert(shared_preferences.getString("rune_time", "20"));
        stack_time = convert(shared_preferences.getString("stack_time", "20"));
        spawn_time = convert(shared_preferences.getString("spawn_time", "10"));
        day_night_time = convert(shared_preferences.getString("day_night_time", "30"));

        the_battle_begins_sound = MediaPlayer.create(this, R.raw.the_battle_begins);
        stack_sound = MediaPlayer.create(this, R.raw.stack);
        rune_sound = MediaPlayer.create(this, R.raw.rune);
        day_sound = MediaPlayer.create(this, R.raw.day);
        night_sound = MediaPlayer.create(this, R.raw.night);
        spawn_sound = MediaPlayer.create(this, R.raw.spawn);

        power_manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wake_lock = power_manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Wake Lock Tag");
        wake_lock.acquire();
        this.setTitle("DOTA 2 Timer & Reminder");

        main_activity = new Intent(ReminderActivity.this, MainActivity.class);

        minus_button_listener();
        plus_button_listener();
        play_pause_button_listener();
        reset_button_listener();

        display_time();
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void minus_button_listener() {
        Button minus_button = (Button) findViewById(R.id.minus_button);
        minus_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        backward_second_logic();
                    }
                }
        );
    }

    public void plus_button_listener() {
        Button plus_button = (Button) findViewById(R.id.plus_button);
        plus_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        forward_second_logic();
                    }
                }
        );
    }

    public void play_pause_button_listener() {
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

    public void backward_second_logic() {
        if (positive_negative == 1) {
            if (minute == 0 && second == 0) {
                positive_negative = 0;
                plus_minus = "-";
                second = 1;
            } else if (second == 0) {
                second = 59;
                minute--;
            } else {
                second--;
            }
        } else { // negative
            if (second == 59) {
                second = 0;
                minute++;
            } else {
                second++;
            }
        }
        display_time();
    }

    public void forward_second_logic() {
        if (positive_negative == 1) {
            if (second == 59) {
                second = 0;
                minute++;
            } else {
                second++;
            }
        } else { // negative
            if (minute != 0 && second == 0) {
                second = 59;
                minute--;
            } else {
                if (minute == 0 && second == 1) {
                    plus_minus = "+";
                }
                second--;
            }
        }
        display_time();
    }

    public void display_time() {
        rune_sound.start();
        String time;
        if (minute == 0 && second == 0) {
            time = String.format("%02d : %02d", minute, second);
            the_battle_begins_sound.start();
            positive_negative = 1;
        } else {
            time = String.format(plus_minus + " %02d : %02d", minute, second);
            if (positive_negative == 0) {
                if (rune_time != -1 && minute == 0 && second == rune_time) {
                    rune_sound.start();
                }
                if (spawn_time != -1 && minute == 0 && second == spawn_time) {
                    spawn_sound.start();
                }
            } else { // positive_negative == 1
                if (rune_time != -1 && minute % 2 != 0 && second == 60 - rune_time) {
                    rune_sound.start();
                }
                if (stack_time != -1 && minute % 2 == 0 && second == 54 - stack_time) {
                    stack_sound.start();
                }
                if (spawn_time != -1 && (second == 30 - spawn_time || second == 60 - spawn_time)) {
                    spawn_sound.start();
                }
                if (day_night_time != -1) {
                    if (minute >= 7 && (minute - 7) % 8 == 0 && second == 60 - day_night_time) { //7 + 8n
                        day_sound.start();
                    }
                    if (minute >= 3 && (minute - 3) % 8 == 0 && second == 60 - day_night_time) { // 3 + 8n
                        night_sound.start();
                    }
                }

            }
        }

        TextView time_text = (TextView) findViewById(R.id.time_text);
        time_text.setText(time);
    }

    public void myFinished() {
        final String pause = getResources().getString(R.string.r_pause);
        final ToggleButton play_pause_button = (ToggleButton) findViewById(R.id.play_pause_button);
        if (play_pause_button.getText().equals(pause)) {
            play_pause_button.performClick();
        }
        timerHandler.removeCallbacksAndMessages(null);
        spawn_sound.stop();
        stack_sound.stop();
        rune_sound.stop();
        day_sound.stop();
        night_sound.stop();
        the_battle_begins_sound.stop();

        spawn_sound.release();
        stack_sound.release();
        rune_sound.release();
        day_sound.release();
        night_sound.release();
        the_battle_begins_sound.release();
        wake_lock.release();
    }

    @Override
    public void onBackPressed() {
        myFinished();
        super.onBackPressed();
        finish(); // calls onDestroy
        startActivity(main_activity);
    }

    public void reset_button_listener() {
        Button reset_button = (Button) findViewById(R.id.reset_button);
        reset_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        myFinished();
                        finish(); // calls onDestroy
                        startActivity(main_activity);
                    }
                }
        );
    }

    // leave the audio running while in background
    /*
    @Override
    protected void onPause() {
        final ToggleButton play_pause_button = (ToggleButton) findViewById(R.id.play_pause_button);
        play_pause_button.performClick();
        super.onPause();
    }
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}