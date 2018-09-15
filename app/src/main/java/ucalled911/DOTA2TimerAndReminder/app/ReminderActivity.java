package ucalled911.DOTA2TimerAndReminder.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import ucalled911.DOTA2TimerAndReminder.R;
import ucalled911.DOTA2TimerAndReminder.library.RepeatListener;

public class ReminderActivity extends AppCompatActivity {
    String TAG = "error";
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
    private Toolbar toolbar;
    private ImageButton forward_button;
    private ImageButton rewind_button;
    private FloatingActionButton play_pause_button;
    private boolean play;

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
        }
        return Integer.parseInt(string_value);
    }


    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        setupToolbar();


        main_activity = new Intent(ReminderActivity.this, MainActivity.class);

        rewind_button_listener();
        forward_button_listener();
        play_pause_button_listener();

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
        display_time();

        play = true;
    }

    public void rewind_button_listener() {
        rewind_button = findViewById(R.id.rewind_button);
        rewind_button.setOnTouchListener(new RepeatListener(800, 500, new OnClickListener() {
            @Override
            public void onClick(View view) {
                backward_second_logic();
            }
        }));
    }

    public void forward_button_listener() {
        forward_button = findViewById(R.id.forward_button);
        forward_button.setOnTouchListener(new RepeatListener(800, 500, new OnClickListener() {
            @Override
            public void onClick(View view) {
                forward_second_logic();
            }
        }));
    }

    public void play_pause_button_listener() {
        play_pause_button = findViewById(R.id.play_pause_button);

        play_pause_button.setOnClickListener(
                new FloatingActionButton.OnClickListener() {
                    public void onClick(View v) {
                        if (play) {
                            timerHandler.removeCallbacks(timerRunnable);
                            play_pause_button.setImageResource(R.mipmap.ic_play);
                            play = false;
                        } else { //pause
                            startTime = System.currentTimeMillis();
                            timerHandler.postDelayed(timerRunnable, 0);
                            play_pause_button.setImageResource(R.mipmap.ic_pause);
                            play = true;
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
        try {
            display_time();
        } catch (IllegalStateException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
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
                second--;
            }
        }
        try {
            display_time();
        } catch (IllegalStateException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public void display_time() {
        String time;
        if (minute == 0 && second == 0) {
            time = String.format("%02d : %02d", minute, second);
            positive_negative = 1;
            plus_minus = "+";
            the_battle_begins_sound.start();
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
                if (rune_time != -1 && (minute % 2 != 0 && second == 60 - rune_time) || (minute % 5 == 4 && second == 60 - rune_time)) {
                    rune_sound.start();
                }
                if (stack_time != -1 && second == 54 - stack_time) {
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
        if (play) {
            play_pause_button.performClick();
        }
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
        timerHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {
        myFinished();
        super.onBackPressed();
        finish(); // calls onDestroy
        startActivity(main_activity);
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
    protected void onDestroy() {
        super.onDestroy();
    }
}